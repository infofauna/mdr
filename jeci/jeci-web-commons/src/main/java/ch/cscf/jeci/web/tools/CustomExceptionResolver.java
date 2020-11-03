package ch.cscf.jeci.web.tools;

import ch.cscf.jeci.web.security.UnauthorizedAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 *
 */
@Service
public class CustomExceptionResolver implements HandlerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        logger.info("Resolving exception !");

        ModelAndView result;

        if(ex instanceof MaxUploadSizeExceededException){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
            mav.addObject("message", ex.getMessage());
            mav.addObject("cause", ex);
            mav.addObject("type", ex.getClass().getSimpleName());

            return mav;
        }

        if(ex instanceof UnauthorizedAccessException){
            logger.warn("A user attempted to access a source he wasn't authorized to access :", ex);
            request.setAttribute("accessDenied", true);
            request.setAttribute("requestedPath", request.getRequestURL());
            result = new ModelAndView("login");
        }
        else if(request.getServletPath().contains("/authentication")){
            logger.error("Error in the authentication :", ex);
            result = new ModelAndView("authenticationError");
        }
        else{
            logger.error("Error in the application :", ex);
            result = new ModelAndView("defaultError");
        }

        if(ex instanceof UncategorizedSQLException){

        }

        result.addObject("exception", ex);
        result.addObject("exceptionTime", new Date());

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        result.addObject("stackTrace", sw.toString());

        return result;
    }
}
