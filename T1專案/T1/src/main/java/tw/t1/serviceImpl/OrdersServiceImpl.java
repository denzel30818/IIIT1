package tw.t1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.entity.Orders;
import tw.t1.service.OrdersService;
import tw.t1.dao.OrdersRepository;

@Service
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrdersRepository ordersRepository;

	@Override
	public List<Orders> findAllOrders() {
		return ordersRepository.findAll();
	}

	@Override
	public Orders findOrdersById(long oId) {
		Optional<Orders> result = ordersRepository.findById(oId);
		Orders orders = null;
		if (result.isPresent()) {
			orders = result.get();
		} else {
			throw new RuntimeException("找不到此ID" + oId);
		}
		return orders;
	}

	@Override
	public List<Orders> findByUser_idAndO_id(int uId, int oId) {
		return ordersRepository.findByUser_idAndO_id(uId, oId);
	}

	@Override
	public List<Orders> findByUserId(int uId) {
		return ordersRepository.findByUserId(uId);
	}

	@Override
	public List<Orders> findByUserIdAndOrderTime(int uId, String oTime) {
		return ordersRepository.findByUserIdAndOrderTime(uId, oTime);
	};

	@Override
	public void save(Orders orders) {
		ordersRepository.save(orders);
	}

	@Override
	public void deleteByOId(long oId) {
		ordersRepository.deleteById(oId);

	}

	@Override
	public List<Orders> findByUserIdByDesc(int uId) {
		return ordersRepository.findByUserIdByDesc(uId);
	}
	
	@Override
	public List<Orders> findAllByDESC(){
		return ordersRepository.findAllByDESC();
	};
	
	@Override
	public List<Orders> findByIdd(long id){
		return ordersRepository.findByIdd(id);
	};
}
