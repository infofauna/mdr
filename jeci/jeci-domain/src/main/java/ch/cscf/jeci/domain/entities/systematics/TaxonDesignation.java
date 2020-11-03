package ch.cscf.jeci.domain.entities.systematics;

import ch.cscf.jeci.domain.entities.base.BaseEntity;

import javax.persistence.*;

/**
 * @author: henryp
 */
@Entity
@Table(
        name="SYSTDESIGNATION", schema = "INFOFAUNA"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="SYD_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="SYD_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "SYD_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "SYD_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "SYD_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "SYD_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "SYD_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "SYD_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "SYD_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "SYD_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="SEQ_SAMPLEPROTOCOLGRND")
public class TaxonDesignation extends BaseEntity {

    @Column(name="SYD_CVL_ID_TYPEDESIGN")
    private Long designationTypeId;

    @Column(name="SYD_SYV_ID")
    private Long taxonId;

    @Column(name="SYD_LAN_ID")
    private Long languageId;

    @Column(name="SYD_DESIGNATION")
    private String designation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SYD_SYV_ID", updatable = false, insertable = false)
    private Taxon masterTaxon;

    public Long getDesignationTypeId() {
        return designationTypeId;
    }

    public Long getTaxonId() {
        return taxonId;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public String getDesignation() {
        return designation;
    }
}
