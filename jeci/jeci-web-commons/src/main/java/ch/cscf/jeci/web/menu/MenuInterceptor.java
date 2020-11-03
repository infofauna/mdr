package ch.cscf.jeci.web.menu;

import ch.cscf.jeci.web.tools.ServletContextsKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This simple Spring MVC interceptor does only one thing : it holds a reference to the singleton menu bean and
 * puts it as an attribute to each incoming HTTP request object. This places the menu object in the request scope and
 * allows JSP's to access it in sortOrder to render it.
 *
 * @author Pierre Henry
 */
public class MenuInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    Menu menu;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        request.setAttribute(ServletContextsKeys.REQUEST_MENU, menu);



        return true;
    }


}
