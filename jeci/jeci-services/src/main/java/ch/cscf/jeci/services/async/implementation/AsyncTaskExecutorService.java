package ch.cscf.jeci.services.async.implementation;

import ch.cscf.jeci.domain.entities.utility.TaskStatus;
import ch.cscf.jeci.exceptions.NotFoundException;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.TaskStatusDAO;
import ch.cscf.jeci.persistence.daos.interfaces.midat.ProtocolImportDAO;
import ch.unine.sitel.o2m.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author: henryp
 */
@Service
public class AsyncTaskExecutorService implements ch.cscf.jeci.services.async.interfaces.AsyncTaskExecutorService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Mapper mapper;

    @Autowired
    private TaskStatusDAO taskStatusDAO;

    @Autowired
    private ProtocolImportDAO protocolImportDAO;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    private Map<Long, Future<?>> futures = new HashMap();

    @Override
    public Long submitTask(CallableTask callable) {
        logger.info("Adding asynchronous task.");

        logger.info("Initializing the status row in the DB.");
        Long taskStatusId =protocolImportDAO.initTaskStatus();
        logger.info("Status row successfully initialized in the DB. Recieved ID : {}", taskStatusId);

        logger.info("Now adding the task to executor.");
        callable.setId(taskStatusId);
        Future future = executor.submit(callable);
        futures.put(taskStatusId, future);
        logger.info("Task added and future stored.");
        return taskStatusId;
    }

    @Override
    @Transactional(readOnly = true)
    public TaskStatus getTaskStatus(Long id) {
        logger.info("Getting task status for task id \"{}\"", id);

        Future future = futures.get(id);
        if(future == null){
            logger.error("The id {} does not match any task status in the futures store.", id);
            throw new NotFoundException("future result of asynchronous task status", id.toString());
        }

        TaskStatus status = taskStatusDAO.findById(id);
        if(status == null) {
            logger.error("The id {} does not match any task status in the DB.", id);
            throw new NotFoundException("task status in the database", id.toString());
        }

        status.setDone(future.isDone());
        logger.info("Found task status : "+status.toString());
        return status;
    }

    @Transactional(readOnly = true)
    @Override
    public Map<String,Object> getTaskStatusMap(Long taskId, String propertiesDescriptor) {
        TaskStatus status = getTaskStatus(taskId);
        return mapper.mapWithJsonPropertyObjects(status, propertiesDescriptor);
    }

    @Override
    public Object getTaskResult(Long taskId) {
        logger.info("Retrieving the result for task id {}", taskId);

        Future future = futures.get(taskId);
        if(future == null){
            String msg = "The id " + taskId + " does not match any running task.";
            logger.error(msg);
            throw new NotFoundException(msg, taskId.toString());
        }
        if(!future.isDone()){
            String msg = "The task registered under the key " + taskId + " is not done yet. Please check with the isDone() method or retry later.";
            logger.error(msg);
            throw new IllegalStateException(msg);
        }

        boolean ok = true;
        try {
            return future.get();
        }catch(ExecutionException e){
            ok=false;
            logger.error("Execution error of the async mass import task. Attempting to unwrap and throw the cause.", e);
            Throwable cause = e.getCause();
            if(cause != null && cause instanceof RuntimeException){
                throw (RuntimeException) cause;
            }
            String message = MessageFormat.format("Error while executing the asynchronous mass import validation task with key {}.", taskId);
            throw new IllegalStateException(message, cause);
        }catch(Exception e){
            ok=false;
            logger.error("Error while trying to get the result of future with task key "+taskId, e);
            if(e instanceof RuntimeException){
                throw (RuntimeException)e;
            }
            throw new RuntimeException(e);
        }finally{
            if(ok) {
                logger.info("Successfully retrieved the result of task with key {}", taskId);
            }
        }
    }

}
