package ch.cscf.jeci.persistence.daos.interfaces.midat;

import ch.cscf.jeci.domain.entities.midat.sample.SampleStationItem;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.List;
import java.util.Set;

/**
 * @author: henryp
 */
public interface SampleStationItemDAO extends GenericEntityDAO<SampleStationItem> {


    List<String> findWatercourseNamesLike(String query, Long userId);
    List<String> findWatercourseNamesLikeInGroups(String query, Long userId, Set<Long> groupIds);

    List<String> findLocalityNamesLike(String query, Long userId);
    List<String> findLocalityNamesLikeInGroups(String query, Long userId, Set<Long> groupIds);





}
