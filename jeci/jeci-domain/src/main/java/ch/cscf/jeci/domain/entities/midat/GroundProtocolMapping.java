package ch.cscf.jeci.domain.entities.midat;

import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusValue;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author: henryp
 */
@Entity
@Table(
        name="PROTOCOLMAPPINGGRND", schema = "MIDAT"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="PMR_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="PMR_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "PMR_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "PMR_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "PMR_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "PMR_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "PMR_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "PMR_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "PMR_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "PMR_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="MIDAT.SEQ_SAMPLEPROTOCOLLABO")
public class GroundProtocolMapping extends BaseEntity{

    @NotNull
    @Column(name = "PMR_PTV_ID")
    private Long protocolVersionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PMR_PTV_ID", insertable = false, updatable = false)
    private MIDATProtocolVersion protocolVersion;


    @Column(name = "PMR_CELLROWVALUE")
    private String cellRowValue;

    @Column(name = "PMR_CELLCOLUMNVALUE")
    private String cellColumnValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PMR_PMR_ID")
    GroundProtocolMapping parent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PMR_CVL_ID_MIDATGRND")
    ThesaurusValue label;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PMR_CVL_ID_DATATYPE")
    ThesaurusValue type;

    @Column(name = "PMR_SORTORDER")
    Integer sortOrder;

    @OneToMany(mappedBy = "parent",fetch = FetchType.EAGER)
    @OrderBy("sortOrder asc")
    List<GroundProtocolMapping> children;


    public MIDATProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }

    public GroundProtocolMapping getParent() {
        return parent;
    }

    public ThesaurusValue getLabel() {
        return label;
    }

    public ThesaurusValue getType() {
        return type;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public List<GroundProtocolMapping> getChildren() {
        return children;
    }

    public Long getProtocolVersionId() {
        return protocolVersionId;
    }

    public void setProtocolVersionId(Long protocolVersionId) {
        this.protocolVersionId = protocolVersionId;
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
}
