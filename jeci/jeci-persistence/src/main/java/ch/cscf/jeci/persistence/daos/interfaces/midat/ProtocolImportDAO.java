package ch.cscf.jeci.persistence.daos.interfaces.midat;

import ch.cscf.jeci.domain.dto.errors.Message;
import ch.cscf.jeci.domain.entities.midat.ProtocolImportHeader;
import ch.cscf.jeci.domain.dto.jeci.UploadedFileInfo;

import java.util.List;

/**
 * @author: henryp
 */
public interface ProtocolImportDAO {

    static final String IN_EXCEL_FILE_BLOB ="inExcelFileBlob";
    static final String OUT_SHEET_COUNT="outSheetCount";

    static final String IN_PRINCIPAL_INSTITUTION_ID="inPrincipalInstitutionId";
    static final String IN_MANDATARY_INSTITUTION_ID="inMandataryInstitutionId";
    static final String IN_PROTOCOL_VERSION_ID="inProtocolVersionId";
    static final String IN_ANALYSIS_PERSON_ID="inAnalysisPersonId";
    static final String IN_OPERATOR_PERSON_ID="inOperatorPersonId";
    static final String IN_ANALYSIS_DATE="inAnalysisDate";
    static final String IN_SAMPLE_DATE ="inSamplingDate";
    static final String IN_INDICE_TYPE_ID="inIndiceTypeId";
    static final String IN_DOCUMENT_URL="inDocumentUrl";
    static final String IN_LANGUAGE_ID="inLanguageId";
    static final String IN_REFERENCE_SYSTEM_ID="inReferenceSystemId";
    static final String IN_PRECISION_LEVEL_ID="inPrecisionLevelId";
    static final String IN_EXCEL_FILE_NAME="inExcelFileName";
    static final String IN_EXCEL_SHEET_NAME="inExcelSheetName";
    static final String IN_USER_ID="inUserId";
    static final String IN_TASK_STATUS_ID="inTaskStatusId";
    static final String IN_PARENT_ID="inParentId";
    static final String IN_PUBLISHED="inPublished";
    static final String IN_PUBLIC="inPublic";

    static final String IN_PROJECT_ID="inProjectId";

    static final String OUT_PROTOCOL_IMPORT_ID="outProtocolImportId";
    static final String OUT_PROTOCOL_HEADER_ID="outProtocolHeaderId";

    static final String IN_PROTOCOL_HEADER_ID="inProtocolHeaderId";
    static final String IN_SAMPLE_HEADER_ID="inSampleHeaderId";
    static final String OUT_CURSOR="outCursor";
    static final String OUT_MAX_ERROR_LEVEL="outMaxErrorLevel";
    static final String OUT_TASK_STATUS_ID = "outTaskStatusId";


    List<String> getNamesOfSheetsInExcelFile(UploadedFileInfo fileInfo);

    Long executeImportProcedure(ProtocolImportHeader protocolImportHeader, Long sessionUserId);

    List<Message> executeValidateProtocolProcedure(Long protocolHeaderId, Long languageId, Long userId);

    List<Message> executeValidateProtocolProcedure(Long protocolHeaderId, Long languageId, Long userId, Long taskStatusId);

    Long executeConfirmProtocolProcedure(Long protocolHeaderId, Long languageId, Long userId, boolean publish, boolean makePublic);

    Long initTaskStatus();

    void deleteProtocolEntry(Long protocolHeaderId, Long protocolVersionId);

    void deleteSample(Long sampleHeaderId);

}
