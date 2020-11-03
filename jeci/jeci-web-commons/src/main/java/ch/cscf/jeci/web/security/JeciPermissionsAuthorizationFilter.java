package ch.cscf.jeci.web.security;

import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by henryp on 05/03/15.
 */
public class JeciPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {

    public JeciPermissionsAuthorizationFilter(){
        System.out.println("Hello !");
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        return super.isAccessAllowed(request, response, mappedValue);
    }
}
