package tw.t1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import tw.t1.entity.Cart;


public interface CartRepository extends JpaRepository<Cart, Integer> {
	@Query(value = "SELECT * FROM cart WHERE user_id = ?1 and product_id = ?2", nativeQuery = true) 
	public List<Cart> findByUser_idAndP_id(int uId,int pId);
	
	@Modifying
	@Transactional
	@Query(value = "SELECT * FROM cart WHERE user_id = ?1", nativeQuery = true) 
	public List<Cart> findByUserid(int uId);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE  FROM cart WHERE product_id = ?1", nativeQuery = true) 
	public int deleteByProduct_id(int Id);

	@Modifying
	@Transactional
	@Query(value = "SELECT * FROM cart WHERE product_id = ?1", nativeQuery = true) 
	public List<Cart> findByP_id(int pId);
	
	@Query(value = "SELECT count(*) FROM cart WHERE user_id = ?1", nativeQuery = true) 
	public long countByUserid(int uId);
}
