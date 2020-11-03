package ch.cscf.jeci.domain.entities.thesaurus;

import ch.cscf.jeci.domain.entities.base.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * @author: henryp
 */
@Entity
@Table(
        name="LANGUAGE", schema = "INFOFAUNA"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="LAN_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="LAN_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "LAN_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "LAN_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "LAN_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "LAN_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "LAN_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "LAN_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "LAN_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "LAN_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="SEQ_LANGUAGE")
public class Language extends BaseEntity {

    public final static Long LATIN_ID = 10L;

    @Column(name="LAN_CODE")
    private String code;

    @Column(name="LAN_DESIGNATION")
    private String designation;

    @Column(name="LAN_IS_USER_SELECTABLE")
    @Type(type="yes_no")
    private Boolean selectableInGui;


    public String getCode() {
        return code;
    }

    public String getDesignation() {
        return designation;
    }

    public Boolean getSelectableInGui() {
        return selectableInGui;
    }
}
