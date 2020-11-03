package ch.cscf.jeci.domain.entities.midat.sample;

import ch.cscf.jeci.domain.annotations.LocalizedProperty;
import ch.cscf.jeci.domain.entities.base.BaseEntity;
import ch.cscf.jeci.domain.entities.thesaurus.Language;
import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusValue;

import javax.persistence.*;

/**
 * @author: henryp
 */
@Entity
@Table(
        name="SAMPLESTATIONITEM", schema = "MIDAT"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="SSI_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="SSI_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "SSI_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "SSI_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "SSI_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "SSI_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "SSI_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "SSI_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "SSI_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "SSI_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="SEQ_SAMPLESTATIONITEM")
public class SampleStationItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "SSI_SST_ID")
    private SampleStation station;

    @Column(name = "SSI_SST_ID", insertable = false, updatable = false)
    private Long stationId;

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "SSI_LAN_ID")
    private Language language;

    @Column(name = "SSI_LAN_ID", insertable = false, updatable = false)
    Long languageId;

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name = "SSI_CVL_ID_MIDATSTITMTY")
    private ThesaurusValue type;

    @Transient
    @LocalizedProperty("type")
    private String typeI18n;

    @Column(name = "SSI_CVL_ID_MIDATSTITMTY", insertable = false, updatable = false)
    private Long typeId;

    @Column(name = "SSI_ITEM")
    private String value;


    public SampleStation getStation() {
        return station;
    }

    public void setStation(SampleStation station) {
        this.station = station;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public ThesaurusValue getType() {
        return type;
    }

    public void setType(ThesaurusValue type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public String getTypeI18n() {
        return typeI18n;
    }

    public Long getStationId() {
        return stationId;
    }
}
