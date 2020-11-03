package ch.cscf.midat.web.controllers.app.protocolimport;

import ch.cscf.jeci.domain.dto.errors.Message;
import ch.cscf.jeci.domain.entities.midat.ProtocolImportHeader;
import ch.cscf.jeci.domain.dto.jeci.UploadedFileInfo;
import ch.cscf.jeci.web.controllers.AbstractController;
import ch.cscf.jeci.web.controllers.app.upload.FileUploadController;
import ch.cscf.midat.services.implementations.ProtocolVersionCachedRepository;
import ch.cscf.midat.services.interfaces.ProtocolImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @author: henryp
 */
@Controller()
@RequestMapping("/app/import/mass")
public class MassImportController extends AbstractController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String MODEL_KEY = "protocolImportHeader";

    @Autowired
    ProtocolImportService service;

    @Autowired
    ProtocolVersionCachedRepository protocolVersionCachedRepository;

    @Autowired
    private FileUploadController fileUploadController;

    /**
     * Go to the mass import form
     * @param mvcModel
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String newImport(Model mvcModel, HttpSession session){
        //make sure no uploaded file left over
        fileUploadController.resetForSessionAndKey(session.getId(), FileUploadController.PROTOCOL_EXCEL_FILE);

        mvcModel.addAttribute(MODEL_KEY, service.buildNewImportHeader());
        mvcModel.addAttribute("formValues", service.getMassFormValues());
        mvcModel.addAttribute("mass", true);

        return "massImportForm";
    }

    /**
     * Submit the protocol for import and validation
     * @param protocolImportHeader
     * @param result
     * @param session
     * @param mvcModel
     * @return
     */
    @RequestMapping(value = "validation", method = RequestMethod.POST)
    public String validateMassData(@ModelAttribute(MODEL_KEY) @Valid final ProtocolImportHeader protocolImportHeader, final BindingResult result, HttpSession session, Model mvcModel){

        UploadedFileInfo fileInfo = fileUploadController.getLastFile(session.getId(), FileUploadController.PROTOCOL_EXCEL_FILE);

        mvcModel.addAttribute("formValues", service.getMassFormValues());
        mvcModel.addAttribute("mass", true);

        //check that the file has been uploaded via ajax
        if(fileInfo == null){
            result.rejectValue("excelFileBytes", "import.protocol.validation.fileNull");
            result.rejectValue("excelSheetName", "javax.validation.constraints.NotNull.message");

            protocolImportHeader.setExcelFileName(null);
            protocolImportHeader.setExcelSheetName(null);
        }

        if(result.hasErrors()){
            return "massImportForm";
        }

        //get file values
        protocolImportHeader.setExcelFileBytes(fileInfo.getBytes());
        protocolImportHeader.setExcelFileName(fileInfo.getFileName());

        Long taskStatusId = service.asyncImportMassDataAndExcelFile(protocolImportHeader);
        mvcModel.addAttribute("isWaitingForMassTask", true);
        mvcModel.addAttribute("massImportTaskKey", taskStatusId);
        return "massImportForm";
    }

    /**
     * Confirm the import of a validated protocol and copy the data to the actual tables
     * @param importProtocolId
     * @return
     */
    @RequestMapping(value="confirmation/{importProtocolId}", method = RequestMethod.POST)
    public String confirm(@PathVariable Long importProtocolId, @RequestParam("publish") boolean publish, @RequestParam("makePublic") boolean makePublic){

        service.confirmImportProtocol(importProtocolId, publish, makePublic);
        addSuccessMessage("import.protocol.confirm.success", true);

        return "redirect:/app/import/mass";
    }

    @RequestMapping(value = "task-result/{task-key}", method = RequestMethod.GET)
    public String getValidationResult(@PathVariable("task-key") Long taskId, Model mvcModel){
        List<Message> protocolValidationMessages;
        ProtocolImportHeader header;

        protocolValidationMessages = service.getMassImportTaskResult(taskId);
        header = service.getHeaderForTask(taskId);

        String returnPath = "/app/import/mass";

        String protocolType = protocolVersionCachedRepository.getTypeCodeForVersionId(header.getProtocolVersionId());

        mvcModel.addAttribute("validationResult",
                new ExcelFileValidationResult(header.getId(), "/app/import/mass/confirmation/"+header.getId(), returnPath, protocolValidationMessages, header.getExcelFileName(), header.getExcelSheetName(), protocolType)
        );

        return "dataValidationResult";
    }



}
