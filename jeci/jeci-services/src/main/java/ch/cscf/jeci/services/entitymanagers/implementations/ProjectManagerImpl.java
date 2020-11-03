package ch.cscf.jeci.services.entitymanagers.implementations;

import ch.cscf.jeci.domain.entities.base.Project;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.ProjectDAO;
import ch.cscf.jeci.services.entitymanagers.interfaces.ProjectManager;
import ch.cscf.jeci.services.general.EntityFieldTranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kanso on 28.06.2017
 */
@Service
public class ProjectManagerImpl implements ProjectManager {

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private EntityFieldTranslatorService translatorService;

    @Override
    @Transactional
    public List<Project> listFilterByMultiCriteriasAndTranslate(String orderByCol, SortOrder sortOrder, Page page,
                                                                String codeName, String institutionName) {
        List<Project> projects = projectDAO.listFilterByMultiCriterias(orderByCol, sortOrder, page, codeName, institutionName);
        translatorService.fillLocalizedFields(projects);
        return projects;
    }
}
