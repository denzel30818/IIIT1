package tw.t1.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import tw.t1.entity.Manager;
import tw.t1.entity.News;
import tw.t1.entity.NewsArchive;
import tw.t1.service.NewsArchiveService;
import tw.t1.service.NewsService;

//封存新聞的後台
@Controller
@RequestMapping("/newsArchive")
public class NewsArchiveController {

	@Autowired
	private NewsService newsService;

	@Autowired
	private NewsArchiveService newsArchiveService;

	// 進入新聞封存頁面
	@GetMapping("/archive")
	public String listNews(Model m, HttpSession session) {

		// 管理者進新聞管理頁面(後台)
		if (null != session.getAttribute("currentManager")) {
			List<NewsArchive> newsFind = newsArchiveService.findAllAndOrderByUpdateTimeDescTop100();// 搜尋全部並以時間排序
			List<NewsArchive> newsFindPC = newsArchiveService.findNewsByTypeWithUpdateTimeTop100("PC");// 參數為PC
			List<NewsArchive> newsFindMobile = newsArchiveService.findNewsByTypeWithUpdateTimeTop100("手機");// 參數為手機
			List<NewsArchive> newsFindTV = newsArchiveService.findNewsByTypeWithUpdateTimeTop100("TV");// 參數為TV
			List<NewsArchive> newsFindReport = newsArchiveService.findNewsByTypeWithUpdateTimeTop100("主題報導");// 參數為主題報導

			m.addAttribute("news", newsFind);// 傳遞news變數到news-Home頁面 其值為newsFind
			m.addAttribute("newsPC", newsFindPC);// 傳遞newsPC變數到news-Home頁面 其值為newsFindPC
			m.addAttribute("newsMobile", newsFindMobile);
			m.addAttribute("newsTV", newsFindTV);
			m.addAttribute("newsReport", newsFindReport);
			return "news/manager-news-archive-Home";
		}

		return null;
	}

	// 新聞依類別及年月搜尋
	@GetMapping("/searchByMonth")
	public String searchByMonth(Model m, HttpSession session, HttpServletRequest re) {
		int year = Integer.parseInt(re.getParameter("year"));
		int month = Integer.parseInt(re.getParameter("month"));
		// 管理者進入新聞年月搜尋(後台)
		if (null != session.getAttribute("currentManager")) {
			List<NewsArchive> findAllNewsByDate = newsArchiveService.findAllNewsByDate(year, month);
			List<NewsArchive> FindPCNewsbyDate = newsArchiveService.findNewsByTypeAndDate("PC", year, month);
			List<NewsArchive> FindMobileNewsbyDate = newsArchiveService.findNewsByTypeAndDate("手機", year, month);
			List<NewsArchive> FindTVNewsbyDate = newsArchiveService.findNewsByTypeAndDate("TV", year, month);
			List<NewsArchive> FindReportbyDate = newsArchiveService.findNewsByTypeAndDate("主題報導", year, month);

			m.addAttribute("news", findAllNewsByDate);
			m.addAttribute("newsPC", FindPCNewsbyDate);
			m.addAttribute("newsMobile", FindMobileNewsbyDate);
			m.addAttribute("newsTV", FindTVNewsbyDate);
			m.addAttribute("newsReport", FindReportbyDate);
			m.addAttribute("year", year);
			m.addAttribute("month", month);
			return "news/manager-oldNews-archive-search";
		}
		return null;
	}

	// 新聞內容
	@GetMapping("/showNews")
	public ModelAndView showNews(@RequestParam("newsId") int theId, HttpSession session, Model m) {
		NewsArchive newsA = newsArchiveService.findById(theId);

		// 管理者進入新聞內容(可編輯刪除)
		if (null != session.getAttribute("currentManager")) {
			ModelAndView mav = new ModelAndView("news/manager-news-archive-show");
			mav.addObject("news", newsA);
			return mav;
		}
		return null;
	}

	@GetMapping("/search")
	public String newsSearch(Model m, @RequestParam("keyword") String keyword, HttpSession session) {

		// 管理者後台搜尋
		if (null != session.getAttribute("currentManager")) {

			List<NewsArchive> newsKeyword = newsArchiveService.searchNewsByKeyword(keyword);
			List<NewsArchive> newsKeywordPC = newsArchiveService.searchNewsByKeywordAndType("PC", keyword);
			List<NewsArchive> newsKeywordMobile = newsArchiveService.searchNewsByKeywordAndType("手機", keyword);
			List<NewsArchive> newsKeywordTV = newsArchiveService.searchNewsByKeywordAndType("TV", keyword);
			List<NewsArchive> newsKeywordReport = newsArchiveService.searchNewsByKeywordAndType("主題報導", keyword);
			m.addAttribute("news", newsKeyword);
			m.addAttribute("newsPC", newsKeywordPC);
			m.addAttribute("newsMobile", newsKeywordMobile);
			m.addAttribute("newsTV", newsKeywordTV);
			m.addAttribute("newsReport", newsKeywordReport);

			m.addAttribute("keyword", keyword);
			return "news/manager-news-archive-Home-search";
		}
		// 使用者新聞搜尋

		return "/news/news-search";
	}

	// 新增新聞
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model m) {
		// create model attribute to bind form data
		NewsArchive news = new NewsArchive();
		news.setNewsPhoto("/images/news/newsCover/default.png"); // 給預設圖
		m.addAttribute("news", news); // 傳遞news變數到news-form-add頁面 其值為news
		return "news/manager-news-archive-form-add";

	}

	// 修改新聞
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("newsId") int theId, Model m) {
		// 取得新聞
		NewsArchive news = newsArchiveService.findById(theId);
		m.addAttribute("news", news); // 傳遞news變數到news-form-add頁面 其值為news
		return "news/manager-news-archive-form-update";
	}

	// 封存新聞
	@GetMapping("/archiveSomeNews")
	public String archiveNews(@ModelAttribute("news") NewsArchive news, @RequestParam("newsId") int theId) {
		// 取得新聞
		News newsA = newsService.findById(theId);
		news.setTitle(newsA.getTitle());
		news.setArticle(newsA.getArticle());
		news.setManager(newsA.getManager()); // 加入管理者名稱
		news.setType(newsA.getType()); // 加入新聞類別
		news.setNewsPhoto(newsA.getNewsPhoto()); // 加入新聞封面路徑
		news.setPostTime(newsA.getPostTime()); // 加入新聞發佈時間

		newsArchiveService.save(news);// 儲存新聞至封存庫
		newsService.deleteById(theId);// 刪除該新聞

		return "redirect:/news/list";// 回到新聞管理首頁
	}

	@GetMapping("post")
	public String postNews(@ModelAttribute("news") News news, @RequestParam("newsId") int theId) {
		NewsArchive newsA = newsArchiveService.findById(theId);
		news.setTitle(newsA.getTitle());
		news.setArticle(newsA.getArticle());
		news.setManager(newsA.getManager()); // 加入管理者名稱
		news.setType(newsA.getType()); // 加入新聞類別
		news.setNewsPhoto(newsA.getNewsPhoto()); // 加入新聞封面路徑
		news.setPostTime(new Date()); // 加入新聞發佈時間
		newsService.save(news);
		newsArchiveService.deleteById(theId);

		return "redirect:/newsArchive/archive";// 回到新聞檔案庫首頁
	}

	// 儲存新聞
	@PostMapping("/save")
	public String saveNews(@ModelAttribute("news") NewsArchive news, HttpSession session,
			@RequestParam("news_image") MultipartFile fileImage, @RequestParam("newsType") String type)
			throws IOException {

		// 取得圖檔根路徑
		String rootPath = "C:/SpringBoot/workspace/T1/src/main/resources/static/images/news/newsCover/";

		// 若是修改新聞
		if (news.getId() != 0) {
			//如果沒有上傳圖片
			if (fileImage.getOriginalFilename().equals("")) {

				NewsArchive oldNews = newsArchiveService.findById(news.getId());
				Manager currentManager = (Manager) session.getAttribute("currentManager"); // 取得當前管理者資料

				news.setManager(currentManager.getManagerName()); // 加入管理者名稱
				news.setType(type); // 加入新聞類別
				news.setPostTime(new Date()); // 加入新聞發佈時間
				news.setNewsPhoto(oldNews.getNewsPhoto());

				// 儲存新聞
				newsArchiveService.save(news);

				return "redirect:/newsArchive/archive";
			}else {
				NewsArchive tempNews = newsArchiveService.findById(news.getId()); // 取得文章資料
				String oldFilePath = tempNews.getNewsPhoto(); // 取的舊檔案相對路徑
				String oldFileName = oldFilePath.substring(oldFilePath.lastIndexOf("/") + 1, oldFilePath.length()); // 取得舊檔名

				if (!"default.png".equals(oldFileName)) { // 刪除舊頭像
				   File delFile = new File(rootPath + oldFileName);
				   delFile.delete();
				}
			}


		}

		Manager currentManager = (Manager) session.getAttribute("currentManager"); // 取得當前管理者資料

		String fileName = fileImage.getOriginalFilename(); // 取得圖片名稱
		int dot = fileName.lastIndexOf("."); // 取得副檔名索引
		long timeNow = new Date().getTime(); // 取得現在時間
		String newFileName = fileName.substring(0, dot) + timeNow + fileName.substring(dot, fileName.length()); // 新檔名加上時間戳記避免重名
		String filePath = rootPath + newFileName; // 存檔路徑

		news.setManager(currentManager.getManagerName()); // 加入管理者名稱
		news.setType(type); // 加入新聞類別
		news.setNewsPhoto("/images/news/newsCover/" + newFileName); // 加入新聞封面路徑
		news.setPostTime(new Date()); // 加入新聞發佈時間

		fileImage.transferTo(new File(filePath));

		// 儲存新聞
		newsArchiveService.save(news);

		// 睡二秒防圖片延遲
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return "redirect:/newsArchive/archive";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("newsId") int theId) {

		// 刪除新聞封面圖
		NewsArchive delNews = newsArchiveService.findById(theId);
		// 取得圖片根路徑
		String rootPath = "C:/SpringBoot/workspace/T1/src/main/resources/static";
		// 取得要刪除的圖片路徑
		String imgPath = rootPath + delNews.getNewsPhoto();
		File tempImg = new File(imgPath);

		if (tempImg.exists()) {
			tempImg.delete();
		}

		// 刪除新聞
		newsArchiveService.deleteById(theId);

		return "redirect:/newsArchive/archive";
	}

}
