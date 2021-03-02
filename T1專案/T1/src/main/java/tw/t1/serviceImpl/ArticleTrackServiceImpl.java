package tw.t1.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.dao.ArticleTrackRepository;
import tw.t1.entity.ArticleTrack;
import tw.t1.service.ArticleTrackService;

@Service
public class ArticleTrackServiceImpl implements ArticleTrackService {

	@Autowired
	private ArticleTrackRepository atRep;

	@Override
	public List<ArticleTrack> findAll() {
		return atRep.findAll();
	}

	@Override
	public ArticleTrack findById(int Id) {
		return atRep.findArticletrackById(Id);
	}

	@Override
	public void save(ArticleTrack track) {
		atRep.save(track);
	}

	@Override
	public void deleteById(int Id) {
		atRep.deleteById(Id);
	}

	@Override
	public List<ArticleTrack> findArticletrackByuserId(int user_id) {
		return atRep.findArticletrackByuserId(user_id);
	}

	@Override
	public ArticleTrack findTrackByUseridandArticleid(int user_id, int article_id) {
		return atRep.findTrackByUseridandArticleid(user_id, article_id);
	}

	@Override
	public int deleteTrackByUseridandArticleid(int user_id, int article_id) {
		return atRep.deleteTrackByUseridandArticleid(user_id, article_id);
	}

	@Override
	public List<ArticleTrack> findArticletrackByuserIdandOrderByIdDesc(int user_id) {
		return atRep.findArticletrackByuserIdandOrderByIdDesc(user_id);
	}

	@Override
	public int deleteTrackByArticleid(int article_id) {
		return atRep.deleteTrackByArticleid(article_id);
	}

	@Override
	public List<ArticleTrack> findArticleTrackByUserIdandTitleWithLikeDesc(int UserID, String title) {
		return atRep.findArticleTrackByUserIdandTitleWithLikeDesc(UserID, title);
	}
}
