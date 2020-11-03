package ch.cscf.jeci.web.controllers.app;

import ch.cscf.jeci.persistence.daos.interfaces.admin.SecurityDAO;
import ch.cscf.jeci.persistence.daos.interfaces.admin.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Spring MVC controller for the home page.
 * Hust loads a bunch of info to be displayed on the home page.
 * @author Pierre Henry
 */
@Controller
@RequestMapping({"/app/home"})
public class HomeController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SecurityDAO securityDAO;

    @RequestMapping()
    public String goHome(Model model, HttpServletRequest request){

        return "home";

    }
}
