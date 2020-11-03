package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.entities.midat.sample.SampleComment;
import ch.cscf.jeci.persistence.daos.interfaces.midat.SampleCommentDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

/**
 * @author: abdallah kanso
 */

@Repository
public class SampleCommentJPADAO extends GenericJpaEntityDAO<SampleComment> implements SampleCommentDAO {


}
