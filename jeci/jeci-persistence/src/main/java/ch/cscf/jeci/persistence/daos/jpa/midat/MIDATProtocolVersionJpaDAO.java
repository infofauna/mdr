package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.entities.midat.MIDATProtocolVersion;
import ch.cscf.jeci.persistence.daos.interfaces.midat.MIDATProtocolVersionDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: henryp
 */
@Repository
public class MIDATProtocolVersionJpaDAO extends GenericJpaEntityDAO<MIDATProtocolVersion> implements MIDATProtocolVersionDAO {

    @Override
    public List<MIDATProtocolVersion> findForType(Long protocolTypeId) {

        return getEm().createQuery("select version from MIDATProtocolVersion version where version.protocolType.id=:protocolTypeId order by version.sortOrder", MIDATProtocolVersion.class)
                .setParameter("protocolTypeId", protocolTypeId).getResultList();
    }

}
