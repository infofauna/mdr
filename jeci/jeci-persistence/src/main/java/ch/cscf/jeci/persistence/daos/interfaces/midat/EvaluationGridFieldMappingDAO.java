package ch.cscf.jeci.persistence.daos.interfaces.midat;

import ch.cscf.jeci.domain.entities.midat.EvaluationGridFieldMapping;
import ch.cscf.jeci.domain.entities.midat.GroundProtocolMapping;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;

/**
 * @author: henryp
 */
public interface EvaluationGridFieldMappingDAO extends GenericEntityDAO<EvaluationGridFieldMapping> {

    List<EvaluationGridFieldMapping> getAllMappingsOrderedByRowAndCol();

    EvaluationGridFieldMapping findMappingByThesaurusValueCodeForContentDefinition(String thesaurusValueCode);

    List<EvaluationGridFieldMapping> findGridMappingByVersion(Long versionId) ;
}
