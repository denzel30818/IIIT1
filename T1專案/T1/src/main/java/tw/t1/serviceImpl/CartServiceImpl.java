package tw.t1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.entity.Cart;
import tw.t1.service.CartService;
import tw.t1.dao.CartRepository;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartRepository cartRepo;
	
	
	@Override
	public List<Cart> getCartList() {
		
		return cartRepo.findAll();
	}

	@Override
	public Cart findCartById(int id) {
		Optional<Cart> result = cartRepo.findById(id);
		Cart cart = null ;
		if(result.isPresent()) {
			cart = result.get();
		}
		else {
			throw new RuntimeException("找不到此ID" + id);
		}
		
		return cart ;
	}
	 @Override
	 public void save(Cart cart) {
		 
		 cartRepo.save(cart);
	 };
	
	@Override
	public List<Cart> findByUser_idAndP_id(int uId, int pId) {		
		return cartRepo.findByUser_idAndP_id(uId, pId);
	}

	@Override
	public List<Cart> findByUserId(int uId){
		return cartRepo.findByUserid(uId);
	};
	

	
	@Override
	public void deleteById(int cId) {
		cartRepo.deleteById(cId);
	}

	@Override
	public int deleteByProduct_id(int Id) {
		return cartRepo.deleteByProduct_id(Id);
	}

	@Override
	public List<Cart> findByP_id(int pId) {
		return cartRepo.findByP_id(pId);
	}

	@Override
	public long countByUserid(int uId) {
		return cartRepo.countByUserid(uId);
	};
}
