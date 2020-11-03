package ch.cscf.jeci.web.security;

/**
 * Created with IntelliJ IDEA.
 * User: henryp
 * Date: 21.01.13
 * Time: 09:03
 * To change this template use File | Settings | File Templates.
 */
public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
