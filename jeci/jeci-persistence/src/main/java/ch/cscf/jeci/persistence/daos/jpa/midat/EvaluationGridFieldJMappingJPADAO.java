package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.entities.midat.EvaluationGridFieldMapping;
import ch.cscf.jeci.domain.entities.midat.GroundProtocolMapping;
import ch.cscf.jeci.persistence.daos.interfaces.midat.EvaluationGridFieldMappingDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: henryp
 */
@Repository
public class EvaluationGridFieldJMappingJPADAO extends GenericJpaEntityDAO<EvaluationGridFieldMapping> implements EvaluationGridFieldMappingDAO{

    @Override
    public List<EvaluationGridFieldMapping> getAllMappingsOrderedByRowAndCol() {

        return getEm()
                .createQuery("select m from EvaluationGridFieldMapping m order by m.rowIndex, m.colIndex", EvaluationGridFieldMapping.class)
                .getResultList();

    }

    @Override
    public EvaluationGridFieldMapping findMappingByThesaurusValueCodeForContentDefinition(String thesaurusValueCode) {
        return
        getEm()
                .createQuery("select mapping from EvaluationGridFieldMapping mapping where mapping.contentDefinitionCode=:thesaurusValueCode", EvaluationGridFieldMapping.class)
                .setParameter("thesaurusValueCode", thesaurusValueCode)
                .getSingleResult();

    }


    @Override
    public List<EvaluationGridFieldMapping>  findGridMappingByVersion(Long versionId) {
        return findMappingByVersion(versionId);
    }

    private List<EvaluationGridFieldMapping>  findMappingByVersion(Long versionId) {
        //the query converts to us ascii in sortOrder to be accent-insensitive

        return getEm()
                .createQuery(" from EvaluationGridFieldMapping pmh " +
                                " where (pmh.protocolVersionId=:versionId)  " ,
                        EvaluationGridFieldMapping.class)
                .setParameter("versionId", versionId)
                .getResultList();

    }
}
