package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.entities.midat.GridProtocolImport;
import ch.cscf.jeci.persistence.daos.interfaces.midat.GridProtocolImportDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

/**
 * @author: abdallah kanso
 */

@Repository
public class GridProtocolImportJPADAO extends GenericJpaEntityDAO<GridProtocolImport> implements GridProtocolImportDAO {

}
