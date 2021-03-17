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
			model.addAttribute("message", "�û������������");
			return "login";
		}
	}
	
	@RequestMapping(value="/user/checkUser",method=RequestMethod.POST)
	public void check(HttpServletRequest request,HttpServletResponse reponse) throws IOException {
		String username = request.getParameter("username");
		if(userService.checkUsername(username)) {
			reponse.getWriter().print("�û���"+username+"�ѱ�ע��!");
		}else {
			reponse.getWriter().print("��ϲ��,"+username+"����ʹ��!");
		}
	}
	
	@RequestMapping("/user/regist")
	public String regist(User user,String valistr,HttpSession session,Model model,@RequestParam String code) {
		if(user.getUsername()==null || user.getUsername()=="") {
			model.addAttribute("msg","�û�������Ϊ��");
			return "regist";
		}
		if(user.getPassword()==null||user.getPassword()=="") {
			model.addAttribute("msg","���벻��Ϊ��");
			return "regist";
		}
		
		if(!valistr.equalsIgnoreCase(session.getAttribute("code").toString())) {
			model.addAttribute("msg", "��֤�����!");
			return "regist";
		}

		if(!resultMap.get("code").equalsIgnoreCase(code)) {
			model.addAttribute("msg", "������֤�����");
			return "regist";
		}
		//�÷��������ã���Ϊ������֤��ʱ�Ѵ�����session�У���ʹ��ע��@RequestParm������֤��
//		if(!resultMap.get("code").equalsIgnoreCase(session.getAttribute("emailCode").toString())) {
//			model.addAttribute("msg", "������֤�����");
//			return "regist";
//		}
		
		
		if(userService.regist(user)>0) {
//			session.setAttribute("user", user);
			model.addAttribute("msg", "ע��ɹ�");
			return "regist";
		}else {
			model.addAttribute("msg", "ע��ʧ��");
			return "regist";
		}
	}
	
	@RequestMapping("/user/sendEmail")
    //ת��json����  @ResponseBody
    @ResponseBody
	public String sendEmail(String email) {
		HtmlEmail htmlEmail = new HtmlEmail();//����һ��HtmlEmailʵ������
		// ��ȡ�����֤��   
		String code = getCode();       
		try {    
			htmlEmail.setHostName("smtp.qq.com");	  		
			htmlEmail.setAuthentication("2745674345@qq.com", "gjewzkgopwfedhdh"); //��һ�������Ƿ����ߵ�QQEamil����   �ڶ��������Ǹոջ�ȡ����Ȩ��
   
			htmlEmail.setFrom("2745674345@qq.com", "JavaWeb������֤");//�����˵�����Ϊ�Լ��ģ��û������������
//			htmlEmail.setSmtpPort(465); 	//�˿ں� ���Բ���       
			htmlEmail.setSSLOnConnect(true); //����SSL����  
			htmlEmail.setCharset("utf-8");      
			htmlEmail.addTo(email);  //�����ռ���    emailΪ��Ҫ���͸�˭�������˻�
			htmlEmail.setSubject("JavaWeb������֤"); //�������  
			htmlEmail.setMsg("JavaWebע��ҳ��������֤��Ϊ��" + code); //Eamil���͵�����
			htmlEmail.send();  //����
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
