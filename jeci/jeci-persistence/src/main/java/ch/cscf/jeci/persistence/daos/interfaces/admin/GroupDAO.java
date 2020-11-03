package ch.cscf.jeci.persistence.daos.interfaces.admin;

import ch.cscf.jeci.domain.entities.security.Group;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;

/**
 * @author: henryp
 */
public interface GroupDAO extends GenericEntityDAO<Group> {

    Group findByName(String groupName);

    Long getIdOfPublicGroup();

    List<Group> findAllGroupsExceptPublic();
}
