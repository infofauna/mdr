package ch.cscf.jeci.web.controllers.api;

import ch.cscf.jeci.exceptions.NotFoundException;
import ch.cscf.jeci.web.rest.RestError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: henryp
 */
public class AbstractRestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody RestError handleNotFoundException(NotFoundException e) {

        logger.error("Handling Not Found Exception.", e);
        return buildRestError(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public @ResponseBody RestError handleIllegalArgumentException(IllegalArgumentException e) {

        logger.error("Handling IllegalArgumentException.", e);
        return buildRestError(e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public @ResponseBody RestError handleExceptionDefault(Throwable t){

        logger.error("Handling generic exception...", t);
        return buildRestError(t);
    }

    private RestError buildRestError(Throwable t) {
        return new RestError(t.getMessage(), t.getClass().getSimpleName(), t);
    }

    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        //The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        //Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }


}
