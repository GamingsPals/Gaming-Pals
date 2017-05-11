package controllers.API;

import domain.Actor;
import domain.Language;
import domain.User;
import forms.PasswordRecoveryForm;
import forms.SignupForm;
import security.Error;
import security.UserAccount;

import org.apache.http.cookie.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import services.ActorService;
import services.UserService;
import services.login.Gmail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/api")
public class AuthController extends ApiAbstractController {

    @Autowired
    private ActorService actorService;
    
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/isauthenticated")
    public Object auth(HttpServletRequest request, HttpServletResponse response) {
     Map<String, Object> result = new HashMap<>();
        try{
            Actor actor = actorService.findActorByPrincipal();
            result.put("roles",actor.getUserAccount().getAuthorities());
            result.put("actor",actor);
            if (actor instanceof User) {
                User user = (User) actor;
                result.put("followers", user.getFollowerUsers());
                result.put("following", user.getFollowingUsers());
            }
            result.put("authenticated",true);
        }catch (Exception e){
            result.put("authenticated",false);
        }
        return result;
    }
 
    
    @RequestMapping(value = "/passwordRecovery")
    public Object passwordRecovery(PasswordRecoveryForm passwordRecoveryForm, HttpServletRequest request, HttpServletResponse response) {
    	try {
			Assert.notNull(passwordRecoveryForm);
		} catch (Exception e) {
			return notFoundError(response, null);
		}
    	
    	try{
        	Map<Boolean,User> map=Gmail.existeUsuario(passwordRecoveryForm.getUsername(), passwordRecoveryForm.getEmail());
        	if(map.containsKey(true)){
        		User user=map.get(true);
        		 String idioma = "en";
                 for(javax.servlet.http.Cookie c: request.getCookies()){
                 	if(c.getName().equals("language")){
                 		idioma = c.getValue();
                 		break;
                 	}
                 }
                String nuevaContraseña=Gmail.send(idioma, user.getEmail());
                UserAccount userAccount=user.getUserAccount();
         		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
         		userAccount.setPassword(encoder.encodePassword(nuevaContraseña, null));
         		user.setUserAccount(userAccount);
         		userService.save(user);
         		
                 return ok(response,null);
        	}
        	else{
        		
        	}
        	          
        }catch (Exception e){
        	return internalservererror(response,null);
        }
		return response;
   
    }
    
}
