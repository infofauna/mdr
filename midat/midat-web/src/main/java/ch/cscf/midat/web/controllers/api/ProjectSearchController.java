package ch.cscf.midat.web.controllers.api;

import ch.cscf.jeci.web.controllers.api.AbstractRestController;
import ch.cscf.midat.services.interfaces.ProjectReadService;
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
@RequestMapping("/api/search-project/")
public class ProjectSearchController extends AbstractRestController {

    @Autowired
    private ProjectReadService projectReadService;

    @RequestMapping(value = "project-names-like/{query}", method = RequestMethod.GET)
    public @ResponseBody
    List<String> findProjectNamesLike(@PathVariable String query) {
        return projectReadService.findProjectNamesLike(query);
    }
}
