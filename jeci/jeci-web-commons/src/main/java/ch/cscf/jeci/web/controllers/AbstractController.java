package ch.cscf.jeci.web.controllers;

import ch.cscf.jeci.services.general.I18nService;
import ch.cscf.jeci.web.tools.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Abstract Spring MVC controller that provides some common behaviour for other contorllers to re-use, i.e. messages handling and state of the lists (pagination etc)
 * The listDetailsForMaster state is used to resetForSessionAndKey a listDetailsForMaster in the same state that the user left it, without having to pass this state across all thje cRUD requests.
 * @author Pierre Henry
 */
public abstract class AbstractController {

    public static final String MESSAGES = "messages";

    @Autowired
    private Validator validator;

    @Autowired
    protected I18nService i18NService;

    protected AbstractController() {}

    protected WebDataBinder binder;

    @InitBinder
    public void prepareBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true)); //this will put null for empty strings, allowing for example to add a minimum size to an optional field
        this.binder=binder;
    }

    /**
     * Helper method for manual binding of fields from request to bean and validating the populated/updated bean.
     * @param bean
     * @param request
     * @return
     */
    protected BindingResult bindAndValidate(Object bean, String beanName, HttpServletRequest request, Model model){
        ServletRequestDataBinder binder = new ServletRequestDataBinder(bean, beanName);
        prepareBinder(binder);
        binder.setValidator(validator);
        binder.bind(request);
        binder.validate();
        //expose result for spring:errors tags
        model.addAttribute(BindingResult.class.getCanonicalName()+'.'+beanName, binder.getBindingResult());
        return binder.getBindingResult();
    }

    /**
     * Get the messages object from request / sesion scope. If not present a new object is created and added to the specified scope.
     * @param session
     * @return
     */
    protected Messages  getMessages(boolean session){

        Messages messages;

        if(session){
            messages = (Messages) RequestContextHolder.currentRequestAttributes().getAttribute(MESSAGES, RequestAttributes.SCOPE_SESSION);
        }else{
            messages = (Messages) RequestContextHolder.currentRequestAttributes().getAttribute(MESSAGES, RequestAttributes.SCOPE_REQUEST);
        }

        if(messages == null){
            messages = new Messages();
            if(session){
                RequestContextHolder.currentRequestAttributes().setAttribute(MESSAGES, messages, RequestAttributes.SCOPE_SESSION);
            }else{
                RequestContextHolder.currentRequestAttributes().setAttribute(MESSAGES, messages, RequestAttributes.SCOPE_REQUEST);
            }
        }

        return messages;
    }

    protected void addSuccessMessage(String message, boolean afterRedirect){
        Messages messages = getMessages(afterRedirect);
        messages.addSuccess(message);
    }

    protected void addErrorMessage(String message, boolean afterRedirect){
        Messages messages = getMessages(afterRedirect);
        messages.addError(message);
    }

    protected void addNoticeMessage(String message, boolean afterRedirect){
        Messages messages = getMessages(afterRedirect);
        messages.addNotice(message);
    }

    protected void addInfoMessage(String message, boolean afterRedirect){
        Messages messages = getMessages(afterRedirect);
        messages.addInfo(message);
    }

}
