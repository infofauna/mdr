package ch.cscf.jeci.persistence.daos.jpa.infofauna;

import ch.cscf.jeci.domain.entities.thesaurus.Language;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.LanguageDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: henryp
 */
@Repository
public class LanguageJpaDAO extends GenericJpaEntityDAO<Language> implements LanguageDAO {

    @Override
    public List<Language> findUiSelectableLanguages() {
        return getEm().createQuery("select lan from Language lan where lan.selectableInGui=true", Language.class).getResultList();
    }
}
