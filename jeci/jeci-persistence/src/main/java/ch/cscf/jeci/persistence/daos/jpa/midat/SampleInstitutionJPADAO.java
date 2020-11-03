package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.entities.base.Institution;
import ch.cscf.jeci.persistence.daos.interfaces.midat.SampleInstitutionDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author: abdallah kanso
 */
@Repository
public class SampleInstitutionJPADAO extends GenericJpaEntityDAO<Institution> implements SampleInstitutionDAO {

    @Override
    public List<String> findPrincipalInstitutionNamesLike(String query, Long userId) {
        return findPrincipalInstitutionNamesQuery(query, userId);
    }

    @Override
    public List<String> findPrincipalInstitutionNamesLikeInGroups(String query, Long userId, Set<Long> groupIds) {
        return findPrincipalInstitutionNamesQueryInGroups(query, userId, groupIds);
    }

    private List<String> findPrincipalInstitutionNamesQuery(String query, Long userId) {
        //the query converts to us ascii in sortOrder to be accent-insensitive
        return
                getEm()
                        .createQuery("select distinct institution.name from Institution institution left join institution.samples sample left join sample.groups grp  " +
                                        "where (sample.published=true or sample.creationUser.id=:userId)  " +
                                        " and (lower(institution.acronym) like lower(convert(CONCAT('%',:query,'%'), 'US7ASCII')) or lower(institution.name) like lower(convert(CONCAT('%',:query,'%'), 'US7ASCII')))",
                                String.class)
                        .setParameter("userId", userId)
                        .setParameter("query", query)
                        .getResultList();
    }

    private List<String> findPrincipalInstitutionNamesQueryInGroups(String query, Long userId, Set<Long> groupIds) {
        //the query converts to us ascii in sortOrder to be accent-insensitive
        return
                getEm()
                        .createQuery(" select distinct institution.name from Institution institution left join institution.samples sample left join sample.groups grp  " +
                                        " where ((sample.published=true or sample.creationUser.id=:userId) and grp.id in (:groupIds)) " +
                                        " and (lower(institution.acronym) like lower(convert(CONCAT('%',:query,'%'), 'US7ASCII')) or lower(institution.name) like lower(convert(CONCAT('%',:query,'%'), 'US7ASCII')))",
                                String.class)
                        .setParameter("userId", userId)
                        .setParameter("groupIds", groupIds)
                        .setParameter("query", query)
                        .getResultList();
    }
}
