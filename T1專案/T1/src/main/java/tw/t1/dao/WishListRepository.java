package tw.t1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import tw.t1.entity.WishList;

public interface WishListRepository extends JpaRepository<WishList, Integer> {

	@Query(value = "SELECT * FROM WishList WHERE user_id = ?1", nativeQuery = true)
	public List<WishList> findByUser_id(int id);

	@Modifying
	@Transactional
	@Query(value = "SELECT * FROM WishList WHERE user_id = ?1 and p_id = ?2", nativeQuery = true)
	public List<WishList> findByUser_idAndP_id(int uId, int pId);
	
	@Modifying
	@Transactional
	@Query(value = "SELECT * FROM WishList WHERE p_id = ?1", nativeQuery = true)
	public List<WishList> findByP_id(int pId);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM WishList WHERE user_id = ?1 and p_id = ?2", nativeQuery = true)
	public void deleteByUser_idAndP_id(int uId, int pId);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM WishList WHERE p_id = ?1", nativeQuery = true) 
	public void deleteByP_id(int pId);

}
