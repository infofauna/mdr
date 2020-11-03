package ch.cscf.jeci.domain.entities.thesaurus;

import ch.cscf.jeci.domain.entities.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * @author: henryp
 */
@Entity
@Table(
        name="CODEVALUE", schema = "INFOFAUNA"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="CVL_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="CVL_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "CVL_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "CVL_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "CVL_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "CVL_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "CVL_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "CVL_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "CVL_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "CVL_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="SEQ_CODEVALUE")
public class ThesaurusValue extends BaseEntity {

    @Column(name = "CVL_CODE")
    private String code;

    @Column(name = "CVL_SORTORDER")
    private Long sortOrder;

    @OneToMany(mappedBy = "value")
    private List<LocalizedThesaurusEntry> localizedEntries;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVL_CVL_ID")
    private ThesaurusValue parentValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVL_CRF_ID")
    private ThesaurusCodesRealm realm;

    @Override
    public String toString() {
        return "ThesaurusValue{" +
                "code='" + code + '\'' +
                ", sortOrder=" + sortOrder +
                '}';
    }

    public String getCode() {
        return code;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public List<LocalizedThesaurusEntry> getLocalizedEntries() {
        return localizedEntries;
    }

    public ThesaurusValue getParentValue() {
        return parentValue;
    }

    public ThesaurusCodesRealm getRealm() {
        return realm;
    }
}
