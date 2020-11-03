package ch.cscf.jeci.domain.dto.jeci;

import java.util.Date;

/**
 * Created by henryp on 02/09/15.
 */
public class AuditingInfo {

    private Date creationDate, dbCreationDate, updateDate, dbUpdateDate;
    private String creationUser, dbCreationUser, updateUser, dbUpdateUser;

    public AuditingInfo(Date creationDate, Date dbCreationDate, Date updateDate, Date dbUpdateDate, String creationUser, String dbCreationUser, String updateUser, String dbUpdateUser) {
        this.creationDate = creationDate;
        this.dbCreationDate = dbCreationDate;
        this.updateDate = updateDate;
        this.dbUpdateDate = dbUpdateDate;
        this.creationUser = creationUser;
        this.dbCreationUser = dbCreationUser;
        this.updateUser = updateUser;
        this.dbUpdateUser = dbUpdateUser;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getDbCreationDate() {
        return dbCreationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public Date getDbUpdateDate() {
        return dbUpdateDate;
    }

    public String getCreationUser() {
        return creationUser;
    }

    public String getDbCreationUser() {
        return dbCreationUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public String getDbUpdateUser() {
        return dbUpdateUser;
    }
}
