package vip.wulinzeng.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import vip.wulinzeng.entity.Clazz;
import vip.wulinzeng.entity.Grade;
import vip.wulinzeng.page.Page;
import vip.wulinzeng.service.ClazzService;
import vip.wulinzeng.service.GradeService;
import vip.wulinzeng.util.StringUtil;

/**
 * 班级管理
 * @author 22304
 *
 */
@RequestMapping("/clazz")
@Controller
public class ClazzController {
	
	@Autowired
	private GradeService gradeService;
	
	@Autowired
	private ClazzService clazzService;

	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("clazz/clazz_list");
		List<Grade> findAll = gradeService.findAll();
		model.addObject("gradeList", gradeService.findAll());
		model.addObject("gradeListJson", JSONArray.fromObject(findAll));
		return model;
	}
	
	/**
	 * 查询班级列表
	 * @param name
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/get_list",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList( 
			@RequestParam(value = "name",required = false,defaultValue = "")String name,
			@RequestParam(value = "gradeId",required = false)Long gradeId,
			Page page){
		Map<String, Object> ret=new HashMap<String, Object>();
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("name", "%"+name+"%");//模糊查询
		if (gradeId!=null) {
			queryMap.put("gradeId", gradeId);
		}
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", clazzService.findList(queryMap));//返回list
		ret.put("total", clazzService.getTotal(queryMap));//返回total总数
	 return ret;
	}
	
	
	/**
	 * 添加班级
	 * @param grade
	 * @return
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(
			@RequestParam(value = "gradeId",required = true)Long gradeId,
			Clazz clazz ){
		Map<String, String> ret=new HashMap<String, String>();
		if (StringUtils.isEmpty(clazz.getName())) {
			ret.put("type", "error");
			ret.put("msg", "班级名称不能为空");
			return ret;
		}
		/**
		 * bug 待优化
		 * clazz.getGradeId  返回值long类型  
		 * 方法获取年级id失效多次测试一直输出默认值0 sql语句无法执行 下段代码以springmvc方式获取年级id值
		 */
	//System.out.println("springmvc:"+gradeId);
	       clazz.setGradeID(gradeId);
	//System.out.println("springioc:"+clazz.getId()+" "+clazz.getName()+" "+clazz.getGradeId()+" "+clazz.getRemark());
	       
		if (clazz.getGradeId() == 0) {//getGradeId  返回值long类型
	//System.out.println("clazz-name:"+clazz.getName());
			ret.put("type", "error");
			ret.put("msg", "请选择所属年级");
			return ret;
		}
		if (clazzService.add(clazz)<=0) {
			ret.put("type", "error");
			ret.put("msg", "班级添加失败");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "班级添加成功");
		return ret;
	}
	
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> Delete(
			@RequestParam(value = "ids[]",required = true)Long[] ids){
		Map<String,String> ret = new HashMap<String, String>();
		if (ids==null||ids.length==0) {
			ret.put("type", "error");
			ret.put("msg", "选择要删除的数据");
			return ret;
		}
        try {
    		if (clazzService.delete(StringUtil.joinString(Arrays.asList(ids), ","))<=0) {//aslist将一个数组转化为一个List对象 jdk1.2 返回一个arrayList
    			ret.put("type", "error");
    			ret.put("msg", "删除数据失败");
    			return ret;
    		}
        	
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "班级下存在学生信息");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "班级删除成功");
		return ret;
	}
	
	/**
	 * 修改班级信息
	 * @param grade
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(
			@RequestParam(value = "gradeId",required = true)Long gradeId,
			Clazz clazz ){
		Map<String, String> ret=new HashMap<String, String>();
		if (StringUtils.isEmpty(clazz.getName())) {
			ret.put("type", "error");
			ret.put("msg", "班级名称不能为空");
			return ret;
		}
		/**
		 * 同添加待优化
		 */
		 clazz.setGradeID(gradeId);
	//System.out.println(clazz);
		if (clazz.getGradeId()==0) {
			ret.put("type", "error");
			ret.put("msg", "所属年级不能为空不能为空");
			return ret;
		}
		if (clazzService.edit(clazz)<=0) {
			ret.put("type", "error");
			ret.put("msg", "班级修改失败");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "班级修改成功");		
		return ret;
	}
	
	
	
}
