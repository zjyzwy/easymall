package easymall.controller.admin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import easymall.po.admin.Admin;
import easymall.service.admin.AdminService;
@Controller("adminController")
@RequestMapping("/admin")
public class AdminController extends AdminBaseController{
	@Autowired
	private AdminService adminService;
	
	@RequestMapping("/manage")
	public String manage(HttpSession session) {
		return "admin/manage";
	}
	
	@RequestMapping("/tologin")
	public String tologin(Admin admin,HttpSession session,Model model) {
		Admin newAdmin = adminService.tologin(admin);
		if(newAdmin!=null) {
			session.setAttribute("admin", newAdmin);
			return "redirect:/admin/manage";
		}else {
			model.addAttribute("message","用户名或密码错误");
			return "admin/adminlogin";
		}
	}
}
