package ch.cscf.jeci.domain.entities.midat;

import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.domain.entities.midat.sample.Sample;

import javax.persistence.*;

/**
 * @author: henryp
 */
@Entity
@Table(
        name="SAMPLEPROTOCOLGRND", schema = "MIDAT"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="SPD_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="SPD_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "SPD_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "SPD_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "SPD_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "SPD_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "SPD_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "SPD_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "SPD_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "SPD_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="SEQ_SAMPLEPROTOCOLGRND")
public class GroundProtocolItem extends BaseEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPD_SPH_ID")
    Sample parentSample;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPD_PMR_ID")
    GroundProtocolMapping sourceMapping;

    @Column(name = "SPD_VALUE")
    String value;


    public Sample getParentSample() {
        return parentSample;
    }

    public GroundProtocolMapping getSourceMapping() {
        return sourceMapping;
    }

    public String getValue() {
        return value;
    }
}
