package ch.cscf.jeci.domain.entities.midat;

import ch.cscf.jeci.domain.annotations.LocalizedProperty;
import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusValue;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: henryp
 */

@Entity
@Table(
        name="PROTOCOLVERSION", schema = "MIDAT"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="PTV_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="PTV_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "PTV_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "PTV_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "PTV_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "PTV_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "PTV_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "PTV_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "PTV_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "PTV_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="SEQ_PROTOCOLVERSION")
public class MIDATProtocolVersion extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PTV_CVL_ID_PROTOCOLTYPE")
    private ThesaurusValue protocolType;


    @Column(name="PTV_DEFAULTFLAG")
    @Type(type="yes_no")
    private boolean usedByDefault;

    @Column(name = "PTV_TEXT")
    private String description;

    @Column(name = "PTV_STARTDATE")
    private Date startDate;

    @Column(name = "PTV_ENDDATE")
    private Date endDate;

    @Column(name = "PTV_ORDER")
    private int sortOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PTV_CVL_ID")
    private ThesaurusValue localizedDescriptionThesaurusValue;

    @LocalizedProperty("localizedDescriptionThesaurusValue")
    @Transient
    private String localizedDescription;

    @Override
    public String toString() {
        return "ProtocolVersion{" +
                "description='" + description + '\'' +
                '}';
    }

    public ThesaurusValue getProtocolType() {
        return protocolType;
    }

    public boolean isUsedByDefault() {
        return usedByDefault;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public ThesaurusValue getLocalizedDescriptionThesaurusValue() {
        return localizedDescriptionThesaurusValue;
    }

    public String getLocalizedDescription() {
        return localizedDescription;
    }
}
