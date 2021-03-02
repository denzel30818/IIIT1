package tw.t1.service;

import java.util.List;

import tw.t1.entity.ArticleTrack;

public interface ArticleTrackService {
	
	public List<ArticleTrack> findAll();

	public ArticleTrack findById(int Id);

	public void save(ArticleTrack track);

	public void deleteById(int Id);
	
	public List<ArticleTrack> findArticletrackByuserId(int user_id);
	
	public ArticleTrack findTrackByUseridandArticleid(int user_id, int article_id);
	
	public int deleteTrackByUseridandArticleid(int user_id, int article_id);
	
	public List<ArticleTrack> findArticletrackByuserIdandOrderByIdDesc(int user_id);
	
	public int deleteTrackByArticleid(int article_id);
	
	public List<ArticleTrack> findArticleTrackByUserIdandTitleWithLikeDesc(int UserID, String title);
}

