package tw.t1.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.dao.SupplierRepository;
import tw.t1.entity.Supplier;
import tw.t1.service.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService {
	
	@Autowired
	private SupplierRepository sRep;

	@Override
	public List<Supplier> findAll() {
		return sRep.findAll();
	}

	@Override
	public Supplier findById(int id) {
		return sRep.findSupplierById(id);
	}

	@Override
	public Supplier save(Supplier supplier) {
		return sRep.save(supplier);
	}

	@Override
	public void deleteById(int id) {
		sRep.deleteById(id);

	}

	@Override
	public long countAll() {
		return sRep.countAll();
	}

	@Override
	public List<Supplier> findBySupplierContaining(String supplier) {
		return sRep.findBySupplierContaining(supplier);
	}

	@Override
	public long countBySearch(String supplier) {
		return sRep.countBySearch(supplier);
	}

	@Override
	public Supplier findBySupplierIgnoreCase(String supplier) {
		return sRep.findBySupplierIgnoreCase(supplier);
	}

}
