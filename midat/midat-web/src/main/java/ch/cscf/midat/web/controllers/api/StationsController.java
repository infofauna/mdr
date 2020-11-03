package ch.cscf.midat.web.controllers.api;

import ch.cscf.jeci.domain.dto.midat.FilterDTO;
import ch.cscf.jeci.domain.dto.midat.StationDTO;
import ch.cscf.jeci.domain.entities.midat.sample.SampleStation;
import ch.cscf.jeci.web.controllers.api.AbstractRestController;
import ch.cscf.midat.services.interfaces.StationReadService;
import ch.cscf.midat.services.interfaces.StationUpdateService;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;
import org.geojson.FeatureCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author: henryp
 */
@Controller
@RequestMapping("/api/stations")
public class StationsController extends AbstractRestController {


    @Autowired
    private StationReadService stationReadService;

    @Autowired
    private StationUpdateService stationUpdateService;


    /*
      the below service is called on the map load, please note that the date filter is not taken into consideration
     */
    @RequestMapping(value = "/geodata", method = RequestMethod.GET)
    public @ResponseBody
    FeatureCollection getStationsAsGeoJSON(@RequestParam("editionMode") boolean editionMode,@RequestParam("filered") boolean filered ) {
        FeatureCollection authorizedStationsAsFeatures ;
        if(filered){
            authorizedStationsAsFeatures = stationReadService.getAuthorizedStationsBasedOnPresetFiltersAsFeatures(editionMode);
        }else{
            authorizedStationsAsFeatures = stationReadService.getAllAuthorizedStationsAsFeatures(editionMode);
        }
        return authorizedStationsAsFeatures;
    }


    /*
     the below service is called whenever we change the data range or we change the indice biotique
    */
    @RequestMapping(value = "/geodata/v2", method = RequestMethod.GET)
    public @ResponseBody
    FeatureCollection getStationsV2AsGeoJSON(@RequestParam("editionMode") boolean editionMode,
                                             @RequestParam("startDate") String startDate,
                                             @RequestParam("endDate") String endDate,
                                             @RequestParam("index") String index) {

        return stationReadService.getFilteredAuthorizedStationsAsFeatures(editionMode, index, startDate, endDate);
    }

    @RequestMapping(value = "/filters", method = RequestMethod.GET)
    public @ResponseBody
    List<FilterDTO> getFiltersJSON() {
        return stationReadService.getFilters();
    }


    @RequestMapping(value = "/linkunlink", method = RequestMethod.POST)
    @ResponseBody
    public StationDTO linkunlinkStation(@RequestParam("mStation") Long mStation, @RequestParam(value = "cStations") String cStations) {


        List<Long> cStationsList = null;

        if (cStations.trim().length() > 0) {
            cStationsList = Lists.newArrayList(Iterables.transform(Splitter.on(',').split(cStations), new Function<String, Long>() {
                public Long apply(final String in) {
                    return in == null ? null : Longs.tryParse(in);
                }
            }));
        }
        SampleStation sStation = stationUpdateService.linkunlinkStation(cStationsList, mStation);
        StationDTO stationDTO = new StationDTO();
        stationDTO.setId(sStation.getId());
        return stationDTO;
    }



}
