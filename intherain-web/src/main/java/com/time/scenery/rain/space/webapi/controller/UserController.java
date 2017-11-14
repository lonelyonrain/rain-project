package com.time.scenery.rain.space.webapi.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.time.scenery.rain.space.webapi.common.CommonConfig;
import com.time.scenery.rain.space.webapi.common.CommonMethod;
import com.time.scenery.rain.space.webapi.daoentity.RentEnterpriseStoreMap;
import com.time.scenery.rain.space.webapi.service.UserService;

@Controller
@RequestMapping("webapi")
public class UserController {	
	
	@Resource(name="userService")
	private UserService userService;
	
	@RequestMapping("/test")
	public String  login(){
		return "webapi/test";
	}
	
	@RequestMapping(value = "/say",produces=CommonConfig.CONTENT_TYPE)   
    public void say(HttpServletResponse response) {  
		List<RentEnterpriseStoreMap> hi=userService.getAllShopAndUnitInfo();
		CommonMethod.responseToSend(response,CommonConfig.CONTENT_TYPE, hi);
		hi=null;
    }  		
}
