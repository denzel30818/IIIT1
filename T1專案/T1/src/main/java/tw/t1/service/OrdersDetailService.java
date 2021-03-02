package tw.t1.service;

import java.util.List;

import tw.t1.entity.OrdersDetail;



public interface OrdersDetailService {

	public List<OrdersDetail> findAllDetail();
    public OrdersDetail findById(int id);
    public List<OrdersDetail> findByOrderId(long oId);
    public List<OrdersDetail> findByOrderIdAndId(long oId, int odId);
    public List<OrdersDetail> findByProductId(int p_id);
    
    public void save(OrdersDetail oDetail);
	public void deleteById(int odId);
	
}
