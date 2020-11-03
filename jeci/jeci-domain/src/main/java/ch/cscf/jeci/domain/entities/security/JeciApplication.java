package ch.cscf.jeci.domain.entities.security;

import ch.cscf.jeci.domain.entities.base.BaseEntity;

import javax.persistence.*;

/**
 * @author: henryp
 */

@Entity()
@Table(name="admin_application", schema = "appmanager")
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="APL_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="APL_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "APL_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "APL_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "APL_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "APL_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "APL_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "APL_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "APL_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "APL_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="SEQ_admin_application")
public class JeciApplication extends BaseEntity {

    @Column(name = "APL_CODE", length = 30, nullable = false)
    private String code;

    @Column(name = "APL_DESCRIPTION", length = 256, nullable = false)
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "JeciApplication{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
