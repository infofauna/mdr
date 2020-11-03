package ch.cscf.jeci.persistence.daos.interfaces.infofauna;

import ch.cscf.jeci.domain.entities.thesaurus.Language;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;

/**
 * @author: henryp
 */
public interface LanguageDAO extends GenericEntityDAO<Language> {


    List<Language> findUiSelectableLanguages();

}
