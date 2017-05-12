
package controllers.API;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Actor;
import forms.EditProfileForm;
import org.apache.commons.codec.language.bm.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import domain.Language;
import domain.User;
import forms.SignupForm;
import services.ActorService;
import services.LanguageService;
import services.RatingService;
import services.UserService;

@Controller
@RequestMapping("/api")
public class UserController extends ApiAbstractController {

	@Autowired
	private ActorService	actorService;

	@Autowired
	private UserService		userService;

	@Autowired
	private RatingService	ratingService;

	@Autowired
	private LanguageService	languageService;


	@ResponseBody
	@RequestMapping(value = "/user/{user2}")
	public Object usersBestRanked(@PathVariable String user2, HttpServletRequest request, HttpServletResponse response) {
		try {
			user2 = new String(user2.getBytes(),"UTF-8");
			User user = userService.findByUserAccountUsername(user2);
			Assert.notNull(user2);
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("actor", user);
			result.put("followers", user.getFollowerUsers());
			result.put("following", user.getFollowingUsers());
			result.put("teams", user.getTeams());
			result.put("relatedUsers",userService.getRelatedUsers(user));
			return result;
		} catch (Exception e) {
			return notFoundError(response, null);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/user/{user}/follow")
	public Object followOrUnfollow(@PathVariable User user, HttpServletRequest request, HttpServletResponse response) {
		try {
			Assert.notNull(user);
		} catch (Exception e) {
			return notFoundError(response, null);
		}
		try {
			Assert.notNull(actorService.findActorByPrincipal());
		} catch (Exception e) {
			return unauthorized(response, null);
		}
		try {
			Assert.isTrue(user.getId() != actorService.findActorByPrincipal().getId());
		} catch (Exception e) {
			return badrequest(response, null);
		}
		try {
			userService.followOrUnfollowUser(user);
			return ok(response, null);
		} catch (Exception e) {
			return internalservererror(response, null);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public Object register(SignupForm signupForm, HttpServletRequest request, HttpServletResponse response, BindingResult binding) {
		try {
			Assert.notNull(signupForm);
		} catch (Exception e) {
			return notFoundError(response, null);
		}
		try {
			User user = userService.create();
			user.setAge(signupForm.getAge());

			Md5PasswordEncoder encoder = new Md5PasswordEncoder();

			user.getUserAccount().setPassword(encoder.encodePassword(signupForm.getPassword(), null));
			user.getUserAccount().setUsername(signupForm.getUsername());
			user.setPicture(signupForm.getPicture());
			user.setEmail(signupForm.getEmail());
			user.setHeader(signupForm.getHeader());
            List<Language> languages = new ArrayList<>();
            for(String e: signupForm.getLanguages().split(",")){
                languages.add(languageService.findOne(Integer.valueOf(e)));
            }
			user.setLanguages(languages);
			userService.save(user);
			return ok(response, null);
		} catch (Exception e) {

			return internalservererror(response, null);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/user/all")
	public Object allUsers(HttpServletRequest request, HttpServletResponse response) {
		try {
			Actor actor = actorService.findActorByPrincipal();
			Assert.notNull(actor);
			return userService.findAll();
		}catch (Exception e){
			return unauthorized(response,null);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/user/teams")
	public Object teams(HttpServletRequest request, HttpServletResponse response) {
		Actor user;
		try {
			user =  actorService.findActorByPrincipal();
			if(user instanceof User){
				return (User) ((User) user).getTeams();
			}
			return new ArrayList<>();
		}catch (Exception e){
			return internalservererror(response,null);
		}
	}


	@ResponseBody
	@RequestMapping(value = "/user/edit", method = RequestMethod.POST)
	public Object editProfile(EditProfileForm signupForm, HttpServletRequest request, HttpServletResponse response) {
	    User user;
		try {
			Assert.notNull(signupForm);
		} catch (Exception e) {
			return notFoundError(response, null);
		}
		try {
			userService.editForm(signupForm);

			return ok(response, null);
		} catch (Exception e) {

			return internalservererror(response, e.getMessage());
		}
	}
}
