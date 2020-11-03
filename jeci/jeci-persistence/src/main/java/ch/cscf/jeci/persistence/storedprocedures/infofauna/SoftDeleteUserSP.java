package ch.cscf.jeci.persistence.storedprocedures.infofauna;

import ch.cscf.jeci.persistence.daos.interfaces.admin.UserSPDAO;
import ch.cscf.jeci.persistence.storedprocedures.StoredProcedure;
import org.springframework.jdbc.core.SqlParameter;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: henryp
 */
public class SoftDeleteUserSP extends StoredProcedure {

    private static final String SQL = "appmanager.PKG_ADMIN_USERW.P_DELETE";

    public SoftDeleteUserSP(DataSource ds){

        setDataSource(ds);
        setSql(SQL);

        declareParameter(new SqlParameter(UserSPDAO.IN_USER_ID, Types.BIGINT));
        compile();
    }

    public void execute(Long userId){
        Map<String, Object> inputs = new HashMap<>(1);
        inputs.put(UserSPDAO.IN_USER_ID, userId);
        execute(inputs);
    }
}
