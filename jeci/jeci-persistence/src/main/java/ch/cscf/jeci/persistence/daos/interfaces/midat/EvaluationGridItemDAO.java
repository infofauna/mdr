package ch.cscf.jeci.persistence.daos.interfaces.midat;

import ch.cscf.jeci.domain.entities.midat.EvaluationGridItem;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;

/**
 * @author: henryp
 */
public interface EvaluationGridItemDAO extends GenericEntityDAO<EvaluationGridItem> {

    List<EvaluationGridItem> findBySampleId(Long sampleId);

}
