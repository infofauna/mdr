package ch.cscf.jeci.web.jsi18n;

import ch.cscf.jeci.services.general.CustomReloadableResourceBundleMessageSource;
import ch.cscf.jeci.web.tools.ServletContextsKeys;
import org.springframework.beans.factory.annotation.Autowired;
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
public class JsI18nInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    CustomReloadableResourceBundleMessageSource bundleMessageSource;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        request.setAttribute(ServletContextsKeys.REQUEST_BUNDLE_SOURCE, bundleMessageSource);

        return true;
    }


}
