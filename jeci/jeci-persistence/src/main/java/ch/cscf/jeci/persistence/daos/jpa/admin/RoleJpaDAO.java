package ch.cscf.jeci.persistence.daos.jpa.admin;

import ch.cscf.jeci.domain.entities.security.Role;
import ch.cscf.jeci.persistence.daos.interfaces.admin.RoleDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

/**
 * @author: henryp
 */
@Repository
public class RoleJpaDAO extends GenericJpaEntityDAO<Role> implements RoleDAO {

}
