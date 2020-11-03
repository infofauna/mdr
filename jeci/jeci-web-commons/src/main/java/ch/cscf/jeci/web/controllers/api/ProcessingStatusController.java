package ch.cscf.jeci.web.controllers.api;

import ch.cscf.jeci.services.async.interfaces.AsyncTaskExecutorService;
import ch.unine.sitel.o2m.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author: henryp
 */
@Controller
@RequestMapping("/api/task-status/")
public class ProcessingStatusController extends AbstractRestController {

    @Autowired
    AsyncTaskExecutorService executorService;

    @Autowired
    Mapper mapper;

    @RequestMapping(value = "/{task-key}", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getTaskStatus(@PathVariable("task-key") Long taskId){
        Map<String, Object> statusMap = executorService.getTaskStatusMap(taskId, "[externalId, currentStep, numOfRecords, secondsTotal, secondsLeft, percentageProcessed, done]");
        return statusMap;
    }


}
