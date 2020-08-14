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
 * ����Ա
 * @author 22304
 *
 */
@RequestMapping("/user")
@Controller
public class UserController {

	@Autowired
	public UserService userService;
	/**
	 * �û������б�ʵ��
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
	   model.setViewName("user/user_list");
		return model;
	}
	
	/**
	 * ��ѯ�û�������Ա���б�
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
		queryMap.put("username", "%"+username+"%");//ģ����ѯ
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", userService.findList(queryMap));//����list
		ret.put("total", userService.getTotal(queryMap));//����total����
	 return ret;
	}
	
	/**
	 * �޸��û�������Ա��
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(User user){
		Map<String, String> ret=new HashMap<String, String>();
		if (user==null) {//user����δ����
			ret.put("type", "error");
			ret.put("msg", "���ݳ���,����ϵϵͳ����Ա");
			return ret;
		}
		if (StringUtils.isEmpty(user.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "�û�������Ϊ��");
			return ret;
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "���벻��Ϊ��");
			return ret;
		}
		//��ѯ�Ƿ����
		User findByUserName = userService.findByUserName(user.getUsername());
		if (findByUserName!=null) {
			if (user.getId()!=findByUserName.getId()) {//ֻ�������ж�ȥ��
				ret.put("type", "error");
				ret.put("msg", "�û����Ѿ����ڣ��뻻һ���û���");
				return ret;
			}
			
		}
		if (userService.edit(user)<=0) {
			ret.put("type", "error");
			ret.put("msg", "�޸�ʧ��");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "�޸ĳɹ�");
		
		return ret;
	}
	
	/**
	 * ����û�����
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(User user){
		Map<String, String> ret=new HashMap<String, String>();
		if (user==null) {//user����δ����
			ret.put("type", "error");
			ret.put("msg", "���ݳ���,����ϵϵͳ����Ա");
			return ret;
		}
		if (StringUtils.isEmpty(user.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "�û�������Ϊ��");
			return ret;
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "���벻��Ϊ��");
			return ret;
		}
		//����û��� �Ȳ�ѯ�Ƿ����
		User findByUserName = userService.findByUserName(user.getUsername());
		if (findByUserName!=null) {
			ret.put("type", "error");
			ret.put("msg", "�û����Ѿ����ڣ��뻻һ���û���");
			return ret;
		}
		if (userService.add(user)<=0) {
			ret.put("type", "error");
			ret.put("msg", "���ʧ��");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "��ӳɹ�");
		
		return ret;
	}
}
