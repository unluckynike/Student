package vip.wulinzeng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
}
