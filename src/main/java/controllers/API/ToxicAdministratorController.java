package controllers.API;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ToxicService;

import controllers.AbstractController;
import domain.Toxic;

@Controller
@RequestMapping("/toxic/administrator")
public class ToxicAdministratorController extends AbstractController{
	
	// Services ---------------------------------------------------------------
	@Autowired
	private ToxicService toxicService;
	
	// Constructors -----------------------------------------------------------
	public ToxicAdministratorController(){
		super();
	}

		
	// Listing ----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result=new ModelAndView("toxic/list");
		Collection<Toxic> toxics=toxicService.findAll();
		
		result.addObject("requestURI","toxic/list.do");
		result.addObject("toxics",toxics);
		
		return result;
	}
	
	// Add ------------------------------------------------------------
	
	@RequestMapping(value = "/addToxic", method = RequestMethod.GET)
	public ModelAndView addToxic(@RequestParam String keyword){
		ModelAndView result;
		Collection<Toxic> toxics=toxicService.findAll();
		Toxic toxic=toxicService.create();
		for(Toxic s:toxics){
			toxic=s;
		}
		toxic.getKeywords().add(keyword);
		toxicService.save(toxic);
		result = list();
		result.addObject("toxic", toxic);
		return result;
	}
	
	// Edition ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView add(@RequestParam int toxicId, String keyword){
		
		ModelAndView result = null;
		Toxic toxic;
		try{
			toxic=toxicService.findOne(toxicId);
			if(keyword!=null){
				Collection<String> keywords=toxic.getKeywords();
				keywords.remove(keyword);
				toxic.setKeywords(keywords);
				toxicService.save(toxic);
				result=list();
			}else{
				result = new ModelAndView("toxic/edit");
				result.addObject("toxic", toxic);
			}
		} catch (Throwable oops){
			result = list();
		}
		return result;
	}
	

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid @ModelAttribute Toxic toxic,BindingResult bindingResult) {
		ModelAndView result;
		
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult);
			result = list();
			result.addObject("toxic", toxic);
		} else {
			try {
				toxicService.save(toxic);
				result = list();
			
			} catch (Throwable oops) {
				result = list();
				result.addObject("toxic", toxic);
				result.addObject("message","toxic.commit.error");
			}
		}
		return result;
	}

//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
//	public ModelAndView delete(@ModelAttribute Toxic toxic,BindingResult bindingResult) {
//		ModelAndView result;
//
//		if (bindingResult.hasErrors()) {
//			result = new ModelAndView("administrator");
//			result.addObject("toxic", toxic);
//		} else {
//			try {
//				toxicService.delete(toxic);
//				result = new ModelAndView("administrator");
//			
//			} catch (Throwable oops) {
//				result = new ModelAndView("administrator");
//
//				result.addObject("toxic", toxic);
//				result.addObject("message", "category.commit.error");
//			}
//		}
//		return result;
//	}
}
