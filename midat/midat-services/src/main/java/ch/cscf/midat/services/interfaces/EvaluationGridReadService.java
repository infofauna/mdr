package ch.cscf.midat.services.interfaces;

import ch.cscf.jeci.domain.dto.midat.EvaluationGridDTO;

/**
 * @author: henryp
 */
public interface EvaluationGridReadService {

    EvaluationGridDTO getEvalutationGrid(Long sampleId);

}
