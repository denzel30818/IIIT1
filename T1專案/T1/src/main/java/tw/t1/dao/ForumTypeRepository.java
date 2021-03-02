package tw.t1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tw.t1.entity.ForumType;

public interface ForumTypeRepository extends JpaRepository<ForumType, Integer> {

	// 搜尋論壇大分類項目
	@Query(value = "SELECT * FROM forumtype where category = ?1", nativeQuery = true)
	public List<ForumType> findByCategory(String category);

	// 計算指定論壇大分類數量
	@Query(value = "SELECT count(*) FROM forumtype where forum_name = ?1", nativeQuery = true)
	public long countByForumName(String forum_name);

	// 搜尋討論版
	@Query(value = "SELECT * FROM forumtype WHERE UPPER(forum_name) LIKE UPPER(CONCAT('%',?1,'%'))", nativeQuery = true)
	public List<ForumType> searchForumByForumName(String forum_name);

}
