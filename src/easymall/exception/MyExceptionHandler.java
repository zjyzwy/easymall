package easymall.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import easymall.po.User;
import easymall.po.admin.Admin;

public class MyExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj,
			Exception ex) {
		// TODO 自动生成的方法存根
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ex", ex);
		if(ex instanceof UserLoginNoException) {
			request.setAttribute("user", new User());
			request.setAttribute("msg", "请先登录！");
			return new ModelAndView("login",model);
		}else if(ex instanceof AdminLoginNoException){
			request.setAttribute("admin", new Admin());
			request.getSession().setAttribute("admin", new Admin());
			request.setAttribute("msg", "请先登录！");
			return new ModelAndView("admin/adminlogin",model);
		}else {
			return new ModelAndView("error",model);
		}
	}

}
