package tw.t1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import tw.t1.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	// 尋找指定文章留言
	@Query(value = "SELECT * FROM comment WHERE art_id = ?1", nativeQuery = true)
	public List<Comment> findCommentByArticleId(int id);

	// 刪除指定文章全部留言
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM comment WHERE art_id = ?1",nativeQuery = true)
	public void deleteCommentByArticleId(int id);

}
