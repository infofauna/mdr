package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.entities.midat.LabProtocolImport;
import ch.cscf.jeci.persistence.daos.interfaces.midat.LabProtocolImportDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

/**
 * @author: abdallah kanso
 */

@Repository
public class LabProtocolImportJPADAO extends GenericJpaEntityDAO<LabProtocolImport> implements LabProtocolImportDAO {

}
