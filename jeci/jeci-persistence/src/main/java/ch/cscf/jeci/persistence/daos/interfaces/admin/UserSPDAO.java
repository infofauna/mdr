package ch.cscf.jeci.persistence.daos.interfaces.admin;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author: henryp
 */
public interface UserSPDAO {

    final String IN_USER_ID = "inUserId";

    @Transactional
    void deleteUser(Long userId);

}
