package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.entities.base.Project;
import ch.cscf.jeci.persistence.daos.interfaces.midat.SampleProjectDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author: Abdallah kanso
 */
@Repository
public class SampleProjectJPADAO extends GenericJpaEntityDAO<Project> implements SampleProjectDAO {

    @Override
    public List<String> findProjectNamesLike(String query, Long userId) {
        return findProjectNamesQuery(query, userId);
    }

    @Override
    public List<String> findProjectNamesLikeInGroups(String query, Long userId, Set<Long> groupIds) {
        return findProjectNamesQueryInGroups(query, userId, groupIds);
    }

    private List<String> findProjectNamesQuery(String query, Long userId) {
        //the query converts to us ascii in sortOrder to be accent-insensitive
        return
                getEm()
                        .createQuery("select distinct  project.designation  from Project project left join project.samples sample left join sample.groups grp  " +
                                        " where (sample.published=true or sample.creationUser.id=:userId)  " +
                                        " and (lower(project.designation) like lower(convert(CONCAT('%',:query,'%'), 'US7ASCII')) or lower(project.code) like lower(convert(CONCAT('%',:query,'%'), 'US7ASCII')))",
                                String.class)
                        .setParameter("userId", userId)
                        .setParameter("query", query)
                        .getResultList();
    }

    private List<String> findProjectNamesQueryInGroups(String query, Long userId, Set<Long> groupIds) {
        //the query converts to us ascii in sortOrder to be accent-insensitive
        return
                getEm()
                        .createQuery(" select distinct project.designation from Project project left join project.samples sample left join sample.groups grp  " +
                                        " where ((sample.published=true or sample.creationUser.id=:userId) and grp.id in (:groupIds)) " +
                                        " and (lower(project.designation) like lower(convert(CONCAT('%',:query,'%'), 'US7ASCII')) or lower(project.code) like lower(convert(CONCAT('%',:query,'%'), 'US7ASCII')))",
                                String.class)
                        .setParameter("userId", userId)
                        .setParameter("groupIds", groupIds)
                        .setParameter("query", query)
                        .getResultList();
    }


}
