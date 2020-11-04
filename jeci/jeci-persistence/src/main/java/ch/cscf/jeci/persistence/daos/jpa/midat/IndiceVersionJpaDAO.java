package ch.cscf.jeci.persistence.daos.jpa.midat;

import ch.cscf.jeci.domain.entities.midat.LabProtocolImport;
import ch.cscf.jeci.domain.entities.midat.sample.SampleIndiceVersion;
import ch.cscf.jeci.persistence.daos.interfaces.midat.IndiceVersionDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import org.springframework.stereotype.Repository;


@Repository
public class IndiceVersionJpaDAO extends GenericJpaEntityDAO<SampleIndiceVersion> implements IndiceVersionDAO {

}



