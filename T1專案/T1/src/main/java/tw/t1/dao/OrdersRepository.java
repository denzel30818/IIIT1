package tw.t1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tw.t1.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

	@Query(value = "SELECT * FROM orders ORDER BY id DESC", nativeQuery = true)
	public List<Orders> findAllByDESC();

	@Query(value = "SELECT * FROM orders WHERE user_id = ?1 and id = ?2", nativeQuery = true)
	public List<Orders> findByUser_idAndO_id(int uId, long oId);

	@Query(value = "SELECT * FROM orders WHERE user_id = ?1", nativeQuery = true)
	public List<Orders> findByUserId(int uId);

	@Query(value = "SELECT * FROM orders WHERE user_id = ?1 and order_time = ?2", nativeQuery = true)
	public List<Orders> findByUserIdAndOrderTime(int uId, String oTime);

	@Query(value = "SELECT * FROM orders WHERE user_id = ?1 ORDER BY id DESC", nativeQuery = true)
	public List<Orders> findByUserIdByDesc(int uId);
	
	@Query(value = "SELECT * FROM orders WHERE id = ?1", nativeQuery = true)
	public List<Orders> findByIdd(long id);

}
