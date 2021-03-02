package tw.t1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tw.t1.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

	public Supplier findSupplierById(int id);

	@Query(value = "SELECT COUNT(*) FROM supplier", nativeQuery = true)
	public long countAll();

	// 搜尋供應商
	@Query(value = "SELECT * FROM supplier WHERE UPPER(supplier) LIKE UPPER(CONCAT('%',?1,'%'))", nativeQuery = true)
	public List<Supplier> findBySupplierContaining(String supplier);

	// 搜尋總數
	@Query(value = "SELECT COUNT(*) FROM supplier WHERE UPPER(supplier) LIKE UPPER(CONCAT('%',?1,'%'))", nativeQuery = true)
	public long countBySearch(String supplier);
	
	public Supplier findBySupplierIgnoreCase(String supplier);

}
