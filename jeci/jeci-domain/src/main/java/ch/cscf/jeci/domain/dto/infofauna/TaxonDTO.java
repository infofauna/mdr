package ch.cscf.jeci.domain.dto.infofauna;

public class TaxonDTO {

    private Long id;
    private Long parentId;
    private Long childrenCount;
    private Long synonymChildrenCount;
    private Long synonymsCount;
    private Long synonymTargetId;
    private String designation, authorYear, taxonomicRankDesignation, synonymTargetDesignation, synonymTargetAuthorYear;
    private TaxonDTO parent;

    public TaxonDTO(Long id, Long parentId, String designation, String taxonomicRankDesignation, String authorYear, Long childrenCount, Long synonymChildrenCount, Long synonymsCount, Long synonymTargetId, String synonymTargetDesignation, String synonymTargetAuthorYear) {
        this.id = id;
        this.parentId = parentId;
        this.designation = designation;
        this.taxonomicRankDesignation = taxonomicRankDesignation;
        this.authorYear = authorYear;
        this.childrenCount = childrenCount;
        this.synonymChildrenCount = synonymChildrenCount;
        this.synonymsCount = synonymsCount;
        this.synonymTargetId = synonymTargetId;
        this.synonymTargetDesignation = synonymTargetDesignation;
        this.synonymTargetAuthorYear = synonymTargetAuthorYear;
    }

    @Override
    public String toString() {
        return ("id: " + id) + ", designation: " + designation + ", taxonomicRankDesignation: " + taxonomicRankDesignation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaxonDTO taxonDTO = (TaxonDTO) o;

        if (!id.equals(taxonDTO.id)) return false;
        if (parentId != null ? !parentId.equals(taxonDTO.parentId) : taxonDTO.parentId != null) return false;
        if (!designation.equals(taxonDTO.designation)) return false;
        if (authorYear != null ? !authorYear.equals(taxonDTO.authorYear) : taxonDTO.authorYear != null) return false;
        if (!taxonomicRankDesignation.equals(taxonDTO.taxonomicRankDesignation)) return false;
        if (childrenCount != null ? !childrenCount.equals(taxonDTO.childrenCount) : taxonDTO.childrenCount != null)
            return false;
        if (synonymChildrenCount != null ? !synonymChildrenCount.equals(taxonDTO.childrenCount) : taxonDTO.synonymChildrenCount != null)
            return false;
        if (synonymTargetId != null ? !synonymTargetId.equals(taxonDTO.synonymTargetId) : taxonDTO.synonymTargetId != null)
            return false;
        return !(synonymTargetDesignation != null ? !synonymTargetDesignation.equals(taxonDTO.synonymTargetDesignation) : taxonDTO.synonymTargetDesignation != null);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + designation.hashCode();
        result = 31 * result + (authorYear != null ? authorYear.hashCode() : 0);
        return result;
    }

    public String getSynonymTargetDesignation() {
        return synonymTargetDesignation;
    }

    public void setSynonymTargetDesignation(String synonymTargetDesignation) {
        this.synonymTargetDesignation = synonymTargetDesignation;
    }

    public Long getId() {
        return id;
    }

    public Long getParentId() {
        return parentId;
    }

    public String getDesignation() {
        return designation;
    }

    public String getTaxonomicRankDesignation() {
        return taxonomicRankDesignation;
    }

    public String getAuthorYear() {
        return authorYear;
    }

    public Long getChildrenCount() {
        return childrenCount;
    }

    public Long getSynonymChildrenCount() { return synonymChildrenCount; }

    public Long getSynonymsCount() { return synonymsCount; }

    public Long getSynonymTargetId() {
        return synonymTargetId;
    }

    public String getSynonymTargetAuthorYear() {
        return synonymTargetAuthorYear;
    }

    public boolean isSynonym(){
        return synonymTargetId != null;
    }

    public TaxonDTO getParent() {
        return parent;
    }

    public void setParent(TaxonDTO parent) {
        this.parent = parent;
    }
}
