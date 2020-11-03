package ch.cscf.jeci.domain.entities.base;

import ch.cscf.jeci.domain.annotations.LocalizedProperty;
import ch.cscf.jeci.domain.entities.midat.sample.Sample;
import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusValue;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by henryp on 17/06/16.
 */
@Entity
@Table(
        name = "PROJECT", schema = "INFOFAUNA"
)
@AssociationOverrides({
        @AssociationOverride(name = "creationUser", joinColumns = @JoinColumn(name = "PRJ_USR_ID_CREATE")),
        @AssociationOverride(name = "updateUser", joinColumns = @JoinColumn(name = "PRJ_USR_ID_MODIFY"))
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "PRJ_ID")),
        @AttributeOverride(name = "status", column = @Column(name = "PRJ_STATUS")),
        @AttributeOverride(name = "creationDate", column = @Column(name = "PRJ_USR_CREATE_DATE")),
        @AttributeOverride(name = "updateDate", column = @Column(name = "PRJ_USR_MODIFY_DATE")),
        @AttributeOverride(name = "dbCreationDate", column = @Column(name = "PRJ_CREDATE")),
        @AttributeOverride(name = "dbUpdateDate", column = @Column(name = "PRJ_MODDATE")),
        @AttributeOverride(name = "dbCreationUser", column = @Column(name = "PRJ_CREUSER")),
        @AttributeOverride(name = "dbUpdateUser", column = @Column(name = "PRJ_MODUSER"))
})
@SequenceGenerator(allocationSize=1, name = "idSeq", sequenceName = "INFOFAUNA.SEQ_PERSON")
public class Project extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRJ_CVL_ID_PROJETTYPE", updatable = false, insertable = false)
    private ThesaurusValue projectType;

    @Column(name = "PRJ_CVL_ID_PROJETTYPE")
    private Long projectTypeId;

    @Transient
    @LocalizedProperty("projectType")
    private String projectTypeI18n;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRJ_CVL_ID_PROJETORIG", updatable = false, insertable = false)
    private ThesaurusValue projectOrigin;

    @Column(name = "PRJ_CVL_ID_PROJETORIG")
    private Long projectOriginId;

    @Transient
    @LocalizedProperty("projectOrigin")
    private String projectOriginI18n;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRJ_CVL_ID_PROJETLIMA", updatable = false, insertable = false)
    private ThesaurusValue projectLima;

    @Column(name = "PRJ_CVL_ID_PROJETLIMA")
    private Long projectLimaId;


    @Transient
    @LocalizedProperty("projectLima")
    private String projectLimaI18n;

    @Column(name = "PRJ_ECHEANCEBLOCAGE")
    private Date echeanceBlocage;

    @Column(name = "PRJ_CODE", length = 20)
    @NotEmpty
    private String code;

    @Column(name = "PRJ_CODECH", length = 25)
    private String codeCh;

    @Column(name = "PRJ_DESIGNATION", length = 255)
    @NotEmpty
    private String designation;

    @Column(name = "PRJ_DESCRIPTION", length = 255)
    private String description;

    @Column(name = "PRJ_NOMINSMANDANTE", length = 255)
    private String principalInstitutionName;

    @Column(name = "PRJ_PRENOMRESPINSMANDANTE", length = 60)
    private String principalInstitutionRespFirstName;

    @Column(name = "PRJ_NOMRESPINSMANDANTE", length = 60)
    private String principalInstitutionRespLastName;

    @Column(name = "PRJ_NOMINSMANDATAIRE", length = 255)
    private String mandataryInstitutionName;

    @Column(name = "PRJ_PRENOMRESPINSMANDATAIRE", length = 60)
    private String mandataryInstitutionRespFirstName;

    @Column(name = "PRJ_NOMRESPINSMANDATAIRE", length = 60)
    private String mandataryInstitutionRespLastName;

    @Column(name = "PRJ_DATE_DEBUT")
    private Date startDate;

    @Column(name = "PRJ_DATE_FIN")
    private Date endDate;

    @Column(name = "PRJ_BENEVOLAT")
    @Type(type = "yes_no")
    private Boolean voluntaryWork;

    @Column(name = "PRJ_URL", length = 255)
    private String url;



    @OneToMany(mappedBy = "sampleProject")
    private List<Sample> samples;

    @Column(name="PRJ_PRJ_ID")
    private Long projectProjectId;



    @Column(name = "PRJ_DEBUTJOUR")
    private Long debutJour;


    @Column(name = "PRJ_DEBUTMOIS")
    private Long debutMois;


    @Column(name = "PRJ_DEBUTANNEE")
    private Long debutAnnee;


    @Column(name = "PRJ_FINJOUR")
    private Long finJour;


    @Column(name = "PRJ_FINMOIS")
    private Long finMois;


    @Column(name = "PRJ_FINANNEE")
    private Long finAnnee;


    @Column(name="PRJ_INS_ID_MANDANT")
    private Long principalInstitutionId;


    public Long getPrincipalInstitutionPersonId() {
        return principalInstitutionPersonId;
    }

    public void setPrincipalInstitutionPersonId(Long principalInstitutionPersonId) {
        this.principalInstitutionPersonId = principalInstitutionPersonId;
    }

    public Long getMandataryInstitutionPersonId() {
        return mandataryInstitutionPersonId;
    }

    public void setMandataryInstitutionPersonId(Long mandataryInstitutionPersonId) {
        this.mandataryInstitutionPersonId = mandataryInstitutionPersonId;
    }

    @Column(name="PRJ_PER_ID_MANDANT")
    private Long principalInstitutionPersonId;


    @Column(name="PRJ_INS_ID_MANDATAIRE")
    private Long mandataryInstitutionId;

    @Column(name="PRJ_PER_ID_MANDATAIRE")
    private Long mandataryInstitutionPersonId;


    @ManyToOne(cascade = CascadeType.ALL )
    @JoinColumn(name = "PRJ_INS_ID_MANDANT", updatable = false, insertable = false)
    private Institution principalInstitution;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PRJ_INS_ID_MANDATAIRE", updatable = false, insertable = false)
    private Institution mandataryInstitution;

    public Institution getPrincipalInstitution() {
        return principalInstitution;
    }

    public Institution getMandataryInstitution() {
        return mandataryInstitution;
    }

    public Long getProjectTypeId() {
        return projectTypeId;
    }

    public void setProjectTypeId(Long projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    public Long getProjectOriginId() {
        return projectOriginId;
    }

    public void setProjectOriginId(Long projectOriginId) {
        this.projectOriginId = projectOriginId;
    }

    public Long getProjectLimaId() {
        return projectLimaId;
    }

    public void setProjectLimaId(Long projectLimaId) {
        this.projectLimaId = projectLimaId;
    }

    public Date getEcheanceBlocage() {
        return echeanceBlocage;
    }

    public void setEcheanceBlocage(Date echeanceBlocage) {
        this.echeanceBlocage = echeanceBlocage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeCh() {
        return codeCh;
    }

    public void setCodeCh(String codeCh) {
        this.codeCh = codeCh;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrincipalInstitutionName() {
        return principalInstitutionName;
    }

    public void setPrincipalInstitutionName(String principalInstitutionName) {
        this.principalInstitutionName = principalInstitutionName;
    }

    public String getPrincipalInstitutionRespFirstName() {
        return principalInstitutionRespFirstName;
    }

    public void setPrincipalInstitutionRespFirstName(String principalInstitutionRespFirstName) {
        this.principalInstitutionRespFirstName = principalInstitutionRespFirstName;
    }

    public String getPrincipalInstitutionRespLastName() {
        return principalInstitutionRespLastName;
    }

    public void setPrincipalInstitutionRespLastName(String principalInstitutionRespLastName) {
        this.principalInstitutionRespLastName = principalInstitutionRespLastName;
    }

    public String getMandataryInstitutionName() {
        return mandataryInstitutionName;
    }

    public void setMandataryInstitutionName(String mandataryInstitutionName) {
        this.mandataryInstitutionName = mandataryInstitutionName;
    }

    public String getMandataryInstitutionRespFirstName() {
        return mandataryInstitutionRespFirstName;
    }

    public void setMandataryInstitutionRespFirstName(String mandataryInstitutionRespFirstName) {
        this.mandataryInstitutionRespFirstName = mandataryInstitutionRespFirstName;
    }

    public String getMandataryInstitutionRespLastName() {
        return mandataryInstitutionRespLastName;
    }

    public void setMandataryInstitutionRespLastName(String mandataryInstitutionRespLastName) {
        this.mandataryInstitutionRespLastName = mandataryInstitutionRespLastName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getDebutJour() {
        return debutJour;
    }

    public void setDebutJour(Long debutJour) {
        this.debutJour = debutJour;
    }

    public Long getDebutMois() {
        return debutMois;
    }

    public void setDebutMois(Long debutMois) {
        this.debutMois = debutMois;
    }

    public Long getDebutAnnee() {
        return debutAnnee;
    }

    public void setDebutAnnee(Long debutAnnee) {
        this.debutAnnee = debutAnnee;
    }

    public Long getFinJour() {
        return finJour;
    }

    public void setFinJour(Long finJour) {
        this.finJour = finJour;
    }

    public Long getFinMois() {
        return finMois;
    }

    public void setFinMois(Long finMois) {
        this.finMois = finMois;
    }

    public Long getFinAnnee() {
        return finAnnee;
    }

    public void setFinAnnee(Long finAnnee) {
        this.finAnnee = finAnnee;
    }

    public ThesaurusValue getProjectType() {
        return projectType;
    }

    public void setProjectType(ThesaurusValue projectType) {
        this.projectType = projectType;
    }

    public String getProjectTypeI18n() {
        return projectTypeI18n;
    }

    public void setProjectTypeI18n(String projectTypeI18n) {
        this.projectTypeI18n = projectTypeI18n;
    }

    public ThesaurusValue getProjectOrigin() {
        return projectOrigin;
    }

    public void setProjectOrigin(ThesaurusValue projectOrigin) {
        this.projectOrigin = projectOrigin;
    }

    public String getProjectOriginI18n() {
        return projectOriginI18n;
    }

    public void setProjectOriginI18n(String projectOriginI18n) {
        this.projectOriginI18n = projectOriginI18n;
    }

    public ThesaurusValue getProjectLima() {
        return projectLima;
    }

    public void setProjectLima(ThesaurusValue projectLima) {
        this.projectLima = projectLima;
    }

    public String getProjectLimaI18n() {
        return projectLimaI18n;
    }

    public void setProjectLimaI18n(String projectLimaI18n) {
        this.projectLimaI18n = projectLimaI18n;
    }

    public Boolean getVoluntaryWork() {
        return voluntaryWork;
    }

    public void setVoluntaryWork(Boolean voluntaryWork) {
        this.voluntaryWork = voluntaryWork;
    }

    public void setPrincipalInstitution(Institution principalInstitution) {
        this.principalInstitution = principalInstitution;
    }

    public void setMandataryInstitution(Institution mandataryInstitution) {
        this.mandataryInstitution = mandataryInstitution;
    }

    public Long getPrincipalInstitutionId() {
        return principalInstitutionId;
    }

    public void setPrincipalInstitutionId(Long principalInstitutionId) {
        this.principalInstitutionId = principalInstitutionId;
    }

    public Long getMandataryInstitutionId() {
        return mandataryInstitutionId;
    }

    public void setMandataryInstitutionId(Long mandataryInstitutionId) {
        this.mandataryInstitutionId = mandataryInstitutionId;
    }

    public Long getProjectProjectId() {
        return projectProjectId;
    }

    public void setProjectProjectId(Long projectProjectId) {
        this.projectProjectId = projectProjectId;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

}
