package ch.cscf.jeci.services.general;

import ch.cscf.jeci.domain.entities.security.JeciApplication;
import ch.cscf.jeci.persistence.daos.interfaces.admin.JeciApplicationDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

/**
 * @author: henryp
 */
public class ActiveApplicationService {

    private Long applicationId;
    private JeciApplication application;

    @Autowired
    private JeciApplicationDAO appDao;

    @Autowired
    private ServletContext servletContext;

    public ActiveApplicationService(Long applicationId) {
        this.applicationId = applicationId;
    }

    @PostConstruct
    public void setupApplication(){
        this.application = appDao.findById(this.applicationId);
        servletContext.setAttribute("currentJeciApplication", application);
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public JeciApplication getApplication() {
        return application;
    }
}
