package ch.cscf.jeci.persistence.daos.interfaces.infofauna;

import ch.cscf.jeci.domain.dto.infofauna.TaxonDTO;
import ch.cscf.jeci.domain.entities.systematics.Taxon;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;

/**
 * @author: henryp
 */
public interface TaxonDAO extends GenericEntityDAO<Taxon> {

    TaxonDTO findRootWithStandardDesignation();

    TaxonDTO findDTOById(Long id);

    List<TaxonDTO> findAllWithStandardDesignationByParent(Long parentTaxonId);

    List<TaxonDTO> findAll(String designation, Long rankId, String author, String year);

}
