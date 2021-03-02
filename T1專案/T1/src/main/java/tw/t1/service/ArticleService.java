package tw.t1.service;

import java.util.List;

import tw.t1.entity.Article;

public interface ArticleService {

	public Article findById(int id);

	public void deleteById(int id);

	public List<Article> findAll();

	public Article save(Article article);

	public List<Article> findArticlesDescByLimit(int start, int end);

	public List<Article> findArticlesByLimit(int start, int end);

	public List<Article> findArticlesByUserID(int userID);

	public long countByUserArticle(int userID);

	public List<Article> findArticlesByUserIDForPage(int userID, int start, int end);

	public long countBySearch(String title);

	public List<Article> findArticlesByDesc();

	public long countAllArticle();

	public List<Article> findArticleByCategory(String category);

	public List<Article> findArticleByForumName(String forumName);

	public List<Article> findArticleByForumNameByDesc(String forumName);

	public List<Article> findArticlesByUserIDByDescForPage(int UserID, int start, int end);

	public List<Article> findArticlesByUserIdandTitleWithLike(int UserID, String title);

	public List<Article> findArticlesByTitleWithLike(String key);

	public List<Article> findArticleByForumNameByDescWithKey(String forumName, String key);

	public List<Article> findArticleByForumNameByDescWithType(String forumName, String type);

	public List<Article> findArticleByDescWithType(String type);
}
