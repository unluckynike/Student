package vip.wulinzeng.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
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
import vip.wulinzeng.entity.Student;
import vip.wulinzeng.page.Page;
import vip.wulinzeng.service.ClazzService;
import vip.wulinzeng.service.StudentService;
import vip.wulinzeng.util.StringUtil;

/**
 * 学生管理
 * 
 * @author 22304
 *
 */
@RequestMapping("/student")
@Controller
public class StudentController {
  
	@Autowired
	private ClazzService clazzService;
	@Autowired
	private StudentService studentService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("student/student_list");
		/**
		 * 获取全部班级
		 */
		List<Clazz> clazzList = clazzService.findAll();
		model.addObject("clazzList", clazzList);
		model.addObject("clazzListJson", JSONArray.fromObject(clazzList));
		return model;
	}

	/**
	 * 查询学生列表
	 * 
	 * @param name
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/get_list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "clazzId", required = false) Long clazzId,
			Page page,HttpServletRequest request) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("username", "%" + name + "%");// 模糊查询
		Object attribute = request.getSession().getAttribute("userType");//权限
		if ("2".equals(attribute.toString())) {//说明是学生进入
			Student loginStudent = (Student)request.getSession().getAttribute("user");
			queryMap.put("username", "%" + loginStudent.getUsername() + "%");
		}
		if (clazzId != null) {
			queryMap.put("clazzId", clazzId);
		}
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", studentService.findList(queryMap));// 返回list
		ret.put("total", studentService.getTotal(queryMap));// 返回total总数
		return ret;
	}

	/**
	 * 添加学生
	 * 
	 * @param grade
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add( Student student) {
		Map<String, String> ret = new HashMap<String, String>();
		if (StringUtils.isEmpty(student.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "学生姓名不能为空");
			return ret;
		}
		if (StringUtils.isEmpty(student.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "登录密码不能为空");
			return ret;
		}
		/**
		 * bug 待优化 clazz.getGradeId 返回值long类型 方法获取年级id失效多次测试一直输出默认值0 sql语句无法执行
		 * 下段代码以springmvc方式获取年级id值
		 */
		// System.out.println("springmvc:"+gradeId);
		//student.setGradeID(gradeId);
		// System.out.println("springioc:"+clazz.getId()+" "+clazz.getName()+"
		// "+clazz.getGradeId()+" "+clazz.getRemark());

		if (student.getClazzId() == null) {// getGradeId 返回值long类型
			// System.out.println("clazz-name:"+clazz.getName());
			ret.put("type", "error");
			ret.put("msg", "请选择所属班级");
			return ret;
		}
		if (isExist(student.getUsername(), null)) {
			ret.put("type", "error");
			ret.put("msg", "该姓名已存在");
		}
		student.setSn(StringUtil.generateSn("18001", ""));
		if (studentService.add(student) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "学生添加失败");
			return ret;
		}
		System.out.println("student:"+student);
		ret.put("type", "success");
		ret.put("msg", "学生添加成功");
		return ret;
	}

	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> Delete(@RequestParam(value = "ids[]", required = true) Long[] ids) {
		Map<String, String> ret = new HashMap<String, String>();
		if(ids == null || ids.length == 0){
			ret.put("type", "error");
			ret.put("msg", "请选择要删除的数据！");
			return ret;
		}
		try {
			if(studentService.delete(StringUtil.joinString(Arrays.asList(ids), ",")) <= 0){
				ret.put("type", "error");
				ret.put("msg", "删除失败！");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "该学生下存在其他信息，请勿冲动！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "学生删除成功！");
		return ret;
	}

	/**
	 * 修改学生信息
	 * 
	 * @param grade
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit( Student student) {
		Map<String, String> ret = new HashMap<String, String>();
		if(StringUtils.isEmpty(student.getUsername())){
			ret.put("type", "error");
			ret.put("msg", "学生姓名不能为空！");
			return ret;
		}
		if(StringUtils.isEmpty(student.getPassword())){
			ret.put("type", "error");
			ret.put("msg", "学生登录密码不能为空！");
			return ret;
		}
		if(student.getClazzId() == null){
			ret.put("type", "error");
			ret.put("msg", "请选择所属班级！");
			return ret;
		}
		if(isExist(student.getUsername(), student.getId())){
			ret.put("type", "error");
			ret.put("msg", "该姓名已存在！");
			return ret;
		}
		student.setSn(StringUtil.generateSn("18001", ""));
		if(studentService.edit(student) <= 0){
			ret.put("type", "error");
			ret.put("msg", "学生添加失败！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "学生修改成功！");
		return ret;
	}

	/**
	 * 图片（头像）上传
	 * 
	 * @param photo
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/upload_photo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> uploadPhoto(MultipartFile photo, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		Map<String, String> ret = new HashMap<String, String>();
		if (photo == null) {
			// 未选择文件
			ret.put("type", "error");
			ret.put("msg", "未选择头像");
			return ret;
		}
		if (photo.getSize() > 10485760) {
			ret.put("type", "error");
			ret.put("msg", "图片大小超过10M，请换一张小于10M的图片");
			return ret;
		}
		String suffixString = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1,
				photo.getOriginalFilename().length());
		System.out.println("suffixString:" + suffixString);
		if (!"jpg,png,gif,jpeg".contains(suffixString.toLowerCase())) {
			ret.put("type", "error");
			ret.put("msg", "文件格式错误，上传jpg,png,gif,jpeg文件");
			return ret;
		}

		String savePathString = request.getServletContext().getRealPath("/") + "\\upload\\";
		String contextPath = request.getServletContext().getContextPath();
		System.out.println("contextPath:" + contextPath);
		System.out.println("savePathString:" + savePathString);
		File savePathFile = new File(savePathString);
		if (!savePathFile.exists()) {
			savePathFile.mkdir();// 如果不存在则创建一个文件夹
		}
		// 把文件转存到此文件夹下
		String filenameString = new Date().getTime() + "." + suffixString;
		photo.transferTo(new File(savePathString + filenameString));
		ret.put("type", "success");
		ret.put("msg", "学生添加成功");
		ret.put("src", request.getServletContext().getContextPath() + "/upload/" + filenameString);
		return ret;
	}

	private boolean isExist(String username,Long id) {
		Student student = studentService.findByUserName(username);
		if (student!=null) {
			if (id==null) {
				return true;
			}
			if (student.getId().longValue()!=id.longValue()) {
				return true;
			}
		}
		return false;
	}
}
