package vip.wulinzeng.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vip.wulinzeng.entity.Student;
import vip.wulinzeng.entity.User;
import vip.wulinzeng.service.StudentService;
import vip.wulinzeng.service.UserService;
import vip.wulinzeng.util.CpachaUtil;

/**
 * 
 * @author 22304
 *
 */

@RequestMapping("/system")
@Controller
public class SystemController {

	@Autowired
	private UserService userService;
    @Autowired	
	private StudentService studentService;
	
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public ModelAndView index(ModelAndView module) {
		//System.out.println("测试环境执行");
		//module.addObject("testInfor", "添加成功");
		module.setViewName("system/index");
		return module;
	}
	
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public ModelAndView login(ModelAndView model) {
		model.setViewName("system/login");
		return model;
	}
	
	@RequestMapping(value = "/login_out",method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		request.getSession().setAttribute("user", null);
		return "redirect:login";
	}
	
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> login(
			@RequestParam(value = "username",required = true)String usernameString,
			@RequestParam(value = "password",required = true)String passwordString,
			@RequestParam(value = "vcode",required = true)String vcode,
			@RequestParam(value = "type",required = true)int type,
			HttpServletRequest request)
	{
		
		Map<String, String> ret= new HashMap<String, String>();
		if (org.apache.commons.lang.StringUtils.isEmpty(usernameString)) {
			ret.put("type", "erro");
			ret.put("msg", "用户名不能为空");
			return ret;
		}
		if (org.apache.commons.lang.StringUtils.isEmpty(passwordString)) {
			ret.put("type", "error");
			ret.put("msg","密码不能为空");
			return ret;
		}
		if (org.apache.commons.lang.StringUtils.isEmpty(vcode)) {
			ret.put("type", "error");
			ret.put("msg","验证码不能为空");
			return ret;
		}
		String loginCpachString = (String)request.getSession().getAttribute("loginCpacha");//取出生成的验证码
		if (StringUtils.isEmpty(loginCpachString)) {
			ret.put("type", "error");
			ret.put("msg", "验证码已经失效，刷新后重试");
			return ret;
		}
		if (!vcode.toUpperCase().equals(loginCpachString.toUpperCase())) {//大写判断
			ret.put("type", "error");
			ret.put("msg", "验证码错误，刷新后重试");
			return ret;
		}
		request.getSession().setAttribute("loginCpacha", null);//验证通过从session种去掉验证码  允许进入系统
		if (type==1) {//管理员
		//用户名 密码 判断
		User user = userService.findByUserName(usernameString);
		if (user==null) {
			ret.put("type", "error");
			ret.put("msg", "用户不存在");
			//System.out.println("has running");
			return ret;
		}
		if (!passwordString.equals(user.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "密码错误");
			return ret;
		}
		request.getSession().setAttribute("user", user);//将用户名放入session
	 }
		if(type==2){//学生
	    System.out.println("学生登录");
		Student student = studentService.findByUserName(usernameString);
		if (student==null) {
			ret.put("type", "error");
			ret.put("msg", "学生不存在");
			//System.out.println("has running");
			return ret;
		}
		if (!passwordString.equals(student.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "密码错误");
			return ret;
		}
		request.getSession().setAttribute("user", student);//将用户名放入session
	}
		request.getSession().setAttribute("userType", type);
		ret.put("type", "success");
		ret.put("msg", "登录成功");
		return ret;
	}
	
	@RequestMapping(value="/get_cpacha",method=RequestMethod.GET)
	public void getCpacha(HttpServletRequest request,
			@RequestParam(value="vl",defaultValue="4",required=false) Integer vl,
			@RequestParam(value="w",defaultValue="98",required=false) Integer w,
			@RequestParam(value="h",defaultValue="33",required=false) Integer h,
			HttpServletResponse response){
		CpachaUtil cpachaUtil = new CpachaUtil(vl, w, h);
		String generatorVCode = cpachaUtil.generatorVCode();
		request.getSession().setAttribute("loginCpacha", generatorVCode);//验证码放进session
		BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
		try {
			ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
