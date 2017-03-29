package controllers.API;

import domain.Actor;
import domain.User;
import forms.TestForm;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import services.GameService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/api")
public class MainPageController extends ApiAbstractController {

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @ResponseBody
    @RequestMapping(value = "/main")
    public Object main(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<User> users = userService.findBestRanked();
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("games", gameService.findAll());
            result.put("bestclassified", users);
            return result;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return notFoundError(response,null);
        }
    }


    @ResponseBody
    @RequestMapping(value = "/test")
    public Object test(TestForm  testForm, HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("testForm",testForm);

            return mapa;
        } catch (Exception e){

           return notFoundError(response,"No puedes ");
        }
    }

}
