package controllers.API;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import domain.Team;
import forms.TeamForm;
import services.TeamService;
import services.UserService;

@Controller
@RequestMapping("/team/user")
public class TeamUserController extends ApiAbstractController{
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Object create(TeamForm teamForm, HttpServletRequest request, HttpServletResponse response, BindingResult binding) {
		try {
			System.out.println("null");
			Assert.notNull(teamForm);
		} catch (Exception e) {
			System.out.println("error");
			return notFoundError(response, null);
		}
		try {
			
			Team team=teamService.create();
			team.getUsers().add(userService.findByPrincipal());
			team.setName(teamForm.getName());
			team.setPicture(teamForm.getPicture());
			System.out.println("1");
			teamService.save(team);
			System.out.println("2");
			return ok(response, null);
		} catch (Exception e) {

			System.out.println(binding.getAllErrors());

			return internalservererror(response, null);
		}
	}
}

