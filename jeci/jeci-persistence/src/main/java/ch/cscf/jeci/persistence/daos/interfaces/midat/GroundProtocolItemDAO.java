package ch.cscf.jeci.persistence.daos.interfaces.midat;

import ch.cscf.jeci.domain.entities.midat.GroundProtocolItem;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;

/**
 * @author: henryp
 */
public interface GroundProtocolItemDAO extends GenericEntityDAO<GroundProtocolItem> {


    public List<GroundProtocolItem> findByParentSample(Long sampleId);


}
