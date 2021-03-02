package tw.t1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.dao.NewsRepository;
import tw.t1.entity.News;
import tw.t1.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService {

	@Autowired
	private NewsRepository newsRepository;

	@Override
	public List<News> findAll() {

		return newsRepository.findAll();
	}

	@Override
	public News findById(int theId) {

		Optional<News> result = newsRepository.findById(theId);
		News news = null;
		if (result.isPresent()) {
			news = result.get();
		} else {
			// we didn't find the news
			throw new RuntimeException("Did not find news id - " + theId);
		}
		return news;
	}

	@Override
	public News save(News news) {
		newsRepository.save(news);
		return news;

	}

	@Override
	public void deleteById(int theId) {
		newsRepository.deleteById(theId);

	}

	@Override
	public List<News> findNewsByLimit(int start, int end) {
		return newsRepository.findNewsByLimit(start, end);
	}

	@Override
	public List<News> findNewsDescByLimit(int start, int end) {
		return newsRepository.findNewsDescByLimit(start, end);
	}

	@Override
	public List<News> findNewsByManagerName(String managerName) {
		return newsRepository.findNewsByManagerName(managerName);
	}

	@Override
	public long countByManagerNews(String managerName) {
		return newsRepository.countByManagerNews(managerName);
	}

	@Override
	public List<News> findNewsByManagerNameForPage(int managerName, int start, int end) {
		return newsRepository.findNewsByManagerNameForPage(managerName, start, end);
	}

	@Override
	public long countByTitleSearch(String title) {
		return newsRepository.countByTitleSearch(title);
	}

	@Override
	public List<News> findNewsByIdDesc() {
		return newsRepository.findNewsByIdDesc();
	}

	@Override
	public long countAllNews() {
		return newsRepository.countAllNews();
	}

	@Override
	public List<News> findNewsByType(String type) {
		return newsRepository.findNewsByType(type);
	}

	@Override
	public List<News> findNewsByTypeDesc(String type) {
		return newsRepository.findNewsByTypeDesc(type);
	}
	
	@Override
	public List<News> findNewsByTypeDescLimit(String type, int start, int end) {
		return newsRepository.findNewsByTypeDescLimit(type, start, end);
	}

	@Override
	public List<News> findAllAndOrderByUpdateTimeDesc() {
		return newsRepository.findAllAndOrderByUpdateTimeDesc();
	}
	
	@Override
	public List<News> findAllAndOrderByUpdateTimeDescTop100() {
		return newsRepository.findAllAndOrderByUpdateTimeDescTop100();
	}
	
	@Override
	public List<News> findNewsByTypeWithUpdateTime(String Type) {
		return newsRepository.findNewsByTypeWithUpdateTime(Type);
	}
	
	@Override
	public List<News> findNewsByTypeWithUpdateTimeTop100(String Type) {
		return newsRepository.findNewsByTypeWithUpdateTimeTop100(Type);
	}
	
	public List<News> findNewsByTypeAndDate(String Type,int News_year,int News_month) {
		return newsRepository.findNewsByTypeAndDate(Type,News_year,News_month);
	}

	@Override
	public List<News> findAllNewsByDate(int News_year, int News_month) {
		return newsRepository.findAllNewsByDate(News_year,News_month);
	}

	@Override
	public List<News> findAllAndOrderByUpdateTimeDescTop8() {
		return newsRepository.findAllAndOrderByUpdateTimeDescTop8();
	}

	@Override
	public List<News> searchNewsByKeyword(String Keyword) {
		return newsRepository.searchNewsByKeyword(Keyword);
	}

	@Override
	public List<News> searchNewsByKeywordAndType(String Type, String Keyword) {
		return newsRepository.searchNewsByKeywordAndType(Type, Keyword);
	}

	@Override
	public List<News> findNewsByLimitAndType(String type, int start, int end) {
		return newsRepository.findNewsByLimitAndType(type, start, end);
	}

}
