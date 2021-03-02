package tw.t1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.t1.entity.News;
import tw.t1.entity.NewsComment;



public interface NewsCommentRepository extends JpaRepository<NewsComment, Integer> {

	public List<NewsComment> findByNews(News news);
}
