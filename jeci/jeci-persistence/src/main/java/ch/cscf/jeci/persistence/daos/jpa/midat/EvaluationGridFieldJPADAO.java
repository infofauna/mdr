package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.entities.midat.EvaluationGridItem;
import ch.cscf.jeci.persistence.daos.interfaces.midat.EvaluationGridItemDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: henryp
 */
@Repository
public class EvaluationGridFieldJPADAO extends GenericJpaEntityDAO<EvaluationGridItem> implements EvaluationGridItemDAO {

    @Override
    public List<EvaluationGridItem> findBySampleId(Long sampleId){

        return
        getEm().createQuery("select item from EvaluationGridItem item where item.master.id=:sampleId", EvaluationGridItem.class)
                .setParameter("sampleId", sampleId)
                .getResultList();

    }

}
