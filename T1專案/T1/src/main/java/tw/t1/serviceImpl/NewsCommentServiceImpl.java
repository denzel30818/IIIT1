package tw.t1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.dao.NewsCommentRepository;
import tw.t1.entity.News;
import tw.t1.entity.NewsComment;
import tw.t1.service.NewsCommentService;

@Service
public class NewsCommentServiceImpl implements NewsCommentService {

	@Autowired
	private NewsCommentRepository newsCommentRepository;
	
	@Override
	public List<NewsComment> getAllCommendByNews(News news) {
		return newsCommentRepository.findByNews(news);
	}

	@Override
	public NewsComment save(NewsComment newscomment) {
		newsCommentRepository.save(newscomment);
		return newscomment;
	}

	@Override
	public void deleteById(int newsCommentId) {
		newsCommentRepository.deleteById(newsCommentId);
	}
	
	@Override
	public NewsComment findById(int id) {

		Optional<NewsComment> result = newsCommentRepository.findById(id);

		NewsComment comment = null;

		if (result.isPresent()) {
			comment = result.get();
		} else {
			throw new RuntimeException("找不到id為" + id + "的新聞");
		}
		return comment;

	}

}
