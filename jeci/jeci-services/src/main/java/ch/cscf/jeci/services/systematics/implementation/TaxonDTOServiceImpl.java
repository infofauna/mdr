package ch.cscf.jeci.services.systematics.implementation;

import ch.cscf.jeci.domain.dto.infofauna.TaxonDTO;
import ch.cscf.jeci.domain.entities.systematics.Taxon;
import ch.cscf.jeci.domain.entities.systematics.TaxonDesignation;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.TaxonDAO;
import ch.cscf.jeci.services.general.I18nService;
import ch.cscf.jeci.services.systematics.interfaces.TaxonDTOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaxonDTOServiceImpl implements TaxonDTOService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaxonDAO dao;

    @Autowired
    private I18nService i18n;

    @Override
    public List<TaxonDTO> getTaxaChildren(Long taxonParentId) {
        return dao.findAllWithStandardDesignationByParent(taxonParentId);
    }

    @Override
    public TaxonDTO getRootTaxon () {
        return dao.findRootWithStandardDesignation();
    }

    @Override
    public TaxonDTO getTaxonById(Long taxonId) {
        return dao.findDTOById(taxonId);
    }

    @Override
    public List<TaxonDTO> search(String designation, Long rankId, String author, String year) {
        return dao.findAll(designation, rankId, author, year);
    }

    @Override
    @Transactional(readOnly = true)
    public TaxonDTO getTaxonIncludingAncestors(Long taxonId) {

        Taxon taxon = dao.findById(taxonId);
        TaxonDTO dto = buildDTO(taxon);

        if(taxon.getParentTaxonId() != null) {
            dto.setParent(getTaxonIncludingAncestors(taxon.getParentTaxonId()));
        }

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> buildPathToTaxon(Long taxonId) {

        Taxon taxon = dao.findById(taxonId);
        LinkedList<Long> ids = new LinkedList<>();
        ids.add(0, taxon.getId());

        Taxon parent = taxon.getParent();
        while(parent != null){
            ids.add(0, parent.getId());
            parent = parent.getParent();
        }
        return ids;
    }

    private TaxonDTO buildDTO(Taxon taxon){

        String designation, taxonomicRankDesignation;
        designation = getLatinDesignation(taxon);
        taxonomicRankDesignation = taxon.getTaxonomicRank().getDesignation();
        Long childrenCount = (long) taxon.getChildren().size();
        Long synonymChildrenCount = -1L; // dummy
        Long synonymsCount = -1L; // dummy
        return new TaxonDTO(taxon.getId(), taxon.getParentTaxonId(), designation, taxonomicRankDesignation, taxon.getAuthorYear(), childrenCount, synonymChildrenCount, synonymsCount, null, null, null);
    }

    private String getLatinDesignation(Taxon taxon){

        //filter list of designations to only the ones in latin
        List<TaxonDesignation> latinDesignations = taxon.getDesignations().stream().filter(d -> d.getLanguageId().equals(i18n.languageIdForCode("la"))).collect(Collectors.toList());

        //should always be exactly one
        if(latinDesignations.isEmpty()){
            throw new IllegalStateException("There is no latin designation for the taxon with ID = "+taxon.getId());
        }

        if(latinDesignations.size()>1){
            logger.warn("There is more than one latin designation for the taxon with ID="+taxon.getId());
        }

        return latinDesignations.get(0).getDesignation();
    }

}
