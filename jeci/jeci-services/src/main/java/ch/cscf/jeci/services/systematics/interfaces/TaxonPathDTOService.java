package ch.cscf.jeci.services.systematics.interfaces;

import ch.cscf.jeci.domain.entities.systematics.TaxonPathDTO;

import java.util.List;

public interface TaxonPathDTOService {
    List<TaxonPathDTO> search(String designation);

    TaxonPathDTO findById(Long taxonId);

    List<TaxonPathDTO> synonymsFor(Long taxonId);
}
