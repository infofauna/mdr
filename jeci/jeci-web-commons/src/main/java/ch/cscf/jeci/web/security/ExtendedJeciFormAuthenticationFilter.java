package ch.cscf.jeci.web.security;

import ch.cscf.jeci.services.security.implementation.JeciSsoAuthenticationToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.subject.WebSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: henryp
 */
public class ExtendedJeciFormAuthenticationFilter extends FormAuthenticationFilter {

    @Autowired
    private WebSecurityManager securityManager;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Determines whether the current subject should be allowed to make the current request.
     * <p/>
     * The default implementation returns <code>true</code> if the user is authenticated.  Will also return
     * <code>true</code> if the {@link #isLoginRequest} returns false and the &quot;permissive&quot; flag is set.
     *
     * @return <code>true</code> if request should be allowed access
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        if(super.isAccessAllowed(request, response, mappedValue)) return true;

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        //servletRequest.getSession(); //create the session
        String sessionId = servletRequest.getSession().getId();

        logger.info("Access denied. Now checking for SSO session in the DB for session ID = "+sessionId);

        JeciSsoAuthenticationToken token = new JeciSsoAuthenticationToken();
        token.setHost(request.getRemoteAddr());
        token.setSsoSessionId(((HttpServletRequest) request).getSession().getId());

        WebSubject subject1 = createSubject(request, response);
        try{
            SecurityUtils.getSubject().login(token);
            return true;
        }catch(AuthenticationException e){
            return false;
        }
    }

    /**
     * Creates a {@link org.apache.shiro.web.subject.WebSubject} instance to associate with the incoming request/response pair which will be used
     * throughout the request/response execution.
     *
     * @param request  the incoming {@code ServletRequest}
     * @param response the outgoing {@code ServletResponse}
     * @return the {@code WebSubject} instance to associate with the request/response execution
     * @since 1.0
     */
    private WebSubject createSubject(ServletRequest request, ServletResponse response) {
        return new WebSubject.Builder(securityManager, request, response).buildWebSubject();
    }

    /**
     * Override this method so we can implement a different behavior for json resuqests. We just send an empty request with 401 Unauthorized status. The cliet is responsible for redirect or any other behavior.
     * @param servletRequest
     * @param servletResponse
     * @throws IOException
     */
    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {

        HttpServletRequest request;
        HttpServletResponse response;

        try{
            request = (HttpServletRequest) servletRequest;
            response = (HttpServletResponse) servletResponse;
        }catch(ClassCastException e){
            super.saveRequestAndRedirectToLogin(servletRequest, servletResponse);
            return;
        }

        String servletPath = request.getServletPath();

        if(servletPath.equals("/api")){
            ((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());
        }else {
            super.saveRequestAndRedirectToLogin(servletRequest, servletResponse);
        }

    }
}
