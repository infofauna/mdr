package ch.cscf.jeci.persistence.daos.jpa.midat;


import ch.cscf.jeci.domain.entities.midat.ProtocolMappingMassMap;
import ch.cscf.jeci.persistence.daos.interfaces.midat.ProtocolMappingMassMapDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author: kanso
 */
@Repository
public class ProtocolMappingMassMapJpaDAO extends GenericJpaEntityDAO<ProtocolMappingMassMap> implements ProtocolMappingMassMapDAO {


    @Override
    public List<ProtocolMappingMassMap>  findMappingByLang(Long languageId) {
        return findMappingByLangQuery(languageId);
    }

    private List<ProtocolMappingMassMap>  findMappingByLangQuery(Long languageId) {
        //the query converts to us ascii in sortOrder to be accent-insensitive

       return getEm()
                        .createQuery(" from ProtocolMappingMassMap p " +
                                        " where (p.languageId=:languageId)  " ,
                                ProtocolMappingMassMap.class)
                        .setParameter("languageId", languageId)
                        .getResultList();

    }
}
