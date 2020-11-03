package ch.cscf.jeci.domain.entities.midat;

import ch.cscf.jeci.domain.entities.base.BaseEntity;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author: kanso
 */

@Entity
@Table(
        name = "PROTOCOLMAPPINGHEADER", schema = "MIDAT"
)
@AssociationOverrides({
        @AssociationOverride(name = "creationUser", joinColumns = @JoinColumn(name = "PMH_USR_ID_CREATE")),
        @AssociationOverride(name = "updateUser", joinColumns = @JoinColumn(name = "PMH_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "PMH_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "PMH_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "PMH_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "PMH_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "PMH_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "PMH_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "PMH_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "PMH_MODUSER"))
})
@SequenceGenerator(allocationSize=1, name = "idSeq", sequenceName = "SEQ_PROTOCOLMAPPINGHEADER")
public class ProtocolMappingHeader extends BaseEntity {

    @NotNull
    @Column(name = "PMH_PTV_ID")
    private Long protocolVersionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PMH_PTV_ID", insertable = false, updatable = false)
    private MIDATProtocolVersion protocolVersion;


    @Column(name = "PMH_CELLROWVALUE")
    private String cellRowValue;

    @Column(name = "PMH_CELLCOLUMNVALUE")
    private String cellColumnValue;

    @Column(name = "PMH_FIELDNAMETO")
    private String fieldNameTo;

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

    public String getCellRowValue() {
        return cellRowValue;
    }

    public void setCellRowValue(String cellRowValue) {
        this.cellRowValue = cellRowValue;
    }

    public String getCellColumnValue() {
        return cellColumnValue;
    }

    public void setCellColumnValue(String cellColumnValue) {
        this.cellColumnValue = cellColumnValue;
    }

    public String getFieldNameTo() {
        return fieldNameTo;
    }

    public void setFieldNameTo(String fieldNameTo) {
        this.fieldNameTo = fieldNameTo;
    }
}
