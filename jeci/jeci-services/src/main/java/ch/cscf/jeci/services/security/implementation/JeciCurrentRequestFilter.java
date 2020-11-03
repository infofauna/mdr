package ch.cscf.jeci.services.security.implementation;

import ch.cscf.jeci.services.security.interfaces.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: henryp
 */
@Component("jeciCurrentRequestFilter")
public class JeciCurrentRequestFilter extends OncePerRequestFilter {

    public static final String APACHE_MOD_PROXY_IP_HEADER = "X-Forwarded-For";
    @Autowired
    private SSOService ssoService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest servletRequest = request;
        String sessionId = servletRequest.getSession().getId();

        //If present, use the header that has been added by httpd mod_proxy (production / QA environment)
        //otherwise use the direct IP (dev machine)
        String clientIp = request.getHeader(APACHE_MOD_PROXY_IP_HEADER);
        if(clientIp == null){
            clientIp = request.getRemoteAddr();
        }

        ssoService.setCurrentHttpSessionId(sessionId);
        ssoService.setCurrentClientIp(clientIp);

        filterChain.doFilter(request, response);
    }
}
