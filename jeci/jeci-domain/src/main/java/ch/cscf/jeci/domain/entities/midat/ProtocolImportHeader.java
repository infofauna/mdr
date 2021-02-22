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
 * @author: henryp
 */

@Entity
@Table(
        name = "IMPORTPROTOCOLHEADER", schema = "MIDAT"
)
@AssociationOverrides({
        @AssociationOverride(name = "creationUser", joinColumns = @JoinColumn(name = "IPH_USR_ID_CREATE")),
        @AssociationOverride(name = "updateUser", joinColumns = @JoinColumn(name = "IPH_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "IPH_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "IPH_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "IPH_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "IPH_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "IPH_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "IPH_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "IPH_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "IPH_MODUSER"))
})
@SequenceGenerator(allocationSize=1, name = "idSeq", sequenceName = "MIDAT.SEQ_IMPORTPROTOCOLHEADER")
public class ProtocolImportHeader extends BaseEntity {

    @Column(name = "IPH_IPH_ID")
    private Long parentId;

    @Column(name = "IPH_SPH_ID_PARENT")
    private Long sphId;

    @Column(name = "IPH_OID")
    private String sampleStationNumber;

    @Column(name = "IPH_WATERCOURSE")
    private String waterCourse;

    @Column(name = "IPH_VALIDSTATUS", columnDefinition = "default 'P'")
    private Character validated;

    @OneToMany(mappedBy = "ownerProtocol")
    @OrderBy("creationDate")
    private List<ProtocolImportLog> logs;

    @Column(name = "IPH_INS_ID_PRINCIPAL")
    private Long principalInstituionId;

    @Column(name = "IPH_INS_ID_MANDATARY")
    private Long mandataryInstitutionId;

    @NotNull
    @Column(name = "IPH_PTV_ID")
    private Long protocolVersionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IPH_PTV_ID", insertable = false, updatable = false)
    private MIDATProtocolVersion protocolVersion;

    @Column(name = "IPH_PER_ID_DETERMINATOR")
    private Long analysisPersonId;

    @Column(name = "IPH_PER_ID_OPERATOR")
    private Long samplePersonId;

    @Column(name = "IPH_DETERMINATEDDATE")
    private Date analysisDate;

    @Length(max = 512)
    @Column(name = "IPH_REPORTURL", length = 512)
    private String documentUrl;

    @Column(name = "IPH_LAN_ID")
    private Long languageId;

    @Column(name = "IPH_CVL_ID_SYSTLREF")
    private Long referenceSystemId;

    @Column(name = "IPH_CVL_ID_SYSTLPREC")
    private Long precisionLevelId;

    @Column(name = "IPH_INPUTFILENAME")
    private String excelFileName;

    @Column(name = "IPH_SHEETNAME")
    private String excelSheetName;


    @Column(name = "IPH_FILE")
    private byte[] excelFileBytes;


    @Column(name = "IPH_PRJ_ID")
    private Long sampleProjectId;






    @Column(name = "IPH_ABSOLUTENUMBERFLAG")
    private String absoluteNumberFlag;


    @Column(name = "IPH_AUTRENEOZ_1")
    private String autreneoz_1;

    @Column(name = "IPH_AUTRENEOZ_2")
    private String autreneoz_2;



    @Column(name = "IPH_DETERMINATOR")
    private String determinator;


    @Column(name = "IPH_ELEVATION")
    private String elevation;

    @Column(name = "IPH_IBCHQ")
    private String ibchq;


    @Column(name = "IPH_IBCHVALUE")
    private String ibchValue;


    @Column(name = "IPH_IBCHVALUE_R")
    private String ibchValue_r;


    @Column(name = "IPH_LOCALITY")
    private String locality;


    @Column(name = "IPH_OBSERVATIONDATETXT")
    private String observationDateText;


    @Column(name = "IPH_OPERATOR")
    private String operator;


    @Column(name = "IPH_SOMMEABON")
    private String sommeAbon;


    @Column(name = "IPH_SOMMEEPT")
    private String sommePt;

    @Column(name = "IPH_SOMMENEOZ")
    private String sommeNeoz;


    @Column(name = "IPH_SOMMETXCOR")
    private String sommeTxcor;


    @Column(name = "IPH_SOMMETXOBS")
    private String sommeTxobs;

    @Column(name = "IPH_SPEARVALUE")
    private String spearValue;

    @Column(name = "IPH_STARTPOINT_X")
    private String startPointX;

    @Column(name = "IPH_STARTPOINT_Y")
    private String startPointY;

    @Column(name = "IPH_VALEURGI")
    private String valeurGi;

    @Column(name = "IPH_VALEURVT")
    private String valeurVt;

    @Column(name = "IPH_VC")
    private String vc;


    @Column(name = "IPH_VALEURGIMAX")
    private String valeurGiMax;

    @Column(name = "IPH_COUNTAUTOZ_2")
    private String countAutoz2;

    @Column(name = "IPH_COUNTAUTOZ_1")
    private String countAutoz1;



    @Transient
    private Set<Long> commentIds;

    @Transient
    private String commentOther;



    @Transient
    @Type(type = "yes_no")
    private boolean commentOtherChk;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }


    public List<ProtocolImportLog> getLogs() {
        return logs;
    }

    public void setLogs(List<ProtocolImportLog> logs) {
        this.logs = logs;
    }

    public Long getPrincipalInstituionId() {
        return principalInstituionId;
    }

    public void setPrincipalInstituionId(Long principalInstituionId) {
        this.principalInstituionId = principalInstituionId;
    }

    public Long getMandataryInstitutionId() {
        return mandataryInstitutionId;
    }

    public void setMandataryInstitutionId(Long mandataryInstitutionId) {
        this.mandataryInstitutionId = mandataryInstitutionId;
    }

    public Long getProtocolVersionId() {
        return protocolVersionId;
    }

    public void setProtocolVersionId(Long protocolVersionId) {
        this.protocolVersionId = protocolVersionId;
    }

    public Long getAnalysisPersonId() {
        return analysisPersonId;
    }

    public void setAnalysisPersonId(Long analysisPersonId) {
        this.analysisPersonId = analysisPersonId;
    }

    public Long getSamplePersonId() {
        return samplePersonId;
    }

    public void setSamplePersonId(Long samplePersonId) {
        this.samplePersonId = samplePersonId;
    }

    public Date getAnalysisDate() {
        return analysisDate;
    }

    public void setAnalysisDate(Date analysisDate) {
        this.analysisDate = analysisDate;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public Long getReferenceSystemId() {
        return referenceSystemId;
    }

    public void setReferenceSystemId(Long referenceSystemId) {
        this.referenceSystemId = referenceSystemId;
    }

    public Long getPrecisionLevelId() {
        return precisionLevelId;
    }

    public void setPrecisionLevelId(Long precisionLevelId) {
        this.precisionLevelId = precisionLevelId;
    }

    public String getExcelFileName() {
        return excelFileName;
    }

    public void setExcelFileName(String excelFileName) {
        this.excelFileName = excelFileName;
    }

    public String getExcelSheetName() {
        return excelSheetName;
    }

    public void setExcelSheetName(String excelSheetName) {
        this.excelSheetName = excelSheetName;
    }

    public byte[] getExcelFileBytes() {
        return excelFileBytes;
    }

    public void setExcelFileBytes(byte[] excelFileBytes) {
        this.excelFileBytes = excelFileBytes;
    }

    public String getSampleStationNumber() {
        return sampleStationNumber;
    }

    public void setSampleStationNumber(String sampleStationNumber) {
        this.sampleStationNumber = sampleStationNumber;
    }

    public String getWaterCourse() {
        return waterCourse;
    }

    public void setWaterCourse(String waterCourse) {
        this.waterCourse = waterCourse;
    }

    public MIDATProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }

    public Long getSampleProjectId() {
        return sampleProjectId;
    }

    public void setSampleProjectId(Long sampleProjectId) {
        this.sampleProjectId = sampleProjectId;
    }

    public Set<Long> getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(Set<Long> commentIds) {
        this.commentIds = commentIds;
    }

    public String getCommentOther() {
        return commentOther;
    }

    public void setCommentOther(String commentOther) {
        this.commentOther = commentOther;
    }

    public boolean isCommentOtherChk() {
        return commentOtherChk;
    }

    public void setCommentOtherChk(boolean commentOtherChk) {
        this.commentOtherChk = commentOtherChk;
    }

    public String getAbsoluteNumberFlag() {
        return absoluteNumberFlag;
    }

    public void setAbsoluteNumberFlag(String absoluteNumberFlag) {
        this.absoluteNumberFlag = absoluteNumberFlag;
    }

    public String getAutreneoz_1() {
        return autreneoz_1;
    }

    public void setAutreneoz_1(String autreneoz_1) {
        this.autreneoz_1 = autreneoz_1;
    }

    public String getAutreneoz_2() {
        return autreneoz_2;
    }

    public void setAutreneoz_2(String autreneoz_2) {
        this.autreneoz_2 = autreneoz_2;
    }

    public String getDeterminator() {
        return determinator;
    }

    public void setDeterminator(String determinator) {
        this.determinator = determinator;
    }

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public String getIbchq() {
        return ibchq;
    }

    public void setIbchq(String ibchq) {
        this.ibchq = ibchq;
    }

    public String getIbchValue() {
        return ibchValue;
    }

    public void setIbchValue(String ibchValue) {
        this.ibchValue = ibchValue;
    }

    public String getIbchValue_r() {
        return ibchValue_r;
    }

    public void setIbchValue_r(String ibchValue_r) {
        this.ibchValue_r = ibchValue_r;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getObservationDateText() {
        return observationDateText;
    }

    public void setObservationDateText(String observationDateText) {
        this.observationDateText = observationDateText;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getSommeAbon() {
        return sommeAbon;
    }

    public void setSommeAbon(String sommeAbon) {
        this.sommeAbon = sommeAbon;
    }

    public String getSommePt() {
        return sommePt;
    }

    public void setSommePt(String sommePt) {
        this.sommePt = sommePt;
    }

    public String getSommeNeoz() {
        return sommeNeoz;
    }

    public void setSommeNeoz(String sommeNeoz) {
        this.sommeNeoz = sommeNeoz;
    }

    public String getSommeTxcor() {
        return sommeTxcor;
    }

    public void setSommeTxcor(String sommeTxcor) {
        this.sommeTxcor = sommeTxcor;
    }

    public String getSommeTxobs() {
        return sommeTxobs;
    }

    public void setSommeTxobs(String sommeTxobs) {
        this.sommeTxobs = sommeTxobs;
    }

    public String getSpearValue() {
        return spearValue;
    }

    public void setSpearValue(String spearValue) {
        this.spearValue = spearValue;
    }

    public String getStartPointX() {
        return startPointX;
    }

    public void setStartPointX(String startPointX) {
        this.startPointX = startPointX;
    }

    public String getStartPointY() {
        return startPointY;
    }

    public void setStartPointY(String startPointY) {
        this.startPointY = startPointY;
    }

    public String getValeurGi() {
        return valeurGi;
    }

    public void setValeurGi(String valeurGi) {
        this.valeurGi = valeurGi;
    }

    public String getValeurVt() {
        return valeurVt;
    }

    public void setValeurVt(String valeurVt) {
        this.valeurVt = valeurVt;
    }

    public String getVc() {
        return vc;
    }

    public void setVc(String vc) {
        this.vc = vc;
    }

    public Character getValidated() {
        return validated;
    }

    public void setValidated(Character validated) {
        this.validated = validated;
    }

    public String getValeurGiMax() {
        return valeurGiMax;
    }

    public void setValeurGiMax(String valeurGiMax) {
        this.valeurGiMax = valeurGiMax;
    }

    public String getCountAutoz2() {
        return countAutoz2;
    }

    public void setCountAutoz2(String countAutoz2) {
        this.countAutoz2 = countAutoz2;
    }

    public String getCountAutoz1() {
        return countAutoz1;
    }

    public void setCountAutoz1(String countAutoz1) {
        this.countAutoz1 = countAutoz1;
    }

    public Long getSphId() {
        return sphId;
    }

    public void setSphId(Long sphId) {
        this.sphId = sphId;
    }

}





