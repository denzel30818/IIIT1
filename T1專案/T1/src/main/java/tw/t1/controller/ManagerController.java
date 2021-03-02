package tw.t1.controller;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tw.t1.entity.Feedback;
import tw.t1.entity.User;
import tw.t1.service.EmailSenderService;
import tw.t1.service.FeedbackService;
import tw.t1.service.UserService;

@Controller
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	private UserService userService;
	@Autowired
	private FeedbackService fbService;
	@Autowired
	private EmailSenderService emailSendService;
	@Autowired
	private HttpSession session;

	// 管理系統
	@GetMapping("/system")
	public String goSystem() {

		// 確認管理者有無登入狀態
		if (null != session.getAttribute("currentManager")) {
			return "manager/manager-system";
		}

		// 確認使用者有無登入狀態
		if (null != session.getAttribute("currentUser")) {
			return "member/member-no-right";
		}

		return "member/unLogin/indexN";

	}

	// 會員管理
	@GetMapping("/memberUpdate")
	public String goMemberUpdate(Model m, HttpServletRequest request) {

		// 確認管理者有無登入狀態
		if (null == session.getAttribute("currentManager")) {
			return "member/member-no-right";
		}

		List<User> users = userService.findAll();
		int totalPage = 0; // 總分頁數
		int paginationNum = 10; // 每頁數量
		int totalUser = users.size();

		if (totalUser != 0) {
			if (totalUser / paginationNum == 0) {
				totalPage = 1;
			} else {
				if ((totalUser % paginationNum) == 0) {
					totalPage = (totalUser / paginationNum);
				} else {
					totalPage = (totalUser / paginationNum) + 1;
				}
			}
		} else {
			m.addAttribute("totalPage", totalPage);
			return "manager/manager-member-update";
		}

		int page = 0;
		if (request.getParameter("page") == null | "1".equals(request.getParameter("page"))) {
			m.addAttribute("users", userService.findUserByLimit(0, paginationNum));
			m.addAttribute("totalPage", totalPage);
			page = 1;
		} else if (totalPage > 1) {
			page = Integer.parseInt(request.getParameter("page"));
			m.addAttribute("users", userService.findUserByLimit((page - 1) * paginationNum, paginationNum));
			m.addAttribute("totalPage", totalPage);
		}

		m.addAttribute("page", page);

		return "manager/manager-member-update";

	}

	@GetMapping("/memberBanned")
	public String toMemberBirthday(Model m, HttpServletRequest request) {

		// 確認管理者有無登入狀態
		if (null == session.getAttribute("currentManager")) {
			return "member/member-no-right";
		}

		List<User> users = userService.findUserByBanned();
		int totalPage = 0; // 總分頁數
		int paginationNum = 10; // 每頁數量
		int totalUser = users.size();

		if (totalUser != 0) {
			if (totalUser / paginationNum == 0) {
				totalPage = 1;
			} else {
				if ((totalUser % paginationNum) == 0) {
					totalPage = (totalUser / paginationNum);
				} else {
					totalPage = (totalUser / paginationNum) + 1;
				}
			}
		} else {
			m.addAttribute("totalPage", totalPage);
			return "manager/manager-member-banned";
		}

		int page = 0;
		if (request.getParameter("page") == null | "1".equals(request.getParameter("page"))) {
			m.addAttribute("users", userService.findUserByBannedLimit(0, paginationNum));
			m.addAttribute("totalPage", totalPage);
			page = 1;
		} else if (totalPage > 1) {
			page = Integer.parseInt(request.getParameter("page"));
			m.addAttribute("users", userService.findUserByBannedLimit((page - 1) * paginationNum, paginationNum));
			m.addAttribute("totalPage", totalPage);
		}

		m.addAttribute("page", page);

		return "manager/manager-member-banned";
	}

	@GetMapping("/memberBirthday")
	public String goMemberBirthday(Model m, HttpServletRequest request) {

		// 確認管理者有無登入狀態
		if (null == session.getAttribute("currentManager")) {
			return "member/member-no-right";
		}

		List<User> users = userService.findUserByBirthDateWithUnbannedThisMonth();
		int totalPage = 0; // 總分頁數
		int paginationNum = 10; // 每頁數量
		int totalUser = users.size();

		if (totalUser != 0) {
			if (totalUser / paginationNum == 0) {
				totalPage = 1;
			} else {
				if ((totalUser % paginationNum) == 0) {
					totalPage = (totalUser / paginationNum);
				} else {
					totalPage = (totalUser / paginationNum) + 1;
				}
			}
		} else {
			m.addAttribute("totalPage", totalPage);
			return "manager/manager-member-birthday";
		}

		int page = 0;
		if (request.getParameter("page") == null | "1".equals(request.getParameter("page"))) {
			m.addAttribute("users", userService.findUserByBirthDateWithUnbannedThisMonthLimit(0, paginationNum));
			m.addAttribute("totalPage", totalPage);
			page = 1;
		} else if (totalPage > 1) {
			page = Integer.parseInt(request.getParameter("page"));
			m.addAttribute("users", userService
					.findUserByBirthDateWithUnbannedThisMonthLimit((page - 1) * paginationNum, paginationNum));
			m.addAttribute("totalPage", totalPage);
		}

		m.addAttribute("page", page);

		return "manager/manager-member-birthday";
	}

	@GetMapping("/userSearchUpdate")
	public String userSearch(@RequestParam("key") String key, HttpSession session, Model m) {

		if (null != session.getAttribute("currentManager")) {
			List<User> users = userService.findUserWithLike(key);
			m.addAttribute("users", users);
			return "manager/manager-member-update";
		}

		return "member/member-no-right";
	}

	@GetMapping("/userSearchBirthday")
	public String userSearchBirthday(@RequestParam("key") String key, HttpSession session, Model m) {

		if (null != session.getAttribute("currentManager")) {
			List<User> users = userService.findUserByBirthDateWithUnbannedThisMonthByKey(key);
			m.addAttribute("users", users);
			return "manager/manager-member-birthday";
		}

		return "member/member-no-right";
	}

	@GetMapping("/userSearchBanned")
	public String userSearchBanned(@RequestParam("key") String key, HttpSession session, Model m) {

		if (null != session.getAttribute("currentManager")) {
			List<User> users = userService.findUserByBannedByKey(key);
			m.addAttribute("users", users);
			return "manager/manager-member-banned";
		}

		return "member/member-no-right";
	}

	/* 0212家寶 */
	@GetMapping("/feedback")
	public String goFeedback(Model m, HttpServletRequest request) {

		// 確認管理者有無登入狀態
		if (null == session.getAttribute("currentManager")) {
			return "member/member-no-right";
		}
		String st = "wait";
		String state = "待處理";

		if ("complete".equals(request.getParameter("state"))) {
			state = "已回覆";
			st = "complete";
		}

		List<Feedback> fbs = fbService.findFeedbackByState(state);
		int totalPage = 0; // 總分頁數
		int NumOfFb = 10; // 每頁數量
		int totalfb = fbs.size();
		int page;

		if (totalfb != 0) {
			if (totalfb / NumOfFb == 0) {
				totalPage = 1;
			} else {
				if ((totalfb % NumOfFb) == 0) {
					totalPage = (totalfb / NumOfFb);
				} else {
					totalPage = (totalfb / NumOfFb) + 1;
				}
			}
		}

		List<Feedback> pgfbs = new ArrayList<Feedback>();

		if (request.getParameter("page") == null) {
			page = 1;
		} else {
			page = Integer.parseInt(request.getParameter("page"));
		}

		if (page == 1) {
			if (totalfb < NumOfFb) {
				for (int i = 0; i < totalfb; i++) {
					pgfbs.add(fbs.get(i));
				}
			} else {
				for (int i = 0; i < NumOfFb; i++) {
					pgfbs.add(fbs.get(i));
				}
			}
		} else if (page == totalPage) {
			for (int i = NumOfFb * (page - 1); i < totalfb; i++) {
				pgfbs.add(fbs.get(i));
			}
		} else {
			for (int i = NumOfFb * (page - 1); i < NumOfFb * page; i++) {
				pgfbs.add(fbs.get(i));
			}
		}

		m.addAttribute("state", st);
		m.addAttribute("feedback", pgfbs);
		m.addAttribute("page", page);
		m.addAttribute("totalPage", totalPage);

		return "manager/manager-member-feedback";
	}

	@PostMapping("/replyfeedback")
	public String replyfeedback(HttpServletRequest request) throws MessagingException {

		int id = Integer.parseInt(request.getParameter("id"));
		String managerName = request.getParameter("manager_name");
		String content = request.getParameter("replycontent");

		Feedback fb = fbService.findById(id);
		fb.setReply(content);
		fb.setManager_name(managerName);
		fb.setState("已回覆");
		fbService.save(fb);

		emailSendService.sendHtmlEmail(fb.getEmail(), "T-one 問題回覆", fb.getUser_name() + " 您好:<br/><br/>針對您的提問 : "
				+ fb.getContent() + "<br/><br/>" + content + "<br/><br/>非常感謝您的回饋!<br/><br/>T-one遊戲論壇 敬上");

		return "redirect:/manager/feedback";
	}
	/* 0212家寶 */

}
