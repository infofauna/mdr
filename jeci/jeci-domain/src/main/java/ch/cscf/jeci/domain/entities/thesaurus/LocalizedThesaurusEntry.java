package ch.cscf.jeci.domain.entities.thesaurus;

import javax.persistence.*;

/**
 * @author: henryp
 */
@Entity()
@Table(name = "v_thesaurus", schema = "infofauna")
public class LocalizedThesaurusEntry {

    @Id
    private Long id;

    @Column(name="realm_code")
    private String realmCode;

    @Column(name="realm_designation")
    private String realmDesignation;

    @Column(name="realm_default_language")
    private String realmDefaultLanguage;

    @Column(name="value_id", insertable=false, updatable=false)
    private Long valueId;

    @Column(name="value_sort_order")
    private Long valueSortOrder;

    @Column(name="value_code")
    private String code;

    @Column(name="language")
    private String language;

    @Column(name="type")
    private String type;

    @Column(name="designation")
    private String designation;

    @Column(name="parent_value_id")
    private Long parentValueId;

    @Column(name="parent_value_code")
    private String parentValueCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="value_id")
    private ThesaurusValue value;

    @Override
    public String toString() {
        return "LocalizedThesaurusEntry{" +
                "realmCode='" + realmCode + '\'' +
                ", code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", language='" + language + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getRealmCode() {
        return realmCode;
    }

    public String getRealmDefaultLanguage() {
        return realmDefaultLanguage;
    }

    public Long getValueId() {
        return valueId;
    }

    public Long getValueSortOrder() {
        return valueSortOrder;
    }

    public String getCode() {
        return code;
    }

    public String getLanguage() {
        return language;
    }

    public String getType() {
        return type;
    }

    public String getDesignation() {
        return designation;
    }

    public String getRealmDesignation() {
        return realmDesignation;
    }

    public ThesaurusValue getValue() {
        return value;
    }

    public Long getParentValueId() {
        return parentValueId;
    }

    public String getParentValueCode() {
        return parentValueCode;
    }
}
