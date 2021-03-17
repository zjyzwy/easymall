package easymall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import easymall.dao.CartDao;
import easymall.po.Cart;
import easymall.pojo.MyCart;

@Service("cartService")
public class CartServiceImpl implements CartService {

	@Autowired
	private CartDao cartDao;
	@Override
	public int addCart(Cart cart) {
		// TODO �Զ����ɵķ������
		return cartDao.addCart(cart);
	}

	@Override
	public Cart findCart(Cart cart) {
		// TODO �Զ����ɵķ������
		return cartDao.findCart(cart);
	}

	@Override
	public int updateCart(Cart cart) {
		// TODO �Զ����ɵķ������
		return cartDao.updateCart(cart);
	}

	@Override
	public List<MyCart> showcart(int user_id) {
		// TODO �Զ����ɵķ������
		return cartDao.showcart(user_id);
	}

	@Override
	public void updateBuyNum(Cart cart) {
		// TODO �Զ����ɵķ������
		cartDao.updateBuyNum(cart);
	}

	@Override
	public void delCart(Integer cartID) {
		// TODO �Զ����ɵķ������
		cartDao.delCart(cartID);
	}

	@Override
	public MyCart findByCartID(Integer cartID) {
		// TODO �Զ����ɵķ������
		return cartDao.findByCartID(cartID);
	}

}
