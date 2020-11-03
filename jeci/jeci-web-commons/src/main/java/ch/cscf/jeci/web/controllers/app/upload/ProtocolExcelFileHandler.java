package ch.cscf.jeci.web.controllers.app.upload;

import ch.cscf.jeci.persistence.daos.interfaces.midat.ProtocolImportDAO;
import ch.cscf.jeci.domain.dto.jeci.UploadedFileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: henryp
 */
@Service
public class ProtocolExcelFileHandler implements FileUploadHandler {

    @Autowired
    private ProtocolImportDAO protocolImportDAO;

    @Override
    public void handleFile(UploadedFileInfo fileInfo) {
        List<String> sheetNames = protocolImportDAO.getNamesOfSheetsInExcelFile(fileInfo);
        fileInfo.setHandlerResult(sheetNames);
    }
}
