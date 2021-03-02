package tw.t1.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tw.t1.entity.User;
import tw.t1.service.UserService;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private UserService us;
	@Autowired
	private HttpSession session;

	@GetMapping("/info")
	public String goInfo() {

		// 確認管理者有無登入狀態
		if (null != session.getAttribute("currentManager")) {
			return "manager/manager-no-right";
		}

		return "member/member-center-info";
	}

	@PostMapping("/info")
	public String updatePhoto(@RequestPart("upload_img") MultipartFile avatar, HttpSession session)
			throws IllegalStateException, IOException {

		User currentUser = (User) session.getAttribute("currentUser");

		String rootPath = "C:/SpringBoot/workspace/T1/src/main/resources/static/images/member/";
		String oldFilePath = currentUser.getMemberPhotoUri(); // 取的舊檔案相對路徑
		String oldFileName = oldFilePath.substring(oldFilePath.lastIndexOf("/") + 1, oldFilePath.length()); // 取得舊檔名
		if (!"joker.png".equals(oldFileName)) { // 刪除舊頭像
			File delFile = new File(rootPath + oldFileName);
			delFile.delete();
		}

		if (avatar.getSize() > 0) {
			File dir = new File(rootPath);

			if (!dir.exists()) { // 如果根目錄不存在就建一個新的
				dir.mkdirs();
			}
		}

		String fileName = avatar.getOriginalFilename(); // 取得檔案名稱
		int dot = fileName.lastIndexOf("."); // 取得副檔名索引
		long timeNow = new Date().getTime(); // 取得現在時間
		String newFileName = fileName.substring(0, dot) + timeNow + fileName.substring(dot, fileName.length()); // 新檔名加上時間戳記避免重名
		String filePath = rootPath + newFileName; // 存檔路徑
		avatar.transferTo(new File(filePath)); // 檔名加上時間戳記避免重名
		currentUser.setMemberPhotoUri("/images/member/" + newFileName); // 更新圖片路徑
		us.save(currentUser);

		return "redirect:/member/info";
	}

	@GetMapping("/password")
	public String goPassword() {

		// 確認管理者有無登入狀態
		if (null != session.getAttribute("currentManager")) {
			return "manager/manager-no-right";
		}

		return "member/member-center-password";
	}

	@PostMapping("/password")
	public String updatePassword(@RequestParam("userPassword") String oldPwd,
			@RequestParam("newPassword") String newPwd, @RequestParam("checkNewPassword") String checkNewPwd,
			HttpSession session, RedirectAttributes ra) {

		User currentUser = (User) session.getAttribute("currentUser");

		if (oldPwd.equals(currentUser.getUserPassword())) { // 如果舊密碼驗證成功就檢查新密碼有沒有輸入相同
			if (newPwd.equals(checkNewPwd)) {
				currentUser.setUserPassword(newPwd);
				us.save(currentUser);
				ra.addFlashAttribute("updatePwdMsg", "密碼修改成功，請重新登入。");
				session.removeAttribute("currentUser"); // 移除登入憑證
				return "redirect:/login";
			} else {
				ra.addFlashAttribute("updatePwdMsg", "兩次輸入的密碼不相同"); // JS關閉時的錯誤訊息
			}
		} else {
			ra.addFlashAttribute("updatePwdMsg", "舊密碼輸入錯誤"); // JS關閉時的錯誤訊息
		}
		return "redirect:/member/password";
	}

//	@GetMapping("/point")
//	public String goPoint() {
//
//		// 確認管理者有無登入狀態
//		if (null != session.getAttribute("currentManager")) {
//			return "manager/manager-no-right";
//		}
//
//		return "member/member-center-point";
//	}

}
