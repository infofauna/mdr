package ch.cscf.jeci.domain.dto.midat;

import ch.cscf.jeci.utils.PartialDateFormatter;
import ch.cscf.jeci.utils.StringUtil;
import com.querydsl.core.annotations.QueryProjection;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: henryp
 */
public class SearchResultDTO {

    private Long sampleId;

    private Map<String, String> watercourse;

    private Map<String, String> locality;

    private Date sampleDate;
    private Integer sampleDateDay, sampleDateMonth, sampleDateYear;

    private Double ibchIndexValue;

    private Double makroIndexValue;

    private Double spearIndexValue;

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

    private Long operatorId;

    private String operatorFirstName;

    private String operatorLastName;

    private String operatorExcel;

    private Long determinatorId;

    private String determinatorFirstName;

    private String determinatorLastName;

    private String determinatorExcel;

    private String protocolType;

    private String project;


    private String stationNumber;

    private BioticIndexesDTO indexes;

    private List<String> groups;

    private boolean published;

    private Long stationId;



    private boolean isMainStations;
    private boolean isLinkedToMainStation;
    private double staLinkBuffInMeter;

    public SearchResultDTO(){}

    @QueryProjection
    public SearchResultDTO(Long sampleId, Date sampleDate, Integer sampleDateDay, Integer sampleDateMonth,Integer sampleDateYear,
                           Double ibchIndexValue, Double makroIndexValue, Double spearIndexValue,
                           Integer ibchLegendVersionId,
                           Integer makroLegendVersionId,
                           Integer spearLegendVersionId,
                           String watercourseFr, String watercourseDe, String watercourseIt,
                           String localityFr, String localityDe, String localityIt,
                           Long operatorId, String operatorFirstName, String operatorLastName,
                           Long determinatorId, String determinatorFirstName, String determinatorLastName,
                           String determinatorExcel, String operatorExcel,String protocolType, boolean published, String project, String stationNumber, Long stationId,
                           Double valeurGi,// GI niveau/taxon(s)
                           String taxonIndicateur,//GI niveau/taxon(s)
                           Double giFinal, //GI_2019
                           Double valuerVt, //VT_2019
                           Double ibchRobust, //IBCH_2019_R
                           Integer taxonSumFamily, // somme taxons IBCH
                           Integer ephemeropteraCounter,// somme familles Ephemeroptera
                           Integer tricopteraCounter,// familles Plecoptera
                           Integer plecopteraCounter,//familles Trichoptera
                           Integer sommeAbon,//Abondances
                           Integer ibchQ, // Régime IBCH-Q
                           Double valeurCorrection//Valeur de correction VC

                           ) {

        this.sampleId = sampleId;
        this.sampleDate = sampleDate;
        this.sampleDateDay = sampleDateDay;
        this.sampleDateMonth = sampleDateMonth;
        this.sampleDateYear = sampleDateYear;
        this.ibchIndexValue = ibchIndexValue;
        this.makroIndexValue = makroIndexValue;
        this.spearIndexValue = spearIndexValue;

        this.ibchLegendVersionId = ibchLegendVersionId;
        this.makroLegendVersionId = makroLegendVersionId;
        this.spearLegendVersionId = spearLegendVersionId;


        this.operatorId = operatorId;
        this.operatorFirstName = operatorFirstName;
        this.operatorLastName = operatorLastName;
        this.determinatorId = determinatorId;
        this.determinatorFirstName = determinatorFirstName;
        this.determinatorLastName = determinatorLastName;
        this.determinatorExcel = determinatorExcel;
        this.operatorExcel = operatorExcel;
        this.protocolType = protocolType;
        this.published=published;
        this.project=project;
        this.stationNumber=stationNumber;
        this.stationId = stationId;

        watercourse=new HashMap<>(3);

        watercourse.put("fr", watercourseFr);
        watercourse.put("de", watercourseDe);
        watercourse.put("it", watercourseIt);

        locality = new HashMap<>(3);

        locality.put("fr", localityFr);
        locality.put("de", localityDe);
        locality.put("it", localityIt);



        this.valeurGi= valeurGi;// GI niveau/taxon(s)
        this.taxonIndicateur= taxonIndicateur;//GI niveau/taxon(s)
        this.giFinal= giFinal; //GI_2019
        this.valuerVt= valuerVt; //VT_2019
        this.ibchRobust= ibchRobust; //IBCH_2019_R
        this.taxonSumFamily= taxonSumFamily; // somme taxons IBCH
        this.ephemeropteraCounter= ephemeropteraCounter;// somme familles Ephemeroptera
        this.tricopteraCounter= tricopteraCounter;// familles Plecoptera
        this.plecopteraCounter= plecopteraCounter;//familles Trichoptera
        this.sommeAbon= sommeAbon;//Abondances
        this.ibchQ= ibchQ; // Régime IBCH-Q
        this.valeurCorrection= valeurCorrection; //Valeur de correction VC

    }

    public String getDeterminator(){
        if(determinatorId != null){
            return StringUtil.formatNames(determinatorFirstName, determinatorLastName);
        }
        return determinatorExcel;
    }

    public String getOperator(){
        if(operatorId != null) {
            return StringUtil.formatNames(operatorFirstName, operatorLastName);
        }
        return operatorExcel;
    }

    public String getSampleDateIncludingPartialDate(){
        return new PartialDateFormatter(sampleDate, sampleDateDay, sampleDateMonth, sampleDateYear).getFormattedDate();
    }


    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    public Map<String, String> getWatercourse() {
        return watercourse;
    }

    public void setWatercourse(Map<String, String> watercourse) {
        this.watercourse = watercourse;
    }

    public Map<String, String> getLocality() {
        return locality;
    }

    public void setLocality(Map<String, String> locality) {
        this.locality = locality;
    }

    public Date getSampleDate() {
        return sampleDate;
    }

    public void setSampleDate(Date sampleDate) {
        this.sampleDate = sampleDate;
    }

    public Double getIbchIndexValue() {
        return ibchIndexValue;
    }

    public void setIbchIndexValue(Double ibchIndexValue) {
        this.ibchIndexValue = ibchIndexValue;
    }

    public Double getMakroIndexValue() {
        return makroIndexValue;
    }

    public void setMakroIndexValue(Double makroIndexValue) {
        this.makroIndexValue = makroIndexValue;
    }

    public Double getSpearIndexValue() {
        return spearIndexValue;
    }

    public void setSpearIndexValue(Double spearIndexValue) {
        this.spearIndexValue = spearIndexValue;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getDeterminatorId() {
        return determinatorId;
    }

    public void setDeterminatorId(Long determinatorId) {
        this.determinatorId = determinatorId;
    }

    public String getOperatorFirstName() {
        return operatorFirstName;
    }

    public void setOperatorFirstName(String operatorFirstName) {
        this.operatorFirstName = operatorFirstName;
    }

    public String getOperatorLastName() {
        return operatorLastName;
    }

    public void setOperatorLastName(String operatorLastName) {
        this.operatorLastName = operatorLastName;
    }

    public String getDeterminatorFirstName() {
        return determinatorFirstName;
    }

    public void setDeterminatorFirstName(String determinatorFirstName) {
        this.determinatorFirstName = determinatorFirstName;
    }

    public String getDeterminatorLastName() {
        return determinatorLastName;
    }

    public void setDeterminatorLastName(String determinatorLastName) {
        this.determinatorLastName = determinatorLastName;
    }

    public BioticIndexesDTO getIndexes() {
        return indexes;
    }

    public void setIndexes(BioticIndexesDTO indexes) {
        this.indexes = indexes;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getProject() {
        return project;
    }

    public String getStationNumber() {
        return stationNumber;
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


    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public boolean isMainStations() {
        return isMainStations;
    }

    public boolean isLinkedToMainStation() {
        return isLinkedToMainStation;
    }
    public void setMainStations(boolean mainStations) {
        isMainStations = mainStations;
    }

    public void setLinkedToMainStation(boolean linkedToMainStation) {
        isLinkedToMainStation = linkedToMainStation;
    }

    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }

    public double getStaLinkBuffInMeter() {
        return staLinkBuffInMeter;
    }

    public void setStaLinkBuffInMeter(double staLinkBuffInMeter) {
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

    public String getTaxonIndicateur() {
        return taxonIndicateur;
    }

    public Double getGiFinal() {
        return giFinal;
    }

    public Double getValuerVt() {
        return valuerVt;
    }

    public Double getIbchRobust() {
        return ibchRobust;
    }

    public Integer getTaxonSumFamily() {
        return taxonSumFamily;
    }

    public Integer getEphemeropteraCounter() {
        return ephemeropteraCounter;
    }

    public Integer getTricopteraCounter() {
        return tricopteraCounter;
    }

    public Integer getPlecopteraCounter() {
        return plecopteraCounter;
    }

    public Integer getSommeAbon() {
        return sommeAbon;
    }

    public Integer getIbchQ() {
        return ibchQ;
    }

    public Double getValeurCorrection() {
        return valeurCorrection;
    }
}
