package controllers.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.LanguageService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class LanguagesController extends ApiAbstractController {

    @Autowired
    LanguageService languageService;

    @RequestMapping(value = "/languages/all")
    public Object getAll(HttpServletResponse response){
        return languageService.findAll();
    }
}
