package easymall.dao;

import java.util.List;

import easymall.po.Orders;

public interface OrderDao {
	//添加订单
	void addOrder(Orders myOrder);
	//显示订单
	List<Orders> findOrderByUserId(Integer user_id);
	public void delorder(String id);
	public void payorder(String id);
	public void sendorder(String id);
	public void checkorder(String id);
	public List<Orders> findAllOrders();
}
