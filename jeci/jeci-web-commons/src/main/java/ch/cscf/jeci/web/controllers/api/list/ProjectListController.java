package ch.cscf.jeci.web.controllers.api.list;

import ch.cscf.jeci.domain.entities.base.Project;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.ProjectDAO;
import ch.cscf.jeci.services.entitymanagers.interfaces.ProjectManager;
import ch.unine.sitel.o2m.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author: kansoa
 */
@Controller
@RequestMapping("/api/projects")
public class ProjectListController extends AbstractListController<Project> {

    @Autowired
    private Mapper mapper;

    private ProjectDAO projectDAO;

    @Autowired
    private ProjectManager projectManager;

    @Autowired
    public ProjectListController(ProjectDAO mainDAO) {
        super(mainDAO);
        this.projectDAO = mainDAO;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody
    JqGridPageModel<Project> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer rowsPerPage,
            @RequestParam(value = "orderBy", required = true) String orderBy,
            @RequestParam(value = "sortOrder", required = true) SortOrder sortOrder,
            @RequestParam(value = "searchCodeName", required = false, defaultValue = "") String searchCodeName,
            @RequestParam(value = "searchInstitutionName", required = false, defaultValue = "") String searchInstitutionName

    ) {
        JqGridPageModel result;

        final Page page = new Page();
        page.setPageNumber(pageNumber);
        page.setPageSize(rowsPerPage);

        final List<Project> projects = projectManager.listFilterByMultiCriteriasAndTranslate(orderBy, sortOrder, page, searchCodeName, searchInstitutionName);

        //fill the thesaurus localized values
        for (Project entity : projects) {
            entityFieldTranslatorService.fillLocalizedFields(entity);
        }

        result = new JqGridPageModel(projects, pageNumber, page.getPageSize(), page.getTotalRows());

        result.setRows(mapper.mapCollectionWithJsonPropertyObjects(result.getRows(), "[id,principalInstitution.name, mandataryInstitution.name,projectTypeI18n,projectOriginI18n,projectLimaI18n,echeanceBlocage,code,codeCh,designation, description" +
                ",principalInstitutionName,principalInstitutionRespFirstName,principalInstitutionRespLastName,mandataryInstitutionName,mandataryInstitutionRespFirstName,mandataryInstitutionRespLastName,voluntaryWork,url,startDate,endDate]"));
        return result;
    }

}