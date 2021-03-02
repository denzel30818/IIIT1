package tw.t1.service;

import java.util.List;

import tw.t1.entity.NewsArchive;

public interface NewsArchiveService {

	public List<NewsArchive> findAll();

	public NewsArchive findById(int theId);

	public NewsArchive save(NewsArchive theNews);

	public void deleteById(int theId);

	public List<NewsArchive> findNewsByLimit(int start, int end);

	public List<NewsArchive> findNewsDescByLimit(int start, int end);

	public List<NewsArchive> findNewsByManagerName(String managerName);

	public long countByManagerNews(String managerName);

	public List<NewsArchive> findNewsByManagerNameForPage(int managerName, int start, int end);

	public long countByTitleSearch(String title);

	public List<NewsArchive> findNewsByIdDesc();

	public long countAllNews();

	public List<NewsArchive> findNewsByType(String type);
	
	public List<NewsArchive> findNewsByTypeDesc(String type);

	public List<NewsArchive> findNewsByTypeDescLimit(String type, int start, int end);
	
	public List<NewsArchive> findAllAndOrderByUpdateTimeDescTop50();
	
	public List<NewsArchive> findAllAndOrderByUpdateTimeDescTop100();
	
	public List<NewsArchive> findNewsByTypeWithUpdateTimeTop50(String Type);
	
	public List<NewsArchive> findNewsByTypeWithUpdateTimeTop100(String Type);
	
	public List<NewsArchive> findNewsByTypeAndDate(String Type,int News_year,int News_month);
	
	public List<NewsArchive> findAllNewsByDate(int News_year,int News_month);
	
	public List<NewsArchive> searchNewsByKeyword(String Keyword);
	
	public List<NewsArchive> searchNewsByKeywordAndType(String Type,String Keyword);
}
