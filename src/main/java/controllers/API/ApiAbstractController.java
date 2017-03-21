package controllers.API;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import security.Error;

import javax.servlet.http.HttpServletResponse;


public abstract class ApiAbstractController {


    protected Error notFoundError(HttpServletResponse response){
        Error error = new Error();
        error.setCode(404);
        error.setMessage("Not Found: Resource not found");

        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return error;
    }


    protected Error unauthorized(HttpServletResponse response){
        Error error = new Error();
        error.setCode(401);
        error.setMessage("Unauthorized: You don't have access to this resource");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return error;
    }

    protected Error badrequest(HttpServletResponse response){
        Error error = new Error();
        error.setCode(400);
        error.setMessage("Bad Request: There is something wrong with the request");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return error;
    }

    protected Error internalservererror(HttpServletResponse response){
        Error error = new Error();
        error.setCode(500);
        error.setMessage("Internal Server Error: There is something wrong with our servers");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return error;
    }

    protected Error ok(HttpServletResponse response){
        Error error = new Error();
        error.setCode(200);
        error.setMessage("OK: The request has done sucerfully");
        response.setStatus(HttpServletResponse.SC_OK);
        return error;
    }
}
