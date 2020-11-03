package ch.cscf.jeci.services.systematics.interfaces;

import ch.cscf.jeci.domain.dto.infofauna.TaxonDTO;

import java.util.List;

public interface TaxonDTOService {

    /**
     * Load the children of the taxon specified by its ID.
     * The loaded DTOs contain synonym information too.
     * @param taxonParentId parent ID
     * @return children
     */
    List<TaxonDTO> getTaxaChildren(Long taxonParentId);

    /**
     * Load the root of taxa tree (should always be the ANIMALIA kingdom)
     * @return root of taxa tree
     */
    TaxonDTO getRootTaxon();

    /**
     * Load any taxon by its ID. Includes synonym information.
     */
    TaxonDTO getTaxonById(Long taxonId);

    /**
     * Build the "path" in the taxa tree to a given taxon.
     * The list contains the IDs of all the ancestors of the taxon, starting with the root and ending with the taxon itself.
     * @param taxonId Taxon ID
     * @return List of IDs
     */
    List<Long> buildPathToTaxon(Long taxonId);

    /**
     *
     * @param taxonId Taxon ID
     * @return TaxonDTO
     */
    TaxonDTO getTaxonIncludingAncestors(Long taxonId);

    /**
     *
     * @param designation designation
     * @param rankId rank ID
     * @param author author
     * @param year year
     * @return List of taxa
     */
    List<TaxonDTO> search(String designation, Long rankId, String author, String year);
}
