package tw.t1.service;

import java.util.List;

import tw.t1.entity.News;
import tw.t1.entity.NewsComment;

public interface NewsCommentService {
	public List<NewsComment> getAllCommendByNews(News news);

	public NewsComment save(NewsComment newscomment);

	public void deleteById(int newsCommentId);
	
	public NewsComment findById(int id);
}
