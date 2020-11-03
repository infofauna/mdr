package ch.cscf.jeci.persistence.daos.interfaces.midat;

import ch.cscf.jeci.domain.entities.midat.ProtocolMappingMassMap;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;

/**
 * @author: kanso
 */
public interface ProtocolMappingMassMapDAO extends GenericEntityDAO<ProtocolMappingMassMap> {
     List<ProtocolMappingMassMap> findMappingByLang(Long languageId) ;

}
