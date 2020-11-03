package ch.cscf.jeci.persistence.daos.thesaurus.interfaces;

import ch.cscf.jeci.domain.entities.thesaurus.LocalizedThesaurusEntry;
import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusRealm;

import java.util.List;

/**
 * Interface for a service that allows read-only access to the thesaurus structure.
 * Since the thesaurus is a structure that is constantly read from but rarely updated,
 * the implementation should do some caching in sortOrder to reduce the number of SQL queries executed,
 * especially the hierarchical queries.
 * The interface provides a method for requesting a refresh the cached data.
 *
 * @author: henryp
 */
public interface ThesaurusReadOnlyService {

    void refreshCache();

    ThesaurusRealm getRealm(String realmCode);

    List<ThesaurusRealm> getRealms();

    List<LocalizedThesaurusEntry> getChildEntriesForRealmWithParent(String realmCode, Long parentId, String language);

    Long getValueId(String realmCode, String valueCode);

    String getRealmCodeForValue(Long valueId);

    LocalizedThesaurusEntry getLocalizedEntry(String realmCode, String valueCode, String language);

    String getLocalizedString(String realmCode, String valueCode, String language);

    List<LocalizedThesaurusEntry> getEntriesForRealm(String realmCode, String language);

}
