package ch.cscf.jeci.domain.entities.midat.sample;

import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.domain.entities.base.Person;
import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusValue;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * @author: henryp
 */
@Entity
@Table(
        name="SAMPLEHEADERITEM", schema = "MIDAT"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="SHM_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="SHM_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "SHM_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "SHM_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "SHM_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "SHM_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "SHM_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "SHM_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "SHM_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "SHM_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="SEQ_SAMPLEHEADERITEM")
public class SampleAttribute extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "SHM_SPH_ID")
    private Sample parent;

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "SHM_CVL_ID_MIDATHDITMTY")
    private ThesaurusValue attributeType;

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "SHM_CVL_ID_MIDATPROTO")
    private ThesaurusValue protocolType;

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name="SHM_PER_ID")
    private Person author;

    @Column(name = "SHM_CVL_ID_MIDATHDITMTY", updatable = false, insertable = false)
    private Long attributeTypeId;

    @Column(name = "SHM_CVL_ID_MIDATPROTO", updatable = false, insertable = false)
    private Long protocolTypeId;

    @Column(name = "SHM_LAN_ID", updatable = false, insertable = false)
    private Long languageId;

    @Column(name = "SHM_ITEM")
    private String value;

    @Column(name="SHM_TYPECODE")
    @Type(type="ch.cscf.jeci.persistence.hibernate.AuthorshipSourceUserType")
    private SampleAttributeSource sourceType;

    public Sample getParent() {
        return parent;
    }

    public ThesaurusValue getAttributeType() {
        return attributeType;
    }

    public Person getAuthor() {
        return author;
    }

    public Long getAttributeTypeId() {
        return attributeTypeId;
    }

    public String getValue() {
        return value;
    }

    public SampleAttributeSource getSourceType() {
        return sourceType;
    }

    public ThesaurusValue getProtocolType() {
        return protocolType;
    }

    public Long getProtocolTypeId() {
        return protocolTypeId;
    }
}
