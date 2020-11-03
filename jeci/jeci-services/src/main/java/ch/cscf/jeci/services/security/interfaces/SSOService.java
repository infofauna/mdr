package ch.cscf.jeci.services.security.interfaces;

/**
 * Created by henryp on 17/03/15.
 */
public interface SSOService {
    String SESSION_LOGGED_OUT="jeciSsoLoggedOut";

    void incrementSSOTokenForUser(Long userId);

    void decrementSSOTokenForUser(Long userId);

    void invalidateSsoSessionForUser(Long userId);

    void resetSsoCounters();

    void setCurrentHttpSessionId(String sessionId);

    String getCurrentHttpSessionId();

    String getCurrentClientIp();

    void setCurrentClientIp(String currentClientIp);
}
