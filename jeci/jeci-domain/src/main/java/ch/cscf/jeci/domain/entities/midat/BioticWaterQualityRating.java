package ch.cscf.jeci.domain.entities.midat;

import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.domain.entities.midat.sample.SampleIndiceVersion;
import ch.cscf.jeci.domain.entities.security.Role;
import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusValue;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * @author: henryp
 */
@Entity
@Table(
        name="BIOLOGICALSTATE", schema = "MIDAT"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="BLS_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="BLS_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "BLS_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "BLS_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "BLS_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "BLS_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "BLS_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "BLS_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "BLS_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "BLS_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="MIDAT.SEQ_BIOLOGICALSTATE")
public class BioticWaterQualityRating extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BLS_CVL_ID_MIDATINDICE")
    private ThesaurusValue indexType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BLS_CVL_ID_BIOLSTATETXT")
    private ThesaurusValue designation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BLS_CVL_ID_COLORINDEX")
    private ThesaurusValue bgColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BLS_CVL_ID_COLORINDEXTEXT")
    private ThesaurusValue textColor;

    @Column(name = "BLS_LITERALRANGE")
    private String rangeLegendText;

    @Column(name = "BLS_FROMVALUE")
    private double fromValue;

    @Column(name = "BLS_TOVALUE")
    private double toValue;

    @Column(name = "BLS_ORDER")
    private int sortOrder;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BLS_IVR_ID" ,referencedColumnName = "IVR_ID")
    private SampleIndiceVersion sampleIndiceVersion;


    public SampleIndiceVersion getSampleIndiceVersion() {
        return sampleIndiceVersion;
    }

    public void setSampleIndiceVersion(SampleIndiceVersion sampleIndiceVersion) {
        this.sampleIndiceVersion = sampleIndiceVersion;
    }

    public ThesaurusValue getIndexType() {
        return indexType;
    }

    public ThesaurusValue getDesignation() {
        return designation;
    }

    public ThesaurusValue getBgColor() {
        return bgColor;
    }

    public ThesaurusValue getTextColor() {
        return textColor;
    }

    public String getRangeLegendText() {
        return rangeLegendText;
    }

    public double getFromValue() {
        return fromValue;
    }

    public double getToValue() {
        return toValue;
    }

    public int getSortOrder() {
        return sortOrder;
    }


}
