package ch.cscf.midat.web.controllers.app;

import ch.cscf.jeci.web.controllers.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: henryp
 */
@Controller
@RequestMapping("/app")
public class PagesController extends AbstractController {

    @RequestMapping(value = "/samples", method = RequestMethod.GET)
    public String goToSamples(){
        return "samples";
    }

}
