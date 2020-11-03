package ch.cscf.jeci.domain.entities.midat;

import ch.cscf.jeci.domain.entities.base.BaseEntity;

import javax.persistence.*;

/**
 * @author: henryp
 */

@Entity
@Table(
        name="IMPORTPROTOCOLLOGPARAM", schema = "MIDAT"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="IPR_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="IPR_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "IPR_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "IPR_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "IPR_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "IPR_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "IPR_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "IPR_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "IPR_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "IPR_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="SEQ_IMPORTPROTOCOLLOGPARAM")
public class ProtocolImportLogParam extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IPR_IPO_ID")
    private ProtocolImportLog ownerLog;

    @Column(name = "IPR_SEQUENCE")
    private Integer sequenceNumber;

    @Column(name = "IPR_PARAMETER")
    private String value;

    public ProtocolImportLog getOwnerLog() {
        return ownerLog;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public String getValue() {
        return value;
    }
}
