package vip.wulinzeng.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vip.wulinzeng.entity.User;
import vip.wulinzeng.service.UserService;

/**
 * 管理员
 * @author 22304
 *
 */
@RequestMapping("/user")
@Controller
public class UserController {

	@Autowired
	public UserService userService;
	/**
	 * 用户管理列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
	   model.setViewName("user/user_list");
		return model;
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(User user){
		Map<String, String> ret=new HashMap<String, String>();
		if (user==null) {//user对象未进入
			ret.put("type", "error");
			ret.put("msg", "数据出错,请联系系统管理员");
			return ret;
		}
		if (StringUtils.isEmpty(user.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "用户名不能为空");
			return ret;
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "密码不能为空");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功");
		
		return ret;
	}
}
