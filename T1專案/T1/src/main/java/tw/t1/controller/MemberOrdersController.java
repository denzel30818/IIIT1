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

import tw.t1.dao.OrdersDetailRepository;
import tw.t1.dao.OrdersRepository;
import tw.t1.entity.Orders;
import tw.t1.entity.OrdersDetail;
import tw.t1.entity.Products;
import tw.t1.entity.User;
import tw.t1.service.ProductsService;

@Controller
@RequestMapping("/member")
public class MemberOrdersController {

	@Autowired
	private HttpSession session;
	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private OrdersDetailRepository ordersDetailRepository;
	@Autowired
	private ProductsService productsService;

	@GetMapping("/orders")
	public String goOrders(Model m, HttpServletRequest request) {

		// 確認管理者有無登入狀態
		if (null != session.getAttribute("currentManager")) {
			return "manager/manager-no-right";
		}

		User currentUser = (User) session.getAttribute("currentUser"); // 取得當前使用者
		List<Orders> orders = ordersRepository.findByUserIdByDesc(currentUser.getUserID()); // 取得當前使用者所有訂單
		if (orders.size() > 0) { // 如果有訂單
			for (int i = 0; i < orders.size(); i++) {
				List<OrdersDetail> tempOrdersDetails = ordersDetailRepository.findByOrderId(orders.get(i).getId()); // 拿到單一訂單明細
				String[] photo_uri = new String[tempOrdersDetails.size()]; // 取得單一訂單裡的照片陣列
				int fee = 0;
				int total = 0; // 總價
				for (int j = 0; j < tempOrdersDetails.size(); j++) { // 拆分訂單明細(計算折扣後總價)
					total += tempOrdersDetails.get(j).getQuantity() * tempOrdersDetails.get(j).getActualPrice();
					Products tempP = productsService.findById(tempOrdersDetails.get(j).getProductId());
					photo_uri[j] = "/images/products/" + tempP.getP_id() + "/" + tempP.getPhoto_uri();
					if (tempP.getCategory().equals("遊戲週邊")) {
						fee = 120;
					}
				}
				orders.get(i).setOrdTotalPrice(total + fee); // 加入訂單總價
				orders.get(i).setPhoto_uri(photo_uri); // 加入商品照片
			}
		}
		int ordersNum = orders.size();
		int totalPage = 0; // 總分頁數
		int paginationNum = 5; // 每頁數量
		if (ordersNum != 0) {
			if (ordersNum / paginationNum == 0) {
				totalPage = 1;
			} else {
				if ((ordersNum % paginationNum) == 0) {
					totalPage = (ordersNum / paginationNum);
				} else {
					totalPage = (ordersNum / paginationNum) + 1;
				}
			}
		} else {
			m.addAttribute("ordersNotFound","您還沒有任何訂單");
			m.addAttribute("totalPage", totalPage);
			return "member/member-center-orders";
		}
		List<Orders> pgorders = new ArrayList<Orders>();

		int page = 0;
		if (request.getParameter("page") == null | "1".equals(request.getParameter("page"))) {
			for (int i = 0; i < paginationNum; i++) {
				pgorders.add(orders.get(i));
			}
			m.addAttribute("orders", pgorders);
			m.addAttribute("totalPage", totalPage);
			page = 1;
		} else if (totalPage > 1) {
			page = Integer.parseInt(request.getParameter("page"));
			if (page < totalPage) {
				for (int i = (page - 1) * paginationNum; i < (page * paginationNum); i++) {
					pgorders.add(orders.get(i));
				}
			} else {
				for (int i = (page - 1) * paginationNum; i < ordersNum; i++) {
					pgorders.add(orders.get(i));
				}
			}
			m.addAttribute("orders", pgorders);
			m.addAttribute("totalPage", totalPage);
		}
		m.addAttribute("page", page);

		return "member/member-center-orders";
	}

}
