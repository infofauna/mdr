package ch.cscf.jeci.persistence.daos.jpa.infofauna;

import ch.cscf.jeci.domain.entities.utility.TaskStatus;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.TaskStatusDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: henryp
 */
@Repository
public class ProcessingStatusJpaDAO extends GenericJpaEntityDAO<TaskStatus> implements TaskStatusDAO {

}
