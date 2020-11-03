package ch.cscf.jeci.domain.entities.midat;

import ch.cscf.jeci.domain.entities.base.BaseEntity;

import javax.persistence.*;

/**
 * @author: henryp
 */
@Entity
@Table(
        name="IMPORTMASSDATAHEADER", schema = "MIDAT"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="IPH_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="IPH_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "IPH_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "IPH_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "IPH_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "IPH_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "IPH_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "IPH_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "IPH_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "IPH_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="SEQ_IMPORTPROTOCOLHEADER")
public class MassImportHeader extends BaseEntity {



}
