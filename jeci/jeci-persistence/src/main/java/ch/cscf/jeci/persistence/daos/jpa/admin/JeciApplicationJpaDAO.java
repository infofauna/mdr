package ch.cscf.jeci.persistence.daos.jpa.admin;

import ch.cscf.jeci.domain.entities.security.JeciApplication;
import ch.cscf.jeci.persistence.daos.interfaces.admin.JeciApplicationDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

/**
 * @author: henryp
 */
@Repository
public class JeciApplicationJpaDAO extends GenericJpaEntityDAO<JeciApplication> implements JeciApplicationDAO {
}
