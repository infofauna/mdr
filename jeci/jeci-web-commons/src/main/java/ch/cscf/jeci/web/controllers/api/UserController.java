package ch.cscf.jeci.web.controllers.api;

import ch.cscf.jeci.services.security.interfaces.SessionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by henryp on 09/03/15.
 */
@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    SessionUserService sessionUserService;

    @RequestMapping("/session-user")
    public @ResponseBody Map<String, Object> getSessionUser(){

        return sessionUserService.getSessionUserWithMappedFields("username", "roles.name", "roles.id", "groups.name", "groups.id");
    }

}
