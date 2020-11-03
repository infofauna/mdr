package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.entities.midat.GroundProtocolMapping;
import ch.cscf.jeci.domain.entities.midat.LabProtocolFieldMapping;
import ch.cscf.jeci.persistence.daos.interfaces.midat.GroundProtocolMappingDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: henryp
 */
@Repository
public class GroundProtocolMappingJPADAO extends GenericJpaEntityDAO<GroundProtocolMapping> implements GroundProtocolMappingDAO {

    @Override
    public List<GroundProtocolMapping> findAllRootMappingsSorted() {

        return
        getEm().createQuery("select mapping from GroundProtocolMapping mapping where mapping.parent is null order by mapping.sortOrder", GroundProtocolMapping.class)
                .getResultList();

    }

    @Override
    public List<GroundProtocolMapping>  findGroundMappingByVersion(Long versionId) {
        return findMappingByVersion(versionId);
    }

    private List<GroundProtocolMapping>  findMappingByVersion(Long versionId) {
        //the query converts to us ascii in sortOrder to be accent-insensitive

        return getEm()
                .createQuery(" from GroundProtocolMapping pmh " +
                                " where (pmh.protocolVersionId=:versionId)  " ,
                        GroundProtocolMapping.class)
                .setParameter("versionId", versionId)
                .getResultList();

    }
}
