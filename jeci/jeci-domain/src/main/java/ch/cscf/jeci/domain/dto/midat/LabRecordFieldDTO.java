package ch.cscf.jeci.domain.dto.midat;

/**
 * @author: henryp
 */
public class LabRecordFieldDTO {


    private String ibchTaxon;

    private String infofaunaTaxon;

    private String stadium;

    private String comment;

    private Long frequency;

    private Integer sortOrder;

    // this is the value of the SPL_SYV_ID
    private Long taxonId;




    public String getIbchTaxon() {
        return ibchTaxon;
    }

    public void setIbchTaxon(String ibchTaxon) {
        this.ibchTaxon = ibchTaxon;
    }

    public String getInfofaunaTaxon() {
        return infofaunaTaxon;
    }

    public void setInfofaunaTaxon(String infofaunaTaxon) {
        this.infofaunaTaxon = infofaunaTaxon;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Long getTaxonId() {
        return taxonId;
    }

    public void setTaxonId(Long taxonId) {
        this.taxonId = taxonId;
    }
}
