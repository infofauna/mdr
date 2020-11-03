package ch.cscf.jeci.domain.entities.thesaurus;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

/**
 * @author: henryp
 */
@Entity
@DiscriminatorValue("2") //This is the value in the CRF_CRC_ID column, corresponding to the entry "APPL" in the CODEREFERENCECATEGORY table
@SequenceGenerator(name="idSeq", sequenceName="SEQ_CODE_REFERENCE")
public class ThesaurusCodesRealm extends AbstractCodesRealm {

}
