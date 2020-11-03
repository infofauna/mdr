package ch.cscf.midat.web.controllers.api;

import ch.cscf.jeci.web.controllers.api.AbstractRestController;
import ch.cscf.midat.services.interfaces.InstitutionReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author: abdallah kanso
 */
@Controller
@RequestMapping("/api/search-institution/")
public class InstitutionSearchController extends AbstractRestController {

    @Autowired
    private InstitutionReadService institutionReadService;

    @RequestMapping(value = "principal-institution-names-like/{query}", method = RequestMethod.GET)
    public @ResponseBody
    List<String> findPrincipalInstitutionNamesLike(@PathVariable String query) {
        return institutionReadService.findPrincipalInstitutionNamesLike(query);
    }
}
