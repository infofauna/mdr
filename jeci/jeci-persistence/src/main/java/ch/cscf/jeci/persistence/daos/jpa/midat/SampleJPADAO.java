package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.ThesaurusCodes;
import ch.cscf.jeci.domain.dto.midat.QSearchResultDTO;
import ch.cscf.jeci.domain.dto.midat.SampleSearchParameters;
import ch.cscf.jeci.domain.dto.midat.SearchResultDTO;
import ch.cscf.jeci.domain.entities.base.QInstitution;
import ch.cscf.jeci.domain.entities.base.QPerson;
import ch.cscf.jeci.domain.entities.base.QProject;
import ch.cscf.jeci.domain.entities.midat.*;
import ch.cscf.jeci.domain.entities.midat.sample.*;
import ch.cscf.jeci.domain.entities.security.QGroup;
import ch.cscf.jeci.domain.entities.systematics.QTaxon;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;
import ch.cscf.jeci.persistence.daos.interfaces.midat.SampleDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import ch.cscf.jeci.persistence.daos.thesaurus.interfaces.ThesaurusReadOnlyService;
import ch.cscf.jeci.persistence.querydsl.QueryDslTools;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: henryp
 */
@Repository
public class SampleJPADAO extends GenericJpaEntityDAO<Sample> implements SampleDAO {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ThesaurusReadOnlyService thesaurus;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private SampleStationJPADAO sampleStationJPADAO;

    private Long stationItemTypeWatercourse;
    private Long stationItemTypeLocality;
    private Long protocolTypeLab;
    private Long protocolTypeMass;
    private Long sampleAttributeTypeOperator;
    private Long sampleAttributeTypeDeterminator;
    private Long sampleAttributeTypeWatercourse;
    private Long sampleAttributeTypeLocality;
    private Long sampleAttributeTypeCalledPlace;

    private Long sampleItemTypeWatercourse;
    private Long sampleItemTypeLocality;

    //the entity paths
    final QSample sample = QSample.sample;
    final QSampleStation station = QSampleStation.sampleStation;


    final QGroup group = QGroup.group;

    final QSampleStationItem watercourseSearch = new QSampleStationItem("watercourseSearch");
    final QSampleAttribute   watercourseSearch2 = new QSampleAttribute("watercourseSearch2");

    final QSampleStationItem localitySearch  = new QSampleStationItem("localitySearch");
    final QSampleAttribute   localitySearch2 = new QSampleAttribute("localitySearch2");

    final QSampleAttribute   watercourseOrLocalitySearch = new QSampleAttribute("watercourseOrLocalitySearch");

    final QSampleAttribute watercourse = new QSampleAttribute("watercourse");
    final QSampleAttribute watercourseIt = new QSampleAttribute("watercourseIt");
    final QSampleAttribute watercourseFr = new QSampleAttribute("watercourseFr");
    final QSampleAttribute watercourseDe = new QSampleAttribute("watercourseDe");

    final QSampleAttribute locality = new QSampleAttribute("locality");
    final QSampleAttribute localityFr = new QSampleAttribute("localityFr");
    final QSampleAttribute localityDe = new QSampleAttribute("localityDe");
    final QSampleAttribute localityIt = new QSampleAttribute("localityIt");

    final QSampleAttribute calledPlace = new QSampleAttribute("calledPlace");


    final QSampleAttribute sampleStationCanton = new QSampleAttribute("canton");


    final QSampleAttribute sampleAttributeOperator = new QSampleAttribute("sampleAttributeOperator");
    final QSampleAttribute sampleAttributeDeterminator = new QSampleAttribute("sampleAttributeDeterminator");
    final QSampleAttribute sampleAttributeOperatorX = new QSampleAttribute("sampleAttributeOperatorX");
    final QSampleAttribute sampleAttributeDeterminatorX = new QSampleAttribute("sampleAttributeDeterminatorX");

    final QPerson operator = new QPerson("operator");
    final QPerson determinator = new QPerson("determinator");

    final QLabRecordField labRecordField = new QLabRecordField("labRecordField");

    final QInstitution mandataryInstitution = new QInstitution("mandataryInstitution");
    final QInstitution principalInstitution = new QInstitution("principalInstitution");

    final QLabProtocolFieldMapping protocolFieldMapping = new QLabProtocolFieldMapping("protocolFieldMapping");
    final QTaxon labRecordFieldTaxon = new QTaxon("labRecordFieldTaxon");


    final QProject project = QProject.project;


    private final List<Expression> expressionsForExport = Lists.newArrayList(sample.id,
            sample.sampleProtocolType.code,
            sample.sampleDate, sample.sampleDateDay, sample.sampleDateMonth, sample.sampleDateYear,
            sample.ibchIndexValue, sample.makroIndexValue, sample.spearIndexValue,
            sample.ibchLegendVersionId, sample.makroLegendVersionId, sample.spearLegendVersionId,

            project.designation,
            station.stationNumber, station.coordinates,
            watercourse.value, calledPlace.value, locality.value,
            labRecordField.taxon.id, labRecordField.frequency, labRecordField.modifiedFrequency, labRecordField.sampleNumber, labRecordField.stadium,labRecordField.comment,
            protocolFieldMapping.ibchTaxon, protocolFieldMapping.ibchDeterminingGroup,
            sampleAttributeDeterminatorX.value, sampleAttributeOperatorX.value, sampleAttributeDeterminator.value, sampleAttributeOperator.value,
            principalInstitution.name, mandataryInstitution.name, sample.published,station.stationCanton,station.stationGewissNumber, station.id,sample.sampleCommentOther,station.coordinateZ,
            sample.valeurGi,// GI niveau/taxon(s)
            sample.taxonIndicateur,//GI niveau/taxon(s)
            sample.giFinal, //GI_2019
            sample.valuerVt, //VT_2019
            sample.ibchRobust, //IBCH_2019_R
            sample.taxonSumFamily, // somme taxons IBCH
            sample.ephemeropteraCounter,// somme familles Ephemeroptera
            sample.tricopteraCounter,// familles Plecoptera
            sample.plecopteraCounter,//familles Trichoptera
            sample.sommeAbon,//Abondances
            sample.ibchQ, // Régime IBCH-Q
            sample.valeurCorrection //Valeur de correction VC
    );


    @Override
    public List<SearchResultDTO> search(SampleSearchParameters parameters, String orderBy, SortOrder sortOrder, Page page) {

        initValuesIdFields();

        SampleQueryBuilder sampleQueryBuilder = new SampleQueryBuilder()
                .setSortOrder(sortOrder)
                .setOrderBy(orderBy)
                .setPage(page);

        sampleQueryBuilder.setRestrictions(buildSearchRestrictionsPredicate(parameters, true));

        return sampleQueryBuilder.executeSearchQuery();
    }


    @Override
    public List<Long> searchSamplesStations(SampleSearchParameters parameters) {
        initValuesIdFields();

        SampleQueryBuilder sampleQueryBuilder = new SampleQueryBuilder()
                .setSortOrder(SortOrder.asc)
                .setOrderBy("sampleDate") //always sorted by date
                .setPage(null); //no paging

        sampleQueryBuilder.setRestrictions(buildSearchRestrictionsPredicate(parameters, false));

        return sampleQueryBuilder.findSamplesStationsIds();
    }

    @Override
    public List<Map<String, Object>> searchForExport(SampleSearchParameters parameters) {

        initValuesIdFields();

        SampleQueryBuilder sampleQueryBuilder = new SampleQueryBuilder()
                .setSortOrder(SortOrder.asc)
                .setOrderBy("sampleDate") //always sorted by date
                .setPage(null); //no paging

        sampleQueryBuilder.setRestrictions(buildSearchRestrictionsPredicate(parameters, true));

        List<Tuple> tuples = sampleQueryBuilder.executeExportQuery();

        //Convert the list of tuples to a Map, containing the needed expressionss
        return QueryDslTools.tuplesToMaps(tuples, expressionsForExport);
    }

    @Override
    public List<SearchResultDTO> searchByStations(List<Long> stationIds, Long userId, String orderBy, SortOrder sortOrder, Page page, boolean isAppManager, boolean isNationalContributor, boolean editionMode) {
        initValuesIdFields();

        return
                new SampleQueryBuilder()
                        .setRestrictions(buildSearchByStationRestrictionsPredicate(stationIds, userId, isAppManager, isNationalContributor, editionMode))
                        .setPage(page)
                        .setOrderBy(orderBy)
                        .setSortOrder(sortOrder)
                        .executeSearchQuery();
    }

    @Override
    public List<Map<String, Object>> searchByStationsForExport(List<Long> stationIds, Long creationUserId,boolean isAppManager,boolean isNationalContributor, boolean editionMode) {

        initValuesIdFields();

        SampleQueryBuilder sampleQueryBuilder = new SampleQueryBuilder()
                .setSortOrder(SortOrder.asc)
                .setOrderBy("sampleDate") //always sorted by date
                .setPage(null) //no paging
                .setRestrictions(buildSearchByStationRestrictionsPredicate(stationIds, creationUserId, isAppManager,isNationalContributor,  editionMode));

        List<Tuple> tuples = sampleQueryBuilder.executeExportQuery();

        return QueryDslTools.tuplesToMaps(tuples, expressionsForExport);
    }

    private Predicate buildSearchRestrictionsPredicate(SampleSearchParameters parameters, boolean dateRestrictions) {
        //Builder to build the search criteria predicate
        BooleanBuilder predicateBuilder = new BooleanBuilder();

        //watercourse restriction
        if (parameters.getWatercourse() != null && parameters.getWatercourse().length() > 0) {
            String waterCourseName = prepareStringForLikeSearch(parameters.getWatercourse());
            /**
            predicateBuilder.and(
                    watercourseSearch.value.toLowerCase().like(waterCourseName)
            );**/

            predicateBuilder.and(
                    watercourseSearch2.value.toLowerCase().like(waterCourseName)
            );
        }

        //locality restriction
        if (parameters.getLocality() != null && parameters.getLocality().length() > 0) {
            String localityName = prepareStringForLikeSearch(parameters.getLocality());
            /*
            predicateBuilder.and(
                    localitySearch.value.toLowerCase().like(localityName)
            );*/


            predicateBuilder.and(
                    localitySearch2.value.toLowerCase().like(localityName)
            );
        }

        //dates restrictions if
        if (dateRestrictions) {
            if (parameters.getMinDate() != null) {
                Date oneSecondBeforeMinDate = DateUtils.truncate(parameters.getMinDate(), Calendar.DAY_OF_MONTH);
                oneSecondBeforeMinDate = DateUtils.addSeconds(oneSecondBeforeMinDate, -1);
                predicateBuilder.and(
                        sample.sampleDate.after(oneSecondBeforeMinDate)
                );
            }
            if (parameters.getMaxDate() != null) {
                Date oneDayAfterMaxDate = DateUtils.truncate(parameters.getMaxDate(), Calendar.DAY_OF_MONTH);
                oneDayAfterMaxDate = DateUtils.addDays(oneDayAfterMaxDate, 1);
                predicateBuilder.and(
                        sample.sampleDate.before(oneDayAfterMaxDate)
                );
            }
        }

        //index restrictions
        if (parameters.getIndexType() != null && (parameters.getMinBioIndex() != null || parameters.getMaxBioIndex() != null)) { //we need at least index type and one of min/max
            NumberPath p = null;

            switch (parameters.getIndexType()) {
                case ThesaurusCodes.MIDATINDICE_IBCH:
                    p = sample.ibchIndexValue;
                    break;
                case ThesaurusCodes.MIDATINDICE_MAKROINDEX:
                    p = sample.makroIndexValue;
                    break;
                case ThesaurusCodes.MIDATINDICE_SPEARINDEX:
                    p = sample.spearIndexValue;
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognised index type :" + parameters.getIndexType());
            }

            if (parameters.getMinBioIndex() != null) {
                predicateBuilder.and(
                        p.goe(parameters.getMinBioIndex())
                );
            }

            if (parameters.getMaxBioIndex() != null) {
                predicateBuilder.and(
                        p.loe(parameters.getMaxBioIndex())
                );
            }

        }


            //published restriction
            if (parameters.getPublished() == null) {
                if(parameters.isAppManager() && parameters.isNationalContributor()){
                    // when  the publish restriction is not selected don't add any restriction on the sample status
                }else{
                    predicateBuilder.and(
                            sample.published.eq(true).or(sample.creationUser.id.eq(parameters.getCreationUserId()))
                    );
                }

            } else {

                predicateBuilder.and(
                        sample.published.eq(parameters.getPublished())
                );

                if (!parameters.getPublished()) {
                    if(parameters.isAppManager() && parameters.isNationalContributor()){
                        // when  the publish restriction is set to unpublished  show all the samples
                    }else{
                        predicateBuilder.and(sample.creationUser.id.eq(parameters.getCreationUserId()));
                    }

                }
            }

        //group restrictions
        //selected group is always more restrictive than user group.
        // We assume that selected group will always be a group that user is part of. Check is made in service method.

            if (parameters.getSelectedGroup() != null) {
                predicateBuilder.and(
                        group.name.eq(parameters.getSelectedGroup())

                );
            }
            if (parameters.isFilterByUserGroups()) {
                predicateBuilder.and(
                        group.name.in(parameters.getUserGroups())
                );
            }

            if(parameters.isEditionMode()){
                if(!parameters.isAppManager() || !parameters.isNationalContributor()){
                    Long creationUserId = parameters.getCreationUserId();
                    predicateBuilder.and(
                            sample.creationUser.id.eq(creationUserId)
                    );

                }
            }


        //restrection StationNumber
        if (parameters.getStationNumber() != null && parameters.getStationNumber().length() > 0) {
            String stationNumber = prepareStringForLikeSearch(parameters.getStationNumber());
            predicateBuilder.and(
                    station.stationNumber.toLowerCase().like(stationNumber)
            );
        }

        //restrection GewissNumber
        if (parameters.getGewissNumber() != null && parameters.getGewissNumber().length() > 0) {
            String gewissNumber = prepareStringForLikeSearch(parameters.getGewissNumber());
            predicateBuilder.and(
                    station.stationGewissNumber.toLowerCase().like(gewissNumber)
            );
        }

        //restrection project
        if (parameters.getProject() != null && parameters.getProject().length() > 0) {
            String project = prepareStringForLikeSearch(parameters.getProject());
            predicateBuilder.and(
                    sample.sampleProject.designation.toLowerCase().like(project)
            );
        }

        //restriction principal institution
        if (parameters.getPrincipalInstitution() != null && parameters.getPrincipalInstitution().length() > 0) {
            String pInstitution = prepareStringForLikeSearch(parameters.getPrincipalInstitution());
            predicateBuilder.and(
                    sample.principalInstitution.name.toLowerCase().like(pInstitution)
            );
        }

        //restriction protocoltye
        if (parameters.getSelectedProtocolType() != null && parameters.getSelectedProtocolType().length() > 0) {
            predicateBuilder.and(
                    sample.sampleProtocolType.code.eq(parameters.getSelectedProtocolType())
            );
        }

        //if there was no restrictions at all add a restriction that is always true
        if (!predicateBuilder.hasValue()) {
            predicateBuilder.and(sample.id.isNotNull()); //always true
        }

        return predicateBuilder.getValue();
    }

    private Predicate buildSearchByStationRestrictionsPredicate(List<Long> stationIds, Long creationUserId, boolean isAppManager, boolean isNationalContributor,  boolean editionMode) {
        BooleanBuilder predicateBuilder = new BooleanBuilder();

        predicateBuilder.and(buildStationIdsInPredicate(stationIds));


        if(!isAppManager || !isNationalContributor){
            if(editionMode){
                predicateBuilder.and(
                        sample.creationUser.id.eq(creationUserId)
                );
            }else{
                predicateBuilder.and(
                        sample.published.eq(true).or(sample.creationUser.id.eq(creationUserId))
                );
            }
        }

        return predicateBuilder.getValue();
    }

    private BooleanExpression buildStationIdsInPredicate(List<Long> stationIds) {
        //the in(...) statement is limited to 1000 values
        //so if we have more we need to split it
        if (stationIds.size() <= 1000) {
            return station.id.in(stationIds);
        } else {
            List<List<Long>> lists = Lists.partition(stationIds, 1000);

            BooleanExpression predicate = station.id.in(lists.get(0));

            for (int i = 1; i < lists.size(); i++) {
                predicate = predicate.or(station.id.in(lists.get(i)));
            }
            return predicate;
        }
    }


    private void initValuesIdFields() {
        if (stationItemTypeWatercourse == null) {

            //Get the IDs for the code value that we need in the baseQuery from the thesaurus
            stationItemTypeWatercourse = thesaurus.getValueId(ThesaurusCodes.REALM_MIDATSTITMTY, ThesaurusCodes.MIDATSTITMTY_WATERCOURSE);
            stationItemTypeLocality = thesaurus.getValueId(ThesaurusCodes.REALM_MIDATSTITMTY, ThesaurusCodes.MIDATSTITMTY_LOCALITY);
            protocolTypeLab = thesaurus.getValueId(ThesaurusCodes.REALM_MIDATPROTO, ThesaurusCodes.MIDATPROTO_LABORATORY);
            protocolTypeMass = thesaurus.getValueId(ThesaurusCodes.REALM_MIDATPROTO, ThesaurusCodes.MIDATPROTO_MASS);
            sampleAttributeTypeOperator = thesaurus.getValueId(ThesaurusCodes.REALM_MIDATHDITMTY, ThesaurusCodes.MIDATHDITMTY_OPERATOR);
            sampleAttributeTypeDeterminator = thesaurus.getValueId(ThesaurusCodes.REALM_MIDATHDITMTY, ThesaurusCodes.MIDATHDITMTY_DETERMINATOR);
            sampleAttributeTypeWatercourse = thesaurus.getValueId(ThesaurusCodes.REALM_MIDATHDITMTY, ThesaurusCodes.MIDATHDITMTY_WATERCOURSE);
            sampleAttributeTypeLocality = thesaurus.getValueId(ThesaurusCodes.REALM_MIDATHDITMTY, ThesaurusCodes.MIDATHDITMTY_LOCALITY);
            sampleAttributeTypeCalledPlace = thesaurus.getValueId(ThesaurusCodes.REALM_MIDATHDITMTY, ThesaurusCodes.MIDATHDITMTY_CALLEDPLACE);

            //added to avoid the  issue caused by the search on water course or the locality
            sampleItemTypeWatercourse=thesaurus.getValueId(ThesaurusCodes.REALM_MIDATHDITMTY, ThesaurusCodes.MIDATHDITMTY_WATERCOURSE);
            sampleItemTypeLocality=thesaurus.getValueId(ThesaurusCodes.REALM_MIDATHDITMTY, ThesaurusCodes.MIDATHDITMTY_LOCALITY);
        }
    }


    /**
     * This private helper class manages the queries and projections to retrieve the results.
     * It was extracted into a separate class so that this logic is not repeated for form search, geo search, and evt. future other types of searches.
     * <p>
     * We had to split the search into 2 queries because of the join with the station item table that caused duplicates if there were more than 1 item in any language (see MID-118)
     * <p>
     * The first query applies the search criteria, and selects only distinct IDs. It also handles sorting and paging.
     * <p>
     * The second query receives the IDs and fetches the rows, including joined table header item that contains watercourse and locality as well as authors info.
     */
    private class SampleQueryBuilder {

        private Predicate restrictions;
        private Page page;
        private String orderBy = "sampleDate";
        private SortOrder sortOrder = SortOrder.asc;


        private JPAQuery baseQuery, resultQuery;

        private QSample sample = QSample.sample;
        private QSampleStation station = QSampleStation.sampleStation;

        private QProject project = QProject.project;


        public SampleQueryBuilder() {
        }

        public List<SearchResultDTO> executeSearchQuery() {

            List<Long> sampleIds = findSampleIds();

            if (sampleIds.isEmpty()) {
                return Collections.emptyList();
            }

            buildResultQuery(sampleIds);
            applySorting(resultQuery);

            List<SearchResultDTO> result = resultQuery.select(
                    new QSearchResultDTO(
                            sample.id.as("sampleId"),
                            sample.sampleDate.as("sampleDate"),
                            sample.sampleDateDay.as("sampleDateDay"),
                            sample.sampleDateMonth.as("sampleDateMonth"),
                            sample.sampleDateYear.as("sampleDateYear"),
                            sample.ibchIndexValue.as("ibchIndexValue"),
                            sample.makroIndexValue.as("makroIndexValue"),
                            sample.spearIndexValue.as("spearIndexValue"),

                            sample.ibchLegendVersionId.as("ibchLegendVersionId"),
                            sample.makroLegendVersionId.as("makroLegendVersionId"),
                            sample.spearLegendVersionId.as("spearLegendVersionId"),

                            watercourseFr.value.as("watercourseFr"),
                            watercourseDe.value.as("watercourseDe"),
                            watercourseIt.value.as("watercourseIt"),
                            localityFr.value.as("localityFr"),
                            localityDe.value.as("localityDe"),
                            localityIt.value.as("localityIt"),
                            operator.id.as("operatorId"),
                            operator.firstName.as("operatorFirstName"),
                            operator.lastName.as("operatorLastName"),
                            determinator.id.as("determinatorId"),
                            determinator.firstName.as("determinatorFirstName"),
                            determinator.lastName.as("determinatorLastName"),
                            sampleAttributeOperatorX.value.as("operatorExcel"),
                            sampleAttributeDeterminatorX.value.as("determinatorExcel"),
                            sample.sampleProtocolType.code.as("protocolType"),
                            sample.published.as("sampleIsPublished"),
                            project.designation.as("project"),
                            sample.station.stationNumber.as("stationNumber"),
                            sample.station.id.as("stationId"),


                            sample.valeurGi.as("valeurGi"),// GI niveau/taxon(s)
                            sample.taxonIndicateur.as("taxonIndicateur"),//GI niveau/taxon(s)
                            sample.giFinal.as("giFinal"), //GI_2019
                            sample.valuerVt.as("valuerVt"), //VT_2019
                            sample.ibchRobust.as("ibchRobust"), //IBCH_2019_R
                            sample.taxonSumFamily.as("taxonSumFamily"), // somme taxons IBCH
                            sample.ephemeropteraCounter.as("ephemeropteraCounter"),// somme familles Ephemeroptera
                            sample.tricopteraCounter.as("tricopteraCounter"),// familles Plecoptera
                            sample.plecopteraCounter.as("plecopteraCounter"),//familles Trichoptera
                            sample.sommeAbon.as("sommeAbon"),//Abondances
                            sample.ibchQ.as("ibchQ"), // Régime IBCH-Q
                            sample.valeurCorrection.as("valeurCorrection") //Valeur de correction VC
                    )
            ).fetch();

            return result;
        }

        public List<Tuple> executeExportQuery() {

            List<Long> sampleIds = findSampleIds();

            if (sampleIds.isEmpty()) {
                return Collections.emptyList();
            }

            buildResultQueryForExport(sampleIds);

            return resultQuery.fetch();
        }

        private List<Long> findSampleIds() {
            buildBaseQuery();
            List<Tuple> tuples = baseQuery.fetch();
            return tuples.stream().map(t -> t.get(0, Long.class)).collect(Collectors.toList());
        }

        public List<Long> findSamplesStationsIds() {
            buildBaseforStationsIdsQuery();
            List<Tuple> tuples = baseQuery.fetch();
            return tuples.stream().map(t -> t.get(0, Long.class)).collect(Collectors.toList());
        }

        @SuppressWarnings("unchecked")
        private void buildBaseQuery() {
            baseQuery = SampleJPADAO.this.queryFactory.from(sample);

            //build the base baseQuery (joins ! lots of joins !)
            baseQuery
                    .leftJoin(sample.station, station)


                    //.leftJoin(station.items, watercourseSearch) // link with sample header item instead
                    //.on(watercourseSearch.typeId.eq(stationItemTypeWatercourse)) // link with sample header item instead

                    //watercourse for search criteria
                    .leftJoin(sample.attributes,watercourseSearch2)
                    .on(watercourseSearch2.attributeType.id.eq(sampleItemTypeWatercourse))

                    //locality for search criteria
                    .leftJoin(sample.attributes,localitySearch2)
                    .on(localitySearch2.attributeType.id.eq(sampleItemTypeLocality))


                    //.leftJoin(station.items, localitySearch) // link with sample header item instead
                    //.on(localitySearch.typeId.eq(stationItemTypeLocality)) // link with sample header item instead

                    //group for filtering
                    .leftJoin(sample.groups, group)

                    .select(sample.id, sample.sampleDate) //we have to select the date otherwise the distinct fails and the query crashes
            ;
            applySearchRestrictions();
            applySorting(baseQuery);
            baseQuery.distinct();
            applyPaging();
        }

        private void buildBaseforStationsIdsQuery() {
            baseQuery = SampleJPADAO.this.queryFactory.from(sample);

            //build the base baseQuery (joins ! lots of joins !)
            baseQuery
                    .leftJoin(sample.station, station)

                    //.leftJoin(station.items, watercourseSearch)  // link with sample header item instead
                    //.on(watercourseSearch.typeId.eq(stationItemTypeWatercourse))  // link with sample header item instead

                    //watercourse for search criteria
                    .leftJoin(sample.attributes,watercourseSearch2)
                    .on(watercourseSearch2.attributeType.id.eq(sampleItemTypeWatercourse))

                    //.leftJoin(station.items, localitySearch)  // link with sample header item instead
                    //.on(localitySearch.typeId.eq(stationItemTypeLocality))  // link with sample header item instead

                    //locality for search criteria
                    .leftJoin(sample.attributes,localitySearch2)
                    .on(localitySearch2.attributeType.id.eq(sampleItemTypeLocality))

                    //group for filtering
                    .leftJoin(sample.groups, group)

                    .select(sample.station.id, sample.sampleDate) //we have to select the date otherwise the distinct fails and the query crashes
            ;
            applySearchRestrictions();
            applySorting(baseQuery);
            baseQuery.distinct();
            applyPaging();
        }


        @SuppressWarnings("unchecked")
        private void buildResultQuery(List<Long> sampleIds) {

            resultQuery = SampleJPADAO.this.queryFactory.from(sample);

            //build the base baseQuery (joins ! lots of joins !)
            resultQuery.from(sample)

                    //watercourse in FR
                    .leftJoin(sample.attributes, watercourseFr)
                    .on(watercourseFr.attributeTypeId.eq(sampleAttributeTypeWatercourse)
                            .and(watercourseFr.protocolTypeId.eq(protocolTypeLab)
                                    .or(watercourseFr.protocolTypeId.eq(protocolTypeMass)))
                            .and(watercourseFr.languageId.eq(1l)))


                    //watercourse entry in DE
                    .leftJoin(sample.attributes, watercourseDe)
                    .on(watercourseDe.attributeTypeId.eq(sampleAttributeTypeWatercourse)
                            .and(watercourseDe.protocolTypeId.eq(protocolTypeLab)
                                    .or(watercourseDe.protocolTypeId.eq(protocolTypeMass)))
                            .and(watercourseDe.languageId.eq(2l)))

                    //watercourse entry in IT
                    .leftJoin(sample.attributes, watercourseIt)
                    .on(watercourseIt.attributeTypeId.eq(sampleAttributeTypeWatercourse)
                            .and(watercourseIt.protocolTypeId.eq(protocolTypeLab)
                                    .or(watercourseIt.protocolTypeId.eq(protocolTypeMass)))
                            .and(watercourseIt.languageId.eq(3l)))

                    //locality entry in FR
                    .leftJoin(sample.attributes, localityFr)
                    .on(localityFr.attributeTypeId.eq(sampleAttributeTypeLocality)
                            .and(localityFr.protocolTypeId.eq(protocolTypeLab)
                                    .or(localityFr.protocolTypeId.eq(protocolTypeMass)))
                            .and(localityFr.languageId.eq(1l)))

                    //locality entry in DE
                    .leftJoin(sample.attributes, localityDe)
                    .on(localityDe.attributeTypeId.eq(sampleAttributeTypeLocality)
                            .and(localityDe.protocolTypeId.eq(protocolTypeLab)
                                    .or(localityDe.protocolTypeId.eq(protocolTypeMass)))
                            .and(localityDe.languageId.eq(2l)))

                    //locality entry in IT
                    .leftJoin(sample.attributes, localityIt)
                    .on(localityIt.attributeTypeId.eq(sampleAttributeTypeLocality)
                            .and(localityIt.protocolTypeId.eq(protocolTypeLab)
                                    .or(localityIt.protocolTypeId.eq(protocolTypeMass)))
                            .and(localityIt.languageId.eq(3l)))

                    //Operator Excel
                    .leftJoin(sample.attributes, sampleAttributeOperatorX)
                    .on(
                            sampleAttributeOperatorX.attributeTypeId.eq(sampleAttributeTypeOperator)
                                    .and(sampleAttributeOperatorX.protocolTypeId.eq(protocolTypeLab).or(sampleAttributeOperatorX.protocolTypeId.eq(protocolTypeMass)))
                                    .and(sampleAttributeOperatorX.sourceType.eq(SampleAttributeSource.EXCEL_FILE))
                    )

                    //Operator DB
                    .leftJoin(sample.attributes, sampleAttributeOperator)
                    .on(
                            sampleAttributeOperator.attributeTypeId.eq(sampleAttributeTypeOperator)
                                    .and(sampleAttributeOperator.protocolTypeId.eq(protocolTypeLab).or(sampleAttributeOperator.protocolTypeId.eq(protocolTypeMass)))
                                    .and(sampleAttributeOperator.sourceType.eq(SampleAttributeSource.PERSON_TABLE))
                    )
                    .leftJoin(sampleAttributeOperator.author, operator)

                    //Determinator Excel
                    .leftJoin(sample.attributes, sampleAttributeDeterminatorX)
                    .on(
                            sampleAttributeDeterminatorX.attributeTypeId.eq(sampleAttributeTypeDeterminator)
                                    .and(sampleAttributeDeterminatorX.protocolTypeId.eq(protocolTypeLab).or(sampleAttributeDeterminatorX.protocolTypeId.eq(protocolTypeMass)))
                                    .and(sampleAttributeDeterminatorX.sourceType.eq(SampleAttributeSource.EXCEL_FILE))
                    )

                    //Determinator DB
                    .leftJoin(sample.attributes, sampleAttributeDeterminator)
                    .on(
                            sampleAttributeDeterminator.attributeTypeId.eq(sampleAttributeTypeDeterminator)
                                    .and(sampleAttributeDeterminator.protocolTypeId.eq(protocolTypeLab).or(sampleAttributeDeterminator.protocolTypeId.eq(protocolTypeMass)))
                                    .and(sampleAttributeDeterminator.sourceType.eq(SampleAttributeSource.PERSON_TABLE))
                    )
                    .leftJoin(sampleAttributeDeterminator.author, determinator)
                    .leftJoin(sample.sampleProject, project)
            ;

            resultQuery.where(sample.id.in(sampleIds));

        }

        @SuppressWarnings("unchecked")
        private void buildResultQueryForExport(List<Long> sampleIds) {

            //build query from samples including provided sample IDs
            resultQuery = SampleJPADAO.this.queryFactory.from(sample);


            //bunch of joins to get the associated entities
            resultQuery

                    //watercourse
                    .leftJoin(sample.attributes, watercourse)
                    .on(
                            watercourse.attributeTypeId.eq(sampleAttributeTypeWatercourse)
                                    .and(
                                            watercourse.protocolTypeId.eq(protocolTypeLab)
                                                    .or(watercourse.protocolTypeId.eq(protocolTypeMass))
                                    )
                    )

                    //locality
                    .leftJoin(sample.attributes, locality)
                    .on(
                            locality.attributeTypeId.eq(sampleAttributeTypeLocality)
                                    .and(
                                            locality.protocolTypeId.eq(protocolTypeLab)
                                                    .or(locality.protocolTypeId.eq(protocolTypeMass))
                                    )
                    )

                    //lieu-dit
                    .leftJoin(sample.attributes, calledPlace)
                    .on(
                            calledPlace.attributeTypeId.eq(sampleAttributeTypeCalledPlace)
                                    .and(
                                            calledPlace.protocolTypeId.eq(protocolTypeLab)
                                                    .or(calledPlace.protocolTypeId.eq(protocolTypeMass))
                                    )
                    )

                    //Operator Excel
                    .leftJoin(sample.attributes, sampleAttributeOperatorX)
                    .on(
                            sampleAttributeOperatorX.attributeTypeId.eq(sampleAttributeTypeOperator)
                                    .and(sampleAttributeOperatorX.protocolTypeId.eq(protocolTypeLab).or(sampleAttributeOperatorX.protocolTypeId.eq(protocolTypeMass)))
                                    .and(sampleAttributeOperatorX.sourceType.eq(SampleAttributeSource.EXCEL_FILE))
                    )
                    //Determinator Excel
                    .leftJoin(sample.attributes, sampleAttributeDeterminatorX)
                    .on(
                            sampleAttributeDeterminatorX.attributeTypeId.eq(sampleAttributeTypeDeterminator)
                                    .and(sampleAttributeDeterminatorX.protocolTypeId.eq(protocolTypeLab).or(sampleAttributeDeterminatorX.protocolTypeId.eq(protocolTypeMass)))
                                    .and(sampleAttributeDeterminatorX.sourceType.eq(SampleAttributeSource.EXCEL_FILE))
                    )
                    //Operator DB
                    .leftJoin(sample.attributes, sampleAttributeOperator)
                    .on(
                            sampleAttributeOperator.attributeTypeId.eq(sampleAttributeTypeOperator)
                                    .and(sampleAttributeOperator.protocolTypeId.eq(protocolTypeLab).or(sampleAttributeOperator.protocolTypeId.eq(protocolTypeMass)))
                                    .and(sampleAttributeOperator.sourceType.eq(SampleAttributeSource.PERSON_TABLE))
                    )
                    //Determinator DB
                    .leftJoin(sample.attributes, sampleAttributeDeterminator)
                    .on(
                            sampleAttributeDeterminator.attributeTypeId.eq(sampleAttributeTypeDeterminator)
                                    .and(sampleAttributeDeterminator.protocolTypeId.eq(protocolTypeLab).or(sampleAttributeDeterminator.protocolTypeId.eq(protocolTypeMass)))
                                    .and(sampleAttributeDeterminator.sourceType.eq(SampleAttributeSource.PERSON_TABLE))
                    )

                    .leftJoin(sampleAttributeDeterminator.author, determinator)
                    .leftJoin(sample.station, station)
                    .leftJoin(sample.labRecordFields, labRecordField)
                    .leftJoin(labRecordField.taxon, labRecordFieldTaxon)
                    .leftJoin(labRecordField.fieldMapping, protocolFieldMapping)
                    .leftJoin(sample.mandataryInstitution, mandataryInstitution)
                    .leftJoin(sample.principalInstitution, principalInstitution)
                    .leftJoin(sample.sampleProject, project);

            //add "where id in (...)" predicate
            resultQuery.where(buildIdsInPredicate(sampleIds));

            //order by sample ID so that rows belonging to the same sample are together
            resultQuery.orderBy(sample.id.asc());

            //projection with fields relevant for data export
            resultQuery.select(
                    sample.id,
                    sample.sampleProtocolType.code,
                    sample.sampleDate,
                    sample.sampleDateDay,
                    sample.sampleDateMonth,
                    sample.sampleDateYear,
                    sample.ibchIndexValue,
                    sample.makroIndexValue,
                    sample.spearIndexValue,

                    sample.ibchLegendVersionId,
                    sample.makroLegendVersionId,
                    sample.spearLegendVersionId,

                    project.designation,
                    station.stationNumber,
                    station.coordinates,
                    watercourse.value,
                    locality.value,
                    calledPlace.value,
                    labRecordField.taxon.id,
                    labRecordField.taxon.code,
                    labRecordField.frequency,
                    labRecordField.modifiedFrequency,
                    labRecordField.sampleNumber,
                    labRecordField.stadium,
                    labRecordField.comment,
                    protocolFieldMapping.ibchTaxon,
                    protocolFieldMapping.ibchDeterminingGroup,
                    principalInstitution.name,
                    mandataryInstitution.name,
                    sampleAttributeDeterminatorX.value,
                    sampleAttributeDeterminator.value,
                    sampleAttributeOperatorX.value,
                    sampleAttributeOperator.value,
                    sample.published,
                    station.stationCanton,
                    station.stationGewissNumber,
                    station.id,
                    sample.sampleCommentOther,
                    station.coordinateZ,



                    sample.valeurGi,// GI niveau/taxon(s)
                    sample.taxonIndicateur,//GI niveau/taxon(s)
                    sample.giFinal, //GI_2019
                    sample.valuerVt, //VT_2019
                    sample.ibchRobust, //IBCH_2019_R
                    sample.taxonSumFamily, // somme taxons IBCH
                    sample.ephemeropteraCounter,// somme familles Ephemeroptera
                    sample.tricopteraCounter,// familles Plecoptera
                    sample.plecopteraCounter,//familles Trichoptera
                    sample.sommeAbon,//Abondances
                    sample.ibchQ, // Régime IBCH-Q
                    sample.valeurCorrection //Valeur de correction VC
            );

        }

        private BooleanExpression buildIdsInPredicate(List<Long> sampleIds) {
            //the in(...) statement is limited to 1000 values
            //so if we have more we need to split it
            if (sampleIds.size() <= 1000) {
                return sample.id.in(sampleIds);
            } else {
                List<List<Long>> lists = Lists.partition(sampleIds, 1000);

                BooleanExpression predicate = sample.id.in(lists.get(0));

                for (int i = 1; i < lists.size(); i++) {
                    predicate = predicate.or(sample.id.in(lists.get(i)));
                }
                return predicate;
            }
        }

        private void applySearchRestrictions() {
            if (restrictions != null) {
                baseQuery.where(restrictions);
            }
        }

        private void applyPaging() {
            if (page != null) {
                //apply paging limitations, count baseQuery

                JPAQuery countQuery = baseQuery.clone(getEm());
                Long count = countQuery.fetchCount();
                page.setTotalRows((int) count.longValue());

                baseQuery.offset(page.getFirstRow());
                baseQuery.limit(page.getPageSize());
            }
        }

        private void applySorting(JPAQuery query) {
            //determine sort sortOrder
            boolean asc = (sortOrder == null) || (sortOrder == SortOrder.asc);

            //apply sortOrder by clause
            //curently only possible by date because the other potential fields (locality, watercourse) are multi-lingual and it is very hard to sort by a multilingual field
            switch (orderBy) {

                case ("sampleDate"): {
                    if (asc) {
                        query.orderBy(sample.sampleDate.asc());
                    } else {
                        query.orderBy(sample.sampleDate.desc());
                    }
                    break;
                }

                default:
                    throw new IllegalArgumentException(orderBy + " is not a valid column to sortOrder by.");
            }
        }

        public SampleQueryBuilder setRestrictions(Predicate restrictions) {
            this.restrictions = restrictions;
            return this;
        }

        public SampleQueryBuilder setPage(Page page) {
            this.page = page;
            return this;
        }

        public SampleQueryBuilder setOrderBy(String orderBy) {
            this.orderBy = orderBy;
            return this;
        }

        public SampleQueryBuilder setSortOrder(SortOrder sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

    }


    @Override
    public void batchUpdateField(List<Long> sampleIds, String fieldName, Object value) {
        String jpql = MessageFormat.format("update Sample set {0}=:value where id in :sampleIds", fieldName);
        getEm().createQuery(jpql).setParameter("value", value).setParameter("sampleIds", sampleIds).executeUpdate();
    }

    @Override
    public Sample loadSampleForDetailsView(Long sampleId) {
        String jpql = "select s from Sample s " +
                "left join fetch s.groups " +
                "left join fetch s.attributes  " +
                "left join fetch s.documents  " +
                "left join fetch s.comments  " +
                "left join fetch s.indiceType  " +
                "left join fetch s.labRecordFields  " +
                "left join fetch s.mandataryInstitution  " +
                "left join fetch s.sampleProject  " +
                "left join fetch s.precision  " +
                "left join fetch s.precisionReferenceSystem  " +
                "left join fetch s.principalInstitution  " +
                "left join fetch s.station  " +
                "left join fetch s.protocolVersion " +
                "where s.id=:sampleId";


        Sample sample = getEm().createQuery(jpql, Sample.class).setParameter("sampleId", sampleId).getSingleResult();

        //prefetch taxa from lab record fields (they will be in hibernate cache)
        jpql = "select l from LabRecordField l left join fetch l.taxon where l.master.id=:sampleId";
        getEm().createQuery(jpql, LabRecordField.class).setParameter("sampleId", sampleId).getResultList();


        //remove duplicates from labRecords collection due to joins
        List<LabRecordField> labRecordFields = sample.getLabRecordFields();
        LinkedHashSet<LabRecordField> set = new LinkedHashSet<>(labRecordFields);
        labRecordFields.clear();
        labRecordFields.addAll(set);
        return sample;
    }


    @Override
    public List<SampleImportDisplayLog> loadSampleImportLog(Long sampleId, Long languageId){
        List messages  = getEm().createQuery("select importlog from SampleImportDisplayLog importlog where importlog.sphId=:sampleId  and importlog.lanId=:languageId " +
                "")
                .setParameter("sampleId", sampleId)
                .setParameter("languageId", languageId)
                .getResultList();
        return messages;
    }

    @Override
    public SampleImportDisplayLog getImportLogKey(Long sampleId, Long languageId){

        SampleImportDisplayLog importLog =
                (SampleImportDisplayLog) getEm().createQuery("select importlog from SampleImportDisplayLog importlog where importlog.sphId=:sampleId  and importlog.lanId=:languageId and rownum<2")
                        .setParameter("sampleId", sampleId)
                        .setParameter("languageId", languageId)
                        .getSingleResult();

        return importLog;
    }

    /** statistic */

    @Override
    public List<SampleStatPerProtocolMonth> getSampleProtocolPerMonth(){

        List records = getEm().createQuery("select sampleStatPerProtocolMonth from SampleStatPerProtocolMonth sampleStatPerProtocolMonth order by sampleStatPerProtocolMonth.year, sampleStatPerProtocolMonth.month ")
                .getResultList();

        return records;
    }


    @Override
    public List<SampleProtocolPerUserCanton> getSampleProtocolPerUserCanton(){
        List records = getEm().createQuery("select sampleProtocolPerUserCanton from SampleProtocolPerUserCanton sampleProtocolPerUserCanton  ")
                .getResultList();
        return records;
    }



    @Override
    public List<ListConnection> getUsersConnectionHistory(){
        List records = getEm().createQuery("select listConnection from ListConnection listConnection where listConnection.application ='MIDAT' ")
                .getResultList();
        return records;
    }


    @Override
    public List<ProtocolIndex> getSampleIndexHistory(){
        List records = getEm().createQuery("select protocolIndex from ProtocolIndex protocolIndex ")
                .getResultList();
        return records;
    }



    /* V_INFOIBCHDATA */

    @Override
    public  SampleInfoIbchData loadSampleInfoIbchData(Long sampleId){
        SampleInfoIbchData messages  = getEm().createQuery("select sampleInfoIbchData from SampleInfoIbchData sampleInfoIbchData where sampleInfoIbchData.sphId=:sampleId  ",SampleInfoIbchData.class)
                .setParameter("sampleId", sampleId)
                .getSingleResult();
        return messages;
    }




    @Override
    public  SampleIndiceHistory loadSampleIndiceHistoryData(Long sampleId){
        SampleIndiceHistory messages  = getEm().createQuery("select sampleIndiceHistory from SampleIndiceHistory sampleIndiceHistory " +
                "where sampleIndiceHistory.sampleId=:sampleId and  sampleIndiceHistory.versionSeq=-1 ",SampleIndiceHistory.class)
                .setParameter("sampleId", sampleId)
                .getSingleResult();
        return messages;
    }



    @Override
    public  Sample loadSampleByIphId(Long sampleIphId){
        Sample sample  = getEm().createQuery("select sample from Sample sample " +
                "where sample.sampleIphId=:sampleIphId ",Sample.class)
                .setParameter("sampleIphId", sampleIphId)
                .getSingleResult();
        return sample;
    }
}

