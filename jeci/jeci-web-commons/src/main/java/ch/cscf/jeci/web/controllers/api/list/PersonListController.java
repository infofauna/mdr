package ch.cscf.jeci.web.controllers.api.list;

import ch.cscf.jeci.domain.entities.base.Person;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.PersonDAO;
import ch.cscf.jeci.services.entitymanagers.interfaces.PersonManager;
import ch.unine.sitel.o2m.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author: henryp
 */
@Controller
@RequestMapping("/api/persons")
public class PersonListController extends AbstractListController<Person> {

    @Autowired
    private Mapper mapper;

    private PersonDAO personDAO;

    @Autowired
    private PersonManager personManager;

    @Autowired
    public PersonListController(PersonDAO mainDAO) {
        super(mainDAO);
        this.personDAO=mainDAO;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody
    JqGridPageModel<Person> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer rowsPerPage,
            @RequestParam(value = "orderBy", required = true) String orderBy,
            @RequestParam(value = "sortOrder", required = true) SortOrder sortOrder,
            @RequestParam(value = "search", required = false, defaultValue = "") String search

    )
    {
        JqGridPageModel result;

        final Page page = new Page();
        page.setPageNumber(pageNumber);
        page.setPageSize(rowsPerPage);

        final List<Person> persons = personManager.listFilterByNamesAndTranslate(orderBy, sortOrder, page, search);

        //fill the thesaurus localized values
        for(Person entity : persons){
            entityFieldTranslatorService.fillLocalizedFields(entity);
        }

        result = new JqGridPageModel(persons, pageNumber, page.getPageSize(), page.getTotalRows());

        result.setRows(mapper.mapCollectionWithJsonPropertyObjects(result.getRows(), "[id, firstName, lastName, zipCode, titleI18n, genderI18n, countryI18n, locality, email]"));
        return result;
    }

}
