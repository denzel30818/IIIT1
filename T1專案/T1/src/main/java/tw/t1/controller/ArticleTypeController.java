package tw.t1.controller;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import tw.t1.entity.ForumType;
import tw.t1.service.ForumTypeService;

@Controller
@RequestMapping("/articletype")
public class ArticleTypeController {

	@Autowired
	private ForumTypeService forumTypeService;

	@GetMapping(path = "/upload")
	public String goUpdate(HttpSession session, Model m) {
		session.setAttribute("currentArticleType", new ForumType());

		return "manager/manager-articletype-upload";
	}

	// list
	@GetMapping(value = "/list")
	public String ArticleTypeList(Model model) {

		List<ForumType> forumType = forumTypeService.findAll();

		model.addAttribute("forumType", forumType);
		return "/manager/manager-articletype-list";

	}

	// save
	@PostMapping(path = "/save") // session.getAttribute(fileName)""user_id還沒加
	public String saveArticlieType(HttpSession session, ForumType currentArticleType,
			@RequestPart(name = "image") MultipartFile photo) throws IOException {

		/*--------取得上傳檔案的檔名存入Photo_uri-----------*/
		String fileName = photo.getOriginalFilename();

		/*------存檔-------*/
		forumTypeService.save(currentArticleType);
		currentArticleType.setPhotoUri("/images/forum/" + currentArticleType.getForumID() + "/" + fileName);
		forumTypeService.save(currentArticleType);
		/*------儲存圖片------*/
		String filePath = "C:/SpringBoot/workspace/T1/src/main/resources/static/images/forum/"
				+ currentArticleType.getForumID() + "/"; // 存檔路徑
		if (photo.getSize() > 0) { // 確認檔案不為空
			File dir = new File(filePath); // 建立檔案根目錄

			if (!dir.exists()) { // 如果根目錄不存在就建一個新的
				dir.mkdirs();
			}
		}
		photo.transferTo(new File(filePath + fileName));
		/*------以上都做完後移除session裡Products物件------*/

		session.removeAttribute("currentArticleType");
		return "redirect:/articletype/list";

	}

	// update論壇分類更新
	@GetMapping(value = "/update")
	public String updateForumType(@RequestParam("articletype_id") int Id, HttpSession session, Model m) {

		ForumType forumType = forumTypeService.findById(Id);

		session.setAttribute("currentArticleType", forumType); // 把選到的物件存到 session
		return "manager/manager-articletype-update";

	}

	// delete 刪除商品
	@GetMapping("/delete")
	public String deleteForumType(@RequestParam("articletype_id") int Id) {
		ForumType forumType = forumTypeService.findById(Id);
		/*------刪除圖檔---------*/
		String imgPath = "C:/SpringBoot/workspace/T1/src/main/resources/static" + forumType.getPhotoUri(); // 換資料夾要修改
		File dir = new File(imgPath);
		if (dir.exists()) {
			System.out.println(dir.delete());
		}
		/*------刪除以id命名的空資料夾------*/
		String imagePath = "C:/SpringBoot/workspace/T1" + forumType.getDeleteImagePath(); // 換資料夾要修改
		System.out.println("刪除資料夾路徑" + imagePath);
		File dir_test = new File(imagePath);
		if (dir_test.isDirectory() && dir_test.exists()) {
			System.out.println(dir_test.delete());
		}
		System.out.println("Directory deleted!");
		/*-------刪除資料-------*/
		forumTypeService.deleteById(Id);
		return "redirect:list";
	}

	// preview最後確認
	@PostMapping(value = "/preview")
	public String goPreview(HttpSession session, ForumType currentArticleType, HttpServletRequest req, Model m) {

		session.setAttribute("currentArticleType", currentArticleType);

		return "/manager/manager-articletype-preview";

	}

	// 更改確認
	@PostMapping(path = "/updateSubmit")
	public String updateProduct(HttpSession session, ForumType currentArticleType, HttpServletRequest request)
			throws IOException {
		// 存檔
		forumTypeService.save(currentArticleType);
		/*------以上都做完後移除session裡Products物件------*/

		session.removeAttribute("currentArticleType");
		return "redirect:list";

	}

}
