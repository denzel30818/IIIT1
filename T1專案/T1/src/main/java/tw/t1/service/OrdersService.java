package tw.t1.service;

import java.util.List;

import tw.t1.entity.Orders;



public interface OrdersService {
	public List<Orders> findAllOrders();
	
    public Orders findOrdersById(long oId);
    
    public List<Orders> findByUser_idAndO_id(int uId, int oId);
    
    public List<Orders> findByUserId(int uId);
    
    public List<Orders> findByUserIdAndOrderTime(int uId, String oTime);
    
    public void save(Orders orders);

    public void deleteByOId(long oId);
    
    public List<Orders> findByUserIdByDesc(int uId);
    
    public List<Orders> findAllByDESC();
    
    public List<Orders> findByIdd(long id);

}
