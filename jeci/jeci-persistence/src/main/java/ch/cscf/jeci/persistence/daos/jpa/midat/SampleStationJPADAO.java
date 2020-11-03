package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.ThesaurusCodes;
import ch.cscf.jeci.domain.dto.midat.SampleSearchParameters;
import ch.cscf.jeci.domain.entities.midat.sample.*;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.ThesaurusValueDAO;
import ch.cscf.jeci.persistence.daos.interfaces.midat.SampleDAO;
import ch.cscf.jeci.persistence.daos.interfaces.midat.SampleStationDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.util.GeometricShapeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: henryp
 */
@Repository
public class SampleStationJPADAO extends GenericJpaEntityDAO<SampleStation> implements SampleStationDAO {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final int SRID_CH03 = 21781;
    private static final String MIDATSTITMTY = "MIDATSTITMTY";
    private static final String WATERCOURSE = "WATERCOURSE";
    @Autowired
    private ThesaurusValueDAO thesaurusValueDAO;
    private GeometryFactory geometryFactory = new GeometryFactory();
    @Autowired
    private JPAQueryFactory queryFactory;
    @Autowired
    private SampleDAO sampleDAO;

    @Override
    public String getWatercourseForStation(Long stationId, Long languageId) {
        return getItemForStationAndType(stationId, languageId, ThesaurusCodes.MIDATHDITMTY_WATERCOURSE).getValue();
    }

    @Override
    public String getLocalityForStation(Long stationId, Long languageId) {
        return getItemForStationAndType(stationId, languageId, ThesaurusCodes.MIDATHDITMTY_LOCALITY).getValue();
    }

    /*
    @Override
    public Set<SampleStation> findPublishedStations(Long userId, boolean isAppManager, boolean filterForEdition) {

        List stations;
        StringBuffer query = new StringBuffer(1024);

        query.append("select station from SampleStation station left join station.samples sample  ");

        if (!isAppManager) {
            if (filterForEdition) {
                query.append(" where sample.creationUser.id=:userId");
            } else {
                query.append(" where sample.published=true or sample.creationUser.id=:userId");
            }

            stations = getEm().createQuery(query.toString()).setParameter("userId", userId).getResultList();
        } else {
            stations = getEm().createQuery(query.toString()).getResultList();
        }

        //eliminate dupes due to join
        return new HashSet<>(stations);
    }

     */


    @Override
    public List<StationSamplesView> findPublishedStations(Long userId, boolean isAppManager, boolean filterForEdition) {

        List stations;
        StringBuffer query = new StringBuffer(1024);

        query.append("select stationSamplesView from StationSamplesView stationSamplesView  ");
        query.append("  where  exists (select 1 from Sample sample  where  stationSamplesView.sampleId=sample.id ");

        if (!isAppManager) {
            if (filterForEdition) {
                query.append(" and  sample.creationUser.id=:userId ) ");
            } else {
                query.append(" and (sample.published=true or sample.creationUser.id=:userId)  ) ");
            }
            query.append(" order by stationSamplesView.sampleDate desc ");
            stations = getEm().createQuery(query.toString()).setParameter("userId", userId).getResultList();
        } else {
            query.append(" ) order by stationSamplesView.sampleDate desc ");
            stations = getEm().createQuery(query.toString()).getResultList();
        }

        //eliminate dupes due to join
        return stations;
    }


    @Override
    public Set<SampleStation> findStationsByIds(List<Long>sIds,Long userId,Collection<String> groupNames, boolean filterForEdition) {

        List stations;
        StringBuffer query = new StringBuffer(1024);

        query.append("select station from SampleStation station left join station.samples sample left join sample.groups grp   ");
            if (filterForEdition) {
                query.append(" where sample.creationUser.id=:userId ");
            } else {
                groupNames.add("PUBLIC");
                query.append(" where (sample.published=true or sample.creationUser.id=:userId )");
            }
            query.append(" and station.id in :sIds  and grp.name in :groupNames");
            stations = getEm().createQuery(query.toString())
                    .setParameter("userId", userId)
                    .setParameter("sIds",sIds)
                    .setParameter("groupNames", groupNames)
                    .getResultList();


        //eliminate dupes due to join
        return new HashSet<>(stations);
    }



    @Override
    public Set<StationSamplesView> findStationSamplesViewByIds(List<Long>sIds,Long userId,Collection<String> groupNames, boolean filterForEdition) {

        StringBuffer query = new StringBuffer(1024);

        query.append(" select stationSamplesView from StationSamplesView stationSamplesView  ");
        query.append(" where  exists ( select 1 from Sample sample left join sample.groups grp ");
        query.append(" where stationSamplesView.sampleId= sample.id  ");


        // edit mode
        if (filterForEdition) {
            // show him only his samples
            query.append(" and  sample.creationUser.id=:userId  and grp.name in :groupNames ) ");
        } else {
            // read mode
            // show him only his samples + the published samples
            query.append(" and (sample.published=true or sample.creationUser.id=:userId)  and grp.name in :groupNames )");
        }
        query.append(" and  stationSamplesView.stationId in :sIds");
        query.append(" order by stationSamplesView.sampleDate desc ");

        List stations = getEm().createQuery(query.toString())
                .setParameter("userId", userId)
                .setParameter("groupNames", groupNames)
                .setParameter("sIds",sIds)
                .getResultList();

        //eliminate dupes due to join
        return new HashSet<>(stations);
    }



    /*
    @Override
    public Set<SampleStation> findPublishedStationsBelongingToGroups(Long userId, Collection<String> groupNames, boolean filterForEdition) {


        StringBuffer query = new StringBuffer(1024);

        query.append(" select station from SampleStation station left join station.samples sample left join sample.groups grp  ");

        // edit mode
        if (filterForEdition) {
            // show him only his samples
            query.append(" where sample.creationUser.id=:userId and grp.name in :groupNames ");
        } else {
            // read mode
            // show him only his samples + the published samples
            query.append(" where  (sample.published=true or sample.creationUser.id=:userId) and grp.name in :groupNames");
        }

        List stations = getEm().createQuery(query.toString())
                .setParameter("userId", userId)
                .setParameter("groupNames", groupNames)
                .getResultList();

        //eliminate dupes due to join
        return new HashSet<>(stations);
    }
*/

    @Override
    public List<StationSamplesView> findPublishedStationsBelongingToGroups(Long userId, Collection<String> groupNames, boolean filterForEdition) {

        StringBuffer query = new StringBuffer(1024);

        query.append(" select stationSamplesView from StationSamplesView stationSamplesView  ");
        query.append(" where  exists ( select 1 from Sample sample left join sample.groups grp ");
        query.append(" where stationSamplesView.sampleId= sample.id  ");


        // edit mode
        if (filterForEdition) {
            // show him only his samples
            query.append(" and  sample.creationUser.id=:userId   and grp.name in :groupNames ) ");
        } else {
            // read mode
            // show him only his samples + the published samples
            query.append(" and (sample.published=true or sample.creationUser.id=:userId)   and grp.name in :groupNames )");
        }
        query.append(" order by stationSamplesView.sampleDate desc ");

        List stations = getEm().createQuery(query.toString())
                .setParameter("userId", userId)
                .setParameter("groupNames", groupNames)
                .getResultList();

        //eliminate dupes due to join
        return stations;
    }

    @Override
    public List<SampleStationItemView> findSampleStationItems() {
        List items = getEm()
                .createQuery("select sampleStationItemView from SampleStationItemView sampleStationItemView ")
                .getResultList();

        return  items;
    }


    @Override
    public Set<SampleStation> findAllStations() {
        long start = System.currentTimeMillis();

        List stations = getEm().createQuery("select station from SampleStation station ").getResultList();

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        logger.info("findAllStations elapsed time:"+timeElapsed);

        //eliminate dupes due to join
        return new HashSet<>(stations);
    }

    @Override
    public SampleStationItem getItemForStationAndType(Long stationId, Long languageId, String itemTypeCode) {

        Long stationItemTypeId = thesaurusValueDAO.getValueId(ThesaurusCodes.REALM_MIDATSTITMTY, itemTypeCode);

        List<SampleStationItem> items = getEm().createQuery("select item from SampleStationItem item where item.station.id=:stationId and item.typeId=:stationItemTypeId", SampleStationItem.class)
                .setParameter("stationId", stationId)
                .setParameter("stationItemTypeId", stationItemTypeId)
                .getResultList();


        if (items.isEmpty()) {
            return null;
        }

        for (SampleStationItem item : items) {
            if (item.getLanguageId().equals(languageId)) {
                return item;
            }
        }

        //if the item does not exist in the required language we return the first item of the required type
        return items.get(0);
    }

    private Geometry buildRectangle(Double west, Double east, Double north, Double south) {
        Geometry rectangle = geometryFactory.createPolygon(new Coordinate[]{
                        new Coordinate(west, north),
                        new Coordinate(east, north),
                        new Coordinate(east, south),
                        new Coordinate(west, south),
                        new Coordinate(west, north)
                }
        );

        rectangle.setSRID(SRID_CH03);
        return rectangle;
    }

    private Geometry createCircle(double raduis, double x, double y) {
        GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
        //shapeFactory.setNumPoints(32); //default is 100
        shapeFactory.setCentre(new Coordinate(x, y));//there are your coordinates
        shapeFactory.setSize(raduis * 2);//this is how you set the radius
        Geometry cercle = shapeFactory.createCircle();
        cercle.setSRID(SRID_CH03);
        return cercle;
    }

    @Override
    public List<Long> findStationsIdsInsideRectangle(Double west, Double east, Double north, Double south, Long userId, boolean isAppManager, boolean editionMode) {

        Geometry rectangle = buildRectangle(west, east, north, south);
        List<Long> result;
        if(!isAppManager){
            StringBuffer query = new StringBuffer(1024);
            query.append(" select station.id from SampleStation station left join station.samples sample ");

            if(editionMode){
                query.append(" where within(station.coordinates, :rectangle ) = true and sample.creationUser.id=:userId");
            }else{
                query.append(" where within(station.coordinates, :rectangle ) and (sample.published=true or sample.creationUser.id=:userId)");
            }
            result = getEm().createQuery(query.toString()).setParameter("rectangle", rectangle).setParameter("userId", userId).getResultList();
        }else{
            Query q = getEm().createQuery("select station.id from SampleStation station where within(station.coordinates, :rectangle ) = true", Long.class);
            q.setParameter("rectangle", rectangle);
            result = q.getResultList();
        }

        return result;
    }

    @Override
    public List<Long> findStationsIdsInsideRectangleAndBelongingToGroups(Double west, Double east, Double north, Double south, Collection<String> groupNames, Long userId, boolean editionMode) {

        StringBuffer query = new StringBuffer(1024);
        List<Long> result;

        Geometry rectangle = buildRectangle(west, east, north, south);

        query.append(" select station.id from SampleStation station " +
                " left join station.samples sample " +
                " left join sample.groups grp " +
                " where grp.name in :groupNames and within(station.coordinates, :rectangle ) = true ");

        if(editionMode){
            query.append(" and sample.creationUser.id=:userId ");
        }else{
            query.append(" and (sample.published=true or sample.creationUser.id=:userId)");
        }

        Query q = getEm().createQuery(query.toString(), Long.class);
        q.setParameter("rectangle", rectangle).setParameter("groupNames", groupNames).setParameter("userId", userId);

        result = q.getResultList();

        return result;

    }

    @Override
    public List<SampleStation> findStationsInsideCercle(SampleStation s, Double raduis) {
        /*
          findStationsInsideCercle finds the stations under the below conditions:
           1- station should be inside the cercle : RADUIS's cercle is a constant
           2- station principl institution should be the same as the smaple principal institution
         */
        Geometry cercle = createCircle(raduis, s.getCoordinates().getX(), s.getCoordinates().getY());
        Query q = getEm().createQuery("select station from SampleStation station where within(station.coordinates, :cercle ) = true ", SampleStation.class);
        q.setParameter("cercle", cercle);
        /** q.setParameter("principalInstitutionId", s.getStationPrincipalInstitutionId());**/
        List<SampleStation> result = q.getResultList();
        return result;
    }

    @Override
    public Integer unlinkStationsFromParentStationById(Long parentId) {
        Query query = getEm().createQuery("update SampleStation station set station.parent = null where station.parent.id=:parentId");
        query.setParameter("parentId", new Long(parentId));
        int result = query.executeUpdate();
        return result;
    }

    @Override
    public Integer linkStationsToParentStationById(List<Long> sIds, Long parentId) {
        Query query = getEm().createQuery("update SampleStation station set station.parent.id =:parentId  where station.id IN (:sIds)");
        query.setParameter("parentId", new Long(parentId));
        query.setParameter("sIds", sIds);
        int result = query.executeUpdate();
        return result;
    }

    //findStationNumbersLike
    @Override
    public List<String> findStationNumbersLike(String query, Long userId) {
        return findStationNumbersQuery(query, userId);
    }

    @Override
    public List<String> findStationNumbersLikeInGroups(String query, Long userId, Set<Long> groupIds) {
        return findStationNumbersQueryInGroups(query, userId, groupIds);
    }

    //findGewissNumbersLike
    @Override
    public List<String> findGewissNumbersLike(String query, Long userId) {
        return findGewissNumbersQuery(query, userId);
    }

    @Override
    public List<String> findGewissNumbersLikeInGroups(String query, Long userId, Set<Long> groupIds) {
        return findGewissNumbersQueryInGroups(query, userId, groupIds);
    }

    private List<String> findStationNumbersQuery(String query, Long userId) {
        //the query converts to us ascii in sortOrder to be accent-insensitive
        return
                getEm()
                        .createQuery("select distinct station.stationNumber from SampleStation station left join station.samples sample left join sample.groups grp  " +
                                        "where (sample.published=true or sample.creationUser.id=:userId)  " +
                                        "and (lower(station.stationNumber) like lower(convert(CONCAT('%',:query,'%'), 'US7ASCII')))",
                                String.class)
                        .setParameter("userId", userId)
                        .setParameter("query", query)
                        .getResultList();
    }

    private List<String> findStationNumbersQueryInGroups(String query, Long userId, Set<Long> groupIds) {
        //the query converts to us ascii in sortOrder to be accent-insensitive
        return
                getEm()
                        .createQuery(" select distinct station.stationNumber from SampleStation station left join station.samples sample left join sample.groups grp  " +
                                        " where ((sample.published=true or sample.creationUser.id=:userId) and grp.id in (:groupIds)) " +
                                        " and (lower(station.stationNumber) like lower(convert(CONCAT('%',:query,'%'), 'US7ASCII')))",
                                String.class)
                        .setParameter("userId", userId)
                        .setParameter("groupIds", groupIds)
                        .setParameter("query", query)
                        .getResultList();
    }

    //gewiss Number
    private List<String> findGewissNumbersQuery(String query, Long userId) {
        //the query converts to us ascii in sortOrder to be accent-insensitive
        return
                getEm()
                        .createQuery("select distinct station.stationGewissNumber from SampleStation station left join station.samples sample left join sample.groups grp  " +
                                        "where (sample.published=true or sample.creationUser.id=:userId)  " +
                                        " and (lower(station.stationGewissNumber) like lower(convert(CONCAT('%',:query,'%'), 'US7ASCII')))",
                                String.class)
                        .setParameter("userId", userId)
                        .setParameter("query", query)
                        .getResultList();
    }

    private List<String> findGewissNumbersQueryInGroups(String query, Long userId, Set<Long> groupIds) {
        //the query converts to us ascii in sortOrder to be accent-insensitive
        return
                getEm()
                        .createQuery(" select distinct station.stationGewissNumber from SampleStation station left join station.samples sample left join sample.groups grp  " +
                                        " where ((sample.published=true or sample.creationUser.id=:userId) and grp.id in (:groupIds)) " +
                                        " and (lower(station.stationGewissNumber) like lower(convert(CONCAT('%',:query,'%'), 'US7ASCII')))",
                                String.class)
                        .setParameter("userId", userId)
                        .setParameter("groupIds", groupIds)
                        .setParameter("query", query)
                        .getResultList();
    }

    /**
     * filter implementation
     **/


    @Override
    public List<StationSamplesView> findFilteredPublishedStations(Long userId, SampleSearchParameters parameters, boolean hasFilterExceptDate, boolean isAppManager, boolean filterForEdition) {
        return hasFilterExceptDate ? searchStations(parameters) : findPublishedStations(userId, isAppManager, filterForEdition);
    }

    @Override
    public List<StationSamplesView> findFilteredPublishedStationsBelongingToGroups(Long userId, Collection<String> groupNames, SampleSearchParameters parameters, boolean hasFilterExceptDate, boolean filterForEdition) {
        return hasFilterExceptDate ? searchStations(parameters) : findPublishedStationsBelongingToGroups(userId, groupNames, filterForEdition);
    }

    /*
    private Set<SampleStation> searchStations(SampleSearchParameters parameters) {

        StationQueryBuilder stationQueryBuilder = new StationQueryBuilder()
                .setSortOrder(SortOrder.asc)
                .setOrderBy("creationDate");//always sorted by date

        List<Long> stationsIds = sampleDAO.searchSamplesStations(parameters);

        return new HashSet<>(stationQueryBuilder.executeSearchQuery(stationsIds));
    }

     */

    private List<StationSamplesView> searchStations(SampleSearchParameters parameters) {
        List<Long> stationsIds = sampleDAO.searchSamplesStations(parameters);
        StationSamplesQueryBuilder stationQueryBuilder = new StationSamplesQueryBuilder();
        return stationQueryBuilder.executeSearchQuery(stationsIds);
    }

    /**
     * This private helper class used to get the stations id(s) from the samples result of the entered search
     */
    private class StationSamplesQueryBuilder {

        private JPAQuery resultQuery;

        private QStationSamplesView station = QStationSamplesView.stationSamplesView;

        public StationSamplesQueryBuilder() {
        }

        public List<StationSamplesView> executeSearchQuery(List<Long> stationsIds) {
            buildResultQuery(stationsIds);
            return resultQuery.fetch();
        }

        private void buildResultQuery(List<Long> stationsIds) {
            resultQuery = SampleStationJPADAO.this.queryFactory.from(station);
            resultQuery.where(buildIdsInPredicate(stationsIds));
        }

        private BooleanExpression buildIdsInPredicate(List<Long> stationsIds) {
            //the in(...) statement is limited to 1000 values
            //so if we have more we need to split it
            if (stationsIds.size() <= 1000) {
                return station.stationId.in(stationsIds);
            } else {
                List<List<Long>> lists = Lists.partition(stationsIds, 1000);

                BooleanExpression predicate = station.stationId.in(lists.get(0));

                for (int i = 1; i < lists.size(); i++) {
                    predicate = predicate.or(station.stationId.in(lists.get(i)));
                }
                return predicate;
            }
        }

        private class StationQueryBuilder {

            private String orderBy = "creationDate";
            private SortOrder sortOrder = SortOrder.asc;
            private JPAQuery resultQuery;

            private QSampleStation station = QSampleStation.sampleStation;

            public StationQueryBuilder() {
            }

            public List<SampleStation> executeSearchQuery(List<Long> stationsIds) {
                buildResultQuery(stationsIds);
                return resultQuery.fetch();
            }

            private void buildResultQuery(List<Long> stationsIds) {
                resultQuery = SampleStationJPADAO.this.queryFactory.from(station);
                resultQuery.where(buildIdsInPredicate(stationsIds));
            }

            private BooleanExpression buildIdsInPredicate(List<Long> stationsIds) {
                //the in(...) statement is limited to 1000 values
                //so if we have more we need to split it
                if (stationsIds.size() <= 1000) {
                    return station.id.in(stationsIds);
                } else {
                    List<List<Long>> lists = Lists.partition(stationsIds, 1000);

                    BooleanExpression predicate = station.id.in(lists.get(0));

                    for (int i = 1; i < lists.size(); i++) {
                        predicate = predicate.or(station.id.in(lists.get(i)));
                    }
                    return predicate;
                }
            }

            public StationQueryBuilder setOrderBy(String orderBy) {
                this.orderBy = orderBy;
                return this;
            }

            public StationQueryBuilder setSortOrder(SortOrder sortOrder) {
                this.sortOrder = sortOrder;
                return this;
            }
        }
    }}
