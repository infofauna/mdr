package ch.cscf.midat.web.controllers.app.protocolimport;

import ch.cscf.jeci.domain.ThesaurusCodes;
import ch.cscf.jeci.domain.dto.errors.Message;
import ch.cscf.jeci.domain.dto.errors.Severity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: henryp
 */
public class ExcelFileValidationResult {

    private Long principalId;
    private String confirmPath, cancelPath;
    private List<Message> messages, sortedMessages;
    private String excelFileName, excelSheetName, protocolType;

    public ExcelFileValidationResult(Long principalId, String confirmPath, String cancelPath, List<Message> messages, String excelFileName, String excelSheetName, String protocolType) {
        this.principalId = principalId;
        this.confirmPath = confirmPath;
        this.cancelPath = cancelPath;
        this.messages = messages;
        this.excelFileName = excelFileName;
        this.excelSheetName = excelSheetName;
        this.protocolType = protocolType;

        sortedMessages = new ArrayList<>(messages.size());
        sortedMessages.addAll(messages);
        Collections.sort(sortedMessages); //sort messages by severity
    }

    public boolean hasMessageOfSeverityEqualOrAbove(Severity severity){
        Severity maxSeverity = sortedMessages.get(sortedMessages.size()-1).getSeverity();
        return maxSeverity.getWeight() >= severity.getWeight();
    }

    public boolean hasValidationPassed(){
        if(protocolType.equals(ThesaurusCodes.MIDATPROTO_MASS)){
            return ! hasMessageOfSeverityEqualOrAbove(Severity.F);
        }else{
            return ! hasMessageOfSeverityEqualOrAbove(Severity.E);
        }
    }

    public boolean hasWarnings(){
        for(Message message : sortedMessages){
            if(message.getSeverity() == Severity.W){
                return true;
            }
            if(message.getSeverity().getWeight() > Severity.W.getWeight()){
                break;
            }
        }
        return false;
    }

    public Long getPrincipalId() {
        return principalId;
    }

    public String getConfirmPath() {
        return confirmPath;
    }

    public String getCancelPath() {
        return cancelPath;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public String getExcelFileName() {
        return excelFileName;
    }

    public String getExcelSheetName() {
        return excelSheetName;
    }

    public String getProtocolType() {
        return protocolType;
    }
}

