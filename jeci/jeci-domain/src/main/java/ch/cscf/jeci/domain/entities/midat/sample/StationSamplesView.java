package ch.cscf.jeci.domain.entities.midat.sample;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity()
@Table(name = "V_STATIONSAMPLE", schema = "MIDAT")
public class StationSamplesView {

    @Column(name="SPH_SST_ID")
    private Long stationId;


    @Column(name = "SST_OID")
    private String stationNumber;

    @Column(name="SWISSCOORDINATE_X")
    private Double coordinateX;

    @Column(name="SWISSCOORDINATE_Y")
    private Double coordinateY;

    @Id
    @Column(name="SPH_ID")
    private Long sampleId;


    @Column(name = "SPH_OBSERVATIONDATE")
    private Date sampleDate;


    @Column(name = "SPH_INDEXVALUEIBCH")
    private Double ibchIndexValue;

    @Column(name = "SPH_MAKROINDEXVALUE")
    private Double makroIndexValue;

    @Column(name = "SPH_SPEARINDEXVALUE")
    private Double spearIndexValue;


    @Column(name = "SPH_IVR_ID_SPEAR")
    private Integer spearLegendVersionId;

    @Column(name = "SPH_IVR_ID_IBCH")
    private Integer ibchLegendVersionId;

    @Column(name = "SPH_IVR_ID_MAKROINDEX")
    private Integer makroLegendVersionId;



    @Transient
    private List<StationSamplesView> stationSamples;


    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Double getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(Double coordinateX) {
        this.coordinateX = coordinateX;
    }




    public Double getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(Double coordinateY) {
        this.coordinateY = coordinateY;
    }

    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    public String getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
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

    public List<StationSamplesView> getStationSamples() {
        return stationSamples;
    }

    public void setStationSamples(List<StationSamplesView> stationSamples) {
        this.stationSamples = stationSamples;
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
}

