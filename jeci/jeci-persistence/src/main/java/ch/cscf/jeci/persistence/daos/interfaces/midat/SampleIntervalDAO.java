package ch.cscf.jeci.persistence.daos.interfaces.midat;

import ch.cscf.jeci.domain.entities.midat.sample.Sample;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;

/**
 * @author: abdallah kanso
 */
public interface SampleIntervalDAO extends GenericEntityDAO<Sample> {
    List<String> findSampleInterval(Long userId);
}
