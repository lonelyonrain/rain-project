package com.time.scenery.rain.space.restfulapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("restfulapi/v1/")
public class RestFulApiController {
	
	@RequestMapping("/test")
	public String  login(){
		return "restfulapi/test";
	}
}
