package ch.cscf.jeci.domain.entities.midat.sample;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "v_displaylog", schema = "MIDAT")
public class SampleImportDisplayLog {

    @Id
    @Column(name="IPO_ID")
    private Long id;

    @Column(name="SPH_ID")
    private Long sphId;

    @Column(name="LAN_ID", insertable = false, updatable = false)
    private Long lanId;

    @Column(name="IPO_IPH_ID", insertable = false, updatable = false)
    private Long ipoIphId;

    @Column(name="SEVERITY", insertable = false, updatable = false)
    private String severity ;

    @Column(name="MESSAGE", insertable = false, updatable = false)
    private String message ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSphId() {
        return sphId;
    }

    public void setSphId(Long sphId) {
        this.sphId = sphId;
    }

    public Long getLanId() {
        return lanId;
    }

    public void setLanId(Long lanId) {
        this.lanId = lanId;
    }

    public Long getIpoIphId() {
        return ipoIphId;
    }

    public void setIpoIphId(Long ipoIphId) {
        this.ipoIphId = ipoIphId;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

