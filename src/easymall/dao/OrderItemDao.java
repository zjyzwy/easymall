package easymall.dao;

import java.util.List;

import easymall.po.OrderItem;

public interface OrderItemDao {
	//添加订单 商品信息
	void addOrderItem(OrderItem orderItem);
	//显示订单 商品信息
	List<OrderItem> orderitem(String order_id);
	public void delorderitem(String id);
}
