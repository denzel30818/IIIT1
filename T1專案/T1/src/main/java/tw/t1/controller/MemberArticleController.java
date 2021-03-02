package tw.t1.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tw.t1.entity.Article;
import tw.t1.entity.User;
import tw.t1.service.ArticleLikeService;
import tw.t1.service.ArticleService;
import tw.t1.service.ArticleTrackService;
import tw.t1.service.CommentService;

@Controller
@RequestMapping("/member")
public class MemberArticleController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private ArticleTrackService atService;
	@Autowired
	private ArticleLikeService alService;

	@GetMapping("/article")
	public String goArticle(HttpSession session, Model m, HttpServletRequest request) {

		// 確認管理者有無登入狀態
		if (null != session.getAttribute("currentManager")) {
			return "manager/manager-no-right";
		}

		User currentUser = (User) session.getAttribute("currentUser");
		int userID = currentUser.getUserID(); // 暫存userID
		int userPostNum = (int) articleService.countByUserArticle(userID); // 取得使用者發文數量
		int totalPage = 0; // 總分頁數
		int paginationNum = 10; // 每頁數量
		if (userPostNum != 0) {
			if (userPostNum / paginationNum == 0) {
				totalPage = 1;
			} else {
				if ((userPostNum % paginationNum) == 0) {
					totalPage = (userPostNum / paginationNum);
				} else {
					totalPage = (userPostNum / paginationNum) + 1;
				}
			}
		} else {
			m.addAttribute("articleNotFound", "您還沒有任何文章");
			m.addAttribute("totalPage", totalPage);
			return "member/member-center-article";
		}

		int page = 0;
		if (request.getParameter("page") == null | "1".equals(request.getParameter("page"))) {
			m.addAttribute("articles", articleService.findArticlesByUserIDByDescForPage(userID, 0, paginationNum));
			m.addAttribute("totalPage", totalPage);
			page = 1;
		} else if (totalPage > 1) {
			page = Integer.parseInt(request.getParameter("page"));
			m.addAttribute("articles", articleService.findArticlesByUserIDByDescForPage(userID,
					(page - 1) * paginationNum, paginationNum));
			m.addAttribute("totalPage", totalPage);
		}

		m.addAttribute("page", page);

		return "member/member-center-article";
	}

	@PostMapping("/deleteArticle")
	public String delArticle(@RequestParam(name = "delID") String delID, HttpSession session) {

		// 確認管理者有無登入狀態
		if (null != session.getAttribute("currentManager")) {
			return "manager/manager-no-right";
		}

		int id = Integer.parseInt(delID);

		Article delArticle = articleService.findById(id);

		// 刪除該文章留言
		commentService.deleteCommentByArticleId(delArticle.getId());
		// 刪除該文章按讚
		alService.deleteLikeByArticleid(id);
		// 刪除該文章追蹤
		atService.deleteTrackByArticleid(id);
		// 刪除文章
		articleService.deleteById(id);

		return "redirect:/member/article";

	}

	@GetMapping("/articleSearch")
	public String articleSearch(@RequestParam("UserID") int UserID, @RequestParam("key") String title, Model m) {

		System.out.println(UserID);
		List<Article> articles = articleService.findArticlesByUserIdandTitleWithLike(UserID, title);
		m.addAttribute("articles", articles);

		return "member/member-center-article";
	}
}
