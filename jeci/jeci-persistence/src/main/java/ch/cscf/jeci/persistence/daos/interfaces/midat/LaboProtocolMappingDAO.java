package ch.cscf.jeci.persistence.daos.interfaces.midat;

import ch.cscf.jeci.domain.entities.midat.LabProtocolFieldMapping;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;

/**
 * @author: kanso
 */
public interface LaboProtocolMappingDAO extends GenericEntityDAO<LabProtocolFieldMapping> {
     List<LabProtocolFieldMapping> findLaboMappingByVersion(Long versionId) ;

}
