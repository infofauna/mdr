package ch.cscf.jeci.persistence.daos.interfaces.midat;

import ch.cscf.jeci.domain.entities.base.Institution;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;
import java.util.Set;

/**
 * @author: abdallah kanso
 */
public interface SampleInstitutionDAO extends GenericEntityDAO<Institution> {
    List<String> findPrincipalInstitutionNamesLike(String query, Long userId);

    List<String> findPrincipalInstitutionNamesLikeInGroups(String query, Long userId, Set<Long> groupIds);
}
