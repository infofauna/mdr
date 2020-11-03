package ch.cscf.jeci.persistence.daos.sp;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

/**
 * @author: henryp
 */
public abstract class StoredProcedureDAO {


    private DataSource dataSource;

    @Autowired
    public StoredProcedureDAO(DataSource dataSource){
        this.dataSource=dataSource;
    }


}
