package ch.cscf.jeci.web.tools;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This simple Spring MVC interceptor does only one thing : it holds a reference source bundle source bean and makes
 * it available to JSPs.
 *
 * This bean is subsequently used to provide i18n support for JavaScript.
 *
 * @author Pierre Henry
 */
public class MessagesInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        Messages sessionMessages = (Messages) request.getSession().getAttribute("messages");
        if(sessionMessages != null){
            Messages requestMessages = (Messages) request.getAttribute("messages");
            if(requestMessages == null){
                request.setAttribute("messages", sessionMessages);
            }else{
                requestMessages.addAll(sessionMessages);
            }
            request.getSession().setAttribute("messages", null);
        }

        return true;
    }

}
