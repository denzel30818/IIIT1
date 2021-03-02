package tw.t1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tw.t1.entity.NewsArchive;

public interface NewsArchiveRepository extends JpaRepository<NewsArchive, Integer> {

	// 找尋限定範圍內的新聞 ( 不指定ID搜尋 )
	@Query(value = "SELECT * FROM newsarchive LIMIT ?1,?2", nativeQuery = true)
	public List<NewsArchive> findNewsByLimit(int start, int end);

	// 找尋限定範圍內的新聞 ( 不指定ID搜尋，且為倒序 )
	@Query(value = "SELECT * FROM newsarchive ORDER BY newsArchive_id DESC LIMIT ?1,?2", nativeQuery = true)
	public List<NewsArchive> findNewsDescByLimit(int start, int end);

	// 找尋指定管理者發的全部新聞
	@Query(value = "SELECT * FROM newsarchive WHERE newsArchive_manager = ?1", nativeQuery = true)
	public List<NewsArchive> findNewsByManagerName(String managerName);

	// 計算指定管理者發佈新聞數量
	@Query(value = "SELECT COUNT(*) FROM newsarchive WHERE newsArchive_manager = ?1", nativeQuery = true)
	public long countByManagerNews(String managerName);

	// 找尋特定管理者新聞 ( 限定範圍搜尋 )
	@Query(value = "SELECT * FROM newsarchive WHERE UPPER(managerName) LIKE UPPER(CONCAT('%',?1,'%')) LIMIT ?2,?3", nativeQuery = true)
	public List<NewsArchive> findNewsByManagerNameForPage(int managerName, int start, int end);

	// 全域搜尋新聞 ( 依搜尋的字 )
	@Query(value = "SELECT COUNT(*) FROM newsarchive WHERE UPPER(title) LIKE UPPER(CONCAT('%',?1,'%'))", nativeQuery = true)
	public long countByTitleSearch(String title);

	// 找尋全部新聞 ( 倒序 )
	@Query(value = "SELECT * FROM newsarchive ORDER BY newsArchive_id DESC", nativeQuery = true)
	public List<NewsArchive> findNewsByIdDesc();

	// 計算全部新聞數量
	@Query(value = "SELECT COUNT(*) FROM newsarchive", nativeQuery = true)
	public long countAllNews();

	// 找尋特定類別的新聞
	@Query(value = "SELECT * FROM newsarchive WHERE newsArchive_type = ?1", nativeQuery = true)
	public List<NewsArchive> findNewsByType(String type);

	// 找尋特定類別的新聞 ( 倒序 )
	@Query(value = "SELECT * FROM newsarchive WHERE newsArchive_type = ?1 ORDER BY newsArchive_id DESC", nativeQuery = true)
	public List<NewsArchive> findNewsByTypeDesc(String type);

	// 找尋特定類別的新聞 ( 倒序 )
	@Query(value = "SELECT * FROM newsarchive WHERE newsArchive_type = ?1 ORDER BY newsArchive_id DESC LIMIT ?2,?3", nativeQuery = true)
	public List<NewsArchive> findNewsByTypeDescLimit(String type, int start, int end);

	// 全部新聞依時間排序前50個
	@Query(value = "SELECT * FROM newsarchive order by newsArchive_updateTime desc limit 50;", nativeQuery = true)
	public List<NewsArchive> findAllAndOrderByUpdateTimeDescTop50();
	
	// 全部新聞依時間排序前100個
	@Query(value = "SELECT * FROM newsarchive order by newsArchive_updateTime desc limit 100;", nativeQuery = true)
	public List<NewsArchive> findAllAndOrderByUpdateTimeDescTop100();

	// 新聞頁籤依新聞類別及時間排序前50個
	@Query(value = "SELECT * FROM newsarchive where newsArchive_type = ?1 order by newsArchive_updateTime desc limit 50;", nativeQuery = true)
	public List<NewsArchive> findNewsByTypeWithUpdateTimeTop50(String Type);
	
	// 新聞頁籤依新聞類別及時間排序前100個
	@Query(value = "SELECT * FROM newsarchive where newsArchive_type = ?1 order by newsArchive_updateTime desc limit 100;", nativeQuery = true)
	public List<NewsArchive> findNewsByTypeWithUpdateTimeTop100(String Type);

	// 依年月搜尋特定類別新聞
	@Query(value = "SELECT * FROM newsarchive where newsArchive_type = ?1 and year(newsArchive_updateTime)= ?2 and month(newsArchive_updateTime)= ?3 order by newsArchive_updateTime desc;",nativeQuery = true)
	public List<NewsArchive> findNewsByTypeAndDate(String Type,int News_year,int News_month);
	
	// 依年月搜尋特定類別新聞
	@Query(value = "SELECT * FROM newsarchive where year(newsArchive_updateTime)= ?1 and month(newsArchive_updateTime)= ?2 order by newsArchive_updateTime desc;",nativeQuery = true)
	public List<NewsArchive> findAllNewsByDate(int News_year,int News_month);
	
	//搜尋新聞關鍵字
	@Query(value = "SELECT * FROM newsarchive WHERE UPPER(newsArchive_title) LIKE UPPER(CONCAT('%',?1,'%')) or UPPER(newsArchive_article) LIKE UPPER(CONCAT('%',?1,'%')) order by newsArchive_updateTime desc;", nativeQuery = true)
	public List<NewsArchive> searchNewsByKeyword(String Keyword);
	
	//搜尋各類別新聞關鍵字
	@Query(value = "SELECT * FROM newsarchive WHERE newsArchive_type= ?1 and UPPER(newsArchive_title) LIKE UPPER(CONCAT('%',?2,'%')) or newsArchive_type= ?1 and UPPER(newsArchive_article) LIKE UPPER(CONCAT('%',?2,'%')) order by newsArchive_updateTime desc;", nativeQuery = true)
	public List<NewsArchive> searchNewsByKeywordAndType(String Type,String Keyword);
}
