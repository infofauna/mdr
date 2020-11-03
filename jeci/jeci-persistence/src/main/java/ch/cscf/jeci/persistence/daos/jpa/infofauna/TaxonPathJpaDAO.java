package ch.cscf.jeci.persistence.daos.jpa.infofauna;

import ch.cscf.jeci.domain.entities.systematics.TaxonPathDTO;
import ch.cscf.jeci.domain.entities.thesaurus.Language;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.TaxonPathDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TaxonPathJpaDAO implements TaxonPathDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TaxonPathDTO> search(String designation) {
        return em.createNamedQuery(TaxonPathDTO.QUERY_SEARCH_TAXON_PATH)
                 .setParameter("languageId", Language.LATIN_ID)
                 .setParameter("designation", designation)
                 .getResultList()
        ;
    }

    @Override
    public TaxonPathDTO findById(Long taxonId) {
        return (TaxonPathDTO) em.createNamedQuery(TaxonPathDTO.QUERY_GET_TAXON_PATH)
                                .setParameter("languageId", Language.LATIN_ID)
                                .setParameter("taxonId", taxonId)
                                .getSingleResult()
        ;
    }

    @Override
    public List<TaxonPathDTO> synonymsFor(Long taxonId) {
        return em.createNamedQuery(TaxonPathDTO.QUERY_SYNONYMS_TAXON_PATH)
                .setParameter("languageId", Language.LATIN_ID)
                .setParameter("taxonId", taxonId)
                .getResultList()
        ;
    }

}
