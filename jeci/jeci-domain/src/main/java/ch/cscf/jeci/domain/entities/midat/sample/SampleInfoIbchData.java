package ch.cscf.jeci.domain.entities.midat.sample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "V_INFOIBCHDATA", schema = "MIDAT")
public class SampleInfoIbchData {

    @Id
    @Column(name="SPH_ID")
    private Long sphId;

    @Column(name="TYPE", insertable = false, updatable = false)
    private String type ;

    @Column(name="COLUMN_TAXONIBCH", insertable = false, updatable = false)
    private String colibch ;

    @Column(name="STATION", insertable = false, updatable = false)
    private String station ;


    @Column(name="TAXON_INDICATEUR", insertable = false, updatable = false)
    private String taxonIndicateur ;


    @Column(name="SUM_TAXON", insertable = false, updatable = false)
    private Long sumTaxon;

    @Column(name="GI", insertable = false, updatable = false)
    private Long groupIndicateur;

    @Column(name="VT", insertable = false, updatable = false)
    private Long vt;

    @Column(name="IBCH", insertable = false, updatable = false)
    private Long ibch;


    @Column(name="COUNT_EPHEMEROPTERA", insertable = false, updatable = false)
    private Long  countEphemeroptera;


    @Column(name="COUNT_PLECOPTERA", insertable = false, updatable = false)
    private Long  countPlecoptera;

    @Column(name="COUNT_TRICOPTERA", insertable = false, updatable = false)
    private Long  countTricoptera;


    public Long getSphId() {
        return sphId;
    }

    public void setSphId(Long sphId) {
        this.sphId = sphId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColibch() {
        return colibch;
    }

    public void setColibch(String colibch) {
        this.colibch = colibch;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getTaxonIndicateur() {
        return taxonIndicateur;
    }

    public void setTaxonIndicateur(String taxonIndicateur) {
        this.taxonIndicateur = taxonIndicateur;
    }

    public Long getSumTaxon() {
        return sumTaxon;
    }

    public void setSumTaxon(Long sumTaxon) {
        this.sumTaxon = sumTaxon;
    }

    public Long getGroupIndicateur() {
        return groupIndicateur;
    }

    public void setGroupIndicateur(Long groupIndicateur) {
        this.groupIndicateur = groupIndicateur;
    }

    public Long getVt() {
        return vt;
    }

    public void setVt(Long vt) {
        this.vt = vt;
    }

    public Long getIbch() {
        return ibch;
    }

    public void setIbch(Long ibch) {
        this.ibch = ibch;
    }

    public Long getCountEphemeroptera() {
        return countEphemeroptera;
    }

    public void setCountEphemeroptera(Long countEphemeroptera) {
        this.countEphemeroptera = countEphemeroptera;
    }

    public Long getCountPlecoptera() {
        return countPlecoptera;
    }

    public void setCountPlecoptera(Long countPlecoptera) {
        this.countPlecoptera = countPlecoptera;
    }

    public Long getCountTricoptera() {
        return countTricoptera;
    }

    public void setCountTricoptera(Long countTricoptera) {
        this.countTricoptera = countTricoptera;
    }
}

