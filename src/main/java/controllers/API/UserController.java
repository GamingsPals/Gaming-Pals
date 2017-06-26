
package controllers.API;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import domain.Actor;
import domain.Language;
import domain.User;
import forms.EditProfileForm;
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
	public Object usersBestRanked(@PathVariable String user2, final HttpServletRequest request, final HttpServletResponse response) {
		try {
			user2 = new String(user2.getBytes(), "UTF-8");
			final User user = this.userService.findByUserAccountUsername(user2);
			Assert.notNull(user2);
			final Map<String, Object> result = new LinkedHashMap<>();
			result.put("actor", user);
			result.put("followers", user.getFollowerUsers());
			result.put("following", user.getFollowingUsers());
			result.put("teams", user.getTeams());
			result.put("relatedUsers", this.userService.getRelatedUsers(user));
			return result;
		} catch (final Exception e) {
			return this.notFoundError(response, null);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/user/{user}/follow")
	public Object followOrUnfollow(@PathVariable final User user, final HttpServletRequest request, final HttpServletResponse response) {
		try {
			Assert.notNull(user);
		} catch (final Exception e) {
			return this.notFoundError(response, null);
		}
		try {
			Assert.notNull(this.actorService.findActorByPrincipal());
		} catch (final Exception e) {
			return this.unauthorized(response, null);
		}
		try {
			Assert.isTrue(user.getId() != this.actorService.findActorByPrincipal().getId());
		} catch (final Exception e) {
			return this.badrequest(response, null);
		}
		try {
			this.userService.followOrUnfollowUser(user);
			return this.ok(response, null);
		} catch (final Exception e) {
			return this.internalservererror(response, null);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public Object register(final SignupForm signupForm, final HttpServletRequest request, final HttpServletResponse response, final BindingResult binding) {
		try {
			Assert.notNull(signupForm);
		} catch (final Exception e) {
			return notFoundError(response, null);
		}
		try{
			Actor actor = actorService.findActorByUsername(signupForm.getUsername());
			Actor actor1 = actorService.findByEmail(signupForm.getEmail());
			Assert.isNull(actor);
			Assert.isNull(actor1);
		} catch (Exception e){
			return badrequest(response);
		}
		try {
			final User user = this.userService.create();
			user.setAge(signupForm.getAge());

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();

			user.getUserAccount().setPassword(encoder.encodePassword(signupForm.getPassword(), null));
			user.getUserAccount().setUsername(signupForm.getUsername());
			user.setPicture(signupForm.getPicture());
			user.setEmail(signupForm.getEmail());
			user.setHeader(signupForm.getHeader());
			final List<Language> languages = new ArrayList<>();
			for (final String e : signupForm.getLanguages().split(","))
				languages.add(this.languageService.findOne(Integer.valueOf(e)));
			user.setLanguages(languages);
			this.userService.save(user);
			return this.ok(response, null);
		} catch (final Exception e) {

			return this.internalservererror(response, null);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/user/all")
	public Object allUsers(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			final Actor actor = this.actorService.findActorByPrincipal();
			Assert.notNull(actor);
			return this.userService.findAll();
		} catch (final Exception e) {
			return this.unauthorized(response, null);
		}
	}



	@ResponseBody
	@RequestMapping(value = "/user/edit", method = RequestMethod.POST)
	public Object editProfile(final EditProfileForm signupForm, final HttpServletRequest request, final HttpServletResponse response) {
		final User user;
		try{
			user = userService.findByPrincipal();
			Assert.notNull(user);
		} catch (Exception e){
			return unauthorized(response,null);
		}
		try {
			Assert.notNull(signupForm);
		} catch (final Exception e) {
			return this.notFoundError(response, null);
		}
		try {
			this.userService.editForm(signupForm);

			return this.ok(response, null);
		} catch (final Exception e) {

			return this.internalservererror(response, e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/user/updatepaypal")
	public Object updatepaypal(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			final Actor actor = this.actorService.findActorByPrincipal();
			Assert.notNull(actor);
			System.out.println("Hola");
			this.userService.postpaypal();
			return this.ok(response, null);
		} catch (final Exception e) {
			return this.unauthorized(response, null);
		}
	}
}
