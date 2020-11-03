package ch.cscf.jeci.web.controllers.app;

import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;
import ch.cscf.jeci.persistence.daos.interfaces.admin.UserDAO;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.InstitutionDAO;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.PersonDAO;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.ProjectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: henryp
 */
@Controller
@RequestMapping("/app/auditinfo")
public class EntityAuditingInfoController {

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private InstitutionDAO institutionDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ProjectDAO projectDAO;

    private Map<String, GenericEntityDAO<? extends BaseEntity>> daoMapping=new HashMap<>();

    @PostConstruct
    public void setup(){
        daoMapping.put("person", personDAO);
        daoMapping.put("institution", institutionDAO);
        daoMapping.put("user", userDAO);
        daoMapping.put("project", projectDAO);
        //TODO: map all other DAO classes
    }

    @RequestMapping("{entityType}/{entityId}")
    public String renderAuditingInfo(@PathVariable String entityType, @PathVariable Long entityId, Model model){

        GenericEntityDAO<? extends BaseEntity> dao = daoMapping.get(entityType);

        if(dao == null){
            throw new IllegalArgumentException("The key "+entityType+" is not mapped to any DAO.");
        }

        model.addAttribute("entity", dao.findById(entityId, "creationUser", "updateUser"));
        model.addAttribute("entityType", entityType);

        return "/WEB-INF/jsp/audit/entityAuditingInfo.jsp";
    }
}
