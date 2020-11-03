package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.entities.midat.BioticWaterQualityRating;
import ch.cscf.jeci.persistence.daos.interfaces.midat.BioticWaterQualityRatingDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

/**
 * @author: henryp
 */
@Repository
public class BioticWaterQualityRatingJPADAO extends GenericJpaEntityDAO<BioticWaterQualityRating> implements BioticWaterQualityRatingDAO {

}
