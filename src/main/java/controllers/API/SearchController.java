package controllers.API;

import domain.Actor;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class SearchController extends ApiAbstractController {


    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "/search")
    public Object search(User u,HttpServletRequest request, HttpServletResponse response) {
        try{
            return userService.findAll();
        }catch (Exception e){
            return internalservererror(response);
        }
    }

}
