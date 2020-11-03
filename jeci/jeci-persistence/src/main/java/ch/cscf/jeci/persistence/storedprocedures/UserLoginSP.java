package ch.cscf.jeci.persistence.storedprocedures;

import ch.cscf.jeci.persistence.daos.interfaces.admin.SecurityDAO;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: henryp
 */
public class UserLoginSP extends StoredProcedure {

    private static final String SQL = "appmanager.PKG_ADMIN_USERW.p_login";

    public UserLoginSP(DataSource ds){
        setDataSource(ds);
        setSql(SQL);
        declareParameter(new SqlParameter(SecurityDAO.IN_USERNAME, Types.VARCHAR));
        declareParameter(new SqlParameter(SecurityDAO.IN_PASSWORD, Types.VARCHAR));
        declareParameter(new SqlParameter(SecurityDAO.IN_PASSWORD_HASH, Types.VARCHAR));
        declareParameter(new SqlParameter(SecurityDAO.IN_LANGUAGE_CODE, Types.VARCHAR));
        declareParameter(new SqlParameter(SecurityDAO.IN_IP_ADDRESS, Types.VARCHAR));
        declareParameter(new SqlParameter(SecurityDAO.IN_APPLICATION_ID, Types.BIGINT));
        declareParameter(new SqlParameter(SecurityDAO.IN_SSO_SESSION_ID, Types.VARCHAR));
        declareParameter(new SqlOutParameter(SecurityDAO.OUT_USER_ID, Types.BIGINT));
        declareParameter(new SqlOutParameter(OUT_STATUS, Types.BIGINT));
        compile();
    }

    public Map<String, Object> execute(String username, String password, String passwordHash, String languageCode, String ipAddress, Long applicationId, String ssoSessionId) throws StoredProcedureExecutionException{
        Map<String, Object> inputs = new HashMap<>(5);
        inputs.put(SecurityDAO.IN_USERNAME, username);
        inputs.put(SecurityDAO.IN_PASSWORD, password);
        inputs.put(SecurityDAO.IN_PASSWORD_HASH, passwordHash);
        inputs.put(SecurityDAO.IN_LANGUAGE_CODE, languageCode);
        inputs.put(SecurityDAO.IN_IP_ADDRESS, ipAddress);
        inputs.put(SecurityDAO.IN_APPLICATION_ID, applicationId);
        inputs.put(SecurityDAO.IN_SSO_SESSION_ID, ssoSessionId);
        Map<String, Object> result = execute(inputs);
        handleStatus(result);
        return result;
    }

}
