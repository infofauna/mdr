package ch.cscf.jeci.domain.entities.midat;

import ch.cscf.jeci.domain.entities.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author: henryp
 */
@Entity
@Table(
        name="PROTOCOLMAPPINGGRID", schema = "MIDAT"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="PMG_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="PMG_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "PMG_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "PMG_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "PMG_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "PMG_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "PMG_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "PMG_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "PMG_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "PMG_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="MIDAT.SEQ_SAMPLEPROTOCOLLABO")
public class EvaluationGridFieldMapping extends BaseEntity{


    @NotNull
    @Column(name = "PMG_PTV_ID")
    private Long protocolVersionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PMG_PTV_ID", insertable = false, updatable = false)
    private MIDATProtocolVersion protocolVersion;



    @Column(name = "PMG_CVL_CODE_MIDATITGRDRO")
    private String rowCode;

    @Column(name = "PMG_CVL_CODE_MIDATITGRDCL")
    private String colCode;

    @Column(name="PMG_CVL_CODE_MIDATITGRDCE")
    private String contentDefinitionCode;

    @Column(name="PMG_CELLROWVALUE")
    private Integer rowIndex;

    @Column(name="PMG_CELLCOLUMNVALUE")
    private Character colIndex;



    public String getRowCode() {
        return rowCode;
    }

    public String getColCode() {
        return colCode;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public Character getColIndex() {
        return colIndex;
    }

    public String getContentDefinitionCode() {
        return contentDefinitionCode;
    }

    public Long getProtocolVersionId() {
        return protocolVersionId;
    }

    public MIDATProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }
}
