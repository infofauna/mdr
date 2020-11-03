package ch.cscf.jeci.persistence.daos.jpa.admin;

import ch.cscf.jeci.domain.entities.base.Person;
import ch.cscf.jeci.domain.entities.security.User;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;
import ch.cscf.jeci.persistence.daos.interfaces.admin.UserDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: henryp
 */
@Repository
public class UserJpaDAO extends GenericJpaEntityDAO<User> implements UserDAO {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User findByUsername(String username) {
        List<User> users = getEm().createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();

        if(users.isEmpty()){
            return null;
        }
        return users.get(0);
    }

    @Override
    public User findForSso(String ssoToken, String ipAddress) {
        List<User> users = getEm().createQuery("select u from User u join fetch u.groups join fetch u.roles where u.ssoSessionId = :ssoToken and u.ssoIpAddress = :ipAddress", User.class)
                .setParameter("ssoToken", ssoToken)
                .setParameter("ipAddress", ipAddress)
                .getResultList();

        if(users.isEmpty()){
            logger.info(MessageFormat.format("Could not find SSO session with session ID = {0} from IP address {1}.", ssoToken, ipAddress));
            return null;
        }
        User user = users.get(0);
        logger.info(MessageFormat.format("Found SSO session with session ID = {0} from IP address {1} : User {2}. Current SSO session counter for this user is : {3}.", ssoToken, ipAddress, user.getUsername(), user.getSsoSessionCounter()));
        return user;
    }

    @Override
    public void resetAllSsoCounters(){

        logger.info("Updating all users in the table. Setting SSO session counter to 0.");
        int result = getEm().createQuery("update User set ssoSessionCounter=0, ssoIpAddress=null, ssoSessionId=null").executeUpdate();
        logger.info(result + " users have been updated.");

    }

    @Override
    public List<User> listAndFilterByUsername(String orderByCol, SortOrder sortOrder, Page page, String query) {

        query = prepareStringForLikeSearch(query);

        List<Predicate> predicates = new ArrayList<>();
        CriteriaBuilder builder = getEm().getCriteriaBuilder();
        CriteriaQuery criteria =  builder.createQuery(User.class);
        Root<Person> root = criteria.from(User.class);
        root.alias(ROOT_ALIAS);

        Expression<String> firstNamePath = root.get("username");
        Expression<String> lowerUsernamePath = builder.lower(firstNamePath);
        predicates.add(builder.like(lowerUsernamePath, query));

        return super.listImpl(orderByCol, sortOrder, page, predicates, null);
    }
}
