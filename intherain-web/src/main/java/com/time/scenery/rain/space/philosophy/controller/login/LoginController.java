package com.time.scenery.rain.space.philosophy.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class LoginController {	
	
//	@Resource(name="userService")
//	private UserService userService;
	
	@RequestMapping("/login")
	public ModelAndView login() {
		ModelAndView mv =new ModelAndView();
		mv.setViewName("login/login");
		return mv;
	}
	
	@RequestMapping("/login_deal")
	public ModelAndView login_deal() {
		ModelAndView mv =new ModelAndView();
		return mv;
	}
	
	@RequestMapping("/main")
	public ModelAndView main_index() {
		ModelAndView mv =new ModelAndView();
		mv.setViewName("public/main");
		return mv;
	}
}
