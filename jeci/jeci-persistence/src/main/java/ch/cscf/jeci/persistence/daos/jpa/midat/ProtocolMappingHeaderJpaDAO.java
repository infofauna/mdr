package ch.cscf.jeci.persistence.daos.jpa.midat;


import ch.cscf.jeci.domain.entities.midat.ProtocolMappingHeader;
import ch.cscf.jeci.persistence.daos.interfaces.midat.ProtocolMappingHeaderDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;
import java.util.List;


/**
 * @author: kanso
 */
@Repository
public class ProtocolMappingHeaderJpaDAO extends GenericJpaEntityDAO<ProtocolMappingHeader> implements ProtocolMappingHeaderDAO {


    @Override
    public List<ProtocolMappingHeader>  findMappingByVersion(Long versionId) {
        return findMappingByVersionQuery(versionId);
    }

    private List<ProtocolMappingHeader>  findMappingByVersionQuery(Long versionId) {
        //the query converts to us ascii in sortOrder to be accent-insensitive

       return getEm()
                        .createQuery(" from ProtocolMappingHeader pmh " +
                                        " where (pmh.protocolVersionId=:versionId)  " ,
                                ProtocolMappingHeader.class)
                        .setParameter("versionId", versionId)
                        .getResultList();

    }
}
