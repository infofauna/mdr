package ch.cscf.jeci.exceptions;

import java.text.MessageFormat;

/**
 * @author: henryp
 */
public class NotFoundException extends RuntimeException{

    private String entityRequested;
    private String identifierRequested;
    private static MessageFormat formatter = new MessageFormat("The {0} with the identifier \"{1}\" cannot be found.");

    public NotFoundException(String entityRequested, String identifierRequested) {
        this.entityRequested = entityRequested;
        this.identifierRequested = identifierRequested;
    }

    @Override
    public String getMessage(){
        Object[] arguments = {entityRequested, identifierRequested};
        return formatter.format(arguments);
    }

}
