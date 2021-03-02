package tw.t1.service;

import java.util.List;

import tw.t1.entity.ArticleLike;

public interface ArticleLikeService {
	
	public List<ArticleLike> findAll();

	public ArticleLike findById(int Id);

	public void save(ArticleLike like);

	public void deleteById(int Id);
	
	public ArticleLike findLikeByUseridandArticleid(int user_id, int article_id);
	
	public int deleteLikeByUseridandArticleid(int user_id, int article_id);
	
	public int deleteLikeByArticleid(int article_id);
}
