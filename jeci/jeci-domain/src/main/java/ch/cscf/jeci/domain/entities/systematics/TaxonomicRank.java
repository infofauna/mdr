package ch.cscf.jeci.domain.entities.systematics;

import ch.cscf.jeci.domain.entities.thesaurus.AbstractCodesRealm;

import javax.persistence.*;

/**
 * @author: henryp
 */

@Entity
@DiscriminatorValue("1") //This is the value in the CRF_CRC_ID column, corresponding to the entry "SYST" in the CODEREFERENCECATEGORY table
@SequenceGenerator(name="idSeq", sequenceName="SEQ_SAMPLEPROTOCOLGRND")
public class TaxonomicRank extends AbstractCodesRealm {

    @ManyToOne(fetch = FetchType.LAZY)    @JoinColumn(name="CRF_CRF_ID", updatable = false, insertable = false)
    private TaxonomicRank parentRank;

    public TaxonomicRank getParentRank() {
        return parentRank;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
