package easymall.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import easymall.exception.AdminLoginNoException;


@Controller
public class AdminBaseController {
	@ModelAttribute
	public void isLogin(HttpSession session,HttpServletRequest request) throws AdminLoginNoException{
		if(session.getAttribute("admin")==null) {
			throw new AdminLoginNoException("Ã»ÓÐµÇÂ¼ £¬ÇëÏÈµÇÂ¼! ");
		}
	}
}
