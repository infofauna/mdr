package ch.cscf.jeci.services.async.interfaces;

import ch.cscf.jeci.domain.entities.utility.TaskStatus;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author: henryp
 */
public interface AsyncTaskExecutorService {

    Long submitTask(CallableTask callable);

    TaskStatus getTaskStatus(Long taskId);

    Map<String,Object> getTaskStatusMap(Long taskId, String propertiesDescriptor);

    Object getTaskResult(Long taskId);

    public interface CallableTask extends Callable {

        Long getId();

        void setId(Long id);
    }

}
