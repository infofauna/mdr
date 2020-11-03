package ch.cscf.jeci.persistence.daos.jpa.midat;


import ch.cscf.jeci.domain.entities.midat.ProtocolMappingMassField;
import ch.cscf.jeci.persistence.daos.interfaces.midat.ProtocolMappingMassFieldDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author: kanso
 */
@Repository
public class ProtocolMappingMassFieldJpaDAO extends GenericJpaEntityDAO<ProtocolMappingMassField> implements ProtocolMappingMassFieldDAO {


    @Override
    public List<ProtocolMappingMassField>  findMappingByVersion(Long versionId) {
        return findMappingByVersionQuery(versionId);
    }

    private List<ProtocolMappingMassField>  findMappingByVersionQuery(Long versionId) {
        //the query converts to us ascii in sortOrder to be accent-insensitive

       return getEm()
                        .createQuery(" from ProtocolMappingMassField p " +
                                        " where (p.protocolVersionId=:versionId)  " ,
                                ProtocolMappingMassField.class)
                        .setParameter("versionId", versionId)
                        .getResultList();

    }
}
