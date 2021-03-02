package tw.t1.controller;

import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
import tw.t1.entity.User;
import tw.t1.service.EmailSenderService;
import tw.t1.service.UserService;

@Slf4j
@Controller
public class RegisterController {

	@Autowired
	private UserService us;
	@Autowired
	private EmailSenderService es;

	@GetMapping("/register")
	public String registerProcess(HttpSession session) {

		if (null != session.getAttribute("currentManager")) { // 如果管理者已登入，跳到請求登出頁面
			return "manager/manager-plzLogout";
		}

		if (null != session.getAttribute("currentUser")) { // 如果使用者已登入，跳到請求登出頁面
			return "member/plzLogout";
		}

		return "member/unLogin/register";
	}

	@PostMapping("/register")
	public String register(User user, Model m, HttpSession session) throws MessagingException {

		boolean accountNotExists = true;
		boolean nickNameNotExists = true;
		boolean emailNotExists = true;

		List<User> users = us.findAll();
		for (User tempUser : users) {
			if (user.getAccount().equals(tempUser.getAccount())) {
				accountNotExists = false;
				m.addAttribute("errorAccount", "帳號已被使用");
			}
			if (user.getNickName().equals(tempUser.getNickName())) {
				nickNameNotExists = false;
				m.addAttribute("errorNickName", "暱稱已被使用");
			}
			if (user.getEmail().equals(tempUser.getEmail())) {
				emailNotExists = false;
				m.addAttribute("errorEmail", "信箱已被使用");
			}
		}

		if (accountNotExists && emailNotExists && nickNameNotExists) { // 帳號和信箱皆可使用就註冊存到資料庫

			user.setUserID(0);
//			user.setMemberPhotoUri("/images/member/test.png"); // 設定預設頭像
			String code = UUID.randomUUID().toString();
			user.setCode(code);
			us.save(user); // save 完已抓到新用戶 id，可直接抓取
//			session.setAttribute("currentUser", user);
			log.info("新用戶資料 :" + user.toString());
			es.sendHtmlEmail("leonpower117@gmail.com", "Welcome", user.getNickName()
					+ "，歡迎使用 <b>T-one!</b><br/><br/>請點擊以下鏈結來啟用您的帳戶。<br/><br/><a href='http://localhost:8080/active?code="
					+ code + "'>http://localhost:8080/active?code=" + code + "'</a>");
			return "redirect:/registerSuccess";
		}

		return "member/unLogin/register";
	}

	@GetMapping("/registerSuccess")
	public String registerSuccess() {

		return "member/unLogin/member-register-success";
	}
}
