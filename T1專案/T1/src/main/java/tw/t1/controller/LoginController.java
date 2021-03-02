package tw.t1.controller;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tw.t1.entity.Article;
import tw.t1.entity.Manager;
import tw.t1.entity.News;
import tw.t1.entity.User;
import tw.t1.service.ArticleService;
import tw.t1.service.EmailSenderService;
import tw.t1.service.ManagerService;
import tw.t1.service.NewsService;
import tw.t1.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private NewsService newsService;
	@Autowired
	private EmailSenderService emailSenderService;
	@Autowired
	private HttpSession session;

	// 登入前檢查
	@GetMapping("/login")
	public String loginProcess(HttpServletRequest request, Model m) throws MessagingException {

		// 確認管理者有無登入狀態
		if (null != session.getAttribute("currentManager")) {

			return "manager/manager-plzLogout";
		}

		// 確認使用者有無登入狀態
		if (null != session.getAttribute("currentUser")) {

			return "member/plzLogout";
		}

		// 確認有無登入失敗過，有的話給舊的Url，沒有就抓取新的
		if (null != session.getAttribute("oldPageUrl")) {
			String oldPageUrl = session.getAttribute("oldPageUrl").toString();
			m.addAttribute("pageUrl", oldPageUrl);
		} else {
			String pageUrl = request.getHeader("referer");
			m.addAttribute("pageUrl", pageUrl);
		}

		return "member/unLogin/login";
	}

	// 登入
	@PostMapping("/login")
	public String login(User user, @RequestParam("pageUrl") String pageUrl, RedirectAttributes ra) {

		String urlStart = "http://localhost:8080";
		String[] urlNeedToBackIndex = { "/login", "/register", "/forgetPassword", "/activeSuccess", "/activeFail" };

		List<Manager> managers = managerService.findAll();
		for (Manager tempManager : managers) { // 驗證管理者存在
			if (user.getAccount().equals(tempManager.getManagerAccount())
					&& user.getUserPassword().equals(tempManager.getManagerPassword())) {
				session.setAttribute("currentManager", tempManager); // 設置登入憑證

				if (null != session.getAttribute("oldPageUrl")) { // 如果有登入失敗過
					String oldPageUrl = session.getAttribute("oldPageUrl").toString(); // 暫存舊請求路徑
					session.removeAttribute("oldPageUrl"); // 移除 oldPageUrl

					// 過濾登入前網址
					for (int i = 0; i < urlNeedToBackIndex.length; i++) {
						if (oldPageUrl.equals(urlStart + urlNeedToBackIndex[i])) {
							return "redirect:/index";
						} else if (oldPageUrl.startsWith(urlStart + "/active")) {
							return "redirect:/index";
						}
					}

					if (oldPageUrl.equals("http://localhost:8080/news/list")) {
						return "redirect:/news/managerList";
					} else {
						return "redirect:" + oldPageUrl;
					}
				} else { // 如果沒有登入失敗過
					// 過濾登入前網址
					for (int i = 0; i < urlNeedToBackIndex.length; i++) {
						if (pageUrl.equals(urlStart + urlNeedToBackIndex[i])) {
							return "redirect:/index";
						} else if (pageUrl.startsWith(urlStart + "/active")) {
							return "redirect:/index";
						}
					}
					if (pageUrl.equals("http://localhost:8080/news/list")) {
						return "redirect:/news/managerList";
					} else {
						return "redirect:" + pageUrl;
					}
				}
			}
		}

		List<User> users = userService.findAll();
		for (User tempUser : users) { // 驗證使用者存在
			if (user.getAccount().equals(tempUser.getAccount())
					&& user.getUserPassword().equals(tempUser.getUserPassword())) {
				User currentUser = userService.findUserByAccount(user.getAccount());
				if (currentUser.getBanned().equals("y")) { // 檢查帳號有無被 ban
					ra.addFlashAttribute("loginError", "該帳號已被停權!");
					session.setAttribute("oldPageUrl", pageUrl);
					return "redirect:/login";
				}
				if (!currentUser.getActive().equals("否")) {
					session.setAttribute("currentUser", tempUser); // 設置登入憑證

					if (null != session.getAttribute("oldPageUrl")) { // 如果有登入失敗過
						String oldPageUrl = session.getAttribute("oldPageUrl").toString(); // 暫存舊請求路徑
						session.removeAttribute("oldPageUrl"); // 移除 oldPageUrl

						// 過濾登入前網址
						for (int i = 0; i < urlNeedToBackIndex.length; i++) {
							if (pageUrl.equals(urlStart + urlNeedToBackIndex[i])) {
								return "redirect:/index";
							} else if (oldPageUrl.startsWith(urlStart + "/active")) {
								return "redirect:/index";
							}
						}

						if (oldPageUrl.equals("http://localhost:8080/news/list")) {
							return "redirect:/news/list";
						} else {
							return "redirect:" + oldPageUrl;
						}
					} else { // 如果沒有登入失敗過
						// 過濾登入前網址
						for (int i = 0; i < urlNeedToBackIndex.length; i++) {
							if (pageUrl.equals(urlStart + urlNeedToBackIndex[i])) {
								return "redirect:/index";
							} else if (pageUrl.startsWith(urlStart + "/active")) {
								return "redirect:/index";
							}
						}
						if (pageUrl.equals("http://localhost:8080/news/list")) {
							return "redirect:/news/list";
						} else {
							return "redirect:" + pageUrl;
						}
					}
				} else {
					session.removeAttribute("currentUser");
					return "redirect:/activeFail";
				}
			}
		}

		session.setAttribute("oldPageUrl", pageUrl);
		ra.addFlashAttribute("loginError", "帳號或密碼錯誤");

		return "redirect:/login";
	}

	// 首頁
	@GetMapping({ "/", "/index" })
	public String index(Model m) {

		int allArticleNum = (int) articleService.countAllArticle();
		int articleShowNum = 6; // 要取的文章數量
		List<Article> articles = articleService.findArticlesDescByLimit(0, articleShowNum); // 取得舊的文章資料
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

		List<Article> newArticles = articleService.findArticlesDescByLimit(0, articleShowNum);
		m.addAttribute("articles", newArticles);
		m.addAttribute("allArticleNum", allArticleNum);

		int allNewsNum = (int) newsService.countAllNews();
		int newsShowNum = 8; // 要取的新聞數量
		List<News> newsPC = newsService.findNewsByTypeDescLimit("PC", 0, newsShowNum);
		List<News> newsMobile = newsService.findNewsByTypeDescLimit("手機", 0, newsShowNum);
		List<News> newsHost = newsService.findNewsByTypeDescLimit("TV", 0, newsShowNum);
		List<News> newsTheme = newsService.findNewsByTypeDescLimit("主題報導", 0, newsShowNum);
		List<News> newsAll = newsService.findNewsByLimit(allNewsNum - newsShowNum, newsShowNum);
		m.addAttribute("newsPC", newsPC);
		m.addAttribute("newsMobile", newsMobile);
		m.addAttribute("newsHost", newsHost);
		m.addAttribute("newsTheme", newsTheme);
		m.addAttribute("newsAll", newsAll);

		if (null != session.getAttribute("currentManager")) {
			return "manager/manager-index";
		}

		if (null != session.getAttribute("currentUser")) {
			return "member/index";
		}

		return "member/unLogin/indexN";
	}

	// 登出
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {

		String pageUrlString = request.getHeader("referer");
		System.out.println("登出前路徑" + pageUrlString);
		session.removeAttribute("currentUser"); // 移除使用者憑證
		session.removeAttribute("loginError"); // 移除登入錯誤訊息
		session.removeAttribute("currentManager"); // 移除管理者憑證
		String urlStart = "http://localhost:8080";
		String[] urlNeedToBackIndex = { "/login", "/forgetPassword", "/register", "/registerSuccess", "/member/info",
				"/member/password", "/member/point", "/member/article", "/member/deleteArticle",
				"/member/collectionList", "/member/orders", "/member/about", "/manager/system", "/manager/memberUpdate",
				"/manager/memberBanned", "/manager/memberBirthday", "/manager/userSearchUpdate",
				"/manager/userSearchBirthday", "/manager/userSearchBanned", "/manager/articleSearch",
				"/member/trackSearch", "/activeSuccess", "/activeFail", "/active", "/member/feedback",
				"/manager/feedback", "/manager/replyfeedback", "/member/feedback/success",
				"/article/articleManagementSystem", "/article/showFormforAdd", "/article/announcementShowFormforAdd",
				"/article/announcementSave", "/article/announcementShowFormForUpdate", "/article/announcementDelete",
				"/article/announcementDeleteBackend", "/article/showFormForUpdate", "/article/save", "/article/delete",
				"/article/managerDelete", "/article/announcementShowFormForUpdateBackend",
				"/article/announcementSaveManagerSystem", "/article/announcementManage", "/article/announcementSearch",
				"/article/articleSearch", "/articletype/upload", "/articletype/list", "/articletype/save",
				"/articletype/update", "/articletype/delete", "/articletype/preview", "/articletype/updateSubmit",
				"/member/mywishlist", "/member/mywishlist/remove", "/member/mycart", "/member/mycart/delete",
				"/member/mycart/update", "/member/mycart/fillform", "/member/mycart/go-preview",
				"/member/mycart/confirm", "/newsArchive/archive", "/newsArchive/searchByMonth", "/newsArchive/showNews",
				"/newsArchive/showFormForAdd", "/newsArchive/showFormForUpdate", "/newsArchive/archiveSomeNews",
				"/newsArchive/post", "/newsArchive/save", "/newsArchive/delete", "/news/managerListNewsSearchByMonth",
				"/news/managerShowNews", "/news/showFormForAdd", "/news/showFormForUpdate", "/news/save",
				"/news/delete", "/products/list", "/products/upload", "/products/preview", "/products/save",
				"/products/delete", "/products/update", "/products/updateSubmit", "/products/gametype/list",
				"/products/gametype/list/update", "/products/gametype/list/check", "/products/gametype/list/upload",
				"/products/gametype/list/remove", "/products/productcategory/list",
				"/products/productcategory/list/update", "/products/productcategory/list/check",
				"/products/productcategory/list/upload", "/products/productcategory/list/remove", "/products/imgsave",
				"/products/supplier/list", "/products/supplier/list/update", "/products/supplier/list/upload",
				"/products/supplier/list/check", "/products/supplier/list/remove", "/products/imgupdate",
				"/member/status", "/member/order/statchange", "/member/status/search", "/article/managerTypeSearch",
				"/member/orders/income" };

		if (pageUrlString.contains("/news/managerList")) {
			return "redirect:/news/list";
		}

		for (int i = 0; i < urlNeedToBackIndex.length; i++) {
			if (pageUrlString.startsWith(urlStart + urlNeedToBackIndex[i])) {
				System.out.println("被攔截返回首頁的路徑 = " + urlStart + urlNeedToBackIndex[i]);
				return "redirect:/index";
			}
		}
		return "redirect:" + pageUrlString;
	}

	// 跳轉忘記密碼
	@GetMapping("/forgetPassword")
	public String goForgetPassword() {

		// 確認管理者有無登入狀態
		if (null != session.getAttribute("currentManager")) {
			return "manager/manager-plzLogout";
		}

		// 確認使用者有無登入狀態
		if (null != session.getAttribute("currentUser")) {
			return "member/plzLogout";
		}

		return "member/unLogin/member-forget-password";
	}

	// 忘記密碼
	@PostMapping("/forgetPassword")
	public String forgetPassword(@RequestParam(name = "email") String email, Model m, RedirectAttributes attr)
			throws MessagingException {

		// 確認管理者有無登入狀態
		if (null != session.getAttribute("currentManager")) {

			return "manager/manager-plzLogout";
		}

		// 確認使用者有無登入狀態
		if (null != session.getAttribute("currentUser")) {

			return "member/plzLogout";
		}

		User user = userService.findUserByEmail(email);

		if (null != user) {
			emailSenderService.sendHtmlEmail("leonpower117@gmail.com", "T-one忘記密碼",
					"嗨，" + user.getNickName() + "<br/><br/>您的T-one帳戶資訊如下 :<br/><br/>帳號 : <b>" + user.getAccount()
							+ "</b><br/><br/>密碼 : <b>" + user.getUserPassword()
							+ "</b><br/><br/>建議您立刻修改密碼，以確保帳戶的安全。<br/><br/>※此E-Mail由系統自動發送，請勿回覆。<br/><br/>"
							+ "T-one遊戲論壇 敬上");
		} else {
			m.addAttribute("emailSendError", "該用戶不存在。");
			return "member/unLogin/member-forget-password";
		}

		attr.addFlashAttribute("updatePwdMsg", "密碼已寄送，請到您認證的信箱確認。");
		return "redirect:/login";
	}

	// 帳號啟用成功
	@GetMapping("/activeSuccess")
	public String activeSuccess() {

		// 確認管理者有無登入狀態
		if (null != session.getAttribute("currentManager")) {

			return "manager/manager-plzLogout";
		}

		// 確認使用者有無登入狀態
		if (null != session.getAttribute("currentUser")) {

			return "member/plzLogout";
		}

		return "member/unLogin/member-active-success";
	}

	// 帳號啟用失敗
	@GetMapping("/activeFail")
	public String activeFail() {

		// 確認管理者有無登入狀態
		if (null != session.getAttribute("currentManager")) {

			return "manager/manager-plzLogout";
		}

		// 確認使用者有無登入狀態
		if (null != session.getAttribute("currentUser")) {

			return "member/plzLogout";
		}

		return "member/unLogin/member-active-fail";
	}

	// 啟用帳號
	@GetMapping("/active")
	public String toActive(@RequestParam(name = "code") String code, HttpSession session) {

		// 確認管理者有無登入狀態
		if (null != session.getAttribute("currentManager")) {

			return "manager/manager-plzLogout";
		}

		// 確認使用者有無登入狀態
		if (null != session.getAttribute("currentUser")) {

			return "member/plzLogout";
		}

		User user = userService.findUserByCode(code);

		if (null != user) {
			user.setActive("是");
			user.setCode("");
			userService.save(user);
			return "member/unLogin/member-active-success";
		}

		return "member/unLogin/member-active-fail";
	}
}