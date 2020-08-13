package vip.wulinzeng.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;
import vip.wulinzeng.entity.User;

/**
 * ��̨����������
 * @author 22304
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer urlString=request.getRequestURL();
		System.out.println("����������"+urlString);
		User user = (User)request.getSession().getAttribute("user");
		if (user==null) {
			System.out.println("δ��¼");
			//ajax����
			if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
				Map<String, String> ret=new HashMap<String, String>();
				ret.put("type", "error");
				ret.put("msg", "��¼״̬�Ѿ�ʧЧ�������µ�¼");
				response.getWriter().write(JSONObject.fromObject(ret).toString());
				return false;
			}
			response.sendRedirect(request.getContextPath()+"/system/login");//ת����¼ҳ��
			return false;
		}
		return true;
	}

}
