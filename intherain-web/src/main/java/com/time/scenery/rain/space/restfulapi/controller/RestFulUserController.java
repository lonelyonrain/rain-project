package com.time.scenery.rain.space.restfulapi.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.time.scenery.rain.space.restfulapi.common.CommonConfig;
import com.time.scenery.rain.space.restfulapi.common.CommonMethod;
import com.time.scenery.rain.space.restfulapi.entity.Person;
import com.time.scenery.rain.space.restfulapi.service.RestFulUserService;
import com.time.scenery.rain.spring.utils.DataSourceContextHolder;
import com.time.scenery.rain.utils.XDate;

@Controller
@RequestMapping("restfulapi/v1/users")
public class RestFulUserController {
	@Resource(name = "restFulUserService")
	private RestFulUserService restFulUserService;

	// 我们的 REST API :
	// GET 方式请求 /api/user/ 返回用户列表
	// GET 方式请求 /api/user/1返回id为1的用户
	// POST 方式请求 /api/user/ 通过user对象的JSON 参数创建新的user对象
	// PUT 方式请求 /api/user/3 更新id为3的发送json格式的用户对象
	// DELETE 方式请求/api/user/4删除 ID为 4的user对象
	// DELETE 方式请求/api/user/删除所有user

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void getPerson(@PathVariable(value = "id") Integer id, HttpServletResponse response) {
		System.out.println("id=" + id);
		CommonMethod.responseToSend(response, CommonConfig.CONTENT_TYPE, "获取单个人员信息");
		DataSourceContextHolder.setDataSourceType("mssqlDataSource");     //注意这里在调用service前切换到mssqlDataSource的数据源
		System.err.println(JSON.toJSONString(restFulUserService.getAllShopAndUnitInfo()));
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST, produces = CommonConfig.CONTENT_TYPE)
	@ResponseBody
	public void addPerson(HttpServletRequest request, HttpServletResponse response) {
			String aa=request.getParameter("paramJson"); 
			System.err.println(aa);
			Person persion =JSON.parseObject(aa,Person.class);// 将建json对象转换为Person对象
			if (persion != null) {
				System.err.println(persion.toString());
			} else {
				System.err.println(persion);
			}
			CommonMethod.responseToSend(response, CommonConfig.CONTENT_TYPE, "增加成功！");
			
			//
			DataSourceContextHolder.setDataSourceType("oracleDataSource");     //注意这里在调用service前切换到mssqlDataSource的数据源
			System.err.println("test="+XDate.formateLong(restFulUserService.getSystemTime()));
	}

	@RequestMapping(value = "/user", method = RequestMethod.PUT, produces = CommonConfig.CONTENT_TYPE)
	@ResponseBody
	public void updatePerson(HttpServletRequest request, HttpServletResponse response) {
		System.err.println("更新");
		String aa=request.getParameter("random"); 
		System.err.println(aa);
		CommonMethod.responseToSend(response, CommonConfig.CONTENT_TYPE, "更新成功！");
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deletePerson(@PathVariable(value = "id") Integer id, HttpServletResponse response) {
		System.out.println("id=" + id);
		CommonMethod.responseToSend(response, CommonConfig.CONTENT_TYPE, "删除成功！");
	}

	@RequestMapping(value = "/user", method = RequestMethod.PATCH, produces = CommonConfig.CONTENT_TYPE)
	@ResponseBody
	public void listPerson(HttpServletRequest request, HttpServletResponse response) {
		System.err.println("==================");
		CommonMethod.responseToSend(response, CommonConfig.CONTENT_TYPE, "批量查询成功！");
	}

	public String getStrFromInputSteam(InputStream in) {
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			// 最好在将字节流转换为字符流的时候 进行转码
			StringBuffer buffer = new StringBuffer();
			String line = "";
			try {
				while ((line = bf.readLine()) != null) {
					buffer.append(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return buffer.toString();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
