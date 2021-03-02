package tw.t1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.t1.dao.NewsArchiveRepository;
import tw.t1.entity.NewsArchive;
import tw.t1.service.NewsArchiveService;

@Service
public class NewsArchiveServiceImpl implements NewsArchiveService {

	@Autowired
	private NewsArchiveRepository newsArchiveRepository;

	@Override
	public List<NewsArchive> findAll() {

		return newsArchiveRepository.findAll();
	}

	@Override
	public NewsArchive findById(int theId) {

		Optional<NewsArchive> result = newsArchiveRepository.findById(theId);
		NewsArchive news = null;
		if (result.isPresent()) {
			news = result.get();
		} else {
			// we didn't find the news
			throw new RuntimeException("Did not find news id - " + theId);
		}
		return news;
	}

	@Override
	public NewsArchive save(NewsArchive news) {
		newsArchiveRepository.save(news);
		return news;

	}

	@Override
	public void deleteById(int theId) {
		newsArchiveRepository.deleteById(theId);

	}

	@Override
	public List<NewsArchive> findNewsByLimit(int start, int end) {
		return newsArchiveRepository.findNewsByLimit(start, end);
	}

	@Override
	public List<NewsArchive> findNewsDescByLimit(int start, int end) {
		return newsArchiveRepository.findNewsDescByLimit(start, end);
	}

	@Override
	public List<NewsArchive> findNewsByManagerName(String managerName) {
		return newsArchiveRepository.findNewsByManagerName(managerName);
	}

	@Override
	public long countByManagerNews(String managerName) {
		return newsArchiveRepository.countByManagerNews(managerName);
	}

	@Override
	public List<NewsArchive> findNewsByManagerNameForPage(int managerName, int start, int end) {
		return newsArchiveRepository.findNewsByManagerNameForPage(managerName, start, end);
	}

	@Override
	public long countByTitleSearch(String title) {
		return newsArchiveRepository.countByTitleSearch(title);
	}

	@Override
	public List<NewsArchive> findNewsByIdDesc() {
		return newsArchiveRepository.findNewsByIdDesc();
	}

	@Override
	public long countAllNews() {
		return newsArchiveRepository.countAllNews();
	}

	@Override
	public List<NewsArchive> findNewsByType(String type) {
		return newsArchiveRepository.findNewsByType(type);
	}

	@Override
	public List<NewsArchive> findNewsByTypeDesc(String type) {
		return newsArchiveRepository.findNewsByTypeDesc(type);
	}
	
	@Override
	public List<NewsArchive> findNewsByTypeDescLimit(String type, int start, int end) {
		return newsArchiveRepository.findNewsByTypeDescLimit(type, start, end);
	}

	@Override
	public List<NewsArchive> findAllAndOrderByUpdateTimeDescTop50() {
		return newsArchiveRepository.findAllAndOrderByUpdateTimeDescTop50();
	}
	
	@Override
	public List<NewsArchive> findAllAndOrderByUpdateTimeDescTop100() {
		return newsArchiveRepository.findAllAndOrderByUpdateTimeDescTop100();
	}
	
	@Override
	public List<NewsArchive> findNewsByTypeWithUpdateTimeTop50(String Type) {
		return newsArchiveRepository.findNewsByTypeWithUpdateTimeTop50(Type);
	}
	
	@Override
	public List<NewsArchive> findNewsByTypeWithUpdateTimeTop100(String Type) {
		return newsArchiveRepository.findNewsByTypeWithUpdateTimeTop100(Type);
	}
	
	public List<NewsArchive> findNewsByTypeAndDate(String Type,int News_year,int News_month) {
		return newsArchiveRepository.findNewsByTypeAndDate(Type,News_year,News_month);
	}

	@Override
	public List<NewsArchive> findAllNewsByDate(int News_year, int News_month) {
		return newsArchiveRepository.findAllNewsByDate(News_year,News_month);
	}
	
	@Override
	public List<NewsArchive> searchNewsByKeyword(String Keyword) {
		return newsArchiveRepository.searchNewsByKeyword(Keyword);
	}

	@Override
	public List<NewsArchive> searchNewsByKeywordAndType(String Type, String Keyword) {
		return newsArchiveRepository.searchNewsByKeywordAndType(Type, Keyword);
	}

}
