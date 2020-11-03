package ch.cscf.jeci.persistence.daos.interfaces.midat;

import ch.cscf.jeci.domain.entities.base.Project;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;
import java.util.Set;

/**
 * @author: abdallah kanso
 */
public interface SampleProjectDAO extends GenericEntityDAO<Project> {
    List<String> findProjectNamesLike(String query, Long userId);

    List<String> findProjectNamesLikeInGroups(String query, Long userId, Set<Long> groupIds);
}
