package ch.cscf.jeci.domain.entities.midat;

import ch.cscf.jeci.domain.entities.base.BaseEntity;

import javax.persistence.*;

/**
 * @author: Kanso
 */

@Entity
@Table(
        name = "IMPORTPROTOCOLGRND", schema = "MIDAT"
)
@AssociationOverrides({
        @AssociationOverride(name = "creationUser", joinColumns = @JoinColumn(name = "IPN_USR_ID_CREATE")),
        @AssociationOverride(name = "updateUser", joinColumns = @JoinColumn(name = "IPN_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "IPN_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "IPN_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "IPN_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "IPN_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "IPN_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "IPN_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "IPN_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "IPN_MODUSER"))
})

@SequenceGenerator(allocationSize=1, name = "idSeq", sequenceName = "MIDAT.SEQ_IMPORTPROTOCOLGRND")
public class GroundProtocolImport extends BaseEntity {

    @Column(name = "IPN_IPH_ID")
    private Long parentId;

    @Column(name = "IPN_PMR_ID")
    private Long columnId;

    @Column(name = "IPN_VALUE")
    private String columnValue;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }
}





