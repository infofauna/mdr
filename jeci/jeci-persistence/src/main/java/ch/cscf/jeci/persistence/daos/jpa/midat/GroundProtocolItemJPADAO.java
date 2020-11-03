package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.entities.midat.GroundProtocolItem;
import ch.cscf.jeci.persistence.daos.interfaces.midat.GroundProtocolItemDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: henryp
 */
@Repository
public class GroundProtocolItemJPADAO extends GenericJpaEntityDAO<GroundProtocolItem> implements GroundProtocolItemDAO {

    @Override
    public List<GroundProtocolItem> findByParentSample(Long sampleId) {
        return
                getEm().createQuery("select item from GroundProtocolItem item where item.parentSample.id=:sampleId", GroundProtocolItem.class)
                .setParameter("sampleId", sampleId)
                .getResultList();
    }
}
