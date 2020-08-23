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
	public Map<String, Object> getList(@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "gradeId", required = false) Long gradeId, Page page) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", "%" + name + "%");// 模糊查询
		if (gradeId != null) {
			queryMap.put("gradeId", gradeId);
		}
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", clazzService.findList(queryMap));// 返回list
		ret.put("total", clazzService.getTotal(queryMap));// 返回total总数
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
		student.setSn(StringUtil.generateSn("18001", ""));
		/*if (clazzService.add(student) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "学生添加失败");
			return ret;
		}*/
		System.out.println("student:"+student);
		ret.put("type", "success");
		ret.put("msg", "学生添加成功");
		return ret;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> Delete(@RequestParam(value = "ids[]", required = true) Long[] ids) {
		Map<String, String> ret = new HashMap<String, String>();
		if (ids == null || ids.length == 0) {
			ret.put("type", "error");
			ret.put("msg", "选择要删除的数据");
			return ret;
		}
		try {
			if (clazzService.delete(StringUtil.joinString(Arrays.asList(ids), ",")) <= 0) {// aslist将一个数组转化为一个List对象
																							// jdk1.2 返回一个arrayList
				ret.put("type", "error");
				ret.put("msg", "删除数据失败");
				return ret;
			}

		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "学生下存在学生信息");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "学生删除成功");
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
	public Map<String, String> edit(@RequestParam(value = "gradeId", required = true) Long gradeId, Clazz clazz) {
		Map<String, String> ret = new HashMap<String, String>();
		if (StringUtils.isEmpty(clazz.getName())) {
			ret.put("type", "error");
			ret.put("msg", "学生名称不能为空");
			return ret;
		}
		/**
		 * 同添加待优化
		 */
		clazz.setGradeID(gradeId);
		// System.out.println(clazz);
		if (clazz.getGradeId() == 0) {
			ret.put("type", "error");
			ret.put("msg", "所属年级不能为空不能为空");
			return ret;
		}
		if (clazzService.edit(clazz) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "学生修改失败");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "学生修改成功");
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

}
