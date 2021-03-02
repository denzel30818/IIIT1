package tw.t1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.dao.ProductCategoryRepository;
import tw.t1.entity.Productcategory;
import tw.t1.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	
	@Autowired
	private ProductCategoryRepository pcRep;
	
	
	
	
	public ProductCategoryServiceImpl(ProductCategoryRepository pcRep) {
		this.pcRep = pcRep;
	}
	

	@Override
	public List<Productcategory> findAll() {
		return pcRep.findAll();
	}

	@Override
	public Productcategory findById(int Id) {
		Optional<Productcategory> result = pcRep.findById(Id);
		Productcategory productcategory = null;
		if(result.isPresent()) {
			productcategory = result.get();
		}
		else {
			throw new RuntimeException("找不到此ID類型" + Id);
		}
				
		return productcategory;
	}

	@Override
	public void save(Productcategory productcate) {
		pcRep.save(productcate);
	}

	@Override
	public void deleteById(int Id) {
		pcRep.deleteById(Id);
	}


	@Override
	public Productcategory findByCategory(String cate) {
		return pcRep.findByCategory(cate);
	}

}
