package ch.cscf.midat.web.controllers.api;

import ch.cscf.jeci.web.controllers.api.AbstractRestController;
import ch.cscf.midat.services.interfaces.SampleIntervalReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: abdallah kanso
 */
@Controller
@RequestMapping("/api/samples/")
public class SampleIntervalController extends AbstractRestController {

    @Autowired
    private SampleIntervalReadService sampleIntervalReadService;

    @RequestMapping(value = "interval/{query}", method = RequestMethod.GET)
    public @ResponseBody
    List<String> findSamplesInterval(@PathVariable String query) {
        List<String> dates = sampleIntervalReadService.findSampleInterval(query);
        List<String> distinctDates =dates.stream().distinct().collect(Collectors.toList());
        return distinctDates;
    }
}
