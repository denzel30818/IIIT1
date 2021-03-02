package tw.t1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tw.t1.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

	// 找尋限定範圍內的文章 ( 不指定ID搜尋 )
	@Query(value = "SELECT * FROM article LIMIT ?1,?2", nativeQuery = true)
	public List<Article> findArticlesByLimit(int start, int end);

	// 找尋限定範圍內的文章 ( 不指定ID搜尋，且為倒序 )
	@Query(value = "SELECT * FROM article ORDER BY art_id DESC LIMIT ?1,?2", nativeQuery = true)
	public List<Article> findArticlesDescByLimit(int start, int end);

	// 找指定使用者發的全部文章
	@Query(value = "SELECT * FROM article where userID = ?1", nativeQuery = true)
	public List<Article> findArticlesByUserID(int userID);

	// 計算特定使用者發文數量
	@Query(value = "SELECT COUNT(*) FROM article where userID = ?1", nativeQuery = true)
	public long countByUserArticle(int UserID);

	// 找尋特定使用者文章 ( 限定範圍搜尋 )
	@Query(value = "SELECT * FROM article where UPPER(UserID) LIKE UPPER(CONCAT('%',?1,'%')) LIMIT ?2,?3", nativeQuery = true)
	public List<Article> findArticlesByUserIDForPage(int UserID, int start, int end);

	// 全域搜尋文章 ( 依名稱 )
	@Query(value = "SELECT COUNT(*) FROM article where UPPER(title) LIKE UPPER(CONCAT('%',?1,'%'))", nativeQuery = true)
	public long countBySearch(String title);

	// 找尋全部文章 ( 倒序 )
	@Query(value = "SELECT * FROM article ORDER BY update_millisecond DESC", nativeQuery = true)
	public List<Article> findArticlesByDesc();

	// 計算全部文章數量
	@Query(value = "SELECT COUNT(*) FROM article", nativeQuery = true)
	public long countAllArticle();

	// 找指定論壇大類的文章
	@Query(value = "SELECT * FROM article where category = ?1", nativeQuery = true)
	public List<Article> findArticleByCategory(String category);

	// 找指定論壇小類的文章
	@Query(value = "SELECT * FROM article where forum_name = ?1", nativeQuery = true)
	public List<Article> findArticleByForumName(String forumName);

	// 找指定論壇小類的文章 ( 倒序 )
	@Query(value = "SELECT * FROM article where forum_name = ?1 ORDER BY update_millisecond DESC", nativeQuery = true)
	public List<Article> findArticleByForumNameByDesc(String forumName);

	// 找尋特定使用者文章 ( 限定範圍搜尋 )
	@Query(value = "SELECT * FROM article where UPPER(UserID) LIKE UPPER(CONCAT('%',?1,'%')) ORDER BY update_millisecond DESC LIMIT ?2,?3", nativeQuery = true)
	public List<Article> findArticlesByUserIDByDescForPage(int UserID, int start, int end);

	// 找尋特定使用者文章 ( 限定10筆 )
	@Query(value = "SELECT * FROM article where userID = ?1 and UPPER(title) LIKE UPPER(CONCAT('%',?2,'%')) LIMIT 0,10", nativeQuery = true)
	public List<Article> findArticlesByUserIdandTitleWithLike(int UserID, String title);

	// 依標題找尋篩選過的文章 ( 倒序 )
	@Query(value = "SELECT * FROM article where UPPER(title) LIKE UPPER(CONCAT('%',?1,'%')) ORDER BY update_millisecond DESC", nativeQuery = true)
	public List<Article> findArticlesByTitleWithLike(String key);

	// 找指定論壇小類的文章 ( 倒序 )
	@Query(value = "SELECT * FROM article where forum_name = ?1 AND UPPER(title) LIKE UPPER(CONCAT('%',?2,'%')) ORDER BY update_millisecond DESC", nativeQuery = true)
	public List<Article> findArticleByForumNameByDescWithKey(String forumName, String key);

	// 依論壇名稱和類別找指定論壇小類的文章 ( 倒序 )
	@Query(value = "SELECT * FROM article where forum_name = ?1 AND art_type = ?2 ORDER BY update_millisecond DESC", nativeQuery = true)
	public List<Article> findArticleByForumNameByDescWithType(String forumName, String type);

	// 依類別找指定論壇小類的文章 ( 倒序 )
	@Query(value = "SELECT * FROM article where art_type = ?1 ORDER BY update_millisecond DESC", nativeQuery = true)
	public List<Article> findArticleByDescWithType(String type);
}
