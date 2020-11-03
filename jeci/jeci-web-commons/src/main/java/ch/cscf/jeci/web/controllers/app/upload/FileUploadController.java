package ch.cscf.jeci.web.controllers.app.upload;

import ch.cscf.jeci.domain.dto.jeci.UploadedFileInfo;
import ch.cscf.jeci.web.controllers.api.AbstractRestController;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;


/**
 * Controller that handles files uploads.
 *
 * The controller is statefull and stores the files uploaded for retrieval by other controllers.
 *
 * It uses the session key to differentiate between the clients but uses its own internal storage for the files.
 *
 * @author: henryp
 */
@Controller
public class FileUploadController extends AbstractRestController{

    private static final int MAX_FILES_PER_KEY = 5;
    public static final String PROTOCOL_EXCEL_FILE = "protocol-excel-file";

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    private final Map<String, ListMultimap<String, UploadedFileInfo>> uploadedFiles = new HashMap<>();

    private final Map<String, FileUploadHandler> handlers = new HashMap<>();

    @Autowired
    private ProtocolExcelFileHandler protocolExcelFileHandler;

    @PostConstruct
    public void initializeHandlers(){
       handlers.put(PROTOCOL_EXCEL_FILE, protocolExcelFileHandler);
    }

    /**
     * Return a listAndTranslate of the files that have been uploaded for the given session and the given key.
     * The number of files kept is limited to MAX_FILES_PER_KEY.
     * If no files have been uploaded, returns an empty listAndTranslate.
     * Returns an immutabvle listAndTranslate that cannot be modified.
     * @param sessionId
     * @param key
     * @return
     */
    public List<UploadedFileInfo> getFiles(String sessionId, String key){
        ListMultimap<String, UploadedFileInfo> sessionFiles = uploadedFiles.get(sessionId);

        if(sessionFiles == null){
            return Collections.emptyList();
        }
        List<UploadedFileInfo> fileInfoList = sessionFiles.get(key);
        return ImmutableList.copyOf(fileInfoList);

    }

    /**
     * Return the last of the files that have been uploaded for the given session and the given key.
     * If no file was uploaded, return null.
     * @param sessionId
     * @param key
     * @return
     */
    public UploadedFileInfo getLastFile(String sessionId, String key){
        List<UploadedFileInfo> files = getFiles(sessionId, key);
        if(files.isEmpty()){
            return null;
        }
        return files.get(files.size()-1);
    }

    public void resetForSessionAndKey(String sessionId, String key){
        ListMultimap<String, UploadedFileInfo> sessionFiles = uploadedFiles.get(sessionId);
        if(sessionFiles != null){
            sessionFiles.get(key).clear();
        }
    }

    @RequestMapping(value= "/api/upload/{key}", method = RequestMethod.POST)
    public @ResponseBody
    List<UploadedFileInfo> upload(MultipartHttpServletRequest request, @PathVariable String key) {

        //build an iterator
        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf;

        List<UploadedFileInfo> uploadedFilesInfo = new ArrayList<>();

        // get each file
        while(itr.hasNext()){

            //get next MultipartFile
            mpf = request.getFile(itr.next());
            logger.info(mpf.getOriginalFilename() + " uploaded. Processing it.");


            //create new file meta info
            UploadedFileInfo fileInfo = new UploadedFileInfo();
            fileInfo.setFileName(mpf.getOriginalFilename());
            fileInfo.setFileSize(mpf.getSize() / 1024 + " Kb");
            fileInfo.setType(MediaType.parseMediaType(mpf.getContentType()));

            extractBytes(mpf, fileInfo);

            //add to files for json response
            uploadedFilesInfo.add(fileInfo);

            //add to uploaded files cache
            addFile(request.getSession().getId(), key, fileInfo);

            handleFile(fileInfo, key);
        }

        // result will be like this
        // [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
        return uploadedFilesInfo;
    }

    private synchronized void addFile(String sessionId, String key, UploadedFileInfo file){
        ListMultimap<String, UploadedFileInfo> sessionFiles = uploadedFiles.get(sessionId);

        if(sessionFiles == null){
            sessionFiles = ArrayListMultimap.create();
            uploadedFiles.put(sessionId, sessionFiles);
        }

        sessionFiles.put(key, file);

        //ensure the multimap doesn't contain more than the authorized number of files per session per key
        List<UploadedFileInfo> filesForKey = sessionFiles.get(key);
        while(filesForKey.size() > MAX_FILES_PER_KEY){

            UploadedFileInfo firstFile = filesForKey.get(0);
            logger.info("Deleting temporary uploaded file : " + firstFile.toString());
            filesForKey.remove(0);
        }
    }

    private void handleFile(UploadedFileInfo fileInfo, String key){
        FileUploadHandler handler = handlers.get(key);

        if(handler != null){
            handler.handleFile(fileInfo);
        }
    }

    private void extractBytes(MultipartFile uploadedFile, UploadedFileInfo fileInfo){
        try {
            fileInfo.setBytes(uploadedFile.getBytes());
            logger.info("Successfully received file :" + fileInfo.toString());
        } catch (IOException e) {
            String message = "Could not retrieve content from file : " + fileInfo.toString();
            logger.warn(message);
            throw new RuntimeException(message);
        }
    }
}
