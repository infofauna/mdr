package ch.cscf.jeci.persistence.daos.interfaces.midat;

import ch.cscf.jeci.domain.entities.midat.GroundProtocolMapping;
import ch.cscf.jeci.domain.entities.midat.ProtocolMappingHeader;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;

/**
 * @author: henryp
 */
public interface GroundProtocolMappingDAO extends GenericEntityDAO<GroundProtocolMapping> {

    List<GroundProtocolMapping> findAllRootMappingsSorted();

    List<GroundProtocolMapping> findGroundMappingByVersion(Long versionId) ;


}
