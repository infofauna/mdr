package ch.cscf.jeci.persistence.daos.jpa.infofauna;

import ch.cscf.jeci.domain.entities.thesaurus.LocalizedThesaurusEntry;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.LocalizedThesaurusEntryDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author: henryp
 */
@Repository
public class LocalizedThesaurusEntryJPADAO implements LocalizedThesaurusEntryDAO{

    /**
     * Shared, thread-safe proxy for the actual transactional EntityManager, managed by Spring
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Access to the entity manager. For consistency with generic DAOs.
     * @return
     */
    private EntityManager getEm(){
        return em;
    }

    @Override
    public List<LocalizedThesaurusEntry> findEntriesForRealm(String realmCode) {
        Query entriesForRealmQuery = getEm().createQuery("select entry from LocalizedThesaurusEntry entry where entry.realmCode=:realmCode order by entry.valueSortOrder, entry.code");
        List<LocalizedThesaurusEntry> realmEntries = entriesForRealmQuery.setParameter("realmCode", realmCode).getResultList();
        return realmEntries;
    }

    @Override
     public String getRealmCodeForValueId(Long valueId){
        String realmCode = getEm().createQuery("select entry.realmCode from LocalizedThesaurusEntry entry where entry.valueId=:valueId", String.class)
                .setParameter("valueId", valueId)
                .getSingleResult();

        return realmCode;
    }

}
