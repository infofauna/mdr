package ch.cscf.jeci.persistence.daos.jpa.midat;


import ch.cscf.jeci.domain.entities.midat.LabProtocolFieldMapping;
import ch.cscf.jeci.persistence.daos.interfaces.midat.LaboProtocolMappingDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author: kanso
 */
@Repository
public class LaboProtocolMappingJpaDAO extends GenericJpaEntityDAO<LabProtocolFieldMapping> implements LaboProtocolMappingDAO {


    @Override
    public List<LabProtocolFieldMapping>  findLaboMappingByVersion(Long versionId) {
        return findMappingByVersionQuery(versionId);
    }

    private List<LabProtocolFieldMapping>  findMappingByVersionQuery(Long versionId) {
       return getEm()
                        .createQuery(" from LabProtocolFieldMapping pmh " +
                                        " where (pmh.protocolVersionId=:versionId)  " ,
                                LabProtocolFieldMapping.class)
                        .setParameter("versionId", versionId)
                        .getResultList();

    }
}
