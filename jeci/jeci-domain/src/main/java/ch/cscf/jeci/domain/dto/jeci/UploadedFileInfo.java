package ch.cscf.jeci.domain.dto.jeci;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.http.MediaType;

/**
* @author: henryp
*/
//ignore "bytes" when return json format
@JsonIgnoreProperties({"bytes"})
public class UploadedFileInfo {

    private String fileName;
    private String fileSize;
    private MediaType type;

    private byte[] bytes;

    //Result of the specific handler
    private Object handlerResult;

    @Override
    public String toString() {
        return "FileMeta{" +
                "fileName='" + fileName + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", fileType='" + type + '\'' +
                '}';
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Object getHandlerResult() {
        return handlerResult;
    }

    public void setHandlerResult(Object handlerResult) {
        this.handlerResult = handlerResult;
    }
}
