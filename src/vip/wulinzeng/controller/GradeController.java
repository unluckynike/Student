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

import vip.wulinzeng.entity.Grade;
import vip.wulinzeng.page.Page;
import vip.wulinzeng.service.GradeService;

/**
 * 年级管理
 * @author 22304
 *
 */
@RequestMapping("/grade")
@Controller
public class GradeController {
	
	@Autowired
	private GradeService gradeService;

	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("grade/grade_list");
		return model;
	}
	
	/**
	 * 查询年级列表
	 * @param name
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/get_list",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList( 
			@RequestParam(value = "name",required = false,defaultValue = "")String name,
			Page page){
		Map<String, Object> ret=new HashMap<String, Object>();
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("name", "%"+name+"%");//模糊查询
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", gradeService.findList(queryMap));//返回list
		ret.put("total", gradeService.getTotal(queryMap));//返回total总数
	 return ret;
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Grade grade ){
		Map<String, String> ret=new HashMap<String, String>();
		if (StringUtils.isEmpty(grade.getName())) {
			ret.put("type", "error");
			ret.put("msg", "年级名称不能为空");
			return ret;
		}
		if (gradeService.add(grade)<=0) {
			ret.put("type", "error");
			ret.put("msg", "年级添加失败");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "年级添加成功");
		return ret;
	}
}
