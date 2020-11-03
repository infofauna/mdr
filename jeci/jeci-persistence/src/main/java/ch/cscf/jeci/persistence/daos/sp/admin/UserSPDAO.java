package ch.cscf.jeci.persistence.daos.sp.admin;

import ch.cscf.jeci.persistence.daos.sp.StoredProcedureDAO;
import ch.cscf.jeci.persistence.storedprocedures.infofauna.SoftDeleteUserSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @author: henryp
 */
@Repository
public class UserSPDAO extends StoredProcedureDAO implements ch.cscf.jeci.persistence.daos.interfaces.admin.UserSPDAO {

    private SoftDeleteUserSP sp;

    @Autowired
    public UserSPDAO(DataSource jeciDataSource){
        super(jeciDataSource);
        sp = new SoftDeleteUserSP(jeciDataSource);
    }

    @Override
    public void deleteUser(Long userId) {
        sp.execute(userId);
    }
}
