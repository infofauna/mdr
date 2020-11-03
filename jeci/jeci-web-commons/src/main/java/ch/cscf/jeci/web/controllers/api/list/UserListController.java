package ch.cscf.jeci.web.controllers.api.list;

import ch.cscf.jeci.domain.entities.security.User;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;
import ch.cscf.jeci.persistence.daos.interfaces.admin.UserDAO;
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
@RequestMapping("/api/users")
public class UserListController extends AbstractListController<User> {

    @Autowired
    private Mapper mapper;

    private UserDAO userDAO;

    @Autowired
    public UserListController(UserDAO mainDAO) {
        super(mainDAO);
        this.userDAO=mainDAO;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody
    JqGridPageModel<User> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer rowsPerPage,
            @RequestParam(value = "orderBy", required = true) String orderBy,
            @RequestParam(value = "sortOrder", required = true) SortOrder sortOrder,
            @RequestParam(value = "search", required = false, defaultValue = "") String search

    )
    {
        JqGridPageModel result;

        if(search.length()>0){
            final Page page = new Page();
            page.setPageNumber(pageNumber);
            page.setPageSize(rowsPerPage);

            final List<User> users = userDAO.listAndFilterByUsername(orderBy, sortOrder, page, search);

            //fill the thesaurus localized values
            for(User entity : users){
                entityFieldTranslatorService.fillLocalizedFields(entity);
            }

            result = new JqGridPageModel(users, pageNumber, page.getPageSize(), page.getTotalRows());

        }else{
            result = super.list(pageNumber, rowsPerPage, orderBy, sortOrder);
        }

        result.setRows(mapper.mapCollectionWithJsonPropertyObjects(result.getRows(), "[id, username, ldap, {person : [names]}]"));

        return result;
    }

}
