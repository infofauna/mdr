package ch.cscf.midat.web.controllers.api;

import ch.cscf.jeci.domain.dto.jeci.AuditingInfo;
import ch.cscf.jeci.domain.dto.midat.SampleDetailDTO;
import ch.cscf.jeci.domain.entities.midat.ListConnection;
import ch.cscf.jeci.domain.entities.midat.ProtocolIndex;
import ch.cscf.jeci.domain.entities.midat.sample.*;
import ch.cscf.jeci.web.controllers.api.AbstractRestController;
import ch.cscf.midat.services.interfaces.SampleReadUpdateDeleteService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author: henryp
 */
@Controller
@RequestMapping("/api/samples")
public class SampleReadUpdateDeleteController extends AbstractRestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SampleReadUpdateDeleteService service;

    @RequestMapping(value = "{sampleId:[0-9]+}", method = RequestMethod.GET)
    public @ResponseBody SampleDetailDTO getSampleById(@PathVariable("sampleId") Long id){
        return service.getSampleDetails(id);
    }

    @RequestMapping(value = "/importlog/{sampleId:[0-9]+}", method = RequestMethod.GET)
    public @ResponseBody
    List<SampleImportDisplayLog> loadSampleImportLog(@PathVariable("sampleId") Long id){
        return service.loadSampleImportLog(id);
    }


    @RequestMapping(value = "{sampleIds}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void batchDeleteSamples(@PathVariable Long[] sampleIds){
        logger.info("Received request for batch samples delete");
        service.deleteSamples(Lists.newArrayList(sampleIds));
    }

    @RequestMapping(value = "{sampleIds}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void batchUpdateSamples(@PathVariable Long[] sampleIds, @RequestParam(required = true) String fieldName, @RequestParam(required = true) Boolean value){

        logger.info("Received request for batch samples update");

        if(fieldName.equals("published")){
            service.changeSampledPublishedStatus(Arrays.asList(sampleIds), value);
            return;
        }

        if(fieldName.equals("public")){
            boolean boolValue = Boolean.valueOf(value);
            service.makeSamplesPublic(Arrays.asList(sampleIds), boolValue);
            return;
        }
        throw new IllegalStateException("Only fields 'published' and 'public' are supported for batch update.");

    }

    @RequestMapping(value = "{sampleId:[0-9]+}/auditing-info", method = RequestMethod.GET)
    public @ResponseBody AuditingInfo getAuditingInfo(@PathVariable("sampleId") Long sampleId){
        return service.getAuditInfoForSample(sampleId);
    }

    @RequestMapping(value = "/sampleProtocolPerMonth", method = RequestMethod.GET)
    public @ResponseBody List<SampleStatPerProtocolMonth> getSampleProtocolPerMonth(){
        return service.getSampleProtocolPerMonth();
    }

    @RequestMapping(value = "/sampleProtocolPerUserCanton", method = RequestMethod.GET)
    public @ResponseBody List<SampleProtocolPerUserCanton> getSampleProtocolPerUserCanton(){
        return service.getSampleProtocolPerUserCanton();
    }

    @RequestMapping(value = "/usersConnectionHistory", method = RequestMethod.GET)
    public @ResponseBody List<ListConnection> getUsersConnectionHistory(){
        return service.getUsersConnectionHistory();
    }

    @RequestMapping(value = "/sampleIndexHistory", method = RequestMethod.GET)
    public @ResponseBody List<ProtocolIndex> getSampleIndexHistory(){
        return service.getSampleIndexHistory();
    }

    @RequestMapping(value = "/sampleInfoIbchData/{sampleId:[0-9]+}", method = RequestMethod.GET)
    public @ResponseBody
    SampleInfoIbchData loadSampleInfoIbchData(@PathVariable("sampleId") Long id){
        return service.loadSampleInfoIbchData(id);
    }


    /**
     * midat plus -sampleHistoryIndexInfoData
     */
    @RequestMapping(value = "/sampleIndiceHistoryData/{sampleId:[0-9]+}", method = RequestMethod.GET)
    public @ResponseBody
    SampleIndiceHistory loadSampleIndiceHistoryData(@PathVariable("sampleId") Long id){
        return service.loadSampleIndiceHistoryData(id);
    }

}
