package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.entities.midat.GroundProtocolImport;
import ch.cscf.jeci.persistence.daos.interfaces.midat.GroundProtocolImportDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

/**
 * @author: abdallah kanso
 */

@Repository
public class GroundProtocolImportJPADAO extends GenericJpaEntityDAO<GroundProtocolImport> implements GroundProtocolImportDAO {

}
