package ch.cscf.jeci.persistence.daos.interfaces.midat;

import ch.cscf.jeci.domain.entities.midat.MIDATProtocolVersion;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;

/**
 * @author: henryp
 */
public interface MIDATProtocolVersionDAO extends GenericEntityDAO<MIDATProtocolVersion> {

    List<MIDATProtocolVersion> findForType(Long protocolTypeId);

}
