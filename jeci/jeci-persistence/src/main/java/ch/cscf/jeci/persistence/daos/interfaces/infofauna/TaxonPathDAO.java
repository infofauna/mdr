package ch.cscf.jeci.persistence.daos.interfaces.infofauna;

import ch.cscf.jeci.domain.entities.systematics.TaxonPathDTO;

import java.util.List;

public interface TaxonPathDAO {

    List<TaxonPathDTO> search(String designation);

    TaxonPathDTO findById(Long taxonId);

    List<TaxonPathDTO> synonymsFor(Long taxonId);
}
