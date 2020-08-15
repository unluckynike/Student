package vip.wulinzeng.controller;

import java.util.Arrays;
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
import vip.wulinzeng.util.StringUtil;

/**
 * �꼶����
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
	 * ��ѯ�꼶�б�
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
		queryMap.put("name", "%"+name+"%");//ģ����ѯ
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", gradeService.findList(queryMap));//����list
		ret.put("total", gradeService.getTotal(queryMap));//����total����
	 return ret;
	}
	
	/**
	 * ����꼶
	 * @param grade
	 * @return
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Grade grade ){
		Map<String, String> ret=new HashMap<String, String>();
		if (StringUtils.isEmpty(grade.getName())) {
			ret.put("type", "error");
			ret.put("msg", "�꼶���Ʋ���Ϊ��");
			return ret;
		}
		if (gradeService.add(grade)<=0) {
			ret.put("type", "error");
			ret.put("msg", "�꼶���ʧ��");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "�꼶��ӳɹ�");
		return ret;
	}
	
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> Delete(
			@RequestParam(value = "ids[]",required = true)Long[] ids){
		Map<String,String> ret = new HashMap<String, String>();
		if (ids==null||ids.length==0) {
			ret.put("type", "error");
			ret.put("msg", "ѡ��Ҫɾ��������");
			return ret;
		}
        try {
    		if (gradeService.delete(StringUtil.joinString(Arrays.asList(ids), ","))<=0) {//aslist��һ������ת��Ϊһ��List���� jdk1.2 ����һ��arrayList
    			ret.put("type", "error");
    			ret.put("msg", "ɾ������ʧ��");
    			return ret;
    		}
        	
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "�꼶�´��ڰ༶��Ϣ");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "�꼶ɾ���ɹ�");
		return ret;
	}
	
	/**
	 * �޸��꼶
	 * @param grade
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Grade grade ){
		Map<String, String> ret=new HashMap<String, String>();
		if (StringUtils.isEmpty(grade.getName())) {
			ret.put("type", "error");
			ret.put("msg", "�꼶���Ʋ���Ϊ��");
			return ret;
		}
		if (gradeService.edit(grade)<=0) {
			ret.put("type", "error");
			ret.put("msg", "�꼶�޸�ʧ��");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "�꼶�޸ĳɹ�");		
		return ret;
	}
	
	
	
}
