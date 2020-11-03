package ch.cscf.jeci.domain.entities.midat.sample;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "V_PROTOCOLTYPE", schema = "MIDAT")
public class SampleProtocolType {

    @Id
    @Column(name="SPH_ID")
    private Long sampleId;

    @Column(name="CVL_CODE")
    private String code;

    @Column(name="CDN_DESIGNATION")
    private String designation;


    @Column(name = "HAS_GRID")
    @Type(type = "yes_no")
    private boolean hasGrid;

    @Column(name = "HAS_GROUND")
    @Type(type = "yes_no")
    private boolean hasGround;



    public Long getSampleId() {
        return sampleId;
    }

    public String getCode() {
        return code;
    }

    public String getDesignation() {
        return designation;
    }

    public boolean isHasGrid() {
        return hasGrid;
    }

    public boolean isHasGround() {
        return hasGround;
    }
}

