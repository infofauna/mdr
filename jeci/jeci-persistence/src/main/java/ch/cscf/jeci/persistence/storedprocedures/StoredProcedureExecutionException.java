package ch.cscf.jeci.persistence.storedprocedures;

/**
 * @author: henryp
 */
public class StoredProcedureExecutionException extends Exception {

    public Long errorCode;

    public StoredProcedureExecutionException(Long errorCode){
        super("The STATUS returned by the stored procedure indicates an error or problem occurred.");
        this.errorCode=errorCode;
    }

    public Long getErrorCode() {
        return errorCode;
    }
}
