package controllers.API;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import security.Error;

import javax.servlet.http.HttpServletResponse;


public abstract class ApiAbstractController {


    protected Error notFoundError(HttpServletResponse response, String message){
        Error error = new Error();
        error.setCode(404);
        if (message==null) message = "Not Found: Resource not found";
        error.setMessage(message);

        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return error;
    }


    protected Error unauthorized(HttpServletResponse response, String message){
        Error error = new Error();
        error.setCode(401);
        if (message==null) message = "Unauthorized: You don't have access to this resource";
        error.setMessage(message);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return error;
    }

    protected Error badrequest(HttpServletResponse response,String message){
        Error error = new Error();
        error.setCode(400);
        if (message==null) message = "Bad Request: There is something wrong with the request";
        error.setMessage(message);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return error;
    }

    protected Error internalservererror(HttpServletResponse response, String message){
        Error error = new Error();
        error.setCode(500);
        if (message==null) message = "Internal Server Error: There is something wrong with our servers";
        error.setMessage(message);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return error;
    }

    protected Error ok(HttpServletResponse response,String message){
        Error error = new Error();
        error.setCode(200);
        if (message == null) message = "OK: The request has done sucerfully";
        error.setMessage(message);
        response.setStatus(HttpServletResponse.SC_OK);
        return error;
    }
}
