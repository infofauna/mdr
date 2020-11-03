package ch.cscf.jeci.domain.entities.midat;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "v_protocoleindice", schema = "MIDAT")
public class ProtocolIndex {

    @Id
    @Column(name="ID")
    private Long id;

    @Column(name="CANTON")
    private String canton;

    @Column(name="COUNTIBCH")
    private Double countIBCH;

    @Column(name="MINIBCH")
    private Double minIBCH;

    @Column(name="MAXIBCH")
    private Double maxIBCH;

    @Column(name="AVGIBCH")
    private Double avgIBCH;


    @Column(name="COUNTSPEAR")
    private Double countSPEAR;

    @Column(name="MINSPEAR")
    private Double minSPEAR;

    @Column(name="MAXSPEAR")
    private Double maxSPEAR;

    @Column(name="AVGSPEAR")
    private Double avgSPEAR;


    @Column(name="COUNTMAKROINDEX")
    private Double countMAKROINDEX;

    @Column(name="MINMAKROINDEX")
    private Double minMAKROINDEX;

    @Column(name="MAXMAKROINDEX")
    private Double maxMAKROINDEX;

    @Column(name="AVGMAKROINDEX")
    private Double avgMAKROINDEX;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public Double getCountIBCH() {
        return countIBCH;
    }

    public void setCountIBCH(Double countIBCH) {
        this.countIBCH = countIBCH;
    }

    public Double getMinIBCH() {
        return minIBCH;
    }

    public void setMinIBCH(Double minIBCH) {
        this.minIBCH = minIBCH;
    }

    public Double getMaxIBCH() {
        return maxIBCH;
    }

    public void setMaxIBCH(Double maxIBCH) {
        this.maxIBCH = maxIBCH;
    }

    public Double getAvgIBCH() {
        return avgIBCH;
    }

    public void setAvgIBCH(Double avgIBCH) {
        this.avgIBCH = avgIBCH;
    }

    public Double getCountSPEAR() {
        return countSPEAR;
    }

    public void setCountSPEAR(Double countSPEAR) {
        this.countSPEAR = countSPEAR;
    }

    public Double getMinSPEAR() {
        return minSPEAR;
    }

    public void setMinSPEAR(Double minSPEAR) {
        this.minSPEAR = minSPEAR;
    }

    public Double getMaxSPEAR() {
        return maxSPEAR;
    }

    public void setMaxSPEAR(Double maxSPEAR) {
        this.maxSPEAR = maxSPEAR;
    }

    public Double getAvgSPEAR() {
        return avgSPEAR;
    }

    public void setAvgSPEAR(Double avgSPEAR) {
        this.avgSPEAR = avgSPEAR;
    }

    public Double getCountMAKROINDEX() {
        return countMAKROINDEX;
    }

    public void setCountMAKROINDEX(Double countMAKROINDEX) {
        this.countMAKROINDEX = countMAKROINDEX;
    }

    public Double getMinMAKROINDEX() {
        return minMAKROINDEX;
    }

    public void setMinMAKROINDEX(Double minMAKROINDEX) {
        this.minMAKROINDEX = minMAKROINDEX;
    }

    public Double getMaxMAKROINDEX() {
        return maxMAKROINDEX;
    }

    public void setMaxMAKROINDEX(Double maxMAKROINDEX) {
        this.maxMAKROINDEX = maxMAKROINDEX;
    }

    public Double getAvgMAKROINDEX() {
        return avgMAKROINDEX;
    }

    public void setAvgMAKROINDEX(Double avgMAKROINDEX) {
        this.avgMAKROINDEX = avgMAKROINDEX;
    }
}
