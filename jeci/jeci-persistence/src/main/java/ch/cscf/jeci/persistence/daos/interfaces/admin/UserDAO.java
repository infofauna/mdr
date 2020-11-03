package ch.cscf.jeci.persistence.daos.interfaces.admin;

import ch.cscf.jeci.domain.entities.security.User;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;

/**
 * @author: henryp
 */
public interface UserDAO extends GenericEntityDAO<User> {

    /**
     * Looks for a user with the given username.
     * User name is unique so it can find only one.
     * Returns null if no user with the given username exist.
     * @param username
     * @return
     */
    public User findByUsername(String username);

    public User findForSso(String ssoToken, String ipAddress);

    public void resetAllSsoCounters();

    List<User> listAndFilterByUsername(String orderByCol, SortOrder sortOrder, Page page, String query);
}
