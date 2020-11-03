package ch.cscf.jeci.web.controllers.api.list;

import ch.cscf.jeci.domain.entities.base.EntityStatus;
import ch.cscf.jeci.domain.entities.base.Institution;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.InstitutionDAO;
import ch.cscf.jeci.services.entitymanagers.interfaces.InstitutionManager;
import ch.unine.sitel.o2m.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: henryp
 */
@Controller
@RequestMapping("/api/institutions")
public class InstitutionListController extends AbstractListController<Institution> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private InstitutionListController(InstitutionDAO mainDAO) {
        super(mainDAO);
    }

    @Autowired
    private InstitutionManager institutionManager;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody
    JqGridPageModel<Institution> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer rowsPerPage,
            @RequestParam(value = "orderBy", required = true) String orderBy,
            @RequestParam(value = "sortOrder", required = true) SortOrder sortOrder

    )
    {
        final Page page = new Page();
        page.setPageNumber(pageNumber);
        page.setPageSize(rowsPerPage);

        List<Institution> institutions = institutionManager.listAndTranslate(page, orderBy, sortOrder);
        List<Institution> activeInstitutions = institutions.stream().filter(i->i.getStatus().name().equalsIgnoreCase(EntityStatus.ACTIVE.toString())).collect(Collectors.toList());
        JqGridPageModel result = new JqGridPageModel(activeInstitutions, pageNumber, page.getPageSize(), page.getTotalRows());
        result.setRows(mapper.mapCollectionWithJsonPropertyObjects(result.getRows(), "[id, name, acronym, countryI18n, locality]"));

        return result;
    }

}
