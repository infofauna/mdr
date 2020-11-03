package ch.cscf.jeci.domain.entities.thesaurus;

import ch.cscf.jeci.domain.entities.base.BaseEntity;

import javax.persistence.*;

/**
 * @author: henryp
 */

@Entity
@Table(
        name="CODEREFERENCE", schema = "INFOFAUNA"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="CRF_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="CRF_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "CRF_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "CRF_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "CRF_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "CRF_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "CRF_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "CRF_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "CRF_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "CRF_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="SEQ_SAMPLEPROTOCOLGRND")
@DiscriminatorColumn(name="CRF_CRC_ID", discriminatorType = DiscriminatorType.INTEGER)
public abstract class AbstractCodesRealm extends BaseEntity {

    @Column(name="CRF_CODE", length = 12)
    private String code;

    @Column(name="CRF_DESIGNATION", length = 127)
    private String designation;

    @Column(name="CRF_CRF_ID")
    private Long parentId;

    @Column(name="CRF_LAN_ID_DEFAULT")
    private Long defaultLanguageId;

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name="CRF_LAN_ID_DEFAULT", updatable = false, insertable = false)
    private Language defaultLanguage;


    public String getCode() {
        return code;
    }

    public String getDesignation() {
        return designation;
    }

    public Long getParentId() {
        return parentId;
    }

    public Long getDefaultLanguageId() {
        return defaultLanguageId;
    }

    public Language getDefaultLanguage() {
        return defaultLanguage;
    }
}
