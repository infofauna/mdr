package ch.cscf.midat.web.controllers.app.protocolimport;

import ch.cscf.jeci.domain.ThesaurusCodes;
import ch.cscf.jeci.domain.dto.errors.Message;
import ch.cscf.jeci.domain.dto.jeci.UploadedFileInfo;
import ch.cscf.jeci.domain.entities.midat.ProtocolImportHeader;
import ch.cscf.jeci.persistence.daos.thesaurus.interfaces.ThesaurusReadOnlyService;
import ch.cscf.jeci.web.controllers.AbstractController;
import ch.cscf.jeci.web.controllers.app.upload.FileUploadController;
import ch.cscf.midat.services.interfaces.ProtocolImportService;
import ch.cscf.midat.services.interfaces.ProtocolVersionCachedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: henryp
 */
@Controller()
@RequestMapping("/app/import/lab")
public class ProtocolImportController extends AbstractController {

    public static final String MODEL_KEY = "protocolImportHeader",  PARENT_INFO_KEY ="parentInfo";
    public static final String LAB_PROTOCOL_IMPORT_FORM = "labProtocolImportForm";
    public static final String ADDITIONAL_DATA_FORM = "additionalDataForm";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProtocolImportService service;

    @Autowired
    private ProtocolVersionCachedRepository protocolVersionCachedRepository;

    @Autowired
    FileUploadController fileUploadController;

    @Autowired
    private ThesaurusReadOnlyService thesaurusReadOnlyService;


    /**
     * Start a new import
     * @param mvcModel
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String newProtocol(Model mvcModel, HttpSession session){
        fileUploadController.resetForSessionAndKey(session.getId(), FileUploadController.PROTOCOL_EXCEL_FILE);
        ProtocolImportHeader protocolImportHeader = service.buildNewImportHeader();

        mvcModel.addAttribute("formValues", service.getLabFormValues());
        mvcModel.addAttribute(MODEL_KEY, protocolImportHeader);

        return LAB_PROTOCOL_IMPORT_FORM;
    }

    /**
     * Get back to an already saved lab protocol import
     * @param session
     * @param protocolImportId
     * @param mvcModel
     * @return
     */
    @RequestMapping(value="{protocolImportId:[0-9]+}", method=RequestMethod.GET)
    public String loadProtocolFormForExistingProtocol(HttpSession session, @PathVariable Long protocolImportId, Model mvcModel){
        fileUploadController.resetForSessionAndKey(session.getId(), FileUploadController.PROTOCOL_EXCEL_FILE);
        mvcModel.addAttribute("formValues", service.getLabFormValues());

        ProtocolImportHeader protocolImportHeader = service.buildNewImportHeaderBasedOn(protocolImportId);

        // feed in the with the remarks
        if(protocolVersionCachedRepository.isOfType(protocolImportHeader.getProtocolVersionId(), ThesaurusCodes.MIDATPROTO_LABORATORY)){
            if(protocolImportHeader !=null){
                protocolImportHeader.setCommentIds( session.getAttribute("commentIds")!=null?(Set<Long>)session.getAttribute("commentIds"): Collections.emptySet());
                protocolImportHeader.setCommentOther(session.getAttribute("commentOther")!=null?(String)session.getAttribute("commentOther"):"");
                protocolImportHeader.setCommentOtherChk(session.getAttribute("commentOtherChk")!=null?(Boolean)session.getAttribute("commentOtherChk"):false);
            }
        }

        mvcModel.addAttribute(MODEL_KEY, protocolImportHeader);

        return LAB_PROTOCOL_IMPORT_FORM;
    }

    /**
     * Submit the protocol additionalDataForm for import and validation
     * @param protocolImportHeader
     * @param result
     * @param session
     * @param mvcModel
     * @return
     */
    @RequestMapping(value = "validation", method = RequestMethod.POST)
    public String validateProtocol(@ModelAttribute(MODEL_KEY) @Valid final ProtocolImportHeader protocolImportHeader, final BindingResult result,  HttpSession session, Model mvcModel){

        UploadedFileInfo fileInfo = fileUploadController.getLastFile(session.getId(), FileUploadController.PROTOCOL_EXCEL_FILE);
        fileUploadController.resetForSessionAndKey(session.getId(), FileUploadController.PROTOCOL_EXCEL_FILE);


        //store comments in the session only when it's a laboratory protocol ...
        if(protocolVersionCachedRepository.isOfType(protocolImportHeader.getProtocolVersionId(), ThesaurusCodes.MIDATPROTO_LABORATORY)){
            session.setAttribute("commentIds",protocolImportHeader.getCommentIds());
            session.setAttribute("commentOther",protocolImportHeader.getCommentOther());
            session.setAttribute("commentOtherChk",protocolImportHeader.isCommentOtherChk());
        }

        //validate if the file has been uploaded via ajax
        if(fileInfo == null){
            result.rejectValue("excelFileBytes", "import.protocol.validation.fileNull");
            result.rejectValue("excelSheetName", "javax.validation.constraints.NotNull.message");

            protocolImportHeader.setExcelFileName(null);
            protocolImportHeader.setExcelSheetName(null);
        }

        //any additionalDataForm validation error --> back to form
        if(result.hasErrors()){
            return returnToFormForInvalidProtocol(protocolImportHeader, mvcModel);
        }

        //get file values
        protocolImportHeader.setExcelFileBytes(fileInfo.getBytes());
        protocolImportHeader.setExcelFileName(fileInfo.getFileName());

        ProtocolImportHeader returnedProtocolImportHeader = service.importProtocolDataAndExcelFile(protocolImportHeader);

        List<Message> protocolValidationMessages = service.executeValidateProtocolProcedure(returnedProtocolImportHeader);

        String returnPath;
        if(protocolVersionCachedRepository.isOfType(protocolImportHeader.getProtocolVersionId(), ThesaurusCodes.MIDATPROTO_LABORATORY)){
            returnPath = "/app/import/lab/"+protocolImportHeader.getId();
        }else{
            returnPath = "/app/import/lab/"+protocolImportHeader.getParentId()+"/additional";
        }

        String confirmPath;
        if(protocolVersionCachedRepository.isOfType(protocolImportHeader.getProtocolVersionId(), ThesaurusCodes.MIDATPROTO_MASS)){
            confirmPath = "/app/import/mass/confirmation/"+protocolImportHeader.getId();
        }else{
            confirmPath = "/app/import/lab/confirmation/"+protocolImportHeader.getId();
        }

        String protocolType = protocolVersionCachedRepository.getTypeCodeForVersionId(protocolImportHeader.getProtocolVersionId());

        mvcModel.addAttribute("validationResult",
                new ExcelFileValidationResult(
                        protocolImportHeader.getId(),
                        confirmPath,
                        returnPath,
                        protocolValidationMessages,
                        protocolImportHeader.getExcelFileName(),
                        protocolImportHeader.getExcelSheetName(),
                        protocolType
                )
        );

        return "dataValidationResult";
    }



    /**
     * Confirm the import of a validated protocol and copy the data to the actual tables
     * @return
     */
    @RequestMapping(value="confirmation/{importProtocolId:[0-9]+}", method = RequestMethod.POST)
    public String confirm(@PathVariable Long importProtocolId, @RequestParam(value = "publish", required = false) boolean publish, @RequestParam(value = "makePublic", required = false) boolean makePublic,HttpSession session){

        Long newProtocolHeaderId = service.confirmImportProtocol(importProtocolId, publish, makePublic);

        //MIDAT V2, add the comments to this smaple
        postInsertCommentsList(session, newProtocolHeaderId);

        addSuccessMessage("import.protocol.confirm.success", true);

        return "redirect:/app/import/lab/"+newProtocolHeaderId+"/additional";
    }



    /**
     * Loads the additionalDataForm for CRUD of additional data for a given sample
     * @param parentSampleId
     * @param mvcModel
     * @return
     */
    @RequestMapping(value= "{parentSampleId:[0-9]+}/additional", method=RequestMethod.GET)
    public String additionalDataForm(@PathVariable Long parentSampleId, Model mvcModel, HttpSession session){
        fileUploadController.resetForSessionAndKey(session.getId(), FileUploadController.PROTOCOL_EXCEL_FILE);
        ProtocolImportHeader additionalData = service.buildProtocolImportForAdditionalDataBasedOnParent(parentSampleId);

        bindFormValuesForAdditionalData(mvcModel, additionalData);

        return "additionalDataForm";
    }

    private void bindFormValuesForAdditionalData(Model model, ProtocolImportHeader additionalData) {
        Long parentSampleId = additionalData.getParentId();
        String currentLanguageCode = i18NService.currentLanguageCode();
        Map<String, Object> parentInfo = service.getParentInfo(parentSampleId);
        model.addAttribute(MODEL_KEY, additionalData);

        model.addAttribute(PARENT_INFO_KEY, parentInfo);
        model.addAttribute("typeGrid",
                thesaurusReadOnlyService.getRealm(ThesaurusCodes.REALM_MIDATPROTO).getEntryForCode(ThesaurusCodes.MIDATPROTO_GRDEVAL, currentLanguageCode));
        model.addAttribute("typeField",
                thesaurusReadOnlyService.getRealm(ThesaurusCodes.REALM_MIDATPROTO).getEntryForCode(ThesaurusCodes.MIDATPROTO_GROUND, currentLanguageCode));

        model.addAttribute("formValues", service.getAdditionalDataFormValues());
    }

    private String returnToFormForInvalidProtocol(ProtocolImportHeader protocolImportHeader, Model model){
        String protocolTypeCode = protocolVersionCachedRepository.getTypeCodeForVersionId(protocolImportHeader.getProtocolVersionId());

        switch(protocolTypeCode){
            case ThesaurusCodes.MIDATPROTO_LABORATORY :
                model.addAttribute("formValues", service.getLabFormValues());
                return LAB_PROTOCOL_IMPORT_FORM;
            default :
                bindFormValuesForAdditionalData(model, protocolImportHeader);
                return ADDITIONAL_DATA_FORM;
        }
    }

    private void postInsertCommentsList(HttpSession session, Long newProtocolHeaderId) {
        //After the sample insert is success
        Set<Long> commentIds = session.getAttribute("commentIds") != null ? (Set<Long>) session.getAttribute("commentIds") : Collections.emptySet();
        String commentOther = session.getAttribute("commentOther") != null ? (String) session.getAttribute("commentOther") : "";
        Boolean commentOtherChk = session.getAttribute("commentOtherChk")!=null?(Boolean)session.getAttribute("commentOtherChk"):false;

        service.addCommentsToSample(newProtocolHeaderId,commentIds,commentOtherChk,commentOther);

        //clean up the session attibute .
        if( session.getAttribute("commentIds") != null) {
            session.removeAttribute("commentIds");
        }
        if( session.getAttribute("commentOther") != null){
            session.removeAttribute("commentOther");
        }
        if( session.getAttribute("commentOtherChk") != null) {
            session.removeAttribute("commentOtherChk");
        }
    }

}
