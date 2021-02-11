package ch.cscf.jeci.domain.entities.midat.sample;

import ch.cscf.jeci.domain.dto.midat.BioticWaterQualityRatingDTO;
import ch.cscf.jeci.domain.entities.base.BaseEntity;

import javax.persistence.*;

/**
 * @author: kanso
 */
@Entity
@Table(
        name="INDICEHISTORY", schema = "MIDAT"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="IHY_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="IHY_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "IHY_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "IHY_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "IHY_USR_CREDATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "IHY_USR_MODDATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "IHY_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "IHY_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "IHY_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "IHY_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="MIDAT.SEQ_INDICEHISTORY")
public class SampleIndiceHistory extends BaseEntity {

@Column(name = "IHY_SPH_ID")
private Long sampleId;

/*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IHY_SPH_ID", insertable = false, updatable = false)
    private Sample sample;
*/
@Column(name = "IHY_IVR_ID_IBCH")
private Integer ibchLegendVersionId;


@Column(name = "IHY_IVR_ID_SPEAR")
private Integer spearLegendVersionId;

@Column(name = "IHY_IVR_ID_MAKROINDEX")
private Integer makroLegendVersionId;

    @Column(name = "IHY_SEQUENCE")
    private Integer versionSeq;

    @Column(name = "IHY_GIMAX")
    private Double valeurGi;// GI niveau/taxon(s)

    @Column(name = "IHY_TAXONINDICATEUR")
    private String taxonIndicateur;//GI niveau/taxon(s)

    @Column(name = "IHY_GI_FINAL")
    private Double giFinal; //GI_2019

    @Column(name = "IHY_CLASSEVARIETE_FINAL")
    private Double valuerVt; //VT_2019

    @Column(name = "IHY_INDEXVALUEIBCH")
    private Double ibchIndexValue;

    @Column(name = "IHY_IBCHROBUST")
    private Double ibchRobust; //IBCH_2019_R

    @Column(name = "IHY_SPEARINDEXVALUE")
    private Double spearIndexValue; // SPEAR_2018

    @Column(name = "IHY_SUMFAMILY")
    private Integer taxonSumFamily; // somme taxons IBCH

    @Column(name = "IHY_EPHEMEROPTERACOUNTER")
    private Integer ephemeropteraCounter;// somme familles Ephemeroptera

    @Column(name = "IHY_TRICOPTERACOUNTER")
    private Integer tricopteraCounter;// familles Plecoptera

    @Column(name = "IHY_PLECOPTERACOUNTER")
    private Integer plecopteraCounter;//familles Trichoptera

    @Column(name = "IHY_SOMMEABON")
    private Integer sommeAbon;//Abondances


    @Column(name = "IHY_MAKROINDEX")
    private Double makroIndexValue;

    @Column(name = "IHY_IBCHQ")
    private Integer ibchQ; // Régime IBCH-Q

    @Column(name = "IHY_VC")
    private Double valeurCorrection; //Valeur de correction VC


    @Transient
    private BioticWaterQualityRatingDTO ibchQuality;


    @Transient
    private BioticWaterQualityRatingDTO spearQuality;

    @Transient
    private String ibchQDesignation; // Régime IBCH-Q

    public String getIbchQDesignation() {
        return ibchQDesignation;
    }

    public void setIbchQDesignation(String ibchQDesignation) {
        this.ibchQDesignation = ibchQDesignation;
    }

    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    public Integer getIbchLegendVersionId() {
        return ibchLegendVersionId;
    }

    public void setIbchLegendVersionId(Integer ibchLegendVersionId) {
        this.ibchLegendVersionId = ibchLegendVersionId;
    }

    public Integer getSpearLegendVersionId() {
        return spearLegendVersionId;
    }

    public void setSpearLegendVersionId(Integer spearLegendVersionId) {
        this.spearLegendVersionId = spearLegendVersionId;
    }

    public Integer getMakroLegendVersionId() {
        return makroLegendVersionId;
    }

    public void setMakroLegendVersionId(Integer makroLegendVersionId) {
        this.makroLegendVersionId = makroLegendVersionId;
    }

    public Double getValeurGi() {
        return valeurGi;
    }

    public void setValeurGi(Double valeurGi) {
        this.valeurGi = valeurGi;
    }

    public String getTaxonIndicateur() {
        return taxonIndicateur;
    }

    public void setTaxonIndicateur(String taxonIndicateur) {
        this.taxonIndicateur = taxonIndicateur;
    }

    public Double getGiFinal() {
        return giFinal;
    }

    public void setGiFinal(Double giFinal) {
        this.giFinal = giFinal;
    }

    public Double getValuerVt() {
        return valuerVt;
    }

    public void setValuerVt(Double valuerVt) {
        this.valuerVt = valuerVt;
    }

    public Double getIbchIndexValue() {
        return ibchIndexValue;
    }

    public void setIbchIndexValue(Double ibchIndexValue) {
        this.ibchIndexValue = ibchIndexValue;
    }

    public Double getIbchRobust() {
        return ibchRobust;
    }

    public void setIbchRobust(Double ibchRobust) {
        this.ibchRobust = ibchRobust;
    }

    public Double getSpearIndexValue() {
        return spearIndexValue;
    }

    public void setSpearIndexValue(Double spearIndexValue) {
        this.spearIndexValue = spearIndexValue;
    }

    public Double getMakroIndexValue() {
        return makroIndexValue;
    }

    public void setMakroIndexValue(Double makroIndexValue) {
        this.makroIndexValue = makroIndexValue;
    }

    public Integer getTaxonSumFamily() {
        return taxonSumFamily;
    }

    public void setTaxonSumFamily(Integer taxonSumFamily) {
        this.taxonSumFamily = taxonSumFamily;
    }

    public Integer getEphemeropteraCounter() {
        return ephemeropteraCounter;
    }

    public void setEphemeropteraCounter(Integer ephemeropteraCounter) {
        this.ephemeropteraCounter = ephemeropteraCounter;
    }

    public Integer getTricopteraCounter() {
        return tricopteraCounter;
    }

    public void setTricopteraCounter(Integer tricopteraCounter) {
        this.tricopteraCounter = tricopteraCounter;
    }

    public Integer getPlecopteraCounter() {
        return plecopteraCounter;
    }

    public void setPlecopteraCounter(Integer plecopteraCounter) {
        this.plecopteraCounter = plecopteraCounter;
    }

    public Integer getSommeAbon() {
        return sommeAbon;
    }

    public void setSommeAbon(Integer sommeAbon) {
        this.sommeAbon = sommeAbon;
    }


    public Double getValeurCorrection() {
        return valeurCorrection;
    }

    public void setValeurCorrection(Double valeurCorrection) {
        this.valeurCorrection = valeurCorrection;
    }

    public Integer getVersionSeq() {
        return versionSeq;
    }

    public void setVersionSeq(Integer versionSeq) {
        this.versionSeq = versionSeq;
    }

    public Integer getIbchQ() {
        return ibchQ;
    }

    public void setIbchQ(Integer ibchQ) {
        this.ibchQ = ibchQ;
    }

    public BioticWaterQualityRatingDTO getIbchQuality() {
        return ibchQuality;
    }

    public void setIbchQuality(BioticWaterQualityRatingDTO ibchQuality) {
        this.ibchQuality = ibchQuality;
    }

    public BioticWaterQualityRatingDTO getSpearQuality() {
        return spearQuality;
    }

    public void setSpearQuality(BioticWaterQualityRatingDTO spearQuality) {
        this.spearQuality = spearQuality;
    }

}
