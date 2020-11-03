package ch.cscf.jeci.persistence.storedprocedures.midat;

import ch.cscf.jeci.persistence.daos.interfaces.midat.ProtocolImportDAO;
import ch.cscf.jeci.persistence.storedprocedures.StoredProcedure;
import ch.cscf.jeci.domain.dto.jeci.UploadedFileInfo;
import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.OracleLobHandler;
import org.springframework.jdbc.support.nativejdbc.CommonsDbcpNativeJdbcExtractor;

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
public class GetExcelSheetsSP extends StoredProcedure {

    private static final String SQL = "midat.PKG_EXCELW.p_buildsheetlist";

    public GetExcelSheetsSP(DataSource ds){
        setDataSource(ds);
        setSql(SQL);
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_EXCEL_FILE_BLOB, Types.BLOB));
        declareParameter(new SqlOutParameter(ProtocolImportDAO.OUT_CURSOR, OracleTypes.CURSOR, new RowMapper() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString(2);
            }
        }));
        declareParameter(new SqlOutParameter(ProtocolImportDAO.OUT_SHEET_COUNT, Types.BIGINT));
        compile();
    }

    public List<String> execute(UploadedFileInfo fileInfo){
        Map<String, Object> inputs = new HashMap<>(1);

        DefaultLobHandler handler;
        OracleLobHandler lh = new OracleLobHandler();
        lh.setNativeJdbcExtractor(new CommonsDbcpNativeJdbcExtractor());

        inputs.put(ProtocolImportDAO.IN_EXCEL_FILE_BLOB, new SqlLobValue(fileInfo.getBytes(), lh));

        Map<String, Object> result = execute(inputs);
        return (List<String>)result.get(ProtocolImportDAO.OUT_CURSOR);
    }




}
