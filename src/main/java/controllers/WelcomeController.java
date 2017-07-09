/* WelcomeController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import security.UserAccount;
import services.ActorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class WelcomeController {

	// Constructors -----------------------------------------------------------

	@Autowired
    private ActorService actorService;

	public WelcomeController() {
		super();
	}
		
	// Index ------------------------------------------------------------------		

	@RequestMapping("/**")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelAndView result;
		result = new ModelAndView("master.page");

        SecurityContextImpl sci = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        if (sci != null) {
            UserAccount cud = actorService.findActorByPrincipal().getUserAccount();
            if(cud.getBanned()){
                logout(request,response);
                return new ModelAndView("redirect:/banned");
            }
        }

		return result;
	}

	@RequestMapping("/logout")
	public ModelAndView logoutGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		logout(httpServletRequest,httpServletResponse);
		return new ModelAndView("redirect:/");
	}

	private void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, auth);
        }
        SecurityContextHolder.getContext().setAuthentication(null);

    }
}