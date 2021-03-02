package tw.t1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.dao.WishListRepository;
import tw.t1.entity.WishList;
import tw.t1.service.WishListService;

@Service
public class WishListServiceImpl implements WishListService {
	
	
	private WishListRepository wRep;
	
	public WishListServiceImpl() {}
	
	@Autowired
	public WishListServiceImpl(WishListRepository wRep) {
		this.wRep=wRep;
	}

	@Override
	public void deleteById(int Id) {
		wRep.deleteById(null);

	}

	@Override
	public WishList findById(int Id) {
		Optional<WishList> result = wRep.findById(Id);
		WishList wishList = null;
		if(result.isPresent()) {
			wishList = result.get();
		}
		else {
			throw new RuntimeException("找不到此ID的wishList" + Id);
		}
		
		return wishList;
		
	}

	@Override
	public void save(WishList wishList) {
		wRep.save(wishList);
		
	}

	@Override
	public List<WishList> findAll() {
		
		return wRep.findAll();
	}

	@Override
	public List<WishList> findByUser_id(int id) {
		
		return wRep.findByUser_id(id);
	}

	@Override
	public List<WishList> findByUser_idAndP_id(int uId, int pId) {		
		return wRep.findByUser_idAndP_id(uId, pId);
	}

	@Override
	public void deleteByUser_idAndP_id(int uId, int pId) {
		wRep.deleteByUser_idAndP_id(uId, pId);
		
	}

	@Override
	public void deleteByP_id(int pId) {
		wRep.deleteByP_id(pId);
	}

	@Override
	public List<WishList> findByP_id(int pId) {
		return wRep.findByP_id(pId);
	}
	
	

}
