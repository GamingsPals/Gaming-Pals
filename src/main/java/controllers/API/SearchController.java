package controllers.API;

import domain.Actor;
import domain.User;
import forms.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/api")
public class SearchController extends ApiAbstractController {


    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Object search(SearchForm searchForm, HttpServletRequest request, HttpServletResponse response) {
        Collection<User> result;
        try{
            return userService.search(searchForm);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return internalservererror(response,e.getMessage());
        }
    }

}
