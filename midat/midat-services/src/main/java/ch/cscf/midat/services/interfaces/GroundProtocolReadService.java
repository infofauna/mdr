package ch.cscf.midat.services.interfaces;

import ch.cscf.jeci.domain.dto.midat.GroundProtocolDTO;

/**
 * @author: henryp
 */
public interface GroundProtocolReadService {

    GroundProtocolDTO getGroundProtocol(Long sampleId);
}

