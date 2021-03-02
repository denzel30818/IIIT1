package tw.t1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.dao.ArticleRepository;
import tw.t1.entity.Article;
import tw.t1.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	@Override
	public Article findById(int id) {

		Optional<Article> result = articleRepository.findById(id);

		Article article = null;

		if (result.isPresent()) {
			article = result.get();
		} else {
			throw new RuntimeException("找不到id為" + id + "的文章");
		}
		return article;
	}

	@Override
	public List<Article> findAll() {
		return articleRepository.findAll();
	}

	@Override
	public Article save(Article article) {
		return articleRepository.save(article);
	}

	@Override
	public void deleteById(int id) {
		articleRepository.deleteById(id);
	}

	@Override
	public List<Article> findArticlesByLimit(int start, int end) {
		return articleRepository.findArticlesByLimit(start, end);
	}

	@Override
	public List<Article> findArticlesDescByLimit(int start, int end) {
		return articleRepository.findArticlesDescByLimit(start, end);
	}

	@Override
	public List<Article> findArticlesByUserID(int userID) {
		return articleRepository.findArticlesByUserID(userID);
	}

	@Override
	public long countByUserArticle(int userID) {
		return articleRepository.countByUserArticle(userID);
	}

	@Override
	public List<Article> findArticlesByUserIDForPage(int userID, int start, int end) {
		return articleRepository.findArticlesByUserIDForPage(userID, start, end);
	}

	@Override
	public long countBySearch(String title) {
		return articleRepository.countBySearch(title);
	}

	@Override
	public List<Article> findArticlesByDesc() {
		return articleRepository.findArticlesByDesc();
	}

	@Override
	public long countAllArticle() {
		return articleRepository.countAllArticle();
	}

	@Override
	public List<Article> findArticleByCategory(String category) {
		return articleRepository.findArticleByCategory(category);
	}

	@Override
	public List<Article> findArticleByForumName(String forumName) {
		return articleRepository.findArticleByForumName(forumName);
	}

	@Override
	public List<Article> findArticleByForumNameByDesc(String forumName) {
		return articleRepository.findArticleByForumNameByDesc(forumName);
	}

	@Override
	public List<Article> findArticlesByUserIDByDescForPage(int UserID, int start, int end) {
		return articleRepository.findArticlesByUserIDByDescForPage(UserID, start, end);
	}

	@Override
	public List<Article> findArticlesByUserIdandTitleWithLike(int UserID, String title) {
		return articleRepository.findArticlesByUserIdandTitleWithLike(UserID, title);
	}

	@Override
	public List<Article> findArticlesByTitleWithLike(String key) {
		return articleRepository.findArticlesByTitleWithLike(key);
	}

	@Override
	public List<Article> findArticleByForumNameByDescWithKey(String forumName, String key) {
		return articleRepository.findArticleByForumNameByDescWithKey(forumName, key);
	}

	@Override
	public List<Article> findArticleByForumNameByDescWithType(String forumName, String type) {
		return articleRepository.findArticleByForumNameByDescWithType(forumName, type);
	}

	@Override
	public List<Article> findArticleByDescWithType(String type) {
		return articleRepository.findArticleByDescWithType(type);
	}

}
