package ch.cscf.jeci.persistence.daos.jpa.infofauna;

import ch.cscf.jeci.domain.dto.infofauna.TaxonDTO;
import ch.cscf.jeci.domain.entities.systematics.Taxon;
import ch.cscf.jeci.domain.entities.thesaurus.Language;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.TaxonDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TaxonJpaDAO extends GenericJpaEntityDAO<Taxon> implements TaxonDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaxonJpaDAO.class);

    private final String baseQuery =
    "select NEW ch.cscf.jeci.domain.dto.infofauna.TaxonDTO(" +
        "taxon.id, taxon.parentTaxonId, taxdes.designation, rank.designation, taxon.authorYear," +
        "(select count(child) from Taxon as child where child.parentTaxonId = taxon.id) as childrenCount," +
        "(select count(syn) from Taxon as child join child.synonymTarget syn where child.parentTaxonId = taxon.id) as synonymChildrenCount," +
        "(select count(syn) from Taxon as syn where syn.synonymTarget.id = taxon.id) as synonymsCount," +
        " syn.id, syndes.designation, syn.authorYear" +
    ") " +
    "from Taxon taxon " +
    "join taxon.designations taxdes on taxdes.languageId = :languageId " +
    "join taxon.taxonomicRank rank " +
    "left join taxon.synonymTarget syn " +
    "left join syn.designations syndes on syndes.languageId = :languageId "
    ;

    @Override
    public TaxonDTO findRootWithStandardDesignation() {
        return (TaxonDTO) getEm().createQuery(baseQuery + "where taxon.parentTaxonId is null")
                .setParameter("languageId", Language.LATIN_ID)
                .getSingleResult();
    }

    @Override
    public List<TaxonDTO> findAllWithStandardDesignationByParent(Long parentTaxonId) {
        LOGGER.debug("Find taxa children whose parentTaxonId={}", parentTaxonId);
        List<TaxonDTO> taxa = getEm().createQuery(baseQuery + "where taxon.parentTaxonId = :parentTaxonId order by taxdes.designation")
                                .setParameter("parentTaxonId", parentTaxonId)
                                .setParameter("languageId", Language.LATIN_ID)
                                .getResultList();
        LOGGER.debug("Taxa children count: {}", taxa.size());
        return taxa;
    }

    @Override
    public TaxonDTO findDTOById(Long id) {
        return (TaxonDTO) getEm().createQuery(baseQuery + "where taxon.id=:id")
                .setParameter("id", id)
                .setParameter("languageId", Language.LATIN_ID)
                .getSingleResult();
    }

    @Override
    public List<TaxonDTO> findAll(String designation, Long rankId, String author, String year) {
        LOGGER.debug("Find taxa with designation like \"{}\" and rankId={}", designation, rankId);
        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("languageId", Language.LATIN_ID);
        parametersMap.put("designation", designation + '%');
        String hsql = baseQuery + " where lower(taxdes.designation) like lower(:designation)";
        if (rankId != null) {
            hsql += " and rank.id = :rankId";
            parametersMap.put("rankId", rankId);
        }
        if (author != null && !author.isEmpty()) {
            hsql += " and lower(taxon.author) like lower(:author)";
            parametersMap.put("author", author + '%');
        }
        if (year != null && !year.isEmpty()) {
            hsql += " and taxon.year = :year";
            parametersMap.put("year", year);
        }
        hsql += " order by taxdes.designation";

        Query query = getEm().createQuery(hsql);
        // Set all parameters from map
        parametersMap.forEach(query::setParameter);
        List<TaxonDTO> taxa = query.getResultList();

        LOGGER.debug("Taxa children count: {}", taxa.size());

        return taxa;
    }
}
