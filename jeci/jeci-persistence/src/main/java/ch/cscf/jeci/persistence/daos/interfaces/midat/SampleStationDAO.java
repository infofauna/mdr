package ch.cscf.jeci.persistence.daos.interfaces.midat;

import ch.cscf.jeci.domain.dto.midat.SampleSearchParameters;
import ch.cscf.jeci.domain.entities.midat.sample.SampleStation;
import ch.cscf.jeci.domain.entities.midat.sample.SampleStationItem;
import ch.cscf.jeci.domain.entities.midat.sample.SampleStationItemView;
import ch.cscf.jeci.domain.entities.midat.sample.StationSamplesView;
import ch.cscf.jeci.persistence.daos.interfaces.GenericEntityDAO;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author: henryp
 */
public interface SampleStationDAO extends GenericEntityDAO<SampleStation> {

    List<StationSamplesView> findPublishedStations(Long userId, boolean isAppManager, boolean filterForEdition);

    List<StationSamplesView> findPublishedStationsBelongingToGroups(Long userId, Collection<String> groupNames, boolean filterForEdition);

    List<SampleStationItemView> findSampleStationItems();
    Set<SampleStation> findAllStations();

    String getWatercourseForStation(Long stationId, Long languageId);

    String getLocalityForStation(Long stationId, Long languageId);

    SampleStationItem getItemForStationAndType(Long stationId, Long languageId, String itemTypeCode);

    List<Long> findStationsIdsInsideRectangle(Double west, Double east, Double north, Double south, Long userId, boolean isAppManager, boolean editionMode);

    List<Long> findStationsIdsInsideRectangleAndBelongingToGroups(Double west, Double east, Double north, Double south,
                                                                  Collection<String> groupNames, Long userId, boolean editionMode);

    List<SampleStation> findStationsInsideCercle(SampleStation s, Double raduis);

    Integer unlinkStationsFromParentStationById(Long sId);
    Integer linkStationsToParentStationById(List<Long> sIds,Long pSationId);

    List<String> findStationNumbersLike(String query, Long userId);
    List<String> findStationNumbersLikeInGroups(String query, Long userId, Set<Long> groupIds);


    List<String> findGewissNumbersLike(String query, Long userId);
    List<String> findGewissNumbersLikeInGroups(String query, Long userId, Set<Long> groupIds);


    List<StationSamplesView> findFilteredPublishedStations(Long userId, SampleSearchParameters parameters, boolean hasFilterExceptDate, boolean isAppManager, boolean filterForEdition);
    List<StationSamplesView> findFilteredPublishedStationsBelongingToGroups(Long userId, Collection<String> groupNames, SampleSearchParameters parameters, boolean hasFilterExceptDate, boolean filterForEdition);


    Set<SampleStation> findStationsByIds(List<Long> sIds,Long userId,Collection<String> groupNames, boolean filterForEdition);

    Set<StationSamplesView> findStationSamplesViewByIds(List<Long> sIds,Long userId,Collection<String> groupNames, boolean filterForEdition);
}
