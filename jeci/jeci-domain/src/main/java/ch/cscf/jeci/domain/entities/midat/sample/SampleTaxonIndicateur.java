package ch.cscf.jeci.domain.entities.midat.sample;

import javax.persistence.*;

@Entity()
@Table(name = "V_SAMPLETAXONINDICATEUR", schema = "MIDAT")


public class SampleTaxonIndicateur   {

    @Id
    @Column(name = "ROW_ID")
    private Long id;

    @Column(name = "SPH_ID", insertable = false, updatable = false)
    private Long sampleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPH_ID")
    private Sample sample;


    @Column(name = "SYD_DESIGNATION")
    private String taxonIndicateurName;


    public String getTaxonIndicateurName() {
        return taxonIndicateurName;
    }

    public void setTaxonIndicateurName(String taxonIndicateurName) {
        this.taxonIndicateurName = taxonIndicateurName;
    }

    public Long getId() {
        return id;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }


    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

}
