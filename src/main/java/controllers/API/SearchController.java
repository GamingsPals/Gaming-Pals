package controllers.API;

import domain.Actor;
import domain.User;
import forms.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/api")
public class SearchController extends ApiAbstractController {


    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "/search")
    public Object search(SearchForm searchForm, HttpServletRequest request, HttpServletResponse response) {
        Collection<User> result = null;
        try{
            if (searchForm.getGame()!= null) {
                if (!searchForm.getGame().equals("")) {
                    result = userService.usersForGameTag(searchForm.getGame());
                    if (searchForm.getTier() != null) {
                        if (!searchForm.getTier().equals("")) {
                            result = userService.usersFromGameAndTier(searchForm.getGame(), searchForm.getTier());
                        }
                    }
                    return result;
                }
            }
            return userService.findAll();
        }catch (Exception e){
            return internalservererror(response,null);
        }
    }

}
