package ch.cscf.midat.web.controllers.api;

import ch.cscf.jeci.web.controllers.api.AbstractRestController;
import ch.cscf.midat.services.interfaces.StationReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author: henryp
 */
@Controller
@RequestMapping("/api/stations/")
public class StationItemsSearchController extends AbstractRestController {

    @Autowired
    private StationReadService stationReadService;

    @RequestMapping(value = "watercourse-names-like/{query}", method = RequestMethod.GET)
    public @ResponseBody List<String> findWatercourseNamesLike(@PathVariable String query){
        return stationReadService.findWatercourseNamesLike(query);
    }

    @RequestMapping(value = "locality-names-like/{query}", method = RequestMethod.GET)
    public @ResponseBody List<String> findLocalityNamesLike(@PathVariable String query){
        return stationReadService.findLocalityNamesLike(query);
    }


    //Midat v.2 station-numbers-like
    @RequestMapping(value = "station-numbers-like/{query}", method = RequestMethod.GET)
    public @ResponseBody List<String> findStationNumbersLike(@PathVariable String query){
        return stationReadService.findStationNumbersLike(query);
    }

    //Midat V.2 gewiss-numbers-like
    @RequestMapping(value = "gewiss-numbers-like/{query}", method = RequestMethod.GET)
    public @ResponseBody List<String> findGewissNumbersLike(@PathVariable String query){
        return stationReadService.findGewissNumbersLike(query);
    }


}
