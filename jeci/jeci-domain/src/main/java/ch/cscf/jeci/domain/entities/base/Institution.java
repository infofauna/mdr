package ch.cscf.jeci.domain.entities.base;

import ch.cscf.jeci.domain.annotations.LocalizedProperty;
import ch.cscf.jeci.domain.entities.midat.sample.Sample;
import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusValue;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

/**
 * @author: henryp
 */

@Entity
@Table(
        name="INSTITUTION", schema = "INFOFAUNA"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="INS_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="INS_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "INS_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "INS_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "INS_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "INS_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "INS_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "INS_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "INS_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "INS_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="INFOFAUNA.SEQ_INSTITUTION")
public class Institution extends BaseEntity {

    public Institution(){

    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INS_CVL_ID_COUNTRY", insertable = false, updatable = false)
    private ThesaurusValue country;

    @LocalizedProperty("country")
    @Transient
    private String countryI18n;

    @Column(name = "INS_CVL_ID_COUNTRY")
    private Long countryId;

    @Column(name = "INS_NAME", length=256)
    @Length(max = 256)
    @NotEmpty
    private String name;

    @Column(name = "INS_ABBREVIATION", length=30)
    @Length(max = 30)
    @NotEmpty
    private String acronym;

    @Column(name = "INS_ADDRESSLINE1", length = 60)
    private String address1;

    @Column(name = "INS_ADDRESSLINE2", length = 60)
    @Length(max = 60)
    private String address2;

    @Column(name = "INS_ZIPCODE", length = 20)
    @Length(max = 20)
    private String zipCode;

    @Column(name = "INS_LOCALITY", length = 20)
    @Length(max = 20)
    private String locality;

    @Column(name = "INS_URL", length = 256)
    @Length(max = 256)
    private String url;

    @Column(name = "INS_PHONE", length = 30)
    @Length(max = 30)
    private String phone;

    @Column(name = "INS_FAX", length = 30)
    @Length(max = 30)
    private String fax;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="INS_PER_ID_PERINCHARGE", updatable = false, insertable = false)
    private Person personInCharge;

    @Column(name="INS_PER_ID_PERINCHARGE")
    private Long personInChargeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INS_CVL_ID_PERINCHARGE_FCT", insertable = false, updatable = false)
    private ThesaurusValue personInChargeFunction;

    @LocalizedProperty("personInChargeFunction")
    @Transient
    private String personInChargeFunctionI18n;

    @Column(name = "INS_CVL_ID_PERINCHARGE_FCT")
    private Long personInChargeFunctionId;

    @OneToMany(mappedBy = "principalInstitution")
    private List<Sample> samples;


    @Column(name="INS_ARMAONLY")
    @Type(type="yes_no")
    private Boolean arma;


    @Override
    public String toString() {
        return "Institution{" +
                "name='" + name + '\'' +
                ", accronym='" + acronym + '\'' +
                '}';
    }

    public ThesaurusValue getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public String getAcronym() {
        return acronym;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getLocality() {
        return locality;
    }

    public String getUrl() {
        return url;
    }

    public String getPhone() {
        return phone;
    }

    public String getFax() {
        return fax;
    }

    public void setCountry(ThesaurusValue country) {
        this.country = country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAcronym(String accronym) {
        this.acronym = accronym;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCountryI18n() {
        return countryI18n;
    }

    public void setCountryI18n(String countryI18n) {
        this.countryI18n = countryI18n;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Person getPersonInCharge() {
        return personInCharge;
    }

    public void setPersonInCharge(Person personInCharge) {
        this.personInCharge = personInCharge;
    }

    public Long getPersonInChargeId() {
        return personInChargeId;
    }

    public void setPersonInChargeId(Long personInChargeId) {
        this.personInChargeId = personInChargeId;
    }

    public ThesaurusValue getPersonInChargeFunction() {
        return personInChargeFunction;
    }

    public void setPersonInChargeFunction(ThesaurusValue personInChargeFunction) {
        this.personInChargeFunction = personInChargeFunction;
    }

    public String getPersonInChargeFunctionI18n() {
        return personInChargeFunctionI18n;
    }

    public void setPersonInChargeFunctionI18n(String personInChargeFunctionI18n) {
        this.personInChargeFunctionI18n = personInChargeFunctionI18n;
    }

    public Long getPersonInChargeFunctionId() {
        return personInChargeFunctionId;
    }

    public void setPersonInChargeFunctionId(Long personInChargeFunctionId) {
        this.personInChargeFunctionId = personInChargeFunctionId;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    public Boolean getArma() {
        return arma;
    }

    public void setArma(Boolean arma) {
        this.arma = arma;
    }

}
