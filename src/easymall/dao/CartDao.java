package easymall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import easymall.po.Cart;
import easymall.pojo.MyCart;
@Repository("cartDao")
@Mapper
public interface CartDao {
	//��ӹ��ﳵ
	public int addCart(Cart cart);
	//���ҹ��ﳵ�Ƿ���ڸ���Ʒ
	public Cart findCart(Cart cart);
	//�޸Ĺ��ﳵ��Ʒ����
	public int updateCart(Cart cart);
	//��ʾ���ﳵ
	public List<MyCart> showcart(int user_id);
	//�޸Ĺ��ﳵ��Ʒ����
	public void updateBuyNum(Cart cart);
	//ɾ�����ﳵ�е���Ʒ
	public void delCart(Integer cartID);
	//����cartID���ҹ��ﳵ
	public MyCart findByCartID(Integer cartID);
}
