package ch.cscf.jeci.services.systematics.interfaces;

import ch.cscf.jeci.domain.entities.systematics.Taxon;
import ch.cscf.jeci.domain.entities.systematics.TaxonomicRank;

import java.util.List;

/**
 * @author: henryp
 */
public interface SystematicsReadOnlyService {

    Taxon getTaxonById(Long taxonId);

    TaxonomicRank getRankById(Long rankId);

    List<TaxonomicRank> getAllRanks();

    List<Taxon> getTaxonByParentId(Long parentTaxonId);

    List<Taxon> getTaxonsByRankId(Long taxonomyRankId);

    Taxon getParentByChildId(Long childId);

    String getRankDesignation(Long rankId);

    /**
     * Gets the ancestor of a given rank for a given taxon.
     * @param taxonId
     * @param ancestorRankCode
     * @return
     */
    Taxon getAncestorOfRank(Long taxonId, String ancestorRankCode, boolean includeSelf);

    /**
     * Gets the designation string of an ancestor of a given rank for a given taxon.
     * @param taxonId
     * @param ancestorRankCode
     * @return
     */
    String getAncestorDesignationForRank(Long taxonId, String ancestorRankCode, boolean includeSelf);

    String taxonToString(Long taxonId, int deepness, String separator);

    /**
     * Builds a string representation of the given taxon, including all the taxonomic ranks specified in the listAndTranslate, by code.
     *
     * See test cases for examples of input and outputs.
     *
     * @param taxonId ID of the taxon to be represented.
     * @param rankCodesToInclude Codes of the taxonomic ranks to be included
     * @param separator a String to be used to separate the taxonomic ranks in the resulting string
     * @return A String representation of the taxon, starting with the highest rank in the listAndTranslate and ending with the lowest.
     */
    String taxonToString(Long taxonId, List<String> rankCodesToInclude, String separator);
}
