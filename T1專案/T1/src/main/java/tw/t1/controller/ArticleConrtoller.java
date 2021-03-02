package tw.t1.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tw.t1.entity.Announcement;
import tw.t1.entity.Article;
import tw.t1.entity.ArticleLike;
import tw.t1.entity.ArticleTrack;
import tw.t1.entity.Comment;
import tw.t1.entity.ForumType;
import tw.t1.entity.Manager;
import tw.t1.entity.User;
import tw.t1.service.AnnouncementService;
import tw.t1.service.ArticleLikeService;
import tw.t1.service.ArticleService;
import tw.t1.service.ArticleTrackService;
import tw.t1.service.CommentService;
import tw.t1.service.ForumTypeService;

@Controller
@RequestMapping("/article")
public class ArticleConrtoller {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private ForumTypeService forumTypeService;
	@Autowired
	private ArticleTrackService atService;
	@Autowired
	private ArticleLikeService alService;
	@Autowired
	private AnnouncementService announcementService;

	// 文章列表
	@GetMapping("/list")
	public String list(Model m, HttpSession session, @RequestParam("forumID") int forumID, HttpServletRequest request) {

		// 找到對應的論壇
		ForumType forumType = forumTypeService.findById(forumID);
		// 找到對應論壇的文章
		List<Article> allArticles = articleService.findArticleByForumNameByDesc(forumType.getForumName());

		/* 0213  */
		// 找到對應論壇的公告
		List<Announcement> announcements = announcementService.findAnnouncementByForumIdByDesc(forumID);
		// 暫存現在時間
		long timeNow = new Date().getTime();

		int totalPage = 0; // 總分頁數
		int paginationNum = 10; // 每頁數量
		int announcementNum = announcements.size(); // 該討論版公告數量
		int totalArticles = allArticles.size(); // 該討論版文章數量
		int allPost = announcementNum + totalArticles; // 討論版公告+文章總數

		for (Announcement tempAnnouncement : announcements) {
			Announcement announcement = announcementService.findById(tempAnnouncement.getId());
			String passtime = "";
			long time = (timeNow - announcement.getUpdateMillisecond()) / 1000;
			if (time >= 31536000) {
				passtime = (time / 31536000) + "年前";
			} else if (time >= 86400) {
				passtime = (time / 86400) + "天前";
			} else if (time >= 3600) {
				passtime = (time / 3600) + "小時前";
			} else if (time >= 60) {
				passtime = (time / 60) + "分鐘前";
			} else if (time >= 5) {
				passtime = time + "秒前";
			} else {
				passtime = "幾秒前...";
			}
			announcement.setPasstime(passtime);
			announcementService.save(announcement);
		}
		/* 0213  */

		for (Article tempArticle : allArticles) {
			Article article = articleService.findById(tempArticle.getId());
			String passtime = "";
			long time = (timeNow - article.getUpdate_millisecond()) / 1000;
			if (time >= 31536000) {
				passtime = (time / 31536000) + "年前";
			} else if (time >= 86400) {
				passtime = (time / 86400) + "天前";
			} else if (time >= 3600) {
				passtime = (time / 3600) + "小時前";
			} else if (time >= 60) {
				passtime = (time / 60) + "分鐘前";
			} else if (time >= 5) {
				passtime = time + "秒前";
			} else {
				passtime = "幾秒前...";
			}
			article.setPasstime(passtime);
			articleService.save(article);
		}

		List<Article> articles = new ArrayList<>();

		if (allPost != 0) { // 若有公告或文章
			if (allPost / paginationNum == 0) {
				totalPage = 1;
			} else {
				if ((allPost % paginationNum) == 0) {
					totalPage = (allPost / paginationNum);
				} else {
					totalPage = (allPost / paginationNum) + 1;
				}
			}
			if (totalPage == 1) { // 如果只有一頁直接回傳公告和文章
				for (int i = 0; i < totalArticles; i++) {
					articles.add(allArticles.get(i));
				}
				if (null != session.getAttribute("currentManager")) {
					m.addAttribute("announcements", announcements);
					m.addAttribute("articles", articles);
					m.addAttribute("forumID", forumID);
					m.addAttribute("forumType", forumType);
					m.addAttribute("totalPage", totalPage);

					return "manager/manager-article-Home";
				}
				m.addAttribute("announcements", announcements);
				m.addAttribute("articles", articles);
				m.addAttribute("forumID", forumID);
				m.addAttribute("forumType", forumType);
				m.addAttribute("totalPage", totalPage);
				return "article/article-Home";
			}
		} else { // 若沒有公告也沒有文章
			if (null != session.getAttribute("currentManager")) {
				m.addAttribute("announcements", announcements);
				m.addAttribute("articles", articles);
				m.addAttribute("forumID", forumID);
				m.addAttribute("forumType", forumType);
				m.addAttribute("totalPage", totalPage);
				return "manager/manager-article-Home";
			} else {
				m.addAttribute("announcements", announcements);
				m.addAttribute("articles", articles);
				m.addAttribute("forumID", forumID);
				m.addAttribute("forumType", forumType);
				m.addAttribute("totalPage", totalPage);
				return "article/article-Home";
			}
		}

		int page = 0;
		if (request.getParameter("page") == null | "1".equals(request.getParameter("page"))) {
			if (totalPage > 1) {
				for (int i = 0; i < paginationNum - announcementNum; i++) { // 扣掉公告數
					articles.add(allArticles.get(i));
				}
			} else {
				for (int i = 0; i < allArticles.size(); i++) {
					articles.add(allArticles.get(i));
				}
			}
			m.addAttribute("announcements", announcements);
			m.addAttribute("articles", articles);
			m.addAttribute("totalPage", totalPage);
			page = 1;
		} else if (totalPage > 1) {
			page = Integer.parseInt(request.getParameter("page"));
			if (page < totalPage) {
				for (int i = ((page - 1) * paginationNum) - announcementNum; i < (page * paginationNum)
						- announcementNum; i++) {
					articles.add(allArticles.get(i));
				}
			} else if (page == totalPage) {
				for (int i = ((page - 1) * paginationNum) - announcementNum; i < allArticles.size(); i++) {
					articles.add(allArticles.get(i));
				}
			}
			m.addAttribute("articles", articles);
			m.addAttribute("totalPage", totalPage);
		}

		m.addAttribute("page", page);

		if (null != session.getAttribute("currentManager")) {
			m.addAttribute("forumID", forumID);
			m.addAttribute("forumType", forumType);

			return "manager/manager-article-Home";
		}

		m.addAttribute("forumID", forumID);
		m.addAttribute("forumType", forumType);

		return "article/article-Home";
	}

	// 給管理員的文章管理後台 0208阿誠
	@GetMapping("/articleManagementSystem")
	public String articleManagementSystem(Model m, HttpSession session, HttpServletRequest request) {

		// 確認管理者有無登入狀態
		if (null == session.getAttribute("currentManager")) {
			return "member/member-no-right";
		}

		// 找到所有文章
		List<Article> allArticles = articleService.findArticlesByDesc();

		int totalPage = 0; // 總分頁數
		int paginationNum = 10; // 每頁數量
		int totalArticles = allArticles.size();

		if (totalArticles != 0) {
			if (totalArticles / paginationNum == 0) {
				totalPage = 1;
			} else {
				if ((totalArticles % paginationNum) == 0) {
					totalPage = (totalArticles / paginationNum);
				} else {
					totalPage = (totalArticles / paginationNum) + 1;
				}
			}
		} else {
			m.addAttribute("totalPage", totalPage);
			return "manager/manager-articleManagementSystem";
		}

		List<Article> articles = new ArrayList<>();
		int page = 0;
		if (request.getParameter("page") == null | "1".equals(request.getParameter("page"))) {
			if (totalPage > 1) {
				for (int i = 0; i < paginationNum; i++) {
					articles.add(allArticles.get(i));
				}
			} else {
				for (int i = 0; i < allArticles.size(); i++) {
					articles.add(allArticles.get(i));
				}
			}
			m.addAttribute("articles", articles);
			m.addAttribute("totalPage", totalPage);
			page = 1;
		} else if (totalPage > 1) {
			page = Integer.parseInt(request.getParameter("page"));
			if (page < totalPage) {
				for (int i = (page - 1) * paginationNum; i < (page * paginationNum); i++) {
					articles.add(allArticles.get(i));
				}
			} else if (page == totalPage) {
				for (int i = (page - 1) * paginationNum; i < allArticles.size(); i++) {
					articles.add(allArticles.get(i));
				}
			}
			m.addAttribute("articles", articles);
			m.addAttribute("totalPage", totalPage);
		}

		m.addAttribute("page", page);
		return "manager/manager-articleManagementSystem";

	}

	// 進入特定文章
	@GetMapping("/get")
	public ModelAndView getArticle(@RequestParam("id") int id, HttpSession session,
			@RequestParam("forumID") int forumID) {

		Article article = articleService.findById(id);

		// 找該文章的留言
		List<Comment> comments = commentService.findCommentByArticleId(article.getId());

		if (null != session.getAttribute("currentManager")) {
			ModelAndView mav = new ModelAndView("manager/manager-article-show");
			mav.addObject("article", article);
			mav.addObject("comments", comments);
			mav.addObject("forumID", forumID);
			return mav;
		}

		/* 0208 */

		String alreadyTrack = "false";
		String alreadyLike = "false";

		if (session.getAttribute("currentUser") != null) {

			User user = (User) session.getAttribute("currentUser");
			if (atService.findTrackByUseridandArticleid(user.getUserID(), article.getId()) != null) {
				alreadyTrack = "true";
			}
			if (alService.findLikeByUseridandArticleid(user.getUserID(), article.getId()) != null) {
				alreadyLike = "true";
			}

		}
		;

		ModelAndView mav = new ModelAndView("article/article-show");
		mav.addObject("article", article);
		mav.addObject("comments", comments);
		mav.addObject("forumID", forumID);
		mav.addObject("alreadyTrack", alreadyTrack);
		mav.addObject("alreadyLike", alreadyLike);

		/* 0208 */

		return mav;
	}

	/* 0213  */
	// 進入特定公告
	@GetMapping("/announcementGet")
	public ModelAndView getAnnouncement(@RequestParam("id") int id, HttpSession session,
			@RequestParam("forumID") int forumID) {

		Announcement announcement = announcementService.findById(id);
		ModelAndView mav = new ModelAndView("manager/manager-announcement-show");

		if (null != session.getAttribute("currentManager")) {
			mav.addObject("announcement", announcement);
			mav.addObject("forumID", forumID);
			return mav;
		}

		mav.addObject("announcement", announcement);
		mav.addObject("forumID", forumID);
		return mav;
	}

	@ResponseBody
	@PostMapping("/comment/save")
	public Comment save(Comment com, HttpSession session) {

		if (null != session.getAttribute("currentManager")) {
			Manager currentManager = (Manager) session.getAttribute("currentManager");
			com.setNickName(currentManager.getManagerName() + " (管理員)");
			com.setPosttime(new Date());
			com.setMemberPhotoUri(currentManager.getManagerPhotoUri());
		}

		if (null != session.getAttribute("currentUser")) {
			User currentUser = (User) session.getAttribute("currentUser");
			com.setNickName(currentUser.getNickName());
			com.setPosttime(new Date());
			com.setMemberPhotoUri(currentUser.getMemberPhotoUri());
		}

		return commentService.save(com);
	}
	/* 0213  */

	/* 0210 */
	@ResponseBody
	@GetMapping("/comment/ban")
	public void banComment(@RequestParam("commentid") int id) {
		Comment thiscom = commentService.findById(id);
		thiscom.setContent("本留言包含謾罵等不雅字眼，已違反版規遭到移除");

		commentService.save(thiscom);
	}
	/* 0210 */

	// 投稿頁面
	@GetMapping("/showFormforAdd")
	public String add(Model m, @RequestParam("forumID") int forumID) {

		Article article = new Article();
		m.addAttribute("article", article);
		m.addAttribute("forumID", forumID);

		return "article/article-form";
	}

	/* 0213  */
	// 投稿頁面
	@GetMapping("/announcementShowFormforAdd")
	public String announcementAdd(Model m, @RequestParam("forumID") int forumID) {

		Announcement announcement = new Announcement();
		m.addAttribute("announcement", announcement);
		m.addAttribute("forumID", forumID);

		return "manager/manager-announcement-form";
	}
	/* 0213  */

	// 編輯
	@GetMapping("/showFormForUpdate")
	public String showform(@RequestParam("id") int id, Model m, @RequestParam("forumID") int forumID) {

		Article art = articleService.findById(id);
		m.addAttribute("article", art);
		m.addAttribute("forumID", forumID);

		return "article/article-form";
	}

	/* 0213  */
	// 公告編輯
	@GetMapping("/announcementShowFormForUpdate")
	public String announcementShowform(@RequestParam("id") int id, Model m, @RequestParam("forumID") int forumID) {

		Announcement announcement = announcementService.findById(id);
		m.addAttribute("announcement", announcement);
		m.addAttribute("forumID", forumID);

		return "manager/manager-announcement-form";
	}
	/* 0213  */

	// 後台公告編輯
	@GetMapping("/announcementShowFormForUpdateBackend")
	public String announcementShowFormForUpdateBackend(@RequestParam("id") int id, Model m,
			@RequestParam("forumID") int forumID) {

		Announcement announcement = announcementService.findById(id);
		m.addAttribute("announcement", announcement);
		m.addAttribute("forumID", forumID);

		return "manager/manager-announcement-form-backend";
	}

	// 投稿文章存置資料庫
	@PostMapping("/save")
	public String saveArt(@ModelAttribute("article") Article article, HttpSession session,
			@RequestParam("forumID") int forumID) throws IOException {
		ForumType forumType = forumTypeService.findById(forumID); // 抓取該論壇資訊
		User currentUser = (User) session.getAttribute("currentUser");
		article.setUserID(currentUser.getUserID()); // 加入作者ID
		article.setNickName(currentUser.getNickName()); // 加入作者名稱
		Date now = new Date();
		article.setUpdate_time(now); // 加入發文時間
		article.setUpdate_millisecond(now.getTime()); // 加入發文時間毫秒數 ( 1970年1月1日0時0分0秒至今 )
		article.setCategory(forumType.getCategory()); // 加入文章分類
		article.setForumID(forumType.getForumID()); // 加入論壇分類ID
		article.setForumName(forumType.getForumName()); // 加入論壇分類
		switch (article.getType()) { // 給預設圖
		case "閒聊":
			article.setPhotoUri("/images/articles/a_chat.png");
			break;
		case "攻略":
			article.setPhotoUri("/images/articles/a_method.png");
			break;
		case "心得":
			article.setPhotoUri("/images/articles/a_comment.png");
			break;
		case "提問":
			article.setPhotoUri("/images/articles/a_question.png");
			break;
		case "其他":
			article.setPhotoUri("/images/articles/a_other.png");
			break;
		}
		articleService.save(article);

		return "redirect:/article/list?forumID=" + forumID;
	}

	/* 0213  */
	// 發佈公告存到資料庫
	@PostMapping("/announcementSave")
	public String saveAnnouncement(@ModelAttribute("announcement") Announcement announcement, HttpSession session,
			@RequestParam("forumID") int forumID) throws IOException {
		ForumType forumType = forumTypeService.findById(forumID); // 抓取該論壇資訊
		Manager currentManager = (Manager) session.getAttribute("currentManager");
		announcement.setManagerID(currentManager.getManagerID()); // 加入管理者ID
		announcement.setManagerName(currentManager.getManagerName()); // 加入作者名稱
		Date now = new Date();
		announcement.setUpdateTime(now); // 加入發文時間
		announcement.setUpdateMillisecond(now.getTime()); // 加入發文時間毫秒數 ( 1970年1月1日0時0分0秒至今 )
		announcement.setCategory(forumType.getCategory()); // 加入文章分類
		announcement.setForumID(forumType.getForumID()); // 加入論壇分類ID
		announcement.setForumName(forumType.getForumName()); // 加入論壇分類

		announcementService.save(announcement);

		return "redirect:/article/list?forumID=" + forumID;
	}
	/* 0213  */

	// 發佈公告存到資料庫
	@PostMapping("/announcementSaveManagerSystem")
	public String announcementSaveManagerSystem(@ModelAttribute("announcement") Announcement announcement,
			HttpSession session, @RequestParam("forumID") int forumID) throws IOException {
		ForumType forumType = forumTypeService.findById(forumID); // 抓取該論壇資訊
		Manager currentManager = (Manager) session.getAttribute("currentManager");
		announcement.setManagerID(currentManager.getManagerID()); // 加入管理者ID
		announcement.setManagerName(currentManager.getManagerName()); // 加入作者名稱
		Date now = new Date();
		announcement.setUpdateTime(now); // 加入發文時間
		announcement.setUpdateMillisecond(now.getTime()); // 加入發文時間毫秒數 ( 1970年1月1日0時0分0秒至今 )
		announcement.setCategory(forumType.getCategory()); // 加入文章分類
		announcement.setForumID(forumType.getForumID()); // 加入論壇分類ID
		announcement.setForumName(forumType.getForumName()); // 加入論壇分類

		announcementService.save(announcement);

		return "redirect:/article/announcementManage";
	}

	// 刪除
	@GetMapping("/delete")
	public String delete(@RequestParam("id") int id, @RequestParam("forumID") int forumID) {

		System.out.println("文章ID : " + id);
		Article delArticle = articleService.findById(id);
		// 刪除該文章留言
		commentService.deleteCommentByArticleId(delArticle.getId());
		// 刪除該文章按讚
		alService.deleteLikeByArticleid(id);
		// 刪除該文章追蹤
		atService.deleteTrackByArticleid(id);
		// 刪除文章
		articleService.deleteById(id);

		return "redirect:/article/list?forumID=" + forumID;
	}

	/* 0213  */
	// 後台文章刪除
	@GetMapping("/managerDelete")
	public String managerDelete(@RequestParam("id") int id, @RequestParam("forumID") int forumID) {

		System.out.println("文章ID : " + id);
		Article delArticle = articleService.findById(id);
		// 刪除該文章留言
		commentService.deleteCommentByArticleId(delArticle.getId());
		// 刪除該文章按讚
		alService.deleteLikeByArticleid(id);
		// 刪除該文章追蹤
		atService.deleteTrackByArticleid(id);
		// 刪除文章
		articleService.deleteById(id);

		return "redirect:/article/articleManagementSystem";
	}

	// 公告刪除
	@GetMapping("/announcementDelete")
	public String announcementDelete(@RequestParam("id") int id, @RequestParam("forumID") int forumID,
			HttpSession session) {

		if (null != session.getAttribute("currentManager")) {
			// 刪除公告
			announcementService.deleteById(id);
		}

		return "redirect:/article/list?forumID=" + forumID;
	}
	/* 0213  */

	// 公告刪除
	@GetMapping("/announcementDeleteBackend")
	public String announcementDeleteBackend(@RequestParam("id") int id, @RequestParam("forumID") int forumID,
			HttpSession session) {

		if (null != session.getAttribute("currentManager")) {
			// 刪除公告
			announcementService.deleteById(id);
		}

		return "redirect:/article/announcementManage";
	}

	@GetMapping("/announcementManage")
	public String announcementManage(HttpSession session, Model m, HttpServletRequest request) {

		// 確認管理者有無登入狀態
		if (null == session.getAttribute("currentManager")) {
			return "member/member-no-right";
		}

		// 找到所有公告
		List<Announcement> allAnnouncements = announcementService.findAllByDesc();

		int totalPage = 0; // 總分頁數
		int paginationNum = 10; // 每頁數量
		int totalAnnouncements = allAnnouncements.size();

		if (totalAnnouncements != 0) {
			if (totalAnnouncements / paginationNum == 0) {
				totalPage = 1;
			} else {
				if ((totalAnnouncements % paginationNum) == 0) {
					totalPage = (totalAnnouncements / paginationNum);
				} else {
					totalPage = (totalAnnouncements / paginationNum) + 1;
				}
			}
		} else {
			m.addAttribute("totalPage", totalPage);
			return "manager/manager-announcementManagementSystem";
		}

		List<Announcement> announcements = new ArrayList<>();
		int page = 0;
		if (request.getParameter("page") == null | "1".equals(request.getParameter("page"))) {
			if (totalPage > 1) {
				for (int i = 0; i < paginationNum; i++) {
					announcements.add(allAnnouncements.get(i));
				}
			} else {
				for (int i = 0; i < allAnnouncements.size(); i++) {
					announcements.add(allAnnouncements.get(i));
				}
			}
			m.addAttribute("announcements", announcements);
			m.addAttribute("totalPage", totalPage);
			page = 1;
		} else if (totalPage > 1) {
			page = Integer.parseInt(request.getParameter("page"));
			if (page < totalPage) {
				for (int i = (page - 1) * paginationNum; i < (page * paginationNum); i++) {
					announcements.add(allAnnouncements.get(i));
				}
			} else if (page == totalPage) {
				for (int i = (page - 1) * paginationNum; i < allAnnouncements.size(); i++) {
					announcements.add(allAnnouncements.get(i));
				}
			}
			m.addAttribute("announcements", announcements);
			m.addAttribute("totalPage", totalPage);
		}

		m.addAttribute("page", page);
		return "manager/manager-announcementManagementSystem";

	}

	@GetMapping("/announcementSearch")
	public String announcementSearch(HttpSession session, Model m, @RequestParam("key") String forumName) {

		if (null != session.getAttribute("currentManager")) {
			List<Announcement> announcements = announcementService.findWithLikeDesc(forumName);
			m.addAttribute("announcements", announcements);
			return "manager/manager-announcementManagementSystem";
		}

		return "member/member-no-right";
	}

	@GetMapping("/articleSearch")
	public String articleSearch(HttpSession session, Model m, @RequestParam("key") String key) {

		if (null != session.getAttribute("currentManager")) {
			List<Article> articles = articleService.findArticlesByTitleWithLike(key);
			m.addAttribute("articles", articles);
			return "manager/manager-articleManagementSystem";
		}

		return "member/member-no-right";
	}

	@GetMapping("/addtotrack")
	public @ResponseBody void addToTrack(@RequestParam("userid") int userid, @RequestParam("artid") int artid,
			@RequestParam("title") String title, @RequestParam("category") String category,
			@RequestParam("forumid") int forumid, @RequestParam("forumname") String forumname) {
		ArticleTrack at = new ArticleTrack();
		at.setUser_id(userid);
		at.setArticle_id(artid);
		at.setArticle_title(title);
		at.setArticle_category(category);
		at.setArticle_forumid(forumid);
		at.setArticle_forum(forumname);

		atService.save(at);
	}

	@GetMapping("/removetrack")
	public @ResponseBody void removeFromTrack(@RequestParam("userid") int userid, @RequestParam("artid") int artid) {
		atService.deleteTrackByUseridandArticleid(userid, artid);
	}

	@GetMapping("/like")
	public @ResponseBody void addToLike(@RequestParam("userid") int userid, @RequestParam("artid") int artid) {
		ArticleLike al = new ArticleLike();
		al.setUser_id(userid);
		al.setArticle_id(artid);

		Article article = articleService.findById(artid);
		article.setLikes(article.getLikes() + 1);

		articleService.save(article);
		alService.save(al);
	}

	@GetMapping("/removelike")
	public @ResponseBody void removeFromLike(@RequestParam("userid") int userid, @RequestParam("artid") int artid) {
		alService.deleteLikeByUseridandArticleid(userid, artid);

		Article article = articleService.findById(artid);
		article.setLikes(article.getLikes() - 1);

		articleService.save(article);
	}

	/* 0216 */
	@GetMapping("/forumArticleSearch")
	public String forumArticleSearch(@RequestParam("forumID") int forumID, @RequestParam("key") String key,
			HttpSession session, Model m) {

		// 找到對應的論壇
		ForumType forumType = forumTypeService.findById(forumID);
		// 找到對應論壇的文章
		List<Article> articles = articleService.findArticleByForumNameByDescWithKey(forumType.getForumName(), key);

		// 暫存現在時間
		long timeNow = new Date().getTime();

		for (Article tempArticle : articles) {
			Article article = articleService.findById(tempArticle.getId());
			String passtime = "";
			long time = (timeNow - article.getUpdate_millisecond()) / 1000;
			if (time >= 31536000) {
				passtime = (time / 31536000) + "年前";
			} else if (time >= 86400) {
				passtime = (time / 86400) + "天前";
			} else if (time >= 3600) {
				passtime = (time / 3600) + "小時前";
			} else if (time >= 60) {
				passtime = (time / 60) + "分鐘前";
			} else if (time >= 5) {
				passtime = time + "秒前";
			} else {
				passtime = "幾秒前...";
			}
			article.setPasstime(passtime);
			articleService.save(article);
		}

		if (null != session.getAttribute("currentManager")) {
			m.addAttribute("articles", articles);
			m.addAttribute("forumID", forumID);
			m.addAttribute("forumType", forumType);

			return "manager/manager-article-Home";
		}

		m.addAttribute("articles", articles);
		m.addAttribute("forumID", forumID);
		m.addAttribute("forumType", forumType);
		return "article/article-Home";

	}
	/* 0216 */

	/* 0217 */
	@GetMapping("/typeSearch")
	public String typeSearch(Model m, HttpSession session, @RequestParam("type") String type,
			@RequestParam("forumID") int forumID, HttpServletRequest request) {

		// 找到對應的論壇
		ForumType forumType = forumTypeService.findById(forumID);
		// 找到對應論壇的文章
		List<Article> allArticles = articleService.findArticleByForumNameByDescWithType(forumType.getForumName(), type);

		int totalPage = 0; // 總分頁數
		int paginationNum = 10; // 每頁數量
		int totalArticle = allArticles.size();

		if (totalArticle != 0) {
			if (totalArticle / paginationNum == 0) {
				totalPage = 1;
			} else {
				if ((totalArticle % paginationNum) == 0) {
					totalPage = (totalArticle / paginationNum);
				} else {
					totalPage = (totalArticle / paginationNum) + 1;
				}
			}
		} else {
			m.addAttribute("forumID", forumID);
			m.addAttribute("forumType", forumType);
			m.addAttribute("totalPage", totalPage);
			if (null != session.getAttribute("currentManager")) {
				return "manager/manager-article-Home";
			} else {
				return "article/article-Home";
			}
		}

		List<Article> articles = new ArrayList<>();
		int page = 0;
		if (request.getParameter("page") == null | "1".equals(request.getParameter("page"))) {
			if (totalPage > 1) {
				for (int i = 0; i < paginationNum; i++) {
					articles.add(allArticles.get(i));
				}
			} else {
				for (int i = 0; i < allArticles.size(); i++) {
					articles.add(allArticles.get(i));
				}
			}
			m.addAttribute("articles", articles);
			m.addAttribute("totalPage", totalPage);
			page = 1;
		} else if (totalPage > 1) {
			page = Integer.parseInt(request.getParameter("page"));
			if (page < totalPage) {
				for (int i = (page - 1) * paginationNum; i < (page * paginationNum); i++) {
					articles.add(allArticles.get(i));
				}
			} else if (page == totalPage) {
				for (int i = (page - 1) * paginationNum; i < allArticles.size(); i++) {
					articles.add(allArticles.get(i));
				}
			}
			m.addAttribute("articles", articles);
			m.addAttribute("totalPage", totalPage);
		}

		m.addAttribute("type", type);
		m.addAttribute("forumID", forumID);
		m.addAttribute("forumType", forumType);
		m.addAttribute("page", page);
		if (null != session.getAttribute("currentManager")) {
			return "manager/manager-article-Home";
		} else {
			return "article/article-Home";
		}

	}

	@GetMapping("/managerTypeSearch")
	public String managerTypeSearch(Model m, HttpSession session, @RequestParam("type") String type,
			HttpServletRequest request) {

		// 找到對應類別的文章
		List<Article> allArticles = articleService.findArticleByDescWithType(type);

		int totalPage = 0; // 總分頁數
		int paginationNum = 10; // 每頁數量
		int totalArticle = allArticles.size();

		if (totalArticle != 0) {
			if (totalArticle / paginationNum == 0) {
				totalPage = 1;
			} else {
				if ((totalArticle % paginationNum) == 0) {
					totalPage = (totalArticle / paginationNum);
				} else {
					totalPage = (totalArticle / paginationNum) + 1;
				}
			}
		} else {
			m.addAttribute("totalPage", totalPage);
			if (null != session.getAttribute("currentManager")) {
				return "manager/manager-articleManagementSystem";
			} else {
				return "member/member-no-right";
			}
		}

		List<Article> articles = new ArrayList<>();
		int page = 0;
		if (request.getParameter("page") == null | "1".equals(request.getParameter("page"))) {
			if (totalPage > 1) {
				for (int i = 0; i < paginationNum; i++) {
					articles.add(allArticles.get(i));
				}
			} else {
				for (int i = 0; i < allArticles.size(); i++) {
					articles.add(allArticles.get(i));
				}
			}
			m.addAttribute("articles", articles);
			m.addAttribute("totalPage", totalPage);
			page = 1;
		} else if (totalPage > 1) {
			page = Integer.parseInt(request.getParameter("page"));
			if (page < totalPage) {
				for (int i = (page - 1) * paginationNum; i < (page * paginationNum); i++) {
					articles.add(allArticles.get(i));
				}
			} else if (page == totalPage) {
				for (int i = (page - 1) * paginationNum; i < allArticles.size(); i++) {
					articles.add(allArticles.get(i));
				}
			}
			m.addAttribute("articles", articles);
			m.addAttribute("totalPage", totalPage);
		}

		m.addAttribute("type", type);
		m.addAttribute("page", page);
		if (null != session.getAttribute("currentManager")) {
			return "manager/manager-articleManagementSystem";
		} else {
			return "member/member-no-right";
		}

	}
	
}
