package ch.cscf.jeci.web.controllers.app.upload;

import ch.cscf.jeci.domain.dto.jeci.UploadedFileInfo;

/**
 * @author: henryp
 */
public interface FileUploadHandler {

    void handleFile(UploadedFileInfo fileInfo);

}
