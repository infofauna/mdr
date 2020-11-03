package ch.cscf.jeci.services.security.implementation;

import ch.cscf.jeci.domain.entities.security.User;
import ch.cscf.jeci.persistence.daos.interfaces.admin.UserDAO;
import ch.cscf.jeci.services.security.interfaces.SSOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

/**
 * @author: henryp
 */
@Service
public class JeciSSOService implements SSOService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ThreadLocal<String> currentHttpSessionId = new ThreadLocal<>();
    private ThreadLocal<String> currentClientIp = new ThreadLocal<>();

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public void incrementSSOTokenForUser(Long userId){

        User user = userDAO.findById(userId);
        if(user.getSsoSessionCounter() == null){
            logger.info("Session counter for user was null. Setting to 1.");
            user.setSsoSessionCounter(1);
        }else{
            logger.info(MessageFormat.format("Session counter for user {0} was {1}. Will be set to {2}.", user.getUsername(), user.getSsoSessionCounter(), user.getSsoSessionCounter()+1));
            user.setSsoSessionCounter(user.getSsoSessionCounter()+1);
        }
    }

    @Override
    @Transactional
    public void decrementSSOTokenForUser(Long userId){

        User user = userDAO.findById(userId);

        Integer ssoSessionCounter = user.getSsoSessionCounter();
        if(user.getSsoSessionId() != null && ssoSessionCounter > 1){
            user.setSsoSessionCounter(ssoSessionCounter-1);
        }else{
            user.setSsoSessionCounter(0);
            user.setSsoSessionId(null);
            user.setSsoIpAddress(null);
        }

    }

    @Override
    @Transactional
    public void invalidateSsoSessionForUser(Long userId) {
        User user = userDAO.findById(userId);
        user.setSsoSessionCounter(0);
        user.setSsoSessionId(null);
        user.setSsoIpAddress(null);
    }

    @Override
    @Transactional
    public void resetSsoCounters(){
        userDAO.resetAllSsoCounters();
    }

    @Override
    public void setCurrentHttpSessionId(String sessionId){
        currentHttpSessionId.set(sessionId);
    }

    @Override
    public String getCurrentHttpSessionId(){
        return currentHttpSessionId.get();
    }

    @Override
    public String getCurrentClientIp() {
        return this.currentClientIp.get();
    }

    @Override
    public void setCurrentClientIp(String currentClientIp) {
        this.currentClientIp.set(currentClientIp);
    }


}
