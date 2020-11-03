package ch.cscf.jeci.persistence.storedprocedures.midat;

import ch.cscf.jeci.persistence.daos.interfaces.midat.ProtocolImportDAO;
import ch.cscf.jeci.persistence.storedprocedures.StoredProcedure;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: henryp
 */
public class InitTaskStatusSP extends StoredProcedure {

    private static final String SQL = "midat.PKG_VALIDATEW.p_initprocessingstatus_v2";

    public InitTaskStatusSP(DataSource ds){
        setDataSource(ds);
        setSql(SQL);
        declareParameter(new SqlOutParameter(ProtocolImportDAO.OUT_TASK_STATUS_ID, Types.BIGINT));
        compile();
    }

    public Long execute(){
        Map<String, Object> inputs = new HashMap<>(1);
        Map<String, Object> result = execute(inputs);
        return (Long)result.get(ProtocolImportDAO.OUT_TASK_STATUS_ID);

    }
}
