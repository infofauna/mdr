package ch.cscf.jeci.persistence.daos.jpa.infofauna;

import ch.cscf.jeci.domain.entities.thesaurus.ThesaurusValue;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.ThesaurusValueDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

/**
 * @author: henryp
 */
@Repository
public class ThesaurusValueJPADAO extends GenericJpaEntityDAO<ThesaurusValue> implements ThesaurusValueDAO{

    @Override
    public Long getValueId(String realmCode, String valueCode) {
        return getEm().createQuery("select v.id from ThesaurusValue v where v.code=:valueCode and v.realm.code=:realmCode", Long.class)
                .setParameter("valueCode", valueCode)
                .setParameter("realmCode", realmCode)
                .getSingleResult();
    }
}
