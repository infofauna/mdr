package ch.cscf.jeci.web.controllers.app.authentication;

import ch.cscf.jeci.domain.entities.security.User;
import ch.cscf.jeci.services.general.I18nService;
import ch.cscf.jeci.services.security.interfaces.SSOService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Spring MVC controller for the authentication of users.
 * @author Pierre Henry
 */
@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private SSOService ssoService;

    @Autowired
    private I18nService i18n;

    @RequestMapping(value="/login",method= RequestMethod.POST)
    public String login(@ModelAttribute LoginCommand command, BindingResult errors) {
        validateLoginForm(errors);

        if( errors.hasErrors() ) {
            return showLoginForm(command);
        }

        UsernamePasswordToken token = new UsernamePasswordToken(command.getUsername(), command.getPassword());
        try {
            SecurityUtils.getSubject().login(token);
        } catch (AuthenticationException e) {
            errors.reject( "error.login.generic", i18n.getMessage("login.badLogin"));
        }

        if( errors.hasErrors() ) {
            return showLoginForm(command);
        } else {
            return "redirect:/app/home";
        }
    }

    @RequestMapping(value="/login",method= RequestMethod.GET)
    public String showLoginForm(@ModelAttribute LoginCommand command ) {
        return "login";
    }

    @RequestMapping({"/", "login"})
    public String login(@ModelAttribute LoginCommand command ){
        return showLoginForm(command);
    }

    @RequestMapping("/logout")
    public String logout(Model model, HttpSession session) {
        session.setAttribute(SSOService.SESSION_LOGGED_OUT, true);
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        subject.logout();
        if(user != null){
            ssoService.invalidateSsoSessionForUser(user.getId());
        }
        model.addAttribute("loggedOut", true);
        return "redirect:/authentication/login";
    }

    private void validateLoginForm(Errors errors){
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.username.missing", i18n.getMessage("login.username.missing"));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password.missing", i18n.getMessage("login.password.missing"));
    }

}
