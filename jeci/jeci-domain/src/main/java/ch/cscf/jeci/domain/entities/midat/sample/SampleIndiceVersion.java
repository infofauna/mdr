package ch.cscf.jeci.domain.entities.midat.sample;

import ch.cscf.jeci.domain.entities.base.BaseEntity;

import javax.persistence.*;

/**
 * @author: kanso
 */
@Entity
@Table(
        name="INDICEVERSION", schema = "MIDAT"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="IVR_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="IVR_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "IVR_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "IVR_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "IVR_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "IVR_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "IVR_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "IVR_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "IVR_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "IVR_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="MIDAT.SEQ_INDICEVERSION")
public class SampleIndiceVersion extends BaseEntity {

@Column(name = "IVR_CVL_ID_MIDATINDICE")
private Integer midatIndice;

@Column(name="IVR_CURRENT")
private Character current; //Y or N

public Integer getMidatIndice() {
    return midatIndice;
}

public void setMidatIndice(Integer midatIndice) {
    this.midatIndice = midatIndice;
}

public Character getCurrent() {
    return current;
}

public void setCurrent(Character current) {
    this.current = current;
}
}

