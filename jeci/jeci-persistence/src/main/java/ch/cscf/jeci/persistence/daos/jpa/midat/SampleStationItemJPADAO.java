package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.ThesaurusCodes;
import ch.cscf.jeci.domain.entities.midat.sample.SampleStationItem;
import ch.cscf.jeci.persistence.daos.interfaces.midat.SampleStationItemDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author: henryp
 */
@Repository
public class SampleStationItemJPADAO extends GenericJpaEntityDAO<SampleStationItem> implements SampleStationItemDAO {

    @Override
    public List<String> findWatercourseNamesLikeInGroups(String query, Long userId, Set<Long> groupIds) {
        return findEntriesOfTypeLikeQueryInGroups(ThesaurusCodes.MIDATHDITMTY_WATERCOURSE, query, userId, groupIds);
    }

    @Override
    public List<String> findLocalityNamesLikeInGroups(String query, Long userId, Set<Long> groupIds) {
        return findEntriesOfTypeLikeQueryInGroups(ThesaurusCodes.MIDATHDITMTY_LOCALITY, query, userId, groupIds);
    }

    @Override
    public List<String> findWatercourseNamesLike(String query, Long userId) {
        return findEntriesOfTypeLikeQuery(ThesaurusCodes.MIDATHDITMTY_WATERCOURSE, query, userId);
    }

    @Override
    public List<String> findLocalityNamesLike(String query, Long userId) {
        return findEntriesOfTypeLikeQuery(ThesaurusCodes.MIDATHDITMTY_LOCALITY, query, userId);
    }








    private List<String> findEntriesOfTypeLikeQueryInGroups(String type, String query, Long userId, Set<Long> groupIds) {

        //the query converts to us ascii in sortOrder to be accent-insensitive
        return
            getEm()
                    .createQuery("select distinct item.value from SampleStation station left join station.samples sample left join sample.groups grp join station.items item " +
                                    "where ((sample.published=true or sample.creationUser.id=:userId) and grp.id in (:groupIds)) " +
                                    "and (item.type.code=:type and lower(convert(item.value, 'US7ASCII')) like lower(convert(CONCAT('%',:query,'%'), 'US7ASCII')))",
                            String.class)
                    .setParameter("userId", userId)
                    .setParameter("groupIds", groupIds)
                    .setParameter("type", type)
                    .setParameter("query", query)
                    .getResultList();
    }

    private List<String> findEntriesOfTypeLikeQuery(String type, String query, Long userId) {

        //the query converts to us ascii in sortOrder to be accent-insensitive
        return
                getEm()
                        .createQuery("select distinct item.value from SampleStation station left join station.samples sample left join sample.groups grp join station.items item " +
                                        "where (sample.published=true or sample.creationUser.id=:userId) " +
                                        "and (item.type.code=:type and lower(convert(item.value, 'US7ASCII')) like lower(convert(CONCAT('%',:query,'%'), 'US7ASCII')))",
                                String.class)
                        .setParameter("userId", userId)
                        .setParameter("type", type)
                        .setParameter("query", query)
                        .getResultList();
    }





}
