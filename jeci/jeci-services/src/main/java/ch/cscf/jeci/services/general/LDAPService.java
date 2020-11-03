package ch.cscf.jeci.services.general;

import ch.cscf.jeci.domain.entities.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.List;

/**
 * Service that provides basic business logic on top of LDAP.
 * It uses SPring LDAP's LdapTemplate class to connect to and read from a distant LDAP directory.
 *
 * @author Pierre Henry
 */
@Service
public class LDAPService {

    @Autowired
    private LdapTemplate ldap;

    public boolean authenticateUser(String username, String password){
        try{
            return ldap.authenticate("", "(sAMAccountName="+username+")", password);
        }catch (Exception e){
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public List<User> findByUsername(String username){
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person"));
        filter.and(new LikeFilter("sAMAccountName", '*'+username+'*'));
        return ldap.search("", filter.encode(), new AttributesMapper() {
            @Override
            public Object mapFromAttributes(Attributes attributes) throws NamingException {
                User user = new User();
                user.setUsername((String)attributes.get("sAMAccountName").get());
                //user.setFirstname((String)attributes.get("givenName").get());
                //user.setLastname((String)attributes.get("sn").get());
                //user.setEmail((String)attributes.get("mail").get());
                return user;
            }
        });
    }

    public boolean userExists(String username){
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person"));
        filter.and(new EqualsFilter("sAMAccountName", username));
        return!ldap.search("", filter.encode(), new AttributesMapper() {
            @Override
            public Object mapFromAttributes(Attributes attributes) throws NamingException {
                return true;
            }
        }).isEmpty();
    }


}
