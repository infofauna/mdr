package ch.cscf.jeci.persistence.daos.sp.admin;

import ch.cscf.jeci.persistence.daos.interfaces.admin.SecurityDAO;
import ch.cscf.jeci.persistence.daos.sp.StoredProcedureDAO;
import ch.cscf.jeci.persistence.storedprocedures.StoredProcedureExecutionException;
import ch.cscf.jeci.persistence.storedprocedures.UserLoginSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author: henryp
 */
@Repository
public class SecuritySPDAO extends StoredProcedureDAO implements SecurityDAO {

    private UserLoginSP userLoginSP;

    @Autowired
    public SecuritySPDAO(DataSource jeciDataSource){
        super(jeciDataSource);
        userLoginSP = new UserLoginSP(jeciDataSource);
    }

    @Override
    public Map<String, Object> authenticate(String username, String password, String passwordHash, String languageCode, String ipAddress, Long applicationId, String ssoSessionId) throws StoredProcedureExecutionException {

        return userLoginSP.execute(username, password, passwordHash, languageCode, ipAddress, applicationId, ssoSessionId);
    }

}
