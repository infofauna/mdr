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
public class ConfirmValidateProtocolSP extends StoredProcedure {

    private static final String SQL = "midat.PKG_VALIDATEW.p_validateconfirm";

    public ConfirmValidateProtocolSP(DataSource ds){

        setDataSource(ds);
        setSql(SQL);

        declareParameter(new SqlParameter(ProtocolImportDAO.IN_PROTOCOL_HEADER_ID, Types.BIGINT));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_LANGUAGE_ID, Types.BIGINT));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_USER_ID, Types.BIGINT));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_PUBLISHED, Types.VARCHAR));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_PUBLIC, Types.VARCHAR));

        declareParameter(new SqlOutParameter(ProtocolImportDAO.OUT_PROTOCOL_HEADER_ID, Types.BIGINT));

        compile();
    }

    public Long execute(Long protocolHeaderId, Long languageId, Long userId, boolean publish, boolean makePublic){
        Map<String, Object> inputs = new HashMap<>(3);

        inputs.put(ProtocolImportDAO.IN_PROTOCOL_HEADER_ID, protocolHeaderId);
        inputs.put(ProtocolImportDAO.IN_LANGUAGE_ID, languageId);
        inputs.put(ProtocolImportDAO.IN_USER_ID, userId);
        inputs.put(ProtocolImportDAO.IN_PUBLISHED, publish ? 'Y' : 'N');
        inputs.put(ProtocolImportDAO.IN_PUBLIC, makePublic ? 'Y' : 'N');

        Map<String,Object> resultMap = execute(inputs);
        return (Long) resultMap.get(ProtocolImportDAO.OUT_PROTOCOL_HEADER_ID);
    }




}
