package ch.cscf.jeci.domain.entities.midat;

import ch.cscf.jeci.domain.entities.base.BaseEntity;

import javax.persistence.*;

/**
 * @author: kanso
 */

@Entity
@Table(
        name = "IMPORTMASSDATADETAIL", schema = "MIDAT"
)
@AssociationOverrides({
        @AssociationOverride(name = "creationUser", joinColumns = @JoinColumn(name = "IMD_USR_ID_CREATE")),
        @AssociationOverride(name = "updateUser", joinColumns = @JoinColumn(name = "IMD_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "IMD_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "IMD_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "IMD_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "IMD_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "IMD_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "IMD_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "IMD_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "IMD_MODUSER"))
})
@SequenceGenerator(allocationSize=1, name = "idSeq", sequenceName = "MIDAT.SEQ_IMPORTMASSDATADETAIL")
public class ImportMassDataDetail extends BaseEntity {

    @Column(name = "IMD_VALIDSTATUS", columnDefinition = "default 'P'")
    private Character validated;

    @Column(name = "IMD_IPH_ID")
    private Long protocolHeaderId;

    @Column(name = "IMD_SOURCELINE")
    private Long  excelLineNumber;




    @Column(name = "IMD_HIGHERTAXON")
    private String highertaxon;

    @Column(name = "IMD_FAMILY")
    private String family;

    @Column(name = "IMD_GENUS")
    private String genus;

    @Column(name = "IMD_SPECIES")
    private String species;

    @Column(name = "IMD_SUBSPECIES")
    private String subspecies;

    @Column(name = "IMD_FREQ1")
    private String freq1;

    @Column(name = "IMD_FREQ2")
    private String freq2;

    @Column(name = "IMD_FREQLUM")
    private String freqlum;

    @Column(name = "IMD_STADIUM")
    private String stadium;

    @Column(name = "IMD_SAMPLINGMETHOD")
    private String samplingmethod;

    @Column(name = "IMD_INDICETYPE")
    private String indicetype;

    @Column(name = "IMD_PERIOD")
    private String period;

    @Column(name = "IMD_DAY")
    private String day;

    @Column(name = "IMD_MONTH")
    private String month;

    @Column(name = "IMD_YEAR")
    private String year;

    @Column(name = "IMD_WATERCOURSE")
    private String watercourse;

    @Column(name = "IMD_LOCALITY")
    private String locality;

    @Column(name = "IMD_CALLEDPLACE")
    private String calledplace;

    @Column(name = "IMD_SWISSCOORD_X")
    private String swisscoordX;

    @Column(name = "IMD_SWISSCOORD_Y")
    private String swisscoordY;

    @Column(name = "IMD_SWISSCOORD_Z")
    private String swisscoordZ;

    @Column(name = "IMD_OBSERVERS")
    private String observers;

    @Column(name = "IMD_PROJECT")
    private String project;

    @Column(name = "IMD_TAXON_DEF")
    private String taxonDef;




    @Column(name = "IMD_CODEPRECISION")
    private String codeprecision;


    @Column(name = "IMD_SYSTEMPRECISION")
    private String systemprecision;


    @Column(name = "IMD_SAMPLENUMBER")
    private String samplenumber;

    @Column(name = "IMD_CANTON")
    private String canton;

    @Column(name = "IMD_OID")
    private String oid;

    @Column(name = "IMD_PRCODE")
    private String precode;

    @Column(name = "IMD_COMMENT")
    private String comment;

    @Column(name = "IMD_REPORTURL")
    private String reporturl;

    @Column(name = "IMD_TAXONIBCH")
    private String taxonibch;


    @Column(name = "IMD_MAKROINDEXPROVIDE")
    private String makroindexprovide;

    @Column(name = "IMD_SPEARINDEXPROVIDE")
    private String spearindexprovide;

    @Column(name = "IMD_IBCHINDEXPROVIDE")
    private String ibchindexprovide;


    @Column(name = "IMD_REMARKCODE1")
    private String remarkcode1;

    @Column(name = "IMD_REMARKCODE2")
    private String remarkcode2;

    @Column(name = "IMD_REMARKCODE3")
    private String remarkcode3;

    @Column(name = "IMD_REMARKCODE4")
    private String remarkcode4;

    @Column(name = "IMD_REMARKTEXT")
    private String remarktext;

    @Column(name = "IMD_OIDLINK")
    private String oidlink;

    @Column(name = "IMD_DETERMINATOR")
    private String determinator;



    public Character getValidated() {
        return validated;
    }

    public void setValidated(Character validated) {
        this.validated = validated;
    }

    public Long getProtocolHeaderId() {
        return protocolHeaderId;
    }

    public void setProtocolHeaderId(Long protocolHeaderId) {
        this.protocolHeaderId = protocolHeaderId;
    }

    public Long getExcelLineNumber() {
        return excelLineNumber;
    }

    public void setExcelLineNumber(Long excelLineNumber) {
        this.excelLineNumber = excelLineNumber;
    }

    public String getHighertaxon() {
        return highertaxon;
    }

    public void setHighertaxon(String highertaxon) {
        this.highertaxon = highertaxon;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getSubspecies() {
        return subspecies;
    }

    public void setSubspecies(String subspecies) {
        this.subspecies = subspecies;
    }

    public String getFreq1() {
        return freq1;
    }

    public void setFreq1(String freq1) {
        this.freq1 = freq1;
    }

    public String getFreq2() {
        return freq2;
    }

    public void setFreq2(String freq2) {
        this.freq2 = freq2;
    }

    public String getFreqlum() {
        return freqlum;
    }

    public void setFreqlum(String freqlum) {
        this.freqlum = freqlum;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getSamplingmethod() {
        return samplingmethod;
    }

    public void setSamplingmethod(String samplingmethod) {
        this.samplingmethod = samplingmethod;
    }

    public String getIndicetype() {
        return indicetype;
    }

    public void setIndicetype(String indicetype) {
        this.indicetype = indicetype;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public String getCalledplace() {
        return calledplace;
    }

    public void setCalledplace(String calledplace) {
        this.calledplace = calledplace;
    }

    public String getSwisscoordX() {
        return swisscoordX;
    }

    public void setSwisscoordX(String swisscoordX) {
        this.swisscoordX = swisscoordX;
    }

    public String getSwisscoordY() {
        return swisscoordY;
    }

    public void setSwisscoordY(String swisscoordY) {
        this.swisscoordY = swisscoordY;
    }

    public String getSwisscoordZ() {
        return swisscoordZ;
    }

    public void setSwisscoordZ(String swisscoordZ) {
        this.swisscoordZ = swisscoordZ;
    }

    public String getObservers() {
        return observers;
    }

    public void setObservers(String observers) {
        this.observers = observers;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTaxonDef() {
        return taxonDef;
    }

    public void setTaxonDef(String taxonDef) {
        this.taxonDef = taxonDef;
    }

    public String getCodeprecision() {
        return codeprecision;
    }

    public void setCodeprecision(String codeprecision) {
        this.codeprecision = codeprecision;
    }

    public String getSystemprecision() {
        return systemprecision;
    }

    public void setSystemprecision(String systemprecision) {
        this.systemprecision = systemprecision;
    }

    public String getSamplenumber() {
        return samplenumber;
    }

    public void setSamplenumber(String samplenumber) {
        this.samplenumber = samplenumber;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getPrecode() {
        return precode;
    }

    public void setPrecode(String precode) {
        this.precode = precode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReporturl() {
        return reporturl;
    }

    public void setReporturl(String reporturl) {
        this.reporturl = reporturl;
    }

    public String getTaxonibch() {
        return taxonibch;
    }

    public void setTaxonibch(String taxonibch) {
        this.taxonibch = taxonibch;
    }

    public String getMakroindexprovide() {
        return makroindexprovide;
    }

    public void setMakroindexprovide(String makroindexprovide) {
        this.makroindexprovide = makroindexprovide;
    }

    public String getSpearindexprovide() {
        return spearindexprovide;
    }

    public void setSpearindexprovide(String spearindexprovide) {
        this.spearindexprovide = spearindexprovide;
    }

    public String getIbchindexprovide() {
        return ibchindexprovide;
    }

    public void setIbchindexprovide(String ibchindexprovide) {
        this.ibchindexprovide = ibchindexprovide;
    }

    public String getRemarkcode1() {
        return remarkcode1;
    }

    public void setRemarkcode1(String remarkcode1) {
        this.remarkcode1 = remarkcode1;
    }

    public String getRemarkcode2() {
        return remarkcode2;
    }

    public void setRemarkcode2(String remarkcode2) {
        this.remarkcode2 = remarkcode2;
    }

    public String getRemarkcode3() {
        return remarkcode3;
    }

    public void setRemarkcode3(String remarkcode3) {
        this.remarkcode3 = remarkcode3;
    }

    public String getRemarkcode4() {
        return remarkcode4;
    }

    public void setRemarkcode4(String remarkcode4) {
        this.remarkcode4 = remarkcode4;
    }

    public String getRemarktext() {
        return remarktext;
    }

    public void setRemarktext(String remarktext) {
        this.remarktext = remarktext;
    }

    public String getOidlink() {
        return oidlink;
    }

    public void setOidlink(String oidlink) {
        this.oidlink = oidlink;
    }

    public String getDeterminator() {
        return determinator;
    }

    public void setDeterminator(String determinator) {
        this.determinator = determinator;
    }
}





