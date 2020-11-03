package ch.cscf.jeci.persistence.daos.interfaces.midat;

import ch.cscf.jeci.domain.entities.midat.ProtocolMappingMassField;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;

/**
 * @author: kanso
 */
public interface ProtocolMappingMassFieldDAO extends GenericEntityDAO<ProtocolMappingMassField> {
     List<ProtocolMappingMassField> findMappingByVersion(Long versionId) ;

}
