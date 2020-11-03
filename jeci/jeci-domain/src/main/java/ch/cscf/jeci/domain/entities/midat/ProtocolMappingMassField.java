package ch.cscf.jeci.domain.entities.midat;

import ch.cscf.jeci.domain.entities.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author: kanso
 */

@Entity
@Table(
        name = "PROTOCOLMAPPINGMASSFIELD", schema = "MIDAT"
)
@AssociationOverrides({
        @AssociationOverride(name = "creationUser", joinColumns = @JoinColumn(name = "PMM_USR_ID_CREATE")),
        @AssociationOverride(name = "updateUser", joinColumns = @JoinColumn(name = "PMM_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "PMM_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "PMM_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "PMM_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "PMM_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "PMM_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "PMM_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "PMM_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "PMM_MODUSER"))
})
@SequenceGenerator(allocationSize=1, name = "idSeq", sequenceName = "SEQ_PROTOCOLMAPPINGMASSFIELD")
public class ProtocolMappingMassField extends BaseEntity {

    @NotNull
    @Column(name = "PMM_PTV_ID")
    private Long protocolVersionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PMM_PTV_ID", insertable = false, updatable = false)
    private MIDATProtocolVersion protocolVersion;

    @Column(name = "PMM_COLUMNNAME")
    private String columnName;

    @Column(name = "PMM_CODE_MIDATFLDCMT")
    private String codeConcept;


    public Long getProtocolVersionId() {
        return protocolVersionId;
    }

    public void setProtocolVersionId(Long protocolVersionId) {
        this.protocolVersionId = protocolVersionId;
    }

    public MIDATProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(MIDATProtocolVersion protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getCodeConcept() {
        return codeConcept;
    }

    public void setCodeConcept(String codeConcept) {
        this.codeConcept = codeConcept;
    }
}
