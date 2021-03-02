package tw.t1.service;

import java.util.List;

import tw.t1.entity.Productcategory;

public interface ProductCategoryService {
	
	public List<Productcategory> findAll();

	public Productcategory findById(int Id);

	public void save(Productcategory productcate);

	public void deleteById(int Id);
	
	public Productcategory findByCategory(String cate);
}
