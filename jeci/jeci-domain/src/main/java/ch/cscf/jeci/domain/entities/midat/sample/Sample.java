package ch.cscf.jeci.domain.entities.midat.sample;

import ch.cscf.jeci.domain.annotations.LocalizedProperty;
import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.domain.entities.base.Institution;
import ch.cscf.jeci.domain.entities.base.Project;
import ch.cscf.jeci.domain.entities.midat.LabRecordField;
import ch.cscf.jeci.domain.entities.midat.MIDATProtocolVersion;
import ch.cscf.jeci.domain.entities.security.Group;
import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusValue;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: henryp
 */
@Entity
@Table(
        name = "SAMPLEHEADER", schema = "MIDAT"
)
@AssociationOverrides({
        @AssociationOverride(name = "creationUser", joinColumns = @JoinColumn(name = "SPH_USR_ID_CREATE")),
        @AssociationOverride(name = "updateUser", joinColumns = @JoinColumn(name = "SPH_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "SPH_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "SPH_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "SPH_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "SPH_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "SPH_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "SPH_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "SPH_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "SPH_MODUSER"))
})
@SequenceGenerator(allocationSize=1, name = "idSeq", sequenceName = "SEQ_SAMPLEHEADER")
public class Sample extends BaseEntity {

    //DecimalFormat df3 = new DecimalFormat("#.###");
    //DecimalFormat df2 = new DecimalFormat("#.##");


    @ManyToMany
    @JoinTable(name = "SAMPLEHEADERADMINGROUP", schema = "MIDAT",
            joinColumns = {@JoinColumn(name = "SHG_SPH_ID")},
            inverseJoinColumns = {@JoinColumn(name = "SHG_AGR_ID")}
    )
    private Set<Group> groups;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPH_SST_ID")
    private SampleStation station;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPH_INS_ID_PRINCIPAL")
    private Institution principalInstitution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPH_INS_ID_MANDATARY")
    private Institution mandataryInstitution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPH_PTV_ID")
    private MIDATProtocolVersion protocolVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPH_CVL_ID_SYSPRECISIONREF")
    private ThesaurusValue precisionReferenceSystem;

    @LocalizedProperty("precisionReferenceSystem")
    @Transient
    private String precisionReferenceSystemI18n;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPH_CVL_ID_SYSPRECISION")
    private ThesaurusValue precision;

    @LocalizedProperty("precision")
    @Transient
    private String precisionI18n;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPH_CVL_ID_MIDATSTAT")
    private ThesaurusValue indiceType;

    @LocalizedProperty("indiceType")
    @Transient
    private String indiceTypeI18n;

    @Column(name = "SPH_VISIBILITYSTATUS")
    @Type(type = "yes_no")
    private boolean published;

    @Column(name = "SPH_ABSOLUTENUMBERFLAG")
    @Type(type = "yes_no")
    private boolean absoluteNumbers;

    @Column(name = "SPH_OBSERVATIONDATE")
    private Date sampleDate;

    @Column(name = "SPH_OBSERVATIONDAY")
    private Integer sampleDateDay;

    @Column(name = "SPH_OBSERVATIONMONTH")
    private Integer sampleDateMonth;

    @Column(name = "SPH_OBSERVATIONYEAR")
    private Integer sampleDateYear;

    @Column(name = "SPH_DETERMINATEDDATE")
    private Date analysisDate;

    @OneToMany(mappedBy = "parent")
    private Set<SampleAttribute> attributes;

    @OneToMany(mappedBy = "parent")
    private Set<SampleOriginalFile> originalFiles;

    @OneToMany(mappedBy = "sample")
    private Set<SampleDocument> documents;



    @Column(name = "SPH_MAKROINDEXVALUE")
    private Double makroIndexValue;



    @OneToMany(mappedBy = "master")
    private List<LabRecordField> labRecordFields;

    @Column(name = "SPH_PROJECT")
    private String project;


    @Column(name = "SPH_COMMENT")
    private String sampleCommentOther;

    @Column(name = "SPH_PRJ_ID")
    private Long sampleProjectId;


    @ManyToOne(optional = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "SPH_PRJ_ID", updatable = false, insertable = false)
    private Project sampleProject;


    @Column(name = "SPH_SMF_ID")
    private Long sampleMassOriginalFileId;


    @Column(name = "SPH_IPH_ID")
    private Long sampleIphId;

    @ManyToOne(optional = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "SPH_SMF_ID", updatable = false, insertable = false)
    private SampleMassOriginalFile sampleMassOriginalFile;


    public Set<SampleComment> getComments() {
        return comments;
    }

    @OneToMany(mappedBy = "sample")
    private Set<SampleComment> comments;


    @OneToMany(mappedBy = "sample")
    private List<SampleTaxonIndicateur> sampleTaxonIndicateur;


    @OneToMany(mappedBy = "sampleId")
    private List<SampleIndiceHistory> sampleIndiceHistoryList;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SPH_ID", updatable = false, insertable = false)
    private SampleProtocolType sampleProtocolType;


    /**
     * MIDAT plus fields to get the legend
     *
     * @return
     */
    @Column(name = "SPH_IVR_ID_SPEAR")
    private Integer spearLegendVersionId;

    @Column(name = "SPH_IVR_ID_IBCH")
    private Integer ibchLegendVersionId;

    @Column(name = "SPH_IVR_ID_MAKROINDEX")
    private Integer makroLegendVersionId;


    @Column(name = "SPH_GIMAX")
    private Double valeurGi;// GI niveau/taxon(s)

    @Column(name = "SPH_TAXONINDICATEUR")
    private String taxonIndicateur;//GI niveau/taxon(s)

    @Column(name = "SPH_GI_FINAL")
    private Double giFinal; //GI_2019

    @Column(name = "SPH_CLASSEVARIETE_FINAL")
    private Double valuerVt; //VT_2019

    @Column(name = "SPH_INDEXVALUEIBCH")
    private Double ibchIndexValue;

    @Column(name = "SPH_IBCHROBUST")
    private Double ibchRobust; //IBCH_2019_R

    @Column(name = "SPH_SPEARINDEXVALUE")
    private Double spearIndexValue; // SPEAR_2018

    @Column(name = "SPH_SUMFAMILY")
    private Integer taxonSumFamily; // somme taxons IBCH

    @Column(name = "SPH_EPHEMEROPTERACOUNTER")
    private Integer ephemeropteraCounter;// somme familles Ephemeroptera

    @Column(name = "SPH_TRICOPTERACOUNTER")
    private Integer tricopteraCounter;// familles Plecoptera

    @Column(name = "SPH_PLECOPTERACOUNTER")
    private Integer plecopteraCounter;//familles Trichoptera

    @Column(name = "SPH_SOMMEABON")
    private Integer sommeAbon;//old Abondances


    @Column(name = "SPH_TAXONFREQUENCESUM")
    private Integer taxonFreqSum;//new Abondances


    @Column(name = "SPH_IBCHQ")
    private Integer ibchQ; // Régime IBCH-Q

    @Column(name = "SPH_VC")
    private Double valeurCorrection; //Valeur de correction VC



    public String getProject() {
        return project;
    }

    public String getSampleCommentOther() {
        return sampleCommentOther;
    }

    public Long getSampleProjectId() {
        return sampleProjectId;
    }

    public Project getSampleProject() {
        return sampleProject;
    }

    public void setSampleCommentOther(String sampleCommentOther) {
        this.sampleCommentOther = sampleCommentOther;
    }

    public Set<Long> getGroupIds() {
        return groups.stream().map(group -> group.getId()).collect(Collectors.toSet());
    }

    public SampleStation getStation() {
        return station;
    }

    public Institution getPrincipalInstitution() {
        return principalInstitution;
    }

    public Institution getMandataryInstitution() {
        return mandataryInstitution;
    }

    public MIDATProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }


    public ThesaurusValue getPrecisionReferenceSystem() {
        return precisionReferenceSystem;
    }

    public ThesaurusValue getPrecision() {
        return precision;
    }

    public ThesaurusValue getIndiceType() {
        return indiceType;
    }

    public Date getSampleDate() {
        return sampleDate;
    }

    public Date getAnalysisDate() {
        return analysisDate;
    }

    public Set<SampleAttribute> getAttributes() {
        return attributes;
    }

    public Set<SampleOriginalFile> getOriginalFiles() {
        return originalFiles;
    }

    public String getPrecisionReferenceSystemI18n() {
        return precisionReferenceSystemI18n;
    }

    public String getPrecisionI18n() {
        return precisionI18n;
    }

    public String getIndiceTypeI18n() {
        return indiceTypeI18n;
    }

    public Double getIbchIndexValue() {
        return ibchIndexValue;
    }

    public Double getMakroIndexValue() {
        return makroIndexValue;
    }

    public Double getSpearIndexValue() {
        return spearIndexValue;
    }

    public List<LabRecordField> getLabRecordFields() {
        return labRecordFields;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public boolean getPublished() {
        return published;
    }

    public Set<SampleDocument> getDocuments() {
        return documents;
    }

    public boolean isAbsoluteNumbers() {
        return absoluteNumbers;
    }

    public Integer getSampleDateDay() {
        return sampleDateDay;
    }

    public Integer getSampleDateMonth() {
        return sampleDateMonth;
    }

    public Integer getSampleDateYear() {
        return sampleDateYear;
    }
    public List<SampleTaxonIndicateur> getSampleTaxonIndicateur() {
        return sampleTaxonIndicateur;
    }

    public void setSampleTaxonIndicateur(List<SampleTaxonIndicateur> sampleTaxonIndicateur) {
        this.sampleTaxonIndicateur = sampleTaxonIndicateur;
    }

    public SampleMassOriginalFile getSampleMassOriginalFile() {
        return sampleMassOriginalFile;
    }

    public void setSampleMassOriginalFile(SampleMassOriginalFile sampleMassOriginalFile) {
        this.sampleMassOriginalFile = sampleMassOriginalFile;
    }

    public Long getSampleMassOriginalFileId() {
        return sampleMassOriginalFileId;
    }

    public void setSampleMassOriginalFileId(Long sampleMassOriginalFileId) {
        this.sampleMassOriginalFileId = sampleMassOriginalFileId;
    }

    public SampleProtocolType getSampleProtocolType() {
        return sampleProtocolType;
    }

    public void setSampleProtocolType(SampleProtocolType sampleProtocolType) {
        this.sampleProtocolType = sampleProtocolType;
    }

    public Integer getSpearLegendVersionId() {
        return spearLegendVersionId;
    }

    public void setSpearLegendVersionId(Integer spearLegendVersionId) {
        this.spearLegendVersionId = spearLegendVersionId;
    }

    public Integer getIbchLegendVersionId() {
        return ibchLegendVersionId;
    }

    public void setIbchLegendVersionId(Integer ibchLegendVersionId) {
        this.ibchLegendVersionId = ibchLegendVersionId;
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


    public Double getIbchRobust() {
        return ibchRobust;
    }

    public void setIbchRobust(Double ibchRobust) {
        this.ibchRobust = ibchRobust;
    }

    public void setSpearIndexValue(Double spearIndexValue) {
        this.spearIndexValue = spearIndexValue;
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

    public Integer getIbchQ() {
        return ibchQ;
    }

    public void setIbchQ(Integer ibchQ) {
        this.ibchQ = ibchQ;
    }

    public Double getValeurCorrection() {
        return valeurCorrection;
    }

    public void setValeurCorrection(Double valeurCorrection) {
        this.valeurCorrection = valeurCorrection;
    }

    public List<SampleIndiceHistory> getSampleIndiceHistoryList() {
        return sampleIndiceHistoryList;
    }

    public void setSampleIndiceHistoryList(List<SampleIndiceHistory> sampleIndiceHistoryList) {
        this.sampleIndiceHistoryList = sampleIndiceHistoryList;
    }

    public Integer getTaxonFreqSum() {
        return taxonFreqSum;
    }

    public void setTaxonFreqSum(Integer taxonFreqSum) {
        this.taxonFreqSum = taxonFreqSum;
    }

    public Long getSampleIphId() {
        return sampleIphId;
    }

    public void setSampleIphId(Long sampleIphId) {
        this.sampleIphId = sampleIphId;
    }
}
