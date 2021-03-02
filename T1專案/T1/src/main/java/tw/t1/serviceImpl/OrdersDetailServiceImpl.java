package tw.t1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.entity.OrdersDetail;
import tw.t1.service.OrdersDetailService;
import tw.t1.dao.OrdersDetailRepository;


@Service
public class OrdersDetailServiceImpl implements OrdersDetailService {
	
	@Autowired
	private OrdersDetailRepository odRepo;
	
	@Override
	public List<OrdersDetail> findAllDetail() {
		return odRepo.findAll();
	}

	@Override
	public OrdersDetail findById(int id) {
		Optional<OrdersDetail> result = odRepo.findById(id);
		OrdersDetail od = null ;
		if(result.isPresent()) {
			od = result.get();
		}
		else {
			throw new RuntimeException("找不到此ID" + id);
		}
		return od;
	}

	@Override
	public List<OrdersDetail> findByOrderId(long oId) {
		
		return odRepo.findByOrderId(oId);
	}
	
	@Override
	public List<OrdersDetail> findByProductId(int p_id){
		return odRepo.findByProductId(p_id);
	};

	@Override
	public List<OrdersDetail> findByOrderIdAndId(long oId, int odId) {
		return odRepo.findByOrderIdAndId(oId, odId);
	}

	@Override
	public void save(OrdersDetail oDetail) {
		odRepo.save(oDetail);
	}

	@Override
	public void deleteById(int odId) {
		
		odRepo.deleteById(odId);
	}

}
