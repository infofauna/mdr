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
        name = "PROTOCOLMAPPINGMASSMAP", schema = "MIDAT"
)
@AssociationOverrides({
        @AssociationOverride(name = "creationUser", joinColumns = @JoinColumn(name = "PMA_USR_ID_CREATE")),
        @AssociationOverride(name = "updateUser", joinColumns = @JoinColumn(name = "PMA_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "PMA_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "PMA_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "PMA_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "PMA_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "PMA_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "PMA_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "PMA_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "PMA_MODUSER"))
})
@SequenceGenerator(allocationSize=1, name = "idSeq", sequenceName = "MIDAT.SEQ_PROTOCOLMAPPINGMASSMAP")
public class ProtocolMappingMassMap extends BaseEntity {

    @Column(name = "PMA_PMM_ID")
    private Long mappingFieldId;

    @Column(name = "PMA_LAN_ID")
    private Long  languageId;

    @Column(name = "PMA_ALIASCOLUMNNAME")
    private String aliasColumnName;

    public Long getMappingFieldId() {
        return mappingFieldId;
    }

    public void setMappingFieldId(Long mappingFieldId) {
        this.mappingFieldId = mappingFieldId;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public String getAliasColumnName() {
        return aliasColumnName;
    }

    public void setAliasColumnName(String aliasColumnName) {
        this.aliasColumnName = aliasColumnName;
    }
}





