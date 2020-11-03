package ch.cscf.midat.services.interfaces;

import ch.cscf.jeci.domain.dto.midat.FilterDTO;
import org.geojson.FeatureCollection;

import java.util.List;

/**
 * @author: henryp
 */
public interface StationReadService {

    FeatureCollection getAllAuthorizedStationsAsFeatures(boolean filterForEdition);

    List<String> findWatercourseNamesLike(String query);

    List<String> findLocalityNamesLike(String query);


    List<String> findStationNumbersLike(String query);

    List<String> findGewissNumbersLike(String query);


    FeatureCollection getAuthorizedStationsBasedOnPresetFiltersAsFeatures(boolean filterForEdition);
    FeatureCollection getFilteredAuthorizedStationsAsFeatures(boolean filterForEdition, String index, String startDate, String endDate);
    List<FilterDTO> getFilters();
}
