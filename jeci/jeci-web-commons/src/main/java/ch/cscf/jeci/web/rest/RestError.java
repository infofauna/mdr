package ch.cscf.jeci.web.rest;

/**
 * @author: henryp
 */
public class RestError {

    private String message;

    private String type;

    private Throwable cause;


    public RestError(String message, String type, Throwable cause) {
        this.message = message;
        this.type = type;
        this.cause = cause;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }
}