package tw.t1.service;

import java.util.List;

import tw.t1.entity.Cart;

public interface CartService {

	public List<Cart> getCartList();

	public Cart findCartById(int id);

	public List<Cart> findByUser_idAndP_id(int uId, int pId);

	public List<Cart> findByUserId(int uId);

	public void save(Cart cart);

	public void deleteById(int cId);

	public int deleteByProduct_id(int Id);

	public List<Cart> findByP_id(int pId);
	
	public long countByUserid(int uId);
}
