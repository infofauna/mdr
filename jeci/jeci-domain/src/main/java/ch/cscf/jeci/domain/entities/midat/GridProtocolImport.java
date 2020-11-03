package ch.cscf.jeci.domain.entities.midat;

import ch.cscf.jeci.domain.entities.base.BaseEntity;

import javax.persistence.*;

/**
 * @author: kanso
 */

@Entity
@Table(
        name = "IMPORTPROTOCOLGRID", schema = "MIDAT"
)
@AssociationOverrides({
        @AssociationOverride(name = "creationUser", joinColumns = @JoinColumn(name = "IPG_USR_ID_CREATE")),
        @AssociationOverride(name = "updateUser", joinColumns = @JoinColumn(name = "IPG_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "IPG_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "IPG_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "IPG_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "IPG_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "IPG_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "IPG_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "IPG_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "IPG_MODUSER"))
})

@SequenceGenerator(allocationSize=1, name = "idSeq", sequenceName = "MIDAT.SEQ_IMPORTPROTOCOLGRID")
public class GridProtocolImport extends BaseEntity {

    @Column(name = "IPG_IPH_ID")
    private Long parentId;

    @Column(name = "IPG_PMG_ID")
    private Long columnId;

    @Column(name = "IPG_VALUE")
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





