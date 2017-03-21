package controllers.API;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import security.Error;

import javax.annotation.processing.Completion;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

@ControllerAdvice
public class ErrorController extends ApiAbstractController {
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<String> requestHandlingNoHandlerFound(HttpServletRequest req, NoHandlerFoundException ex) throws IOException {
        Error e = new Error();
        e.setCode(500);
        e.setMessage("Internal Server Error: There is something wrong with our servers");
        return new ResponseEntity<String>(toJson(e), HttpStatus.BAD_REQUEST);
    }

    String toJson(Error e) throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        return  ow.writeValueAsString(e);
    }
}
