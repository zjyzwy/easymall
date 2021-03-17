package easymall.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import easymall.po.User;
import easymall.service.UserService;

@Controller("userController")
public class UserController {
	@Autowired
	private UserService userService;
	
	private Map<String, String> resultMap = new HashMap<>();
	
	@RequestMapping("/user/login")
	public String login(User user, HttpSession session,Model model) {
		//System.out.println(user);
		User muser = userService.login(user);
		if(muser!=null) {
			session.setAttribute("user", muser);
			return "redirect:/index.jsp";
		}else {
			model.addAttribute("message", "用户名或密码错误");
			return "login";
		}
	}
	
	@RequestMapping(value="/user/checkUser",method=RequestMethod.POST)
	public void check(HttpServletRequest request,HttpServletResponse reponse) throws IOException {
		String username = request.getParameter("username");
		if(userService.checkUsername(username)) {
			reponse.getWriter().print("用户名"+username+"已被注册!");
		}else {
			reponse.getWriter().print("恭喜您,"+username+"可以使用!");
		}
	}
	
	@RequestMapping("/user/regist")
	public String regist(User user,String valistr,HttpSession session,Model model,@RequestParam String code) {
		if(user.getUsername()==null || user.getUsername()=="") {
			model.addAttribute("msg","用户名不能为空");
			return "regist";
		}
		if(user.getPassword()==null||user.getPassword()=="") {
			model.addAttribute("msg","密码不能为空");
			return "regist";
		}
		
		if(!valistr.equalsIgnoreCase(session.getAttribute("code").toString())) {
			model.addAttribute("msg", "验证码错误!");
			return "regist";
		}

		if(!resultMap.get("code").equalsIgnoreCase(code)) {
			model.addAttribute("msg", "邮箱验证码错误");
			return "regist";
		}
		//该方法不适用，因为发送验证码时已存在了session中，故使用注解@RequestParm检验验证码
//		if(!resultMap.get("code").equalsIgnoreCase(session.getAttribute("emailCode").toString())) {
//			model.addAttribute("msg", "邮箱验证码错误");
//			return "regist";
//		}
		
		
		if(userService.regist(user)>0) {
//			session.setAttribute("user", user);
			model.addAttribute("msg", "注册成功");
			return "regist";
		}else {
			model.addAttribute("msg", "注册失败");
			return "regist";
		}
	}
	
	@RequestMapping("/user/sendEmail")
    //转换json数据  @ResponseBody
    @ResponseBody
	public String sendEmail(String email) {
		HtmlEmail htmlEmail = new HtmlEmail();//创建一个HtmlEmail实例对象
		// 获取随机验证码   
		String code = getCode();       
		try {    
			htmlEmail.setHostName("smtp.qq.com");	  		
			htmlEmail.setAuthentication("2745674345@qq.com", "gjewzkgopwfedhdh"); //第一个参数是发送者的QQEamil邮箱   第二个参数是刚刚获取的授权码
   
			htmlEmail.setFrom("2745674345@qq.com", "JavaWeb邮箱验证");//发送人的邮箱为自己的，用户名可以随便填
//			htmlEmail.setSmtpPort(465); 	//端口号 可以不开       
			htmlEmail.setSSLOnConnect(true); //开启SSL加密  
			htmlEmail.setCharset("utf-8");      
			htmlEmail.addTo(email);  //设置收件人    email为你要发送给谁的邮箱账户
			htmlEmail.setSubject("JavaWeb邮箱验证"); //邮箱标题  
			htmlEmail.setMsg("JavaWeb注册页面邮箱验证码为：" + code); //Eamil发送的内容
			htmlEmail.send();  //发送
			saveCode(code);
//			session.setAttribute("emailCode", code);
			return "success";
        }catch (EmailException e){
            return "false";
        }
	}
	
	private String getCode() {
		String codes = "TuvWUtV0axAX1BYybZ2czDCd3EfeF4gHGh5IijJ6kKlL7MnNmOp8Po9QqRrsS";
		StringBuffer sb = new StringBuffer();
		String code = "";
		for (int i = 0; i < 6; i++) {
			code = codes.charAt(getRandom(0, codes.length())) + "";
			sb.append(code);
		}
		return sb.toString();
		
	}
	
	private void saveCode(String code) {
		resultMap.put("code", code);
	}
	
	private int getRandom(int start, int end) {
		Random random = new Random();
		return random.nextInt(end - start) + start;
	}
}
