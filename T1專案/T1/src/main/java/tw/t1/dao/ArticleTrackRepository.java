package tw.t1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import tw.t1.entity.ArticleTrack;

public interface ArticleTrackRepository extends JpaRepository<ArticleTrack, Integer> {

	@Query(value = "SELECT * FROM articletrack where id = ?1", nativeQuery = true)
	public ArticleTrack findArticletrackById(int id);

	@Query(value = "SELECT * FROM articletrack where user_id = ?1", nativeQuery = true)
	public List<ArticleTrack> findArticletrackByuserId(int user_id);

	@Query(value = "SELECT * FROM articletrack where user_id = ?1 and article_id= ?2", nativeQuery = true)
	public ArticleTrack findTrackByUseridandArticleid(int user_id, int article_id);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM articletrack where user_id = ?1 and article_id= ?2", nativeQuery = true)
	public int deleteTrackByUseridandArticleid(int user_id, int article_id);

	@Query(value = "SELECT * FROM articletrack where user_id = ?1 ORDER BY id DESC", nativeQuery = true)
	public List<ArticleTrack> findArticletrackByuserIdandOrderByIdDesc(int user_id);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM articletrack where article_id = ?1", nativeQuery = true)
	public int deleteTrackByArticleid(int article_id);

	// 找尋特定使用者追蹤文章 ( 限定10筆 )
	@Query(value = "SELECT * FROM articletrack where user_id = ?1 and UPPER(article_title) LIKE UPPER(CONCAT('%',?2,'%')) ORDER BY article_id DESC LIMIT 0,10", nativeQuery = true)
	public List<ArticleTrack> findArticleTrackByUserIdandTitleWithLikeDesc(int UserID, String title);
}
