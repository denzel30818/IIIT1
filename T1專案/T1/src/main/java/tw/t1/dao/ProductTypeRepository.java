package tw.t1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import tw.t1.entity.ProductType;


public interface ProductTypeRepository extends JpaRepository<ProductType, Integer>{
	
	@Query(value = "SELECT * FROM ProductType WHERE p_id = ?1", nativeQuery = true)
	public List<ProductType> findByP_id(int p_id);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM ProductType WHERE p_id = ?1", nativeQuery = true)
	public int deleteByP_id(int p_id);
	
	@Query(value = "SELECT * FROM ProductType WHERE gametype = ?1", nativeQuery = true)
	public List<ProductType> findByGameType(String gameType);
	
	@Query(value = "SELECT COUNT(*) FROM ProductType WHERE gametype = ?1", nativeQuery = true)
	public long countByGameType(String gameType);
	
	
}
