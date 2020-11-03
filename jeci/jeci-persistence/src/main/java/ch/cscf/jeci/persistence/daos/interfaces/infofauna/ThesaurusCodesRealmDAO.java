package ch.cscf.jeci.persistence.daos.interfaces.infofauna;

import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusCodesRealm;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;

/**
 * @author: henryp
 */
public interface ThesaurusCodesRealmDAO extends GenericEntityDAO<ThesaurusCodesRealm> {

    List<String> getAllRealmCodes();


}
