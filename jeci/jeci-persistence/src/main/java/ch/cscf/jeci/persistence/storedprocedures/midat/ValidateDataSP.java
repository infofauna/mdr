package ch.cscf.jeci.persistence.storedprocedures.midat;

import ch.cscf.jeci.domain.dto.errors.Message;
import ch.cscf.jeci.persistence.daos.interfaces.midat.ProtocolImportDAO;
import ch.cscf.jeci.persistence.storedprocedures.StoredProcedure;
import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: henryp
 */
public class ValidateDataSP extends StoredProcedure {

    private static final String SQL = "midat.PKG_VALIDATEW.p_validateprocess_v2";

    public ValidateDataSP(DataSource ds){
        setDataSource(ds);
        setSql(SQL);


        declareParameter(new SqlParameter(ProtocolImportDAO.IN_PROTOCOL_HEADER_ID, Types.BIGINT));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_LANGUAGE_ID, Types.BIGINT));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_USER_ID, Types.BIGINT));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_TASK_STATUS_ID, Types.VARCHAR));

        //cursor containing the messages
        declareParameter(new SqlOutParameter(ProtocolImportDAO.OUT_CURSOR, OracleTypes.CURSOR, new RowMapper() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                String severity = rs.getString(1);
                String message = rs.getString(2);
                String fieldName = rs.getString(3);
                Long exceptionNumber =  rs.getLong(4);

                return new Message(severity, message, fieldName, exceptionNumber);
            }
        }));

        //out param containing the level of the highest error
        declareParameter(new SqlOutParameter(ProtocolImportDAO.OUT_MAX_ERROR_LEVEL, Types.VARCHAR));

        compile();
    }

    public List<Message> execute(Long protocolHeaderId, Long languageId, Long userId, Long taskStatusId){
        Map<String, Object> inputs = new HashMap<>(3);

        inputs.put(ProtocolImportDAO.IN_PROTOCOL_HEADER_ID, protocolHeaderId);
        inputs.put(ProtocolImportDAO.IN_LANGUAGE_ID, languageId);
        inputs.put(ProtocolImportDAO.IN_USER_ID, userId);
        inputs.put(ProtocolImportDAO.IN_TASK_STATUS_ID, taskStatusId);

        Map<String, Object> result = execute(inputs);

        return (List<Message>)result.get(ProtocolImportDAO.OUT_CURSOR);
    }

}
