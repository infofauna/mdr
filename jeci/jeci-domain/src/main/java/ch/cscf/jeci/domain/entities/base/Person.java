package ch.cscf.jeci.domain.entities.base;

import ch.cscf.jeci.domain.annotations.LocalizedProperty;
import ch.cscf.jeci.domain.entities.thesaurus.Language;
import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusValue;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author: henryp
 */

@Entity
@Table(
        name="PERSON", schema = "INFOFAUNA"
)
@AssociationOverrides( {
        @AssociationOverride(name="creationUser", joinColumns=@JoinColumn(name="PER_USR_ID_CREATE")),
        @AssociationOverride(name="updateUser", joinColumns=@JoinColumn(name="PER_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "PER_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "PER_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "PER_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "PER_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "PER_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "PER_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "PER_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "PER_MODUSER"))
})
@SequenceGenerator(name="idSeq", sequenceName="INFOFAUNA.SEQ_PERSON")
public class Person extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PER_CVL_ID_TITLE", updatable = false, insertable = false)
    private ThesaurusValue title;

    @Column(name = "PER_CVL_ID_TITLE")
    private Long titleId;

    @Transient
    @LocalizedProperty("title")
    private String titleI18n;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PER_CVL_ID_GENDER", updatable = false, insertable = false)
    private ThesaurusValue gender;

    @Column(name = "PER_CVL_ID_GENDER")
    private Long genderId;

    @Transient
    @LocalizedProperty("gender")
    private String genderI18n;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PER_CVL_ID_COUNTRY", updatable = false, insertable = false)
    private ThesaurusValue country;

    @Column(name = "PER_CVL_ID_COUNTRY")
    private Long countryId;

    @Transient
    @LocalizedProperty("country")
    private String countryI18n;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PER_LAN_ID", updatable = false, insertable = false)
    private Language language;

    @Column(name = "PER_LAN_ID")
    private Long languageId;

    @Column(name = "PER_BIRTHDATE")
    private Date dateOfBirth;

    @NotEmpty
    @Size(max = 60)
    @Column(name = "PER_FIRSTNAME", length = 60)
    private String firstName;

    @NotEmpty
    @Size(max = 60)
    @Column(name = "PER_LASTNAME", length = 60)
    private String lastName;

    @Size(max = 60)
    @Column(name = "PER_ADDRESSLINE1", length = 60)
    private String address1;

    @Size(max = 60)
    @Column(name = "PER_ADDRESSLINE2", length = 60)
    private String address2;

    @Size(max = 20)
    @Column(name = "PER_ZIPCODE", length = 20)
    private String zipCode;

    @Size(max = 60)
    @Column(name = "PER_LOCALITY", length = 60)
    private String locality;

    @Size(max = 30)
    @Column(name = "PER_PROFESSIONALPHONE", length = 30)
    private String proPhone;

    @Size(max = 30)
    @Column(name = "PER_PRIVATEPHONE", length = 30)
    private String privatePhone;

    @Size(max = 30)
    @Column(name = "PER_MOBILEPHONE", length = 30)
    private String mobilePhone;

    @Size(max = 128)
    @Column(name = "PER_ADDRESSEMAIL", length = 128)
    private String email;

    @Override
    public String toString() {
        return "Person{" +
                "ID='" + getId() + '\'' +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public String getNames(){
        return lastName + ", " + firstName;
    }

    public ThesaurusValue getTitle() {
        return title;
    }

    public ThesaurusValue getGender() {
        return gender;
    }

    public ThesaurusValue getCountry() {
        return country;
    }

    public Language getLanguage() {
        return language;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public String getProPhone() {
        return proPhone;
    }

    public String getPrivatePhone() {
        return privatePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setProPhone(String proPhone) {
        this.proPhone = proPhone;
    }

    public void setPrivatePhone(String privatePhone) {
        this.privatePhone = privatePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitleI18n() {
        return titleI18n;
    }

    public String getGenderI18n() {
        return genderI18n;
    }

    public String getCountryI18n() {
        return countryI18n;
    }

    public Long getTitleId() {
        return titleId;
    }

    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }

    public Long getGenderId() {
        return genderId;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }
}
