package com.time.scenery.rain.space.restfulapi.common;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import com.alibaba.fastjson.JSON;
import com.time.scenery.rain.log.Log;

public class CommonMethod {
	private static Logger log = Log.get("BackgroundServer");
	
	
	
	public static void responseToSend(HttpServletResponse response,String contentType,Object object) {
		try {
			response.setContentType(contentType);
			response.getWriter().print(JSON.toJSONString(object));
		} catch (IOException e) {
			e.printStackTrace();
			Log.error(log,e,"responseToSend-ERROR");
		}
	}
}
