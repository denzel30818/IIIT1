package tw.t1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import tw.t1.entity.ArticleLike;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Integer> {

	@Query(value = "SELECT * FROM articlelike where id = ?1", nativeQuery = true)
	public ArticleLike findArticlelikeById(int id);

	@Query(value = "SELECT * FROM articlelike where user_id = ?1 and article_id= ?2", nativeQuery = true)
	public ArticleLike findLikeByUseridandArticleid(int user_id, int article_id);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM articlelike where user_id = ?1 and article_id= ?2", nativeQuery = true)
	public int deleteLikeByUseridandArticleid(int user_id, int article_id);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM articlelike where article_id = ?1", nativeQuery = true)
	public int deleteLikeByArticleid(int article_id);

}
