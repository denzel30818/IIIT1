package tw.t1.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tw.t1.entity.Article;
import tw.t1.entity.ArticleTrack;
import tw.t1.entity.User;
import tw.t1.service.ArticleService;
import tw.t1.service.ArticleTrackService;

@Controller
@RequestMapping("/member")
public class MemberCollectionController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleTrackService articleTrackService;

	@GetMapping("/collectionList")
	public String goCollectionList(HttpSession session, Model m, HttpServletRequest request) {

		// 確認管理者有無登入狀態
		if (null != session.getAttribute("currentManager")) {
			return "manager/manager-no-right";
		}

		User currentUser = (User) session.getAttribute("currentUser");
		// 暫存userID
		int userID = currentUser.getUserID();
		// 找出使用者追蹤的清單 (倒序)
		List<ArticleTrack> articleTracks = articleTrackService.findArticletrackByuserIdandOrderByIdDesc(userID);
		// 用來存放有追蹤的文章
		List<Article> userTrack = new ArrayList<>();
		// 個別找出使用者追蹤的文章
		for (ArticleTrack tempArticleTrack : articleTracks) {
			Article tempArticle = articleService.findById(tempArticleTrack.getArticle_id());
			userTrack.add(tempArticle);
		}

		int userTrackNum = userTrack.size(); // 取得使用者追蹤文章數量
		int totalPage = 0; // 總分頁數
		int paginationNum = 10; // 每頁數量
		if (userTrackNum != 0) {
			if (userTrackNum / paginationNum == 0) {
				totalPage = 1;
			} else {
				if ((userTrackNum % paginationNum) == 0) {
					totalPage = (userTrackNum / paginationNum);
				} else {
					totalPage = (userTrackNum / paginationNum) + 1;
				}
			}
		} else {
			m.addAttribute("articleNotFound", "您還沒有收藏任何文章");
			m.addAttribute("totalPage", totalPage);
			return "member/member-center-collection-list";
		}

		List<Article> articles = new ArrayList<>();
		int page = 0;
		if (request.getParameter("page") == null | "1".equals(request.getParameter("page"))) {
			if (totalPage == 1) {
				for (int i = 0; i < userTrackNum; i++) {
					userTrack.get(i).setAdd_time(articleTracks.get(i).getAdd_time());
					articles.add(userTrack.get(i));
				}
			} else if (totalPage != 1) {
				for (int i = 0; i < paginationNum; i++) {
					userTrack.get(i).setAdd_time(articleTracks.get(i).getAdd_time());
					articles.add(userTrack.get(i));
				}
			}
			m.addAttribute("articles", articles);
			m.addAttribute("totalPage", totalPage);
			page = 1;
		} else if (totalPage > 1) {
			page = Integer.parseInt(request.getParameter("page"));
			if (page < totalPage) {
				for (int i = (page - 1) * paginationNum; i < ((page - 1) * paginationNum) + paginationNum; i++) {
					userTrack.get(i).setAdd_time(articleTracks.get(i).getAdd_time());
					articles.add(userTrack.get(i));
				}
			} else if (page == totalPage) {
				for (int i = (page - 1) * paginationNum; i < userTrack.size(); i++) {
					userTrack.get(i).setAdd_time(articleTracks.get(i).getAdd_time());
					articles.add(userTrack.get(i));
				}
			}
			m.addAttribute("articles", articles);
			m.addAttribute("totalPage", totalPage);
		}

		m.addAttribute("page", page);

		return "member/member-center-collection-list";
	}

	@GetMapping("/trackSearch")
	public String articleTrackSearch(@RequestParam("UserID") int UserID, @RequestParam("key") String title, Model m) {

		// 找出使用者追蹤清單 (已篩選過)
		List<ArticleTrack> articleTracks = articleTrackService.findArticleTrackByUserIdandTitleWithLikeDesc(UserID,
				title);
		List<Article> userTrack = new ArrayList<>();
		for (ArticleTrack tempArticleTrack : articleTracks) {
			Article tempArticle = articleService.findById(tempArticleTrack.getArticle_id());
			userTrack.add(tempArticle);
		}
		for (int i = 0; i < userTrack.size(); i++) {
			userTrack.get(i).setAdd_time(articleTracks.get(i).getAdd_time());
		}
		m.addAttribute("articles", userTrack);

		return "member/member-center-collection-list";
	}

}
