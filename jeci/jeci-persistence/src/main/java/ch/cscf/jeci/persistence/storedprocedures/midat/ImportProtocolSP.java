package ch.cscf.jeci.persistence.storedprocedures.midat;

import ch.cscf.jeci.domain.entities.midat.ProtocolImportHeader;
import ch.cscf.jeci.persistence.daos.interfaces.midat.ProtocolImportDAO;
import ch.cscf.jeci.persistence.storedprocedures.StoredProcedure;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.OracleLobHandler;
import org.springframework.jdbc.support.nativejdbc.CommonsDbcpNativeJdbcExtractor;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: henryp
 */
public class ImportProtocolSP extends StoredProcedure {

    private static final String SQL = "midat.PKG_IMPORTPROTOCOLHEADERW.p_insertlabodata";

    public ImportProtocolSP(DataSource ds){

        setDataSource(ds);
        setSql(SQL);

        declareParameter(new SqlParameter(ProtocolImportDAO.IN_PRINCIPAL_INSTITUTION_ID, Types.BIGINT));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_MANDATARY_INSTITUTION_ID, Types.BIGINT));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_PROTOCOL_VERSION_ID, Types.BIGINT));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_ANALYSIS_PERSON_ID, Types.BIGINT));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_ANALYSIS_DATE, Types.DATE));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_OPERATOR_PERSON_ID, Types.BIGINT));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_SAMPLE_DATE, Types.DATE));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_INDICE_TYPE_ID, Types.BIGINT));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_DOCUMENT_URL, Types.VARCHAR));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_LANGUAGE_ID, Types.BIGINT));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_REFERENCE_SYSTEM_ID, Types.BIGINT));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_PRECISION_LEVEL_ID, Types.BIGINT));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_EXCEL_FILE_NAME, Types.VARCHAR));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_EXCEL_FILE_BLOB, Types.BLOB));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_EXCEL_SHEET_NAME, Types.VARCHAR));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_USER_ID, Types.BIGINT));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_PARENT_ID, Types.BIGINT));
        declareParameter(new SqlParameter(ProtocolImportDAO.IN_PROJECT_ID, Types.BIGINT));

        declareParameter(new SqlOutParameter(ProtocolImportDAO.OUT_PROTOCOL_IMPORT_ID, Types.BIGINT));

        compile();
    }

    public Long execute(ProtocolImportHeader protocolImportHeader, Long userId){
        Map<String, Object> inputs = new HashMap<>(1);

        OracleLobHandler lh = new OracleLobHandler();
        lh.setNativeJdbcExtractor(new CommonsDbcpNativeJdbcExtractor());

        //these inputs are not required any more - might be removed in the future
        inputs.put(ProtocolImportDAO.IN_INDICE_TYPE_ID, null);
        inputs.put(ProtocolImportDAO.IN_SAMPLE_DATE, null);

        inputs.put(ProtocolImportDAO.IN_PRINCIPAL_INSTITUTION_ID, protocolImportHeader.getPrincipalInstituionId());
        inputs.put(ProtocolImportDAO.IN_MANDATARY_INSTITUTION_ID, protocolImportHeader.getMandataryInstitutionId());
        inputs.put(ProtocolImportDAO.IN_PROTOCOL_VERSION_ID, protocolImportHeader.getProtocolVersionId());
        inputs.put(ProtocolImportDAO.IN_ANALYSIS_PERSON_ID, protocolImportHeader.getAnalysisPersonId());
        inputs.put(ProtocolImportDAO.IN_ANALYSIS_DATE, protocolImportHeader.getAnalysisDate());
        inputs.put(ProtocolImportDAO.IN_OPERATOR_PERSON_ID, protocolImportHeader.getSamplePersonId());
        inputs.put(ProtocolImportDAO.IN_DOCUMENT_URL, protocolImportHeader.getDocumentUrl());
        inputs.put(ProtocolImportDAO.IN_LANGUAGE_ID, protocolImportHeader.getLanguageId());
        inputs.put(ProtocolImportDAO.IN_REFERENCE_SYSTEM_ID, protocolImportHeader.getReferenceSystemId());
        inputs.put(ProtocolImportDAO.IN_PRECISION_LEVEL_ID, protocolImportHeader.getPrecisionLevelId());
        inputs.put(ProtocolImportDAO.IN_EXCEL_FILE_NAME, protocolImportHeader.getExcelFileName());
        inputs.put(ProtocolImportDAO.IN_EXCEL_FILE_BLOB, new SqlLobValue(protocolImportHeader.getExcelFileBytes(), lh));
        inputs.put(ProtocolImportDAO.IN_EXCEL_SHEET_NAME, protocolImportHeader.getExcelSheetName());
        inputs.put(ProtocolImportDAO.IN_USER_ID, userId);
        inputs.put(ProtocolImportDAO.IN_PARENT_ID, protocolImportHeader.getParentId());
        inputs.put(ProtocolImportDAO.IN_PROJECT_ID,protocolImportHeader.getSampleProjectId());

        Map<String, Object> result = execute(inputs);
        return (Long)result.get(ProtocolImportDAO.OUT_PROTOCOL_IMPORT_ID);
    }

}
