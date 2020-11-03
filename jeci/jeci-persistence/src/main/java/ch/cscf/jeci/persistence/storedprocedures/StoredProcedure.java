package ch.cscf.jeci.persistence.storedprocedures;

import java.util.Map;

/**
 * @author: henryp
 */
public abstract class StoredProcedure extends org.springframework.jdbc.object.StoredProcedure {

    protected static final String OUT_STATUS = "outStatus";

    public void handleStatus(Map<String,Object> result) throws StoredProcedureExecutionException{

        if(((Long)result.get(OUT_STATUS))==0l){
            return;
        }

        Long errorCode = (Long)result.get(OUT_STATUS);
        throw new StoredProcedureExecutionException(errorCode);
    }

}
