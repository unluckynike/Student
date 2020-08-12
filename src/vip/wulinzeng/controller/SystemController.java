package vip.wulinzeng.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vip.wulinzeng.util.CpachaUtil;

/**
 * 
 * @author 22304
 *
 */

@RequestMapping("/system")
@Controller
public class SystemController {

	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public ModelAndView index(ModelAndView module) {
		System.out.println("���Ի���ִ��");
		module.addObject("testInfor", "��ӳɹ�");
		module.setViewName("testEnvironment");
		return module;
	}
	
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public ModelAndView login(ModelAndView model) {
		model.setViewName("system/login");
		return model;
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
			ret.put("msg", "�û�������Ϊ��");
			return ret;
		}
		if (org.apache.commons.lang.StringUtils.isEmpty(passwordString)) {
			ret.put("type", "error");
			ret.put("msg","���벻��Ϊ��");
			return ret;
		}
		if (org.apache.commons.lang.StringUtils.isEmpty(vcode)) {
			ret.put("type", "error");
			ret.put("msg","��֤�벻��Ϊ��");
			return ret;
		}
		String loginCpachString = (String)request.getSession().getAttribute("loginCpacha");//ȡ�����ɵ���֤��
		if (StringUtils.isEmpty(loginCpachString)) {
			ret.put("type", "error");
			ret.put("msg", "��֤���Ѿ�ʧЧ��ˢ�º�����");
			return ret;
		}
		if (!vcode.toUpperCase().equals(loginCpachString.toUpperCase())) {//��д�ж�
			ret.put("type", "error");
			ret.put("msg", "��֤�����ˢ�º�����");
			return ret;
		}
		request.getSession().setAttribute("loginCpacha", null);//��֤ͨ����session��ȥ����֤��  �������ϵͳ
		
		ret.put("type", "success");
		ret.put("msg", "��¼�ɹ�");
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
		request.getSession().setAttribute("loginCpacha", generatorVCode);//��֤��Ž�session
		BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
		try {
			ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
