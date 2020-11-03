package ch.cscf.jeci.web.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: henryp
 * Date: 28.01.13
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */
public class Messages {

    private List<String> errorMessages=new ArrayList<>();
    private List<String> successMessages=new ArrayList<>();
    private List<String> infoMessages=new ArrayList<>();
    private List<String> noticeMessages=new ArrayList<>();

    public Messages(){}

    public void addAll(Messages otherMessages){
        errorMessages.addAll(otherMessages.getError());
        successMessages.addAll(otherMessages.getSuccess());
        noticeMessages.addAll(otherMessages.getNotice());
        infoMessages.addAll(otherMessages.getInfo());
    }

    public void addError(String message){
        errorMessages.add(message);
    }

    public void addSuccess(String message){
        successMessages.add(message);
    }

    public void addInfo(String message){
        infoMessages.add(message);
    }

    public void addNotice(String message){
        noticeMessages.add(message);
    }

    public List<String> getError() {
        return errorMessages;
    }

    public List<String> getSuccess() {
        return successMessages;
    }

    public List<String> getInfo() {
        return infoMessages;
    }

    public List<String> getNotice() {
        return noticeMessages;
    }
}
