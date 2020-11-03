package ch.cscf.jeci.domain.entities.midat;

import ch.cscf.jeci.domain.entities.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author: henryp
 */
@Entity
@Table(
        name="PROTOCOLMAPPINGLABO", schema = "MIDAT"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="PTL_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="PTL_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "PTL_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "PTL_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "PTL_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "PTL_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "PTL_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "PTL_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "PTL_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "PTL_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="MIDAT.SEQ_SAMPLEPROTOCOLLABO")
public class LabProtocolFieldMapping extends BaseEntity{

    @NotNull
    @Column(name = "PTL_PTV_ID")
    private Long protocolVersionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PTL_PTV_ID", insertable = false, updatable = false)
    private MIDATProtocolVersion protocolVersion;


    @Column(name = "PTL_CELLROWVALUE")
    private String cellRowValue;

    @Column(name = "PTL_CELLCOLUMNVALUE")
    private String cellColumnValue;


    @Column(name = "PTL_TAXA")
    private String ibchTaxon;

    @Column(name = "PTL_IBCHFAUNAGROUP_GI")
    private Long ibchDeterminingGroup;

    public String getIbchTaxon() {
        return ibchTaxon;
    }

    public Long getIbchDeterminingGroup() {
        return ibchDeterminingGroup;
    }

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

    public void setIbchTaxon(String ibchTaxon) {
        this.ibchTaxon = ibchTaxon;
    }

    public void setIbchDeterminingGroup(Long ibchDeterminingGroup) {
        this.ibchDeterminingGroup = ibchDeterminingGroup;
    }
}
