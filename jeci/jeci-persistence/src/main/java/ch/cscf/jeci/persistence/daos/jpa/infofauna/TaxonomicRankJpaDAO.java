package ch.cscf.jeci.persistence.daos.jpa.infofauna;

import ch.cscf.jeci.domain.entities.systematics.TaxonomicRank;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.TaxonomicRankDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

/**
 * @author: henryp
 */
@Repository
public class TaxonomicRankJpaDAO extends GenericJpaEntityDAO<TaxonomicRank> implements TaxonomicRankDAO {


}
