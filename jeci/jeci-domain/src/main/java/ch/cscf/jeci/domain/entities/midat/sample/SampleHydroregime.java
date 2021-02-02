package ch.cscf.jeci.domain.entities.midat.sample;

import ch.cscf.jeci.domain.annotations.LocalizedProperty;
import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusValue;

import javax.persistence.*;

/**
 * @author: kanso
 */
@Entity
@Table(
        name="HYDROREGIME", schema = "MIDAT"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="HDR_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="HDR_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "HDR_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "HDR_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "HDR_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "HDR_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "HDR_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "HDR_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "HDR_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "HDR_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="MIDAT.SEQ_HYDROREGIME")
public class SampleHydroregime extends BaseEntity {


@Column(name = "HDR_CVL_ID_IBCH_Q")
private Integer ibchQCvlId;

@Column(name = "HDR_ORDER")
private Integer order;


    @Column(name = "HDR_CODE")
    private String hdrCode;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getIbchQCvlId() {
        return ibchQCvlId;
    }

    public void setIbchQCvlId(Integer ibchQCvlId) {
        this.ibchQCvlId = ibchQCvlId;
    }

    public String getHdrCode() {
        return hdrCode;
    }

    public void setHdrCode(String hdrCode) {
        this.hdrCode = hdrCode;
    }
}

