package tw.t1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.dao.ProductTypeRepository;
import tw.t1.entity.ProductType;
import tw.t1.service.ProductTypeService;
@Service
public class ProductTypeServiceImpl implements ProductTypeService {
	
	@Autowired
	private ProductTypeRepository pRep;
	
	public  ProductTypeServiceImpl(ProductTypeRepository pRep) {
		this.pRep=pRep;
	}
	
	
	@Override
	public List<ProductType> findAll() {
		return pRep.findAll();
	}

	@Override
	public ProductType findById(int Id) {
		Optional<ProductType> result = pRep.findById(Id);
		ProductType productType = null;
		if(result.isPresent()) {
			productType = result.get();
		}
		else {
			throw new RuntimeException("找不到此ID" + Id);
		}
		
		return productType;
	}

	@Override
	public void save(ProductType productType) {
		pRep.save(productType);

	}

	@Override
	public void deleteById(int Id) {
		pRep.deleteById(Id);

	}


	@Override
	public List<ProductType> findByP_id(int p_id) {
		return pRep.findByP_id(p_id);
	}


	@Override
	public int deleteByP_id(int p_id) {
		return pRep.deleteByP_id(p_id);
	}


	@Override
	public List<ProductType> findByGameType(String gameType) {
		return pRep.findByGameType(gameType);
	}


	@Override
	public long countByGameType(String gameType) {
		return pRep.countByGameType(gameType);
	}

}
