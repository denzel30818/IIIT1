package tw.t1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tw.t1.entity.ForumType;
import tw.t1.service.ForumTypeService;

@Controller
@RequestMapping("/forum")
public class ForumController {

	@Autowired
	private ForumTypeService forumTypeService;

	// PC論壇版頁面
	@GetMapping("/pc")
	public String pcForum(Model m) {

		List<ForumType> PCForum = forumTypeService.findByCategory("PC");
		m.addAttribute("forum", PCForum);

		return "article/article-forum-list";
	}

	// TV論壇版頁面
	@GetMapping("/tv")
	public String tvForum(Model m) {

		List<ForumType> TVForum = forumTypeService.findByCategory("TV");
		m.addAttribute("forum", TVForum);

		return "article/article-forum-list";
	}

	// MOBILE論壇版頁面
	@GetMapping("/mobile")
	public String mobileForum(Model m) {

		List<ForumType> MOBForum = forumTypeService.findByCategory("MOB");
		m.addAttribute("forum", MOBForum);

		return "/article/article-forum-list";
	}

	// 搜尋功能
	@GetMapping("/list/search")
	public String forumSearch(Model m, @RequestParam("key") String key) {

		List<ForumType> forumType = forumTypeService.searchForumByForumName(key);
		m.addAttribute("forum", forumType);

		return "/article/article-forum-list";
	}
}
