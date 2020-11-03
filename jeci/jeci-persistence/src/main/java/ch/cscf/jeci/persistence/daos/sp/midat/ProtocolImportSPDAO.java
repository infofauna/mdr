package ch.cscf.jeci.persistence.daos.sp.midat;

import ch.cscf.jeci.domain.dto.errors.Message;
import ch.cscf.jeci.domain.entities.midat.ProtocolImportHeader;
import ch.cscf.jeci.persistence.daos.interfaces.midat.ProtocolImportDAO;
import ch.cscf.jeci.persistence.daos.sp.StoredProcedureDAO;
import ch.cscf.jeci.persistence.storedprocedures.midat.*;
import ch.cscf.jeci.domain.dto.jeci.UploadedFileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author: henryp
 */
@Repository
public class ProtocolImportSPDAO extends StoredProcedureDAO implements ProtocolImportDAO {

    private GetExcelSheetsSP getExcelSheetsSP;
    private ImportProtocolSP importProtocolSP;
    private ValidateDataSP validateDataSP;
    private ConfirmValidateProtocolSP confirmDataSP;
    private DeleteProtocolEntrySP deleteProtocolEntrySP;
    private DeleteSampleHeaderSP deleteSampleHeaderSP;
    private InitTaskStatusSP initTaskStatusSP;

    @Autowired
    public ProtocolImportSPDAO(DataSource dataSource){
        super(dataSource);
        getExcelSheetsSP = new GetExcelSheetsSP(dataSource);
        importProtocolSP = new ImportProtocolSP(dataSource);
        validateDataSP = new ValidateDataSP(dataSource);
        confirmDataSP = new ConfirmValidateProtocolSP(dataSource);
        deleteProtocolEntrySP = new DeleteProtocolEntrySP(dataSource);
        deleteSampleHeaderSP = new DeleteSampleHeaderSP(dataSource);
        initTaskStatusSP = new InitTaskStatusSP(dataSource);
    }

    @Override
    public List<String> getNamesOfSheetsInExcelFile(UploadedFileInfo fileInfo) {
        return getExcelSheetsSP.execute(fileInfo);
    }

    @Override
    public Long executeImportProcedure(ProtocolImportHeader protocolImportHeader, Long sessionUserId) {
        return importProtocolSP.execute(protocolImportHeader, sessionUserId);
    }

    @Override
    public List<Message> executeValidateProtocolProcedure(Long protocolHeaderId, Long languageId, Long userId) {
        return validateDataSP.execute(protocolHeaderId, languageId, userId, null);
    }

    @Override
    public List<Message> executeValidateProtocolProcedure(Long protocolHeaderId, Long languageId, Long userId, Long taskStatusId) {
        return validateDataSP.execute(protocolHeaderId, languageId, userId, taskStatusId);
    }

    @Override
    public Long initTaskStatus() {
        return initTaskStatusSP.execute();
    }

    @Override
    public Long executeConfirmProtocolProcedure(Long protocolHeaderId, Long languageId, Long userId, boolean publish, boolean makePublic) {
        return confirmDataSP.execute(protocolHeaderId, languageId, userId, publish, makePublic);
    }

    @Override
    public void deleteProtocolEntry(Long protocolHeaderId, Long protocolVersionId) {
        deleteProtocolEntrySP.execute(protocolHeaderId, protocolVersionId);
    }

    @Override
    public void deleteSample(Long sampleHeaderId) {
        deleteSampleHeaderSP.execute(sampleHeaderId);
    }
}
