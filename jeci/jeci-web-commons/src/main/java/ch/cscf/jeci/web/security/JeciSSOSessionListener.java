package ch.cscf.jeci.web.security;

import ch.cscf.jeci.domain.entities.security.User;
import ch.cscf.jeci.services.general.SpringContextProxy;
import ch.cscf.jeci.services.security.interfaces.SSOService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author: henryp
 */
public class JeciSSOSessionListener implements HttpSessionListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

        logger.info("Session destroyed.");

        if(se.getSession().getAttribute(SSOService.SESSION_LOGGED_OUT) == null){

            logger.info("Session seems to be destroyed by time out, not logout. Now decrementing the SSO session counter.");

            SSOService service = SpringContextProxy.getBeanByType(SSOService.class);

            SimplePrincipalCollection principals = (SimplePrincipalCollection)se.getSession().getAttribute(org.apache.shiro.subject.support.DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if(principals != null){
                User currentUser = (User)principals.asList().get(0);
                service.decrementSSOTokenForUser(currentUser.getId());
            }

            Subject currentSubject = SecurityUtils.getSubject();
            currentSubject.logout();
        }
    }
}
