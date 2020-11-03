package ch.cscf.jeci.persistence.daos.interfaces.admin;

import ch.cscf.jeci.persistence.storedprocedures.StoredProcedureExecutionException;

import java.util.Map;

/**
 * @author: henryp
 */
public interface SecurityDAO {

    String IN_USERNAME = "inUsername";
    String IN_PASSWORD = "inPassword";
    String IN_PASSWORD_HASH = "inPasswordHash";
    String IN_LANGUAGE_CODE = "inLanguageCode";
    String IN_IP_ADDRESS = "inIpAddress";
    String IN_SSO_SESSION_ID = "inSsoSessionId";
    String IN_APPLICATION_ID = "inApplicationId";
    String OUT_USER_ID = "outUserId";

    public Map<String, Object> authenticate(String username, String password, String passwordHash, String languageCode, String ipAddress, Long applicationId, String ssoSessionId) throws StoredProcedureExecutionException;
}
