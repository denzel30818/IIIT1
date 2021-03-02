package tw.t1.service;

import java.util.List;

import tw.t1.entity.ProductType;

public interface ProductTypeService {
	
	public List<ProductType> findAll();
	
	public ProductType findById(int Id);
	
	public void save(ProductType productType);
	
	public void deleteById(int Id);
	
	public List<ProductType> findByP_id(int p_id);
	
	public int deleteByP_id(int p_id);
	
	public List<ProductType> findByGameType(String gameType);
	
	public long countByGameType(String gameType);
}
