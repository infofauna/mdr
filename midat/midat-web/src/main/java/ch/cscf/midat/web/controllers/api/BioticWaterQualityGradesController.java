package ch.cscf.midat.web.controllers.api;

import ch.cscf.jeci.domain.dto.midat.BioticWaterQualityRatingLocalizedDTO;
import ch.cscf.jeci.web.controllers.AbstractController;
import ch.cscf.jeci.web.controllers.api.AbstractRestController;
import ch.cscf.midat.services.interfaces.BioticWaterQualityRatingReadService;
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
@RequestMapping("/api/biotic-water-quality/")
public class BioticWaterQualityGradesController extends AbstractRestController {

    @Autowired
    private BioticWaterQualityRatingReadService service;

    @RequestMapping(value = "/{indexType}", method = RequestMethod.GET)
    public @ResponseBody List<BioticWaterQualityRatingLocalizedDTO> getGradesForIndexType(@PathVariable String indexType){
        return service.getBiologicalRatingForIndexTypeLocalized(indexType);
    }

}
