package ch.cscf.jeci.persistence.daos.jpa.infofauna;

import ch.cscf.jeci.domain.entities.base.Institution;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.InstitutionDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

/**
 * @author: henryp
 */
@Repository
public class InstitutionJpaDAO extends GenericJpaEntityDAO<Institution> implements InstitutionDAO {

}
