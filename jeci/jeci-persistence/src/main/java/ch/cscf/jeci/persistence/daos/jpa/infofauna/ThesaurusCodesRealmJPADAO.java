package ch.cscf.jeci.persistence.daos.jpa.infofauna;

import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusCodesRealm;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.ThesaurusCodesRealmDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: henryp
 */
@Repository
public class ThesaurusCodesRealmJPADAO extends GenericJpaEntityDAO<ThesaurusCodesRealm> implements ThesaurusCodesRealmDAO {

    @Override
    public List<String> getAllRealmCodes() {
        return getEm().createQuery("select code from ThesaurusCodesRealm realm", String.class).getResultList();
    }

    public List<ThesaurusCodesRealm> loadAll(){


        return getEm().createQuery("select realm from ThesaurusCodesRealm  realm left join realm. ").getResultList();


    }

}
