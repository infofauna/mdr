package ch.cscf.jeci.domain.dto.midat;

import ch.cscf.jeci.domain.dto.jeci.AuditingInfo;
import ch.cscf.jeci.utils.PartialDateFormatter;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: henryp
 */
public class SampleDetailDTO {

    private String watercourse;
    private String locality;
    private String namedPlace;

    private String stationNumber;

    private Long stationId;
    private Double stationCoordinatesX, stationCoordinatesY, stationAltitude;

    private String stationCanton;

    private String stationGewissNumber;

    private String samplePerson;
    private String validationPerson;

    private String mandataryInstitution;
    private String principalInstitution;

    private String precision;
    private String precisionReferenceSystem;

    private Long sampleId;
    private Date sampleDate;
    private Integer sampleDateDay, sampleDateMonth, sampleDateYear;
    private Date analysisDate;

    private boolean isMassImport;
    private boolean isAbsoluteNumbers;

    private Map<String, String> documents;

    private List<LabRecordFieldDTO> labRecordFields;

    private EvaluationGridDTO evaluationGrid;

    private GroundProtocolDTO groundProtocol;

    private BioticIndexesDTO bioticIndexes;

    private AuditingInfo auditingInfo;

    private String sampleCommentOther;

    private List<CommentDTO> sampleComments;
    private List<CommentDTO> comments;

    private List<StationDTO> sampleStationStations;

    private List<StationDTO> stationStations;

    private List<SampleTaxonIndicateurDTO> sampleTaxonIndicateurs;

    private StationDTO mainStation;

    private String project;

    private Double staLinkBuffInMeter;

    private String protocolType;


    private Integer ibchLegendVersionId;
    private Integer makroLegendVersionId;
    private Integer spearLegendVersionId;

    private Double valeurGi;// GI niveau/taxon(s)

    private String taxonIndicateur;//GI niveau/taxon(s)

    private Double giFinal; //GI_2019

    private Double valuerVt; //VT_2019
    private Double ibchRobust; //IBCH_2019_R

    private Integer taxonSumFamily; // somme taxons IBCH

    private Integer ephemeropteraCounter;// somme familles Ephemeroptera

    private Integer tricopteraCounter;// familles Plecoptera

    private Integer plecopteraCounter;//familles Trichoptera

    private Integer sommeAbon;//Abondances

    private Integer ibchQ; // Régime IBCH-Q

    private Double valeurCorrection; //Valeur de correction VC


    public SampleDetailDTO() {
    }


    public String getWatercourse() {
        return watercourse;
    }

    public void setWatercourse(String watercourse) {
        this.watercourse = watercourse;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    public Date getSampleDate() {
        return sampleDate;
    }

    public String getSampleDateIncludingPartialDate() {
        return new PartialDateFormatter(sampleDate, sampleDateDay, sampleDateMonth, sampleDateYear).getFormattedDate();
    }

    public void setSampleDate(Date sampleDate) {
        this.sampleDate = sampleDate;
    }

    public List<LabRecordFieldDTO> getLabRecordFields() {
        return labRecordFields;
    }

    public void setLabRecordFields(List<LabRecordFieldDTO> labRecordFields) {
        this.labRecordFields = labRecordFields;
    }

    public String getSamplePerson() {
        return samplePerson;
    }

    public void setSamplePerson(String samplePerson) {
        this.samplePerson = samplePerson;
    }

    public String getValidationPerson() {
        return validationPerson;
    }

    public void setValidationPerson(String validationPerson) {
        this.validationPerson = validationPerson;
    }

    public String getMandataryInstitution() {
        return mandataryInstitution;
    }

    public void setMandataryInstitution(String mandataryInstitution) {
        this.mandataryInstitution = mandataryInstitution;
    }

    public String getPrincipalInstitution() {
        return principalInstitution;
    }

    public void setPrincipalInstitution(String principalInstitution) {
        this.principalInstitution = principalInstitution;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getPrecisionReferenceSystem() {
        return precisionReferenceSystem;
    }

    public void setPrecisionReferenceSystem(String precisionReferenceSystem) {
        this.precisionReferenceSystem = precisionReferenceSystem;
    }

    public Date getAnalysisDate() {
        return analysisDate;
    }

    public void setAnalysisDate(Date analysisDate) {
        this.analysisDate = analysisDate;
    }

    public String getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }

    public Double getStationCoordinatesX() {
        return stationCoordinatesX;
    }

    public void setStationCoordinatesX(Double stationCoordinatesX) {
        this.stationCoordinatesX = stationCoordinatesX;
    }

    public Double getStationCoordinatesY() {
        return stationCoordinatesY;
    }

    public void setStationCoordinatesY(Double stationCoordinatesY) {
        this.stationCoordinatesY = stationCoordinatesY;
    }

    public EvaluationGridDTO getEvaluationGrid() {
        return evaluationGrid;
    }

    public void setEvaluationGrid(EvaluationGridDTO evaluationGrid) {
        this.evaluationGrid = evaluationGrid;
    }

    public boolean isMassImport() {
        return isMassImport;
    }

    public void setMassImport(boolean isMassImport) {
        this.isMassImport = isMassImport;
    }

    public GroundProtocolDTO getGroundProtocol() {
        return groundProtocol;
    }

    public void setGroundProtocol(GroundProtocolDTO groundProtocol) {
        this.groundProtocol = groundProtocol;
    }

    public BioticIndexesDTO getBioticIndexes() {
        return bioticIndexes;
    }

    public void setBioticIndexes(BioticIndexesDTO bioticIndexes) {
        this.bioticIndexes = bioticIndexes;
    }

    public Double getStationAltitude() {
        return stationAltitude;
    }

    public void setStationAltitude(Double stationAltitude) {
        this.stationAltitude = stationAltitude;
    }

    public String getNamedPlace() {
        return namedPlace;
    }

    public void setNamedPlace(String namedPlace) {
        this.namedPlace = namedPlace;
    }

    public Map<String, String> getDocuments() {
        return documents;
    }

    public void setDocuments(Map<String, String> documents) {
        this.documents = documents;
    }

    public boolean isAbsoluteNumbers() {
        return isAbsoluteNumbers;
    }

    public void setIsAboluteNumbers(boolean isAboluteNumbers) {
        this.isAbsoluteNumbers = isAboluteNumbers;
    }

    public Integer getSampleDateDay() {
        return sampleDateDay;
    }

    public void setSampleDateDay(Integer sampleDateDay) {
        this.sampleDateDay = sampleDateDay;
    }

    public Integer getSampleDateMonth() {
        return sampleDateMonth;
    }

    public void setSampleDateMonth(Integer sampleDateMonth) {
        this.sampleDateMonth = sampleDateMonth;
    }

    public Integer getSampleDateYear() {
        return sampleDateYear;
    }

    public void setSampleDateYear(Integer sampleDateYear) {
        this.sampleDateYear = sampleDateYear;
    }

    public AuditingInfo getAuditingInfo() {
        return auditingInfo;
    }

    public void setAuditingInfo(AuditingInfo auditingInfo) {
        this.auditingInfo = auditingInfo;
    }

    public String getSampleCommentOther() {
        return sampleCommentOther;
    }

    public void setSampleCommentOther(String sampleCommentOther) {
        this.sampleCommentOther = sampleCommentOther;
    }

    public List<CommentDTO> getSampleComments() {
        return sampleComments;
    }

    public void setSampleComments(List<CommentDTO> sampleComments) {
        this.sampleComments = sampleComments;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getStationCanton() {
        return stationCanton;
    }

    public void setStationCanton(String stationCanton) {
        this.stationCanton = stationCanton;
    }

    public String getStationGewissNumber() {
        return stationGewissNumber;
    }

    public void setStationGewissNumber(String stationGewissNumber) {
        this.stationGewissNumber = stationGewissNumber;
    }

    public List<StationDTO> getSampleStationStations() {
        return sampleStationStations;
    }

    public void setSampleStationStations(List<StationDTO> sampleStationStations) {
        this.sampleStationStations = sampleStationStations;
    }

    public List<StationDTO> getStationStations() {
        return stationStations;
    }

    public void setStationStations(List<StationDTO> stationStations) {
        this.stationStations = stationStations;
    }
    public StationDTO getMainStation() {
        return mainStation;
    }

    public void setMainStation(StationDTO mainStation) {
        this.mainStation = mainStation;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public void setAbsoluteNumbers(boolean absoluteNumbers) {
        isAbsoluteNumbers = absoluteNumbers;
    }

    public List<SampleTaxonIndicateurDTO> getSampleTaxonIndicateurs() {
        return sampleTaxonIndicateurs;
    }

    public void setSampleTaxonIndicateurs(List<SampleTaxonIndicateurDTO> sampleTaxonIndicateurs) {
        this.sampleTaxonIndicateurs = sampleTaxonIndicateurs;
    }

    public Double getStaLinkBuffInMeter() {
        return staLinkBuffInMeter;
    }

    public void setStaLinkBuffInMeter(Double staLinkBuffInMeter) {
        this.staLinkBuffInMeter = staLinkBuffInMeter;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
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

    public Integer getSpearLegendVersionId() {
        return spearLegendVersionId;
    }

    public void setSpearLegendVersionId(Integer spearLegendVersionId) {
        this.spearLegendVersionId = spearLegendVersionId;
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
}
