package ch.cscf.jeci.services.entitymanagers.implementations;

import ch.cscf.jeci.domain.entities.base.Institution;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.InstitutionDAO;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.PersonDAO;
import ch.cscf.jeci.services.entitymanagers.interfaces.InstitutionManager;
import ch.cscf.jeci.services.general.EntityFieldTranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by henryp on 15/09/15.
 */
@Service
public class InstitutionManagerImpl implements InstitutionManager {

    @Autowired
    private InstitutionDAO institutionDAO;

    @Autowired
    private EntityFieldTranslatorService translatorService;

    @Override
    @Transactional(readOnly = true)
    public List<Institution> listAndTranslate(Page page, String orderBy, SortOrder sortOrder) {

        List<Institution> institutions = institutionDAO.list(orderBy, sortOrder, page);
        translatorService.fillLocalizedFields(institutions);

        return institutions;
    }
}
