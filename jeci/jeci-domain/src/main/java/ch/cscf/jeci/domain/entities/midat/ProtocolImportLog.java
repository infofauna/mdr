package ch.cscf.jeci.domain.entities.midat;

import ch.cscf.jeci.domain.entities.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * @author: henryp
 */

@Entity
@Table(
        name="IMPORTPROTOCOLLOG", schema = "MIDAT"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="IPL_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="IPL_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "IPL_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "IPL_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "IPL_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "IPL_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "IPL_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "IPL_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "IPL_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "IPL_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="SEQ_IMPORTPROTOCOLLOG")
public class ProtocolImportLog extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IPL_IPH_ID")
    private ProtocolImportHeader ownerProtocol;

    @Column(name = "IPL_EXCEPTIONNUMBER")
    private Long exceptionNumber;

    @Column(name = "IPL_FIELDNAME")
    private String fieldName;

    @OneToMany(mappedBy = "ownerLog")
    @OrderBy("sequenceNumber")
    private List<ProtocolImportLogParam> parameters;


    public ProtocolImportHeader getOwnerProtocol() {
        return ownerProtocol;
    }

    public Long getExceptionNumber() {
        return exceptionNumber;
    }

    public String getFieldName() {
        return fieldName;
    }

    public List<ProtocolImportLogParam> getParameters() {
        return parameters;
    }
}
