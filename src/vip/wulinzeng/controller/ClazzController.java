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

import vip.wulinzeng.entity.Clazz;
import vip.wulinzeng.entity.Grade;
import vip.wulinzeng.page.Page;
import vip.wulinzeng.service.ClazzService;
import vip.wulinzeng.service.GradeService;
import vip.wulinzeng.util.StringUtil;

/**
 * �༶����
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
		model.addObject("gradeList", gradeService.findAll());
		return model;
	}
	
	/**
	 * ��ѯ�༶�б�
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
		ret.put("rows", clazzService.findList(queryMap));//����list
		ret.put("total", clazzService.getTotal(queryMap));//����total����
	 return ret;
	}
	
	
	/**
	 * ��Ӱ༶
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
			ret.put("msg", "�༶���Ʋ���Ϊ��");
			return ret;
		}
		/**
		 * bug ���Ż�
		 * clazz.getGradeId  ����ֵlong����  
		 * ������ȡ�꼶idʧЧ��β���һֱ���Ĭ��ֵ0 sql����޷�ִ�� �¶δ�����springmvc��ʽ��ȡ�꼶idֵ
		 */
	//System.out.println("springmvc:"+gradeId);
	       clazz.setGradeID(gradeId);
	//System.out.println("springioc:"+clazz.getId()+" "+clazz.getName()+" "+clazz.getGradeId()+" "+clazz.getRemark());
		if (clazz.getName() == null) {//getGradeId  ����ֵlong����
	//System.out.println("clazz-name:"+clazz.getName());
			ret.put("type", "error");
			ret.put("msg", "��ѡ�������꼶");
			return ret;
		}
		if (clazzService.add(clazz)<=0) {
			ret.put("type", "error");
			ret.put("msg", "�༶���ʧ��");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "�༶��ӳɹ�");
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
			ret.put("msg", "�༶�´��ڰ༶��Ϣ");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "�༶ɾ���ɹ�");
		return ret;
	}
	
	/**
	 * �޸İ༶
	 * @param grade
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Grade grade ){
		Map<String, String> ret=new HashMap<String, String>();
		if (StringUtils.isEmpty(grade.getName())) {
			ret.put("type", "error");
			ret.put("msg", "�༶���Ʋ���Ϊ��");
			return ret;
		}
		if (gradeService.edit(grade)<=0) {
			ret.put("type", "error");
			ret.put("msg", "�༶�޸�ʧ��");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "�༶�޸ĳɹ�");		
		return ret;
	}
	
	
	
}
