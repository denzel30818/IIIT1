package tw.t1.service;

import java.util.List;


import tw.t1.entity.Supplier;

public interface SupplierService {

	public List<Supplier> findAll();

	public Supplier findById(int id);

	public Supplier save(Supplier supplier);

	public void deleteById(int id);

	public long countAll();

	public List<Supplier> findBySupplierContaining(String supplier);

	public long countBySearch(String supplier);
	
	public Supplier findBySupplierIgnoreCase(String supplier);

}
