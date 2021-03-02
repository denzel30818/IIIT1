package tw.t1.controller;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import tw.t1.entity.Manager;
import tw.t1.entity.News;
import tw.t1.entity.NewsComment;
import tw.t1.entity.User;
import tw.t1.service.NewsCommentService;
import tw.t1.service.NewsService;

@Controller
@RequestMapping("/news")
public class NewsController {

	@Autowired
	private NewsService newsService;

	@Autowired
	private NewsCommentService newsCommentService;

	// 新聞首頁 "/list"
	@GetMapping("/list")
	public String listNews(Model m, HttpSession session, HttpServletRequest request) {

		// 管理者進新聞管理頁面(後台)
		if (null != session.getAttribute("currentManager")) {
			List<News> newsFind = newsService.findAllAndOrderByUpdateTimeDesc();// 搜尋全部並以時間排序
			List<News> newsFindPC = newsService.findNewsByTypeWithUpdateTime("PC");// 參數為PC
			List<News> newsFindMobile = newsService.findNewsByTypeWithUpdateTime("手機");// 參數為手機
			List<News> newsFindTV = newsService.findNewsByTypeWithUpdateTime("TV");// 參數為TV
			List<News> newsFindReport = newsService.findNewsByTypeWithUpdateTime("主題報導");// 參數為主題報導
			// 分頁
			int totalPage = 0; // 總分頁數
			int totalPagePC = 0; // 總分頁數
			int totalPageMobile = 0; // 總分頁數
			int totalPageTV = 0; // 總分頁數
			int totalPageReport = 0; // 總分頁數
			int paginationNum = 10; // 每頁數量
			int totalnews = newsFind.size();
			int totalnewsPC = newsFindPC.size();
			int totalnewsMobile = newsFindMobile.size();
			int totalnewsTV = newsFindTV.size();
			int totalnewsReport = newsFindReport.size();
			/* 全部 */
			if (totalnews != 0) {
				if (totalnews / paginationNum == 0) {
					totalPage = 1;
				} else {
					if ((totalnews % paginationNum) == 0) {
						totalPage = (totalnews / paginationNum);
					} else {
						totalPage = (totalnews / paginationNum) + 1;
					}
				}
				/* PC */
				if (totalnewsPC != 0) {
					if (totalnewsPC / paginationNum == 0) {
						totalPagePC = 1;
					} else {
						if ((totalnewsPC % paginationNum) == 0) {
							totalPagePC = (totalnewsPC / paginationNum);
						} else {
							totalPagePC = (totalnewsPC / paginationNum) + 1;
						}
					}
				}
				/* Mobile */
				if (totalnewsMobile != 0) {
					if (totalnewsMobile / paginationNum == 0) {
						totalPageMobile = 1;
					} else {
						if ((totalnewsMobile % paginationNum) == 0) {
							totalPageMobile = (totalnewsMobile / paginationNum);
						} else {
							totalPageMobile = (totalnewsMobile / paginationNum) + 1;
						}
					}
				}
				/* TV */
				if (totalnewsTV != 0) {
					if (totalnewsTV / paginationNum == 0) {
						totalPageTV = 1;
					} else {
						if ((totalnewsTV % paginationNum) == 0) {
							totalPageTV = (totalnewsTV / paginationNum);
						} else {
							totalPageTV = (totalnewsTV / paginationNum) + 1;
						}
					}
				}
				/* Report */
				if (totalnewsReport != 0) {
					if (totalnewsReport / paginationNum == 0) {
						totalPageReport = 1;
					} else {
						if ((totalnewsReport % paginationNum) == 0) {
							totalPageReport = (totalnewsReport / paginationNum);
						} else {
							totalPageReport = (totalnewsReport / paginationNum) + 1;
						}
					}
				}
			} else {
				m.addAttribute("totalPage", totalPage);
				return "news/news-Home";
			}
			int page = 0;
			if (request.getParameter("page") == null) {
				page = 1;
			} else {
				page = Integer.parseInt(request.getParameter("page"));
			}
			/* 全部 */
			List<News> news = new ArrayList<News>();
			if (page == 1) {
				if (totalnews < paginationNum) {
					for (int i = 0; i < totalnews; i++) {
						news.add(newsFind.get(i));
					}
				} else {
					for (int i = 0; i < paginationNum; i++) {
						news.add(newsFind.get(i));
					}
				}
				// 分頁 若是最後一頁(數量可能不足15)
			} else if (page == totalPage) {
				for (int i = (page - 1) * paginationNum; i < totalnews; i++) {
					news.add(newsFind.get(i));
				}
			} else {
				// 分頁 其他會剛剛好15
				for (int i = (page - 1) * paginationNum; i < page * paginationNum; i++) {
					news.add(newsFind.get(i));
				}
			}
			/* PC */
			int pagePC = 0;
			if (request.getParameter("pagePC") == null) {
				pagePC = 1;
			} else {
				pagePC = Integer.parseInt(request.getParameter("pagePC"));
			}
			List<News> newsPC = new ArrayList<News>();
			if (pagePC == 1) {
				if (totalnewsPC < paginationNum) {
					for (int i = 0; i < totalnewsPC; i++) {
						newsPC.add(newsFindPC.get(i));
					}
				} else {
					for (int i = 0; i < paginationNum; i++) {
						newsPC.add(newsFindPC.get(i));
					}
				}
				// 分頁 若是最後一頁(數量可能不足15)
			} else if (pagePC == totalPagePC) {
				for (int i = (pagePC - 1) * paginationNum; i < totalnewsPC; i++) {
					newsPC.add(newsFindPC.get(i));
				}
			} else {
				// 分頁 其他會剛剛好15
				for (int i = (pagePC - 1) * paginationNum; i < pagePC * paginationNum; i++) {
					newsPC.add(newsFindPC.get(i));
				}
			}
			/* Mobile */
			int pageMobile = 0;
			if (request.getParameter("pageMobile") == null) {
				pageMobile = 1;
			} else {
				pageMobile = Integer.parseInt(request.getParameter("pageMobile"));
			}
			List<News> newsMobile = new ArrayList<News>();
			if (pageMobile == 1) {
				if (totalnewsMobile < paginationNum) {
					for (int i = 0; i < totalnewsMobile; i++) {
						newsMobile.add(newsFindMobile.get(i));
					}
				} else {
					for (int i = 0; i < paginationNum; i++) {
						newsMobile.add(newsFindMobile.get(i));
					}
				}
				// 分頁 若是最後一頁(數量可能不足15)
			} else if (pageMobile == totalPageMobile) {
				for (int i = (pageMobile - 1) * paginationNum; i < totalnewsMobile; i++) {
					newsMobile.add(newsFindMobile.get(i));
				}
			} else {
				// 分頁 其他會剛剛好15
				for (int i = (pageMobile - 1) * paginationNum; i < pageMobile * paginationNum; i++) {
					newsMobile.add(newsFindMobile.get(i));
				}
			}
			/* TV */
			int pageTV = 0;
			if (request.getParameter("pageTV") == null) {
				pageTV = 1;
			} else {
				pageTV = Integer.parseInt(request.getParameter("pageTV"));
			}
			List<News> newsTV = new ArrayList<News>();
			if (pageTV == 1) {
				if (totalnewsTV < paginationNum) {
					for (int i = 0; i < totalnewsTV; i++) {
						newsTV.add(newsFindTV.get(i));
					}
				} else {
					for (int i = 0; i < paginationNum; i++) {
						newsTV.add(newsFindTV.get(i));
					}
				}
				// 分頁 若是最後一頁(數量可能不足15)
			} else if (pageTV == totalPageTV) {
				for (int i = (pageTV - 1) * paginationNum; i < totalnewsTV; i++) {
					newsTV.add(newsFindTV.get(i));
				}
			} else {
				// 分頁 其他會剛剛好15
				for (int i = (pageTV - 1) * paginationNum; i < pageTV * paginationNum; i++) {
					newsTV.add(newsFindTV.get(i));
				}
			}
			/* Report */
			int pageReport = 0;
			if (request.getParameter("pageReport") == null) {
				pageReport = 1;
			} else {
				pageReport = Integer.parseInt(request.getParameter("pageReport"));
			}
			List<News> newsReport = new ArrayList<News>();
			if (pageReport == 1) {
				if (totalnewsReport < paginationNum) {
					for (int i = 0; i < totalnewsReport; i++) {
						newsReport.add(newsFindReport.get(i));
					}
				} else {
					for (int i = 0; i < paginationNum; i++) {
						newsReport.add(newsFindReport.get(i));
					}
				}
				// 分頁 若是最後一頁(數量可能不足15)
			} else if (pageReport == totalPageReport) {
				for (int i = (pageReport - 1) * paginationNum; i < totalnewsReport; i++) {
					newsReport.add(newsFindReport.get(i));
				}
			} else {
				// 分頁 其他會剛剛好15
				for (int i = (pageReport - 1) * paginationNum; i < pageReport * paginationNum; i++) {
					newsReport.add(newsFindReport.get(i));
				}
			}
			m.addAttribute("page", page);
			m.addAttribute("pagePC", pagePC);
			m.addAttribute("pageMobile", pageMobile);
			m.addAttribute("pageTV", pageTV);
			m.addAttribute("pageReport", pageReport);

			m.addAttribute("totalPage", totalPage);
			m.addAttribute("totalPagePC", totalPagePC);
			m.addAttribute("totalPageMobile", totalPageMobile);
			m.addAttribute("totalPageTV", totalPageTV);
			m.addAttribute("totalPageReport", totalPageReport);

			m.addAttribute("news", news);
			m.addAttribute("newsPC", newsPC);// 傳遞newsPC變數到news-Home頁面 其值為newsFindPC
			m.addAttribute("newsMobile", newsMobile);
			m.addAttribute("newsTV", newsTV);
			m.addAttribute("newsReport", newsReport);
			return "news/manager-news-Home";
		}
		// 一般使用者進新聞首頁(前台)
		List<News> newsFindTop8 = newsService.findAllAndOrderByUpdateTimeDescTop8();
		List<News> newsFind = newsService.findAllAndOrderByUpdateTimeDesc();// 搜尋全部並以時間排序
		List<News> newsFindPC = newsService.findNewsByTypeWithUpdateTime("PC");// 參數為PC
		List<News> newsFindMobile = newsService.findNewsByTypeWithUpdateTime("手機");// 參數為手機
		List<News> newsFindTV = newsService.findNewsByTypeWithUpdateTime("TV");// 參數為TV
		List<News> newsFindReport = newsService.findNewsByTypeWithUpdateTime("主題報導");// 參數為主題報導
		// 分頁
		int totalPage = 0; // 總分頁數
		int totalPagePC = 0; // 總分頁數
		int totalPageMobile = 0; // 總分頁數
		int totalPageTV = 0; // 總分頁數
		int totalPageReport = 0; // 總分頁數

		int paginationNum = 10; // 每頁數量

		int totalnews = newsFind.size();
		int totalnewsPC = newsFindPC.size();
		int totalnewsMobile = newsFindMobile.size();
		int totalnewsTV = newsFindTV.size();
		int totalnewsReport = newsFindReport.size();

		/* 全部 */
		if (totalnews != 0) {
			if (totalnews / paginationNum == 0) {
				totalPage = 1;
			} else {
				if ((totalnews % paginationNum) == 0) {
					totalPage = (totalnews / paginationNum);
				} else {
					totalPage = (totalnews / paginationNum) + 1;
				}
			}
			/* PC */
			if (totalnewsPC != 0) {
				if (totalnewsPC / paginationNum == 0) {
					totalPagePC = 1;
				} else {
					if ((totalnewsPC % paginationNum) == 0) {
						totalPagePC = (totalnewsPC / paginationNum);
					} else {
						totalPagePC = (totalnewsPC / paginationNum) + 1;
					}
				}
			}
			/* Mobile */
			if (totalnewsMobile != 0) {
				if (totalnewsMobile / paginationNum == 0) {
					totalPageMobile = 1;
				} else {
					if ((totalnewsMobile % paginationNum) == 0) {
						totalPageMobile = (totalnewsMobile / paginationNum);
					} else {
						totalPageMobile = (totalnewsMobile / paginationNum) + 1;
					}
				}
			}
			/* TV */
			if (totalnewsTV != 0) {
				if (totalnewsTV / paginationNum == 0) {
					totalPageTV = 1;
				} else {
					if ((totalnewsTV % paginationNum) == 0) {
						totalPageTV = (totalnewsTV / paginationNum);
					} else {
						totalPageTV = (totalnewsTV / paginationNum) + 1;
					}
				}
			}
			/* Report */
			if (totalnewsReport != 0) {
				if (totalnewsReport / paginationNum == 0) {
					totalPageReport = 1;
				} else {
					if ((totalnewsReport % paginationNum) == 0) {
						totalPageReport = (totalnewsReport / paginationNum);
					} else {
						totalPageReport = (totalnewsReport / paginationNum) + 1;
					}
				}
			}

		} else {
			m.addAttribute("totalPage", totalPage);

			return "news/news-Home";
		}

		int page = 0;
		if (request.getParameter("page") == null) {
			page = 1;
		} else {
			page = Integer.parseInt(request.getParameter("page"));
		}

		/* 全部 */
		List<News> news = new ArrayList<News>();

		if (page == 1) {
			if (totalnews < paginationNum) {
				for (int i = 0; i < totalnews; i++) {
					news.add(newsFind.get(i));
				}
			} else {
				for (int i = 0; i < paginationNum; i++) {
					news.add(newsFind.get(i));
				}
			}
			// 分頁 若是最後一頁(數量可能不足15)
		} else if (page == totalPage) {
			for (int i = (page - 1) * paginationNum; i < totalnews; i++) {
				news.add(newsFind.get(i));
			}
		} else {
			// 分頁 其他會剛剛好15
			for (int i = (page - 1) * paginationNum; i < page * paginationNum; i++) {
				news.add(newsFind.get(i));
			}
		}

		/* PC */

		int pagePC = 0;
		if (request.getParameter("pagePC") == null) {
			pagePC = 1;
		} else {
			pagePC = Integer.parseInt(request.getParameter("pagePC"));
		}

		List<News> newsPC = new ArrayList<News>();

		if (pagePC == 1) {
			if (totalnewsPC < paginationNum) {
				for (int i = 0; i < totalnewsPC; i++) {
					newsPC.add(newsFindPC.get(i));
				}
			} else {
				for (int i = 0; i < paginationNum; i++) {
					newsPC.add(newsFindPC.get(i));
				}
			}
			// 分頁 若是最後一頁(數量可能不足15)
		} else if (pagePC == totalPagePC) {
			for (int i = (pagePC - 1) * paginationNum; i < totalnewsPC; i++) {
				newsPC.add(newsFindPC.get(i));
			}
		} else {
			// 分頁 其他會剛剛好15
			for (int i = (pagePC - 1) * paginationNum; i < pagePC * paginationNum; i++) {
				newsPC.add(newsFindPC.get(i));
			}
		}

		/* Mobile */

		int pageMobile = 0;
		if (request.getParameter("pageMobile") == null) {
			pageMobile = 1;
		} else {
			pageMobile = Integer.parseInt(request.getParameter("pageMobile"));
		}

		List<News> newsMobile = new ArrayList<News>();

		if (pageMobile == 1) {
			if (totalnewsMobile < paginationNum) {
				for (int i = 0; i < totalnewsMobile; i++) {
					newsMobile.add(newsFindMobile.get(i));
				}
			} else {
				for (int i = 0; i < paginationNum; i++) {
					newsMobile.add(newsFindMobile.get(i));
				}
			}
			// 分頁 若是最後一頁(數量可能不足15)
		} else if (pageMobile == totalPageMobile) {
			for (int i = (pageMobile - 1) * paginationNum; i < totalnewsMobile; i++) {
				newsMobile.add(newsFindMobile.get(i));
			}
		} else {
			// 分頁 其他會剛剛好15
			for (int i = (pageMobile - 1) * paginationNum; i < pageMobile * paginationNum; i++) {
				newsMobile.add(newsFindMobile.get(i));
			}
		}

		/* TV */

		int pageTV = 0;
		if (request.getParameter("pageTV") == null) {
			pageTV = 1;
		} else {
			pageTV = Integer.parseInt(request.getParameter("pageTV"));
		}

		List<News> newsTV = new ArrayList<News>();

		if (pageTV == 1) {
			if (totalnewsTV < paginationNum) {
				for (int i = 0; i < totalnewsTV; i++) {
					newsTV.add(newsFindTV.get(i));
				}
			} else {
				for (int i = 0; i < paginationNum; i++) {
					newsTV.add(newsFindTV.get(i));
				}
			}
			// 分頁 若是最後一頁(數量可能不足15)
		} else if (pageTV == totalPageTV) {
			for (int i = (pageTV - 1) * paginationNum; i < totalnewsTV; i++) {
				newsTV.add(newsFindTV.get(i));
			}
		} else {
			// 分頁 其他會剛剛好15
			for (int i = (pageTV - 1) * paginationNum; i < pageTV * paginationNum; i++) {
				newsTV.add(newsFindTV.get(i));
			}
		}

		/* Report */

		int pageReport = 0;
		if (request.getParameter("pageReport") == null) {
			pageReport = 1;
		} else {
			pageReport = Integer.parseInt(request.getParameter("pageReport"));
		}

		List<News> newsReport = new ArrayList<News>();

		if (pageReport == 1) {
			if (totalnewsReport < paginationNum) {
				for (int i = 0; i < totalnewsReport; i++) {
					newsReport.add(newsFindReport.get(i));
				}
			} else {
				for (int i = 0; i < paginationNum; i++) {
					newsReport.add(newsFindReport.get(i));
				}
			}
			// 分頁 若是最後一頁(數量可能不足15)
		} else if (pageReport == totalPageReport) {
			for (int i = (pageReport - 1) * paginationNum; i < totalnewsReport; i++) {
				newsReport.add(newsFindReport.get(i));
			}
		} else {
			// 分頁 其他會剛剛好15
			for (int i = (pageReport - 1) * paginationNum; i < pageReport * paginationNum; i++) {
				newsReport.add(newsFindReport.get(i));
			}
		}

		m.addAttribute("page", page);
		m.addAttribute("pagePC", pagePC);
		m.addAttribute("pageMobile", pageMobile);
		m.addAttribute("pageTV", pageTV);
		m.addAttribute("pageReport", pageReport);

		m.addAttribute("totalPage", totalPage);
		m.addAttribute("totalPagePC", totalPagePC);
		m.addAttribute("totalPageMobile", totalPageMobile);
		m.addAttribute("totalPageTV", totalPageTV);
		m.addAttribute("totalPageReport", totalPageReport);

		m.addAttribute("newsTop8", newsFindTop8);

		m.addAttribute("news", news);
		m.addAttribute("newsPC", newsPC);// 傳遞newsPC變數到news-Home頁面 其值為newsFindPC
		m.addAttribute("newsMobile", newsMobile);
		m.addAttribute("newsTV", newsTV);
		m.addAttribute("newsReport", newsReport);
		return "news/news-Home";
	}

	// 新聞依類別及年月搜尋
	@GetMapping("/searchByMonth")
	public String searchByMonth(Model m, HttpSession session, HttpServletRequest re) {

		int year = Integer.parseInt(re.getParameter("year"));
		int month = Integer.parseInt(re.getParameter("month"));
		// 管理者進入新聞年月搜尋(後台)
		if (null != session.getAttribute("currentManager")) {
			List<News> findAllNewsByDate = newsService.findAllNewsByDate(year, month);
			List<News> FindPCNewsbyDate = newsService.findNewsByTypeAndDate("PC", year, month);
			List<News> FindMobileNewsbyDate = newsService.findNewsByTypeAndDate("手機", year, month);
			List<News> FindTVNewsbyDate = newsService.findNewsByTypeAndDate("TV", year, month);
			List<News> FindReportbyDate = newsService.findNewsByTypeAndDate("主題報導", year, month);

			m.addAttribute("news", findAllNewsByDate);
			m.addAttribute("newsPC", FindPCNewsbyDate);
			m.addAttribute("newsMobile", FindMobileNewsbyDate);
			m.addAttribute("newsTV", FindTVNewsbyDate);
			m.addAttribute("newsReport", FindReportbyDate);
			m.addAttribute("year", year);
			m.addAttribute("month", month);
			return "news/manager-oldNews-search";
		}
		// 一般使用者進入新聞年月搜尋(前台)
		List<News> newsFindTop8 = newsService.findAllAndOrderByUpdateTimeDescTop8();
		List<News> findAllNewsByDate = newsService.findAllNewsByDate(year, month);
		List<News> FindPCNewsbyDate = newsService.findNewsByTypeAndDate("PC", year, month);
		List<News> FindMobileNewsbyDate = newsService.findNewsByTypeAndDate("手機", year, month);
		List<News> FindTVNewsbyDate = newsService.findNewsByTypeAndDate("TV", year, month);
		List<News> FindReportbyDate = newsService.findNewsByTypeAndDate("主題報導", year, month);
		m.addAttribute("newsTop8", newsFindTop8);
		m.addAttribute("news", findAllNewsByDate);
		m.addAttribute("newsPC", FindPCNewsbyDate);
		m.addAttribute("newsMobile", FindMobileNewsbyDate);
		m.addAttribute("newsTV", FindTVNewsbyDate);
		m.addAttribute("newsReport", FindReportbyDate);
		m.addAttribute("year", year);
		m.addAttribute("month", month);
		return "news/oldNews-search";
	}

	// 給管理者的新聞首頁(前台)
	@GetMapping("/managerList")
	public String managerListNews(Model m, HttpSession session, HttpServletRequest request) {

		List<News> newsFindTop8 = newsService.findAllAndOrderByUpdateTimeDescTop8();
		List<News> newsFind = newsService.findAllAndOrderByUpdateTimeDesc();// 搜尋全部並以時間排序
		List<News> newsFindPC = newsService.findNewsByTypeWithUpdateTime("PC");// 參數為PC
		List<News> newsFindMobile = newsService.findNewsByTypeWithUpdateTime("手機");// 參數為手機
		List<News> newsFindTV = newsService.findNewsByTypeWithUpdateTime("TV");// 參數為TV
		List<News> newsFindReport = newsService.findNewsByTypeWithUpdateTime("主題報導");// 參數為主題報導
		// 分頁
		int totalPage = 0; // 總分頁數
		int totalPagePC = 0; // 總分頁數
		int totalPageMobile = 0; // 總分頁數
		int totalPageTV = 0; // 總分頁數
		int totalPageReport = 0; // 總分頁數

		int paginationNum = 10; // 每頁數量

		int totalnews = newsFind.size();
		int totalnewsPC = newsFindPC.size();
		int totalnewsMobile = newsFindMobile.size();
		int totalnewsTV = newsFindTV.size();
		int totalnewsReport = newsFindReport.size();

		/* 全部 */
		if (totalnews != 0) {
			if (totalnews / paginationNum == 0) {
				totalPage = 1;
			} else {
				if ((totalnews % paginationNum) == 0) {
					totalPage = (totalnews / paginationNum);
				} else {
					totalPage = (totalnews / paginationNum) + 1;
				}
			}
			/* PC */
			if (totalnewsPC != 0) {
				if (totalnewsPC / paginationNum == 0) {
					totalPagePC = 1;
				} else {
					if ((totalnewsPC % paginationNum) == 0) {
						totalPagePC = (totalnewsPC / paginationNum);
					} else {
						totalPagePC = (totalnewsPC / paginationNum) + 1;
					}
				}
			}
			/* Mobile */
			if (totalnewsMobile != 0) {
				if (totalnewsMobile / paginationNum == 0) {
					totalPageMobile = 1;
				} else {
					if ((totalnewsMobile % paginationNum) == 0) {
						totalPageMobile = (totalnewsMobile / paginationNum);
					} else {
						totalPageMobile = (totalnewsMobile / paginationNum) + 1;
					}
				}
			}
			/* TV */
			if (totalnewsTV != 0) {
				if (totalnewsTV / paginationNum == 0) {
					totalPageTV = 1;
				} else {
					if ((totalnewsTV % paginationNum) == 0) {
						totalPageTV = (totalnewsTV / paginationNum);
					} else {
						totalPageTV = (totalnewsTV / paginationNum) + 1;
					}
				}
			}
			/* Report */
			if (totalnewsReport != 0) {
				if (totalnewsReport / paginationNum == 0) {
					totalPageReport = 1;
				} else {
					if ((totalnewsReport % paginationNum) == 0) {
						totalPageReport = (totalnewsReport / paginationNum);
					} else {
						totalPageReport = (totalnewsReport / paginationNum) + 1;
					}
				}
			}

		} else {
			m.addAttribute("totalPage", totalPage);

			return "news/news-Home";
		}

		int page = 0;
		if (request.getParameter("page") == null) {
			page = 1;
		} else {
			page = Integer.parseInt(request.getParameter("page"));
		}

		/* 全部 */
		List<News> news = new ArrayList<News>();

		if (page == 1) {
			if (totalnews < paginationNum) {
				for (int i = 0; i < totalnews; i++) {
					news.add(newsFind.get(i));
				}
			} else {
				for (int i = 0; i < paginationNum; i++) {
					news.add(newsFind.get(i));
				}
			}
			// 分頁 若是最後一頁(數量可能不足15)
		} else if (page == totalPage) {
			for (int i = (page - 1) * paginationNum; i < totalnews; i++) {
				news.add(newsFind.get(i));
			}
		} else {
			// 分頁 其他會剛剛好15
			for (int i = (page - 1) * paginationNum; i < page * paginationNum; i++) {
				news.add(newsFind.get(i));
			}
		}

		/* PC */

		int pagePC = 0;
		if (request.getParameter("pagePC") == null) {
			pagePC = 1;
		} else {
			pagePC = Integer.parseInt(request.getParameter("pagePC"));
		}

		List<News> newsPC = new ArrayList<News>();

		if (pagePC == 1) {
			if (totalnewsPC < paginationNum) {
				for (int i = 0; i < totalnewsPC; i++) {
					newsPC.add(newsFindPC.get(i));
				}
			} else {
				for (int i = 0; i < paginationNum; i++) {
					newsPC.add(newsFindPC.get(i));
				}
			}
			// 分頁 若是最後一頁(數量可能不足15)
		} else if (pagePC == totalPagePC) {
			for (int i = (pagePC - 1) * paginationNum; i < totalnewsPC; i++) {
				newsPC.add(newsFindPC.get(i));
			}
		} else {
			// 分頁 其他會剛剛好15
			for (int i = (pagePC - 1) * paginationNum; i < pagePC * paginationNum; i++) {
				newsPC.add(newsFindPC.get(i));
			}
		}

		/* Mobile */

		int pageMobile = 0;
		if (request.getParameter("pageMobile") == null) {
			pageMobile = 1;
		} else {
			pageMobile = Integer.parseInt(request.getParameter("pageMobile"));
		}

		List<News> newsMobile = new ArrayList<News>();

		if (pageMobile == 1) {
			if (totalnewsMobile < paginationNum) {
				for (int i = 0; i < totalnewsMobile; i++) {
					newsMobile.add(newsFindMobile.get(i));
				}
			} else {
				for (int i = 0; i < paginationNum; i++) {
					newsMobile.add(newsFindMobile.get(i));
				}
			}
			// 分頁 若是最後一頁(數量可能不足15)
		} else if (pageMobile == totalPageMobile) {
			for (int i = (pageMobile - 1) * paginationNum; i < totalnewsMobile; i++) {
				newsMobile.add(newsFindMobile.get(i));
			}
		} else {
			// 分頁 其他會剛剛好15
			for (int i = (pageMobile - 1) * paginationNum; i < pageMobile * paginationNum; i++) {
				newsMobile.add(newsFindMobile.get(i));
			}
		}

		/* TV */

		int pageTV = 0;
		if (request.getParameter("pageTV") == null) {
			pageTV = 1;
		} else {
			pageTV = Integer.parseInt(request.getParameter("pageTV"));
		}

		List<News> newsTV = new ArrayList<News>();

		if (pageTV == 1) {
			if (totalnewsTV < paginationNum) {
				for (int i = 0; i < totalnewsTV; i++) {
					newsTV.add(newsFindTV.get(i));
				}
			} else {
				for (int i = 0; i < paginationNum; i++) {
					newsTV.add(newsFindTV.get(i));
				}
			}
			// 分頁 若是最後一頁(數量可能不足15)
		} else if (pageTV == totalPageTV) {
			for (int i = (pageTV - 1) * paginationNum; i < totalnewsTV; i++) {
				newsTV.add(newsFindTV.get(i));
			}
		} else {
			// 分頁 其他會剛剛好15
			for (int i = (pageTV - 1) * paginationNum; i < pageTV * paginationNum; i++) {
				newsTV.add(newsFindTV.get(i));
			}
		}

		/* Report */

		int pageReport = 0;
		if (request.getParameter("pageReport") == null) {
			pageReport = 1;
		} else {
			pageReport = Integer.parseInt(request.getParameter("pageReport"));
		}

		List<News> newsReport = new ArrayList<News>();

		if (pageReport == 1) {
			if (totalnewsReport < paginationNum) {
				for (int i = 0; i < totalnewsReport; i++) {
					newsReport.add(newsFindReport.get(i));
				}
			} else {
				for (int i = 0; i < paginationNum; i++) {
					newsReport.add(newsFindReport.get(i));
				}
			}
			// 分頁 若是最後一頁(數量可能不足15)
		} else if (pageReport == totalPageReport) {
			for (int i = (pageReport - 1) * paginationNum; i < totalnewsReport; i++) {
				newsReport.add(newsFindReport.get(i));
			}
		} else {
			// 分頁 其他會剛剛好15
			for (int i = (pageReport - 1) * paginationNum; i < pageReport * paginationNum; i++) {
				newsReport.add(newsFindReport.get(i));
			}
		}

		m.addAttribute("page", page);
		m.addAttribute("pagePC", pagePC);
		m.addAttribute("pageMobile", pageMobile);
		m.addAttribute("pageTV", pageTV);
		m.addAttribute("pageReport", pageReport);

		m.addAttribute("totalPage", totalPage);
		m.addAttribute("totalPagePC", totalPagePC);
		m.addAttribute("totalPageMobile", totalPageMobile);
		m.addAttribute("totalPageTV", totalPageTV);
		m.addAttribute("totalPageReport", totalPageReport);

		m.addAttribute("newsTop8", newsFindTop8);

		m.addAttribute("news", news);
		m.addAttribute("newsPC", newsPC);// 傳遞newsPC變數到news-Home頁面 其值為newsFindPC
		m.addAttribute("newsMobile", newsMobile);
		m.addAttribute("newsTV", newsTV);
		m.addAttribute("newsReport", newsReport);
		// 管理者進新聞首頁(前台)
		if (null != session.getAttribute("currentManager")) {
			return "news/m-news-Home";
		}
		// 一般使用者的新聞首頁(前台)
		return "news/news-Home";
	}

	// 給管理者的新聞依類別及年月搜尋(前台)
	@GetMapping("/managerListNewsSearchByMonth")
	public String managerListNewsSearchByMonth(Model m, HttpSession session, HttpServletRequest re) {

		int year = Integer.parseInt(re.getParameter("year"));
		int month = Integer.parseInt(re.getParameter("month"));
		List<News> newsFindTop8 = newsService.findAllAndOrderByUpdateTimeDescTop8();
		List<News> findAllNewsByDate = newsService.findAllNewsByDate(year, month);
		List<News> FindPCNewsbyDate = newsService.findNewsByTypeAndDate("PC", year, month);
		List<News> FindMobileNewsbyDate = newsService.findNewsByTypeAndDate("手機", year, month);
		List<News> FindTVNewsbyDate = newsService.findNewsByTypeAndDate("TV", year, month);
		List<News> FindReportbyDate = newsService.findNewsByTypeAndDate("主題報導", year, month);
		m.addAttribute("newsTop8", newsFindTop8);
		m.addAttribute("news", findAllNewsByDate);
		m.addAttribute("newsPC", FindPCNewsbyDate);
		m.addAttribute("newsMobile", FindMobileNewsbyDate);
		m.addAttribute("newsTV", FindTVNewsbyDate);
		m.addAttribute("newsReport", FindReportbyDate);
		m.addAttribute("year", year);
		m.addAttribute("month", month);
		// 管理者進入新聞年月搜尋(前台)
		if (null != session.getAttribute("currentManager")) {
			return "news/m-oldNews-search";
		}
		// 一般使用者進入新聞年月搜尋(前台)
		return "news/oldNews-search";
	}

	// 新聞內容
	@GetMapping("/showNews")
	public ModelAndView showNews(@RequestParam("newsId") int theId, HttpSession session, Model m) {

		News news = newsService.findById(theId);
		NewsComment ncom = new NewsComment();
		ncom.setNews(news);
		// 管理者進入新聞內容(可編輯刪除)
		if (null != session.getAttribute("currentManager")) {
			ModelAndView mav = new ModelAndView("news/manager-news-show");
			mav.addObject("news", news);
			mav.addObject("comment", ncom);
			return mav;
		}
		// 一般使用者進入新聞內容(前台)
		ModelAndView mav = new ModelAndView("news/news-show");
		mav.addObject("news", news);
		mav.addObject("comment", ncom);
		return mav;
	}

	// 給管理者的新聞內容
	@GetMapping("/managerShowNews")
	public String managerShowNews(@RequestParam("newsId") int theId, HttpSession session, Model m) {

		News news = newsService.findById(theId);
		NewsComment ncom = new NewsComment();
		ncom.setNews(news);
		// 管理者進入新聞內容(前台)
		if (null != session.getAttribute("currentManager")) {
			m.addAttribute("news", news);
			m.addAttribute("comment", ncom);
			return "news/m-news-show";
		}
		// 一般使用者進入新聞內容(前台)
		m.addAttribute("news", news);
		m.addAttribute("comment", ncom);
		return "news/news-show";
	}

	// 搜尋功能
	@GetMapping("/search")
	public String newsSearch(Model m, @RequestParam("keyword") String keyword, HttpSession session) {

		// 管理者後台搜尋
		if (null != session.getAttribute("currentManager")) {

			List<News> newsFindTop8 = newsService.findAllAndOrderByUpdateTimeDescTop8();
			List<News> newsKeyword = newsService.searchNewsByKeyword(keyword);
			List<News> newsKeywordPC = newsService.searchNewsByKeywordAndType("PC", keyword);
			List<News> newsKeywordMobile = newsService.searchNewsByKeywordAndType("手機", keyword);
			List<News> newsKeywordTV = newsService.searchNewsByKeywordAndType("TV", keyword);
			List<News> newsKeywordReport = newsService.searchNewsByKeywordAndType("主題報導", keyword);
			m.addAttribute("newsTop8", newsFindTop8);
			m.addAttribute("news", newsKeyword);
			m.addAttribute("newsPC", newsKeywordPC);
			m.addAttribute("newsMobile", newsKeywordMobile);
			m.addAttribute("newsTV", newsKeywordTV);
			m.addAttribute("newsReport", newsKeywordReport);

			m.addAttribute("keyword", keyword);
			return "news/manager-news-Home-search";
		}

		// 使用者新聞搜尋
		List<News> newsFindTop8 = newsService.findAllAndOrderByUpdateTimeDescTop8();
		List<News> newsKeyword = newsService.searchNewsByKeyword(keyword);
		List<News> newsKeywordPC = newsService.searchNewsByKeywordAndType("PC", keyword);
		List<News> newsKeywordMobile = newsService.searchNewsByKeywordAndType("手機", keyword);
		List<News> newsKeywordTV = newsService.searchNewsByKeywordAndType("TV", keyword);
		List<News> newsKeywordReport = newsService.searchNewsByKeywordAndType("主題報導", keyword);
		m.addAttribute("newsTop8", newsFindTop8);
		m.addAttribute("news", newsKeyword);
		m.addAttribute("newsPC", newsKeywordPC);
		m.addAttribute("newsMobile", newsKeywordMobile);
		m.addAttribute("newsTV", newsKeywordTV);
		m.addAttribute("newsReport", newsKeywordReport);

		m.addAttribute("keyword", keyword);
		return "/news/news-search";
	}

	// 給管理者的搜尋功能
	@GetMapping("/managerSearch")
	public String managerNewsSearch(Model m, @RequestParam("keyword") String keyword, HttpSession session) {

		// 管理者後台搜尋
		if (null != session.getAttribute("currentManager")) {

			List<News> newsFindTop8 = newsService.findAllAndOrderByUpdateTimeDescTop8();
			List<News> newsKeyword = newsService.searchNewsByKeyword(keyword);
			List<News> newsKeywordPC = newsService.searchNewsByKeywordAndType("PC", keyword);
			List<News> newsKeywordMobile = newsService.searchNewsByKeywordAndType("手機", keyword);
			List<News> newsKeywordTV = newsService.searchNewsByKeywordAndType("TV", keyword);
			List<News> newsKeywordReport = newsService.searchNewsByKeywordAndType("主題報導", keyword);
			m.addAttribute("newsTop8", newsFindTop8);
			m.addAttribute("news", newsKeyword);
			m.addAttribute("newsPC", newsKeywordPC);
			m.addAttribute("newsMobile", newsKeywordMobile);
			m.addAttribute("newsTV", newsKeywordTV);
			m.addAttribute("newsReport", newsKeywordReport);

			m.addAttribute("keyword", keyword);
			return "news/m-news-search";
		}

		// 使用者新聞搜尋
		return "/news/news-search";
	}

	// 使用者新增新聞評論
	@ResponseBody
	@PostMapping("/newsComment/save")
	public NewsComment save(@ModelAttribute("news") News news, NewsComment ncom, HttpSession session) {

		// 管理者新增評論
		if (null != session.getAttribute("currentManager")) {
			Manager currentManager = (Manager) session.getAttribute("currentManager");
			ncom.setNickName(currentManager.getManagerName()+" (管理員)");
			ncom.setPosttime(new Date());
			ncom.setMemberPhotoUri(currentManager.getManagerPhotoUri());
			return newsCommentService.save(ncom);
		}
		// 使用者新增評論
		User currentUser = (User) session.getAttribute("currentUser");
		ncom.setNickName(currentUser.getNickName());
		ncom.setPosttime(new Date());
		ncom.setMemberPhotoUri(currentUser.getMemberPhotoUri());
		return newsCommentService.save(ncom);
	}
	
	//留言屏蔽
		@ResponseBody
		@GetMapping("/comment/ban")
		public void banComment(@RequestParam("commentid") int id) {
			NewsComment thiscom = newsCommentService.findById(id);
			thiscom.setContent("本評語包含謾罵等不雅字眼，已違反版規遭到移除");

			newsCommentService.save(thiscom);
		}

	// 新增新聞
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model m) {
		// create model attribute to bind form data
		News news = new News();
		news.setNewsPhoto("/images/news/newsCover/default.png"); // 給預設圖
		m.addAttribute("news", news); // 傳遞news變數到news-form-add頁面 其值為news
		return "news/manager-news-form-add";

	}

	// 修改新聞
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("newsId") int theId, Model m) {
		// 取得新聞
		News news = newsService.findById(theId);
		m.addAttribute("news", news); // 傳遞news變數到news-form-add頁面 其值為news
		return "news/manager-news-form-update";
	}

	// 儲存新聞
	@PostMapping("/save")
	public String saveNews(@ModelAttribute("news") News news, HttpSession session,
			@RequestParam("news_image") MultipartFile fileImage, @RequestParam("newsType") String type)
			throws IOException {
		// 取得圖檔根路徑
		String rootPath = "C:/SpringBoot/workspace/T1/src/main/resources/static/images/news/newsCover/";

		// 若是修改新聞
		if (news.getId() != 0) {
			//如果沒有上傳圖片
			if (fileImage.getOriginalFilename().equals("")) {

				News oldNews = newsService.findById(news.getId());
				Manager currentManager = (Manager) session.getAttribute("currentManager"); // 取得當前管理者資料

				news.setManager(currentManager.getManagerName()); // 加入管理者名稱
				news.setType(type); // 加入新聞類別
				news.setPostTime(new Date()); // 加入新聞發佈時間
				news.setNewsPhoto(oldNews.getNewsPhoto());

				// 儲存新聞
				newsService.save(news);

				return "redirect:/news/list";
			}else {
				News tempNews = newsService.findById(news.getId()); // 取得文章資料
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
		newsService.save(news);

		// 睡二秒防圖片延遲
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return "redirect:/news/list";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("newsId") int theId) {

		// 刪除新聞封面圖
		News delNews = newsService.findById(theId);
		// 取得圖片根路徑
		String rootPath = "C:/SpringBoot/workspace/T1/src/main/resources/static";
		// 取得要刪除的圖片路徑
		String imgPath = rootPath + delNews.getNewsPhoto();
		File tempImg = new File(imgPath);

		if (tempImg.exists()) {
			tempImg.delete();
		}

		// 刪除新聞
		newsService.deleteById(theId);

		return "redirect:/news/list";
	}

}
