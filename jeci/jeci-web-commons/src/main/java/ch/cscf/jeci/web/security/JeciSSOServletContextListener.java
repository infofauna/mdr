package ch.cscf.jeci.web.security;

import ch.cscf.jeci.services.general.SpringContextProxy;
import ch.cscf.jeci.services.security.interfaces.SSOService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author: henryp
 */
public class JeciSSOServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SSOService service = SpringContextProxy.getBeanByType(SSOService.class);
        service.resetSsoCounters();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //nothing to do
    }
}
