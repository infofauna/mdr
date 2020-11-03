package ch.cscf.jeci.domain.entities.midat;

import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.domain.entities.midat.sample.Sample;

import javax.persistence.*;

/**
 * @author: henryp
 */
@Entity
@Table(
        name="SAMPLEPROTOCOLGRID", schema = "MIDAT"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="SPG_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="SPG_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "SPG_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "SPG_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "SPG_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "SPG_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "SPG_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "SPG_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "SPG_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "SPG_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="MIDAT.SEQ_SAMPLEPROTOCOLLABO")
public class EvaluationGridItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPG_SPH_ID")
    private Sample master;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPG_PMG_ID")
    private EvaluationGridFieldMapping mapping;

    @Column(name = "SPG_PMG_ID", insertable = false, updatable = false)
    private Long mappingId;

    @Column(name = "SPG_VALUE")
    private String value;

    public Sample getMaster() {
        return master;
    }

    public EvaluationGridFieldMapping getMapping() {
        return mapping;
    }

    public String getValue() {
        return value;
    }

    public Long getMappingId() {
        return mappingId;
    }
}
