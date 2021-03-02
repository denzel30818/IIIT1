package tw.t1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tw.t1.entity.News;

public interface NewsRepository extends JpaRepository<News, Integer> {

	// 找尋限定範圍內的新聞 ( 不指定ID搜尋 )
	@Query(value = "SELECT * FROM news order by news_updateTime desc LIMIT ?1,?2", nativeQuery = true)
	public List<News> findNewsByLimit(int start, int end);
	
	// 找尋限定類別及範圍內的新聞 ( 不指定ID搜尋 )
	@Query(value = "SELECT * FROM news WHERE news_type = ?1 order by news_updateTime desc LIMIT ?2,?3", nativeQuery = true)
	public List<News> findNewsByLimitAndType(String type,int start, int end);

	// 找尋限定範圍內的新聞 ( 不指定ID搜尋，且為倒序 )
	@Query(value = "SELECT * FROM news ORDER BY news_id DESC LIMIT ?1,?2", nativeQuery = true)
	public List<News> findNewsDescByLimit(int start, int end);

	// 找尋指定管理者發的全部新聞
	@Query(value = "SELECT * FROM news WHERE managerName = ?1", nativeQuery = true)
	public List<News> findNewsByManagerName(String managerName);

	// 計算指定管理者發佈新聞數量
	@Query(value = "SELECT COUNT(*) FROM news WHERE managerName = ?1", nativeQuery = true)
	public long countByManagerNews(String managerName);

	// 找尋特定管理者新聞 ( 限定範圍搜尋 )
	@Query(value = "SELECT * FROM news WHERE UPPER(managerName) LIKE UPPER(CONCAT('%',?1,'%')) LIMIT ?2,?3", nativeQuery = true)
	public List<News> findNewsByManagerNameForPage(int managerName, int start, int end);

	// 全域搜尋新聞 ( 依搜尋的字 )
	@Query(value = "SELECT COUNT(*) FROM news WHERE UPPER(title) LIKE UPPER(CONCAT('%',?1,'%'))", nativeQuery = true)
	public long countByTitleSearch(String title);

	// 找尋全部新聞 ( 倒序 )
	@Query(value = "SELECT * FROM news ORDER BY news_id DESC", nativeQuery = true)
	public List<News> findNewsByIdDesc();

	// 計算全部新聞數量
	@Query(value = "SELECT COUNT(*) FROM news", nativeQuery = true)
	public long countAllNews();

	// 找尋特定類別的新聞
	@Query(value = "SELECT * FROM news WHERE news_type = ?1", nativeQuery = true)
	public List<News> findNewsByType(String type);

	// 找尋特定類別的新聞 ( 倒序 )
	@Query(value = "SELECT * FROM news WHERE news_type = ?1 ORDER BY news_id DESC", nativeQuery = true)
	public List<News> findNewsByTypeDesc(String type);

	// 找尋特定類別的新聞 ( 倒序 )
	@Query(value = "SELECT * FROM news WHERE news_type = ?1 ORDER BY news_id DESC LIMIT ?2,?3", nativeQuery = true)
	public List<News> findNewsByTypeDescLimit(String type, int start, int end);
	
	// 全部新聞依時間排序前8個
	@Query(value = "SELECT * FROM news order by news_updateTime desc limit 8;", nativeQuery = true)
	public List<News> findAllAndOrderByUpdateTimeDescTop8();

	// 全部新聞依時間排序
	@Query(value = "SELECT * FROM news order by news_updateTime desc;", nativeQuery = true)
	public List<News> findAllAndOrderByUpdateTimeDesc();
	
	// 全部新聞依時間排序前100個
	@Query(value = "SELECT * FROM news order by news_updateTime desc limit 100;", nativeQuery = true)
	public List<News> findAllAndOrderByUpdateTimeDescTop100();

	// 新聞頁籤依新聞類別及時間排序
	@Query(value = "SELECT * FROM news where news_type = ?1 order by news_updateTime desc;", nativeQuery = true)
	public List<News> findNewsByTypeWithUpdateTime(String Type);
	
	// 新聞頁籤依新聞類別及時間排序前100個
	@Query(value = "SELECT * FROM news where news_type = ?1 order by news_updateTime desc limit 100;", nativeQuery = true)
	public List<News> findNewsByTypeWithUpdateTimeTop100(String Type);

	// 依年月搜尋特定類別新聞
	@Query(value = "SELECT * FROM news where news_type = ?1 and year(news_updateTime)= ?2 and month(news_updateTime)= ?3 order by news_updateTime desc;",nativeQuery = true)
	public List<News> findNewsByTypeAndDate(String Type,int News_year,int News_month);
	
	// 依年月搜尋特定類別新聞
	@Query(value = "SELECT * FROM news where year(news_updateTime)= ?1 and month(news_updateTime)= ?2 order by news_updateTime desc;",nativeQuery = true)
	public List<News> findAllNewsByDate(int News_year,int News_month);
	
	//搜尋新聞關鍵字
	@Query(value = "SELECT * FROM news WHERE UPPER(news_title) LIKE UPPER(CONCAT('%',?1,'%')) or UPPER(news_article) LIKE UPPER(CONCAT('%',?1,'%')) order by news_updateTime desc;", nativeQuery = true)
	public List<News> searchNewsByKeyword(String Keyword);
	
	//搜尋各類別新聞關鍵字
	@Query(value = "SELECT * FROM news WHERE news_type= ?1 and UPPER(news_title) LIKE UPPER(CONCAT('%',?2,'%')) or news_type= ?1 and UPPER(news_article) LIKE UPPER(CONCAT('%',?2,'%')) order by news_updateTime desc;", nativeQuery = true)
	public List<News> searchNewsByKeywordAndType(String Type,String Keyword);
}
