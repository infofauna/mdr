package ch.cscf.jeci.domain.entities.systematics;

import ch.cscf.jeci.domain.entities.base.BaseEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @author: henryp
 */
@Entity
@Table(
        name="SYSTVALUE", schema = "INFOFAUNA"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="SYV_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="SYV_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "SYV_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "SYV_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "SYV_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "SYV_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "SYV_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "SYV_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "SYV_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "SYV_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="SEQ_SAMPLEPROTOCOLGRND")
public class Taxon extends BaseEntity {

    public final static String ANIMALIA_CODE = "ANIMALIA";

    @Column(name = "SYV_CODE")
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SYV_CRF_ID")
    private TaxonomicRank taxonomicRank;

    @Column(name = "SYV_CRF_ID", updatable = false, insertable = false)
    private Long taxonomicRankId;

    @Column(name = "SYV_SYV_ID", updatable = false, insertable = false)
    private Long parentTaxonId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SYV_SYV_ID")
    private Taxon parent;

    @OneToMany(mappedBy = "parent")
    private Set<Taxon> children;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SYV_SYV_ID_SYNONYMFOR")
    private Taxon synonymTarget;

    @Column(name = "SYV_AUTHORYEARAUTHOR")
    private String author;

    @Column(name = "SYV_AUTHORYEARYEAR")
    private String year;

    @Column(name = "SYV_AUTHORYEAR")
    private String authorYear;

    @OneToMany(mappedBy = "masterTaxon")
    private List<TaxonDesignation> designations;


    public String getCode() {
        return code;
    }

    public Long getParentTaxonId() {
        return parentTaxonId;
    }

    public String getAuthor() {
        return author;
    }

    public String getYear() {
        return year;
    }

    public List<TaxonDesignation> getDesignations() {
        return designations;
    }

    public TaxonomicRank getTaxonomicRank() {
        return taxonomicRank;
    }

    public Long getTaxonomicRankId() {
        return taxonomicRankId;
    }

    public Taxon getParent() {
        return parent;
    }

    public Set<Taxon> getChildren() {
        return children;
    }

    public String getAuthorYear() {
        return authorYear;
    }

    public Taxon getSynonymTarget() {
        return synonymTarget;
    }
}
