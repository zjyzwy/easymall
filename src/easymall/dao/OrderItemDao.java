package easymall.dao;

import java.util.List;

import easymall.po.OrderItem;

public interface OrderItemDao {
	//��Ӷ��� ��Ʒ��Ϣ
	void addOrderItem(OrderItem orderItem);
	//��ʾ���� ��Ʒ��Ϣ
	List<OrderItem> orderitem(String order_id);
	public void delorderitem(String id);
}
