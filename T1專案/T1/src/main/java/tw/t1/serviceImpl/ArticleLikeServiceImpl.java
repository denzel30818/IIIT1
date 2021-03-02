package tw.t1.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.dao.ArticleLikeRepository;
import tw.t1.entity.ArticleLike;
import tw.t1.service.ArticleLikeService;

@Service
public class ArticleLikeServiceImpl implements ArticleLikeService {

	@Autowired
	private ArticleLikeRepository alRep;

	@Override
	public List<ArticleLike> findAll() {

		return alRep.findAll();
	}

	@Override
	public ArticleLike findById(int Id) {
		return alRep.findArticlelikeById(Id);
	}

	@Override
	public void save(ArticleLike like) {
		alRep.save(like);

	}

	@Override
	public void deleteById(int Id) {
		alRep.deleteById(Id);

	}

	@Override
	public ArticleLike findLikeByUseridandArticleid(int user_id, int article_id) {
		return alRep.findLikeByUseridandArticleid(user_id, article_id);
	}

	@Override
	public int deleteLikeByUseridandArticleid(int user_id, int article_id) {
		return alRep.deleteLikeByUseridandArticleid(user_id, article_id);
	}

	@Override
	public int deleteLikeByArticleid(int article_id) {
		return alRep.deleteLikeByArticleid(article_id);
	}

}
