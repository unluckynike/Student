package vip.wulinzeng.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vip.wulinzeng.entity.User;
import vip.wulinzeng.page.Page;
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
	 * 用户管理列表实体
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
	   model.setViewName("user/user_list");
		return model;
	}
	
	/**
	 * 查询用户（管理员）列表
	 * @param username
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/get_list",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList( 
			@RequestParam(value = "username",required = false,defaultValue = "")String username,
			Page page){
		Map<String, Object> ret=new HashMap<String, Object>();
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("username", "%"+username+"%");//模糊查询
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", userService.findList(queryMap));//返回list
		ret.put("total", userService.getTotal(queryMap));//返回total总数
	 return ret;
	}
	
	/**
	 * 修改用户（管理员）
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(User user){
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
		//查询是否存在
		User findByUserName = userService.findByUserName(user.getUsername());
		if (findByUserName!=null) {
			if (user.getId()!=findByUserName.getId()) {//只改密码判断去重
				ret.put("type", "error");
				ret.put("msg", "用户名已经存在，请换一个用户名");
				return ret;
			}
			
		}
		if (userService.edit(user)<=0) {
			ret.put("type", "error");
			ret.put("msg", "修改失败");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "修改成功");
		
		return ret;
	}
	
	/**
	 * 添加用户操作
	 * @param user
	 * @return
	 */
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
		//添加用户名 先查询是否存在
		User findByUserName = userService.findByUserName(user.getUsername());
		if (findByUserName!=null) {
			ret.put("type", "error");
			ret.put("msg", "用户名已经存在，请换一个用户名");
			return ret;
		}
		if (userService.add(user)<=0) {
			ret.put("type", "error");
			ret.put("msg", "添加失败");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功");
		
		return ret;
	}
}
