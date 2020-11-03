package ch.cscf.jeci.domain.entities.midat.sample;


import ch.cscf.jeci.domain.annotations.LocalizedProperty;
import ch.cscf.jeci.domain.entities.thesaurus.Language;
import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusValue;

import javax.persistence.*;

@Entity()
@Table(name = "V_STATIONSAMPLESTATIONITEM", schema = "MIDAT")
public class SampleStationItemView {

    @Id
    @Column(name = "SSI_ID")
    private Long id;

    @Column(name = "SSI_SST_ID", insertable = false, updatable = false)
    private Long stationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SSI_LAN_ID" )
    private Language language;

    @Column(name = "SSI_LAN_ID", insertable = false, updatable = false)
    Long languageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SSI_CVL_ID_MIDATSTITMTY")
    private ThesaurusValue type;

    @Transient
    @LocalizedProperty("type")
    private String typeI18n;

    @Column(name = "SSI_CVL_ID_MIDATSTITMTY", insertable = false, updatable = false)
    private Long typeId;

    @Column(name = "SSI_ITEM")
    private String value;

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public ThesaurusValue getType() {
        return type;
    }

    public void setType(ThesaurusValue type) {
        this.type = type;
    }

    public String getTypeI18n() {
        return typeI18n;
    }

    public void setTypeI18n(String typeI18n) {
        this.typeI18n = typeI18n;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
