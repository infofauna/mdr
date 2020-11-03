package ch.cscf.jeci.services.security.implementation;

import org.apache.shiro.authc.HostAuthenticationToken;

/**
 * @author: henryp
 */
public class JeciSsoAuthenticationToken implements HostAuthenticationToken {

    private String ssoSessionId;
    private String host;

    @Override
    public Object getPrincipal() {
        return host;
    }

    @Override
    public Object getCredentials() {
        return ssoSessionId;
    }

    public String getSsoSessionId() {
        return ssoSessionId;
    }

    public void setSsoSessionId(String ssoSessionId) {
        this.ssoSessionId = ssoSessionId;
    }

    @Override
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
