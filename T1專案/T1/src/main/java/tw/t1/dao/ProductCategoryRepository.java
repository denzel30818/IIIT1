package tw.t1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tw.t1.entity.Productcategory;

public interface ProductCategoryRepository extends JpaRepository<Productcategory, Integer> {

	@Query(value = "SELECT * FROM productcategory WHERE category = ?1", nativeQuery = true) 
	public Productcategory findByCategory(String cate);
}
