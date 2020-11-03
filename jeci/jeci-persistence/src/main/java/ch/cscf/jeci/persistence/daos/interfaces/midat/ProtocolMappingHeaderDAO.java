package ch.cscf.jeci.persistence.daos.interfaces.midat;

import ch.cscf.jeci.domain.entities.midat.ProtocolMappingHeader;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;

/**
 * @author: kanso
 */
public interface ProtocolMappingHeaderDAO extends GenericEntityDAO<ProtocolMappingHeader> {
     List<ProtocolMappingHeader> findMappingByVersion(Long versionId) ;

}
