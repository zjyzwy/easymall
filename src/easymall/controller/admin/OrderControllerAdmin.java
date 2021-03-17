package easymall.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import easymall.po.OrderItem;
import easymall.po.Orders;
import easymall.po.Products;
import easymall.po.User;
import easymall.pojo.OrderInfo;
import easymall.service.OrderService;
import easymall.service.ProductsService;
import easymall.service.UserService;

@Controller("orderCntroller")
@RequestMapping("/admin")
public class OrderControllerAdmin extends AdminBaseController{
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductsService productsService;
	@Autowired
	private UserService userService;
	
	@RequestMapping("/showorder")
	public String showorder(HttpSession session,Model model) {
		List<OrderInfo> orderInfoList = findAllOrders();
		model.addAttribute("orderInfos",orderInfoList);
		return "admin/order_list";
	}
	
	@RequestMapping("/sendorder")
	public String sendorder(String id) {
		orderService.sendorder(id);
		return "redirect:/admin/showorder";
	}
	
	private List<OrderInfo> findAllOrders(){
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		List<Orders> orderList = orderService.findAllOrders();
		for(Orders order : orderList) {
			List<OrderItem> orderitems = orderService.orderitem(order.getId());
			Map<Products,Integer> map = new HashMap<Products,Integer>();
			for(OrderItem orderItem : orderitems) {
				Products product = productsService.oneProduct(orderItem.getProduct_id());
				map.put(product, orderItem.getBuynum());
			}
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrder(order);
			orderInfo.setMap(map);
			orderInfo.setUsername(userService.findUsername(order.getUser_id()));
			orderInfoList.add(orderInfo);
		}
		return orderInfoList;
	}
}
