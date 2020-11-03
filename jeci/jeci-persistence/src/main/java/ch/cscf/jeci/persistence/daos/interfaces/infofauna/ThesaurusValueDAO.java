package ch.cscf.jeci.persistence.daos.interfaces.infofauna;

import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusValue;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

/**
 * @author: henryp
 */
public interface ThesaurusValueDAO extends GenericEntityDAO<ThesaurusValue> {

    Long getValueId(String realmCode, String valueCode);

}
