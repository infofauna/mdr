package ch.cscf.jeci.domain.entities.midat;

import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.domain.entities.midat.sample.Sample;
import ch.cscf.jeci.domain.entities.systematics.Taxon;

import javax.persistence.*;

/**
 * @author: henryp
 */
@Entity
@Table(
        name="SAMPLEPROTOCOLLABO", schema = "MIDAT"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="SPL_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="SPL_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "SPL_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "SPL_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "SPL_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "SPL_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "SPL_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "SPL_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "SPL_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "SPL_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="MIDAT.SEQ_SAMPLEPROTOCOLLABO")
public class LabRecordField extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPL_SPH_ID")
    private Sample master;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPL_PTL_ID")
    private LabProtocolFieldMapping fieldMapping;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPL_SYV_ID")
    private Taxon taxon;

    @Column(name="SPL_FREQUENCY")
    private Long frequency;

    @Column(name="SPL_FREQUENCYMODIFIED")
    private Long modifiedFrequency;

    //TODO: find a better name for this field !
    @Column(name="SPL_FREQLUM")
    private Long freqLum;

    @Column(name="SPL_STADIUM", length=60)
    private String stadium;

    @Column(name="SPL_SAMPLENO", length=30)
    private String sampleNumber;

    @Column(name="SPL_COMMENT", length=256)
    private String comment;

    @Column(name = "SPL_SORTORDER")
    private Integer sortOrder;

    public Sample getMaster() {
        return master;
    }

    public Long getFrequency() {
        return frequency;
    }

    public Long getModifiedFrequency() {
        return modifiedFrequency;
    }

    public Long getFreqLum() {
        return freqLum;
    }

    public String getStadium() {
        return stadium;
    }

    public String getComment() {
        return comment;
    }

    public String getSampleNumber() {
        return sampleNumber;
    }

    public LabProtocolFieldMapping getFieldMapping() {
        return fieldMapping;
    }

    public Taxon getTaxon() {
        return taxon;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }
}
