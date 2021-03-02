package tw.t1.service;

import java.util.List;

import tw.t1.entity.News;

public interface NewsService {

	public List<News> findAll();

	public News findById(int theId);

	public News save(News theNews);

	public void deleteById(int theId);

	public List<News> findNewsByLimit(int start, int end);
	
	public List<News> findNewsByLimitAndType(String type,int start, int end);

	public List<News> findNewsDescByLimit(int start, int end);

	public List<News> findNewsByManagerName(String managerName);

	public long countByManagerNews(String managerName);

	public List<News> findNewsByManagerNameForPage(int managerName, int start, int end);

	public long countByTitleSearch(String title);

	public List<News> findNewsByIdDesc();

	public long countAllNews();

	public List<News> findNewsByType(String type);
	
	public List<News> findNewsByTypeDesc(String type);

	public List<News> findNewsByTypeDescLimit(String type, int start, int end);
	
	public List<News> findAllAndOrderByUpdateTimeDesc();
	
	public List<News> findAllAndOrderByUpdateTimeDescTop100();
	
	public List<News> findNewsByTypeWithUpdateTime(String Type);
	
	public List<News> findNewsByTypeWithUpdateTimeTop100(String Type);
	
	public List<News> findNewsByTypeAndDate(String Type,int News_year,int News_month);
	
	public List<News> findAllNewsByDate(int News_year,int News_month);
	
	public List<News> findAllAndOrderByUpdateTimeDescTop8();
	
	public List<News> searchNewsByKeyword(String Keyword);
	
	public List<News> searchNewsByKeywordAndType(String Type,String Keyword);
	
}
