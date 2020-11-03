package ch.cscf.jeci.domain.entities.midat;

import ch.cscf.jeci.domain.entities.base.BaseEntity;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author: kanso
 */

@Entity
@Table(
        name = "IMPORTPROTOCOLLABO", schema = "MIDAT"
)
@AssociationOverrides({
        @AssociationOverride(name = "creationUser", joinColumns = @JoinColumn(name = "IPL_USR_ID_CREATE")),
        @AssociationOverride(name = "updateUser", joinColumns = @JoinColumn(name = "IPL_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "IPL_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "IPL_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "IPL_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "IPL_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "IPL_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "IPL_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "IPL_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "IPL_MODUSER"))
})

@SequenceGenerator(allocationSize=1, name = "idSeq", sequenceName = "MIDAT.SEQ_IMPORTPROTOCOLLABO")
public class LabProtocolImport extends BaseEntity {

    @Column(name = "IPL_IPH_ID")
    private Long parentId;

    @Column(name = "IPL_PTL_ID")
    private Long columnId;

    @Column(name = "IPL_VALUE")
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





