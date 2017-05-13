package controllers.API;

import domain.Actor;
import domain.User;
import forms.PasswordRecoveryForm;
import org.springframework.web.bind.annotation.RequestMethod;
import security.UserAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import services.ActorService;
import services.UserService;
import services.login.MailService;

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

    @Autowired
    private MailService mailService;

    @ResponseBody
    @RequestMapping(value = "/auth/isauthenticated")
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
            System.out.println(e.getMessage());
            result.put("authenticated",false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/auth/passwordRecovery",method = RequestMethod.POST)
    public Object passwordRecovery(PasswordRecoveryForm passwordRecoveryForm,
                                   HttpServletRequest request, HttpServletResponse response) {
        User user;
    	try {
			Assert.notNull(passwordRecoveryForm);
		} catch (Exception e) {
			return badrequest(response, null);
		}
    	try {
            user = userService.findUserByEmail(passwordRecoveryForm.getEmail());
            Assert.notNull(user);
        }catch (Exception e){
    	    return notFoundError(response,null);
        }
        	try{
            String idioma = "en";
            for(javax.servlet.http.Cookie c: request.getCookies()){
                if(c.getName().equals("language")){
                    idioma = c.getValue();
                    break;
                }
            }
            String nuevaContrasena=  mailService.send(idioma, user.getEmail());
            UserAccount userAccount=user.getUserAccount();
            Md5PasswordEncoder encoder = new Md5PasswordEncoder();
            userAccount.setPassword(encoder.encodePassword(nuevaContrasena, null));
            user.setUserAccount(userAccount);
            userService.save(user);
         		
            return ok(response,null);
        }catch (Exception e){
        	return internalservererror(response,e.getMessage());
        }
   
    }
    
}
