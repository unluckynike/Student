package vip.wulinzeng.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import vip.wulinzeng.entity.Clazz;
import vip.wulinzeng.page.Page;
import vip.wulinzeng.service.ClazzService;
import vip.wulinzeng.util.StringUtil;

/**
 * ѧ������
 * @author 22304
 *
 */
@RequestMapping("/student")
@Controller
public class StudentController {
	
	
	@Autowired
	private ClazzService clazzService;

	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("student/student_list");
		/**
		 * ��ȡȫ���༶
		 */
		List<Clazz> clazzList = clazzService.findAll();
		model.addObject("clazzList", clazzList);
		model.addObject("clazzListJson", JSONArray.fromObject(clazzList));
		return model;
	}
	
	
	/**
	 * ��ѯѧ���б�
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
		queryMap.put("name", "%"+name+"%");//ģ����ѯ
		if (gradeId!=null) {
			queryMap.put("gradeId", gradeId);
		}
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", clazzService.findList(queryMap));//����list
		ret.put("total", clazzService.getTotal(queryMap));//����total����
	 return ret;
	}
	
	
	/**
	 * ���ѧ��
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
			ret.put("msg", "ѧ�����Ʋ���Ϊ��");
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
	       
		if (clazz.getGradeId() == 0) {//getGradeId  ����ֵlong����
	//System.out.println("clazz-name:"+clazz.getName());
			ret.put("type", "error");
			ret.put("msg", "��ѡ�������꼶");
			return ret;
		}
		if (clazzService.add(clazz)<=0) {
			ret.put("type", "error");
			ret.put("msg", "ѧ�����ʧ��");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "ѧ����ӳɹ�");
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
    		if (clazzService.delete(StringUtil.joinString(Arrays.asList(ids), ","))<=0) {//aslist��һ������ת��Ϊһ��List���� jdk1.2 ����һ��arrayList
    			ret.put("type", "error");
    			ret.put("msg", "ɾ������ʧ��");
    			return ret;
    		}
        	
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "ѧ���´���ѧ����Ϣ");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "ѧ��ɾ���ɹ�");
		return ret;
	}
	
	/**
	 * �޸�ѧ����Ϣ
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
			ret.put("msg", "ѧ�����Ʋ���Ϊ��");
			return ret;
		}
		/**
		 * ͬ��Ӵ��Ż�
		 */
		 clazz.setGradeID(gradeId);
	//System.out.println(clazz);
		if (clazz.getGradeId()==0) {
			ret.put("type", "error");
			ret.put("msg", "�����꼶����Ϊ�ղ���Ϊ��");
			return ret;
		}
		if (clazzService.edit(clazz)<=0) {
			ret.put("type", "error");
			ret.put("msg", "ѧ���޸�ʧ��");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "ѧ���޸ĳɹ�");		
		return ret;
	}
	
	/**
	 * ͼƬ��ͷ���ϴ�
	 * @param photo
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/upload_photo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> uploadPhoto(
			MultipartFile photo ,
			HttpServletRequest request,
			HttpServletResponse response
			) throws IOException{
		response.setCharacterEncoding("utf-8");
		Map<String, String> ret=new HashMap<String, String>();
		if (photo==null) {
			//δѡ���ļ�
			ret.put("type", "error");
			ret.put("msg", "δѡ��ͷ��");
			return ret;
		}
		String contextPath = request.getServletContext().getContextPath();
		System.out.println("path:"+contextPath);

//		if (StringUtils.isEmpty(clazz.getName())) {
//			ret.put("type", "error");
//			ret.put("msg", "ѧ�����Ʋ���Ϊ��");
			return ret;
		}
	
}
