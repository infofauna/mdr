package ch.cscf.jeci.domain.entities.midat.sample;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "V_NOMBREPROTPARUTILIPARCANTON", schema = "MIDAT")
public class SampleProtocolPerUserCanton {

    @Id
    @Column(name="ID")
    private Long id;

    @Column(name="LOGIN", insertable = false, updatable = false)
    private String login;

    @Column(name="CANTON", insertable = false, updatable = false)
    private String canton;

    @Column(name="NOMBRELABORATORY", insertable = false, updatable = false)
    private Long numberLab;

    @Column(name="NOMBREMASS" ,insertable = false, updatable = false)
    private Long numberMass;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public Long getNumberLab() {
        return numberLab;
    }

    public void setNumberLab(Long numberLab) {
        this.numberLab = numberLab;
    }

    public Long getNumberMass() {
        return numberMass;
    }

    public void setNumberMass(Long numberMass) {
        this.numberMass = numberMass;
    }
}
