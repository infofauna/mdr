package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.entities.midat.ImportMassDataDetail;
import ch.cscf.jeci.persistence.daos.interfaces.midat.ImportMassDataDetailDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

/**
 * @author: abdallah kanso
 */

@Repository
public class ImportMassDataDetailJPADAO extends GenericJpaEntityDAO<ImportMassDataDetail> implements ImportMassDataDetailDAO {

}
