package ch.cscf.jeci.domain.entities.midat.sample;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "V_MOMBREPROTOPARTYPEPARMOIS", schema = "MIDAT")
public class SampleStatPerProtocolMonth {

    @Id
    @Column(name="ID")
    private Long id;

    @Column(name="ANNEE", insertable = false, updatable = false)
    private Long year;

    @Column(name="MOIS" ,insertable = false, updatable = false)
    private Long month;

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

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Long getMonth() {
        return month;
    }

    public void setMonth(Long month) {
        this.month = month;
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
