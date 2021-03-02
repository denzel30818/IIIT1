package tw.t1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tw.t1.entity.OrdersDetail;

public interface OrdersDetailRepository extends JpaRepository<OrdersDetail, Integer> {

	@Query(value = "SELECT * FROM orderdetail WHERE order_id = ?1", nativeQuery = true) 
	public List<OrdersDetail> findByOrderId(long oId);
	
	@Query(value = "SELECT * FROM orderdetail WHERE order_id = ?1 and id = ?2", nativeQuery = true)
	public List<OrdersDetail> findByOrderIdAndId(long oId, int odId); 
	
	@Query(value = "SELECT * FROM orderdetail WHERE product_id = ?1 ", nativeQuery = true)
	public List<OrdersDetail> findByProductId(int p_id);
	
}
