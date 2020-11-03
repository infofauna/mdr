package ch.cscf.jeci.persistence.daos.interfaces.infofauna;

import ch.cscf.jeci.domain.entities.thesaurus.LocalizedThesaurusEntry;

import java.util.List;

/**
 * @author: henryp
 */
public interface LocalizedThesaurusEntryDAO {

    List<LocalizedThesaurusEntry> findEntriesForRealm(String realmCode);

    String getRealmCodeForValueId(Long valueId);
}
