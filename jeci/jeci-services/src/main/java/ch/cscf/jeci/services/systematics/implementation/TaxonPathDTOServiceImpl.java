package ch.cscf.jeci.services.systematics.implementation;

import ch.cscf.jeci.domain.entities.systematics.TaxonPathDTO;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.TaxonPathDAO;
import ch.cscf.jeci.services.systematics.interfaces.TaxonPathDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaxonPathDTOServiceImpl implements TaxonPathDTOService {

    //private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaxonPathDAO dao;

    @Override
    @Transactional(readOnly = true)
    public List<TaxonPathDTO> search(String designation) {
        return dao.search(designation);
    }

    @Override
    @Transactional(readOnly = true)
    public TaxonPathDTO findById(Long taxonId) {
        return dao.findById(taxonId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaxonPathDTO> synonymsFor(Long taxonId) {
        return dao.synonymsFor(taxonId);
    }

}
