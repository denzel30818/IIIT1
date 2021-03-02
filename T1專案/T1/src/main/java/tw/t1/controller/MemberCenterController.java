package tw.t1.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.t1.entity.Cart;
import tw.t1.entity.Feedback;
import tw.t1.entity.Orders;
import tw.t1.entity.OrdersDetail;
import tw.t1.entity.Products;
import tw.t1.entity.WishList;
import tw.t1.service.CartService;
import tw.t1.service.EmailSenderService;
import tw.t1.service.FeedbackService;
import tw.t1.service.OrdersDetailService;
import tw.t1.service.OrdersService;
import tw.t1.service.ProductsService;
import tw.t1.service.WishListService;

@Controller
@RequestMapping(value = "/member")
public class MemberCenterController {

	private ProductsService pService;
	private WishListService wService;
	private CartService cartService;
	private OrdersService orderService;
	private OrdersDetailService odService;
	private EmailSenderService emailService;
	private FeedbackService fbService;

	@Autowired
	public MemberCenterController(ProductsService service, WishListService wService, CartService cartService,
			OrdersService orderService, OrdersDetailService odService, EmailSenderService emailService,
			FeedbackService fbService) {
		this.pService = service;
		this.wService = wService;
		this.cartService = cartService;
		this.orderService = orderService;
		this.odService = odService;
		this.emailService = emailService;
		this.fbService = fbService;

	}

	@GetMapping("/mywishlist")
	public String wishlist(Model m) {

		// 要改id!!
		int cartnum = (int) cartService.countByUserid(1);
		m.addAttribute("cartnum", cartnum);

		List<WishList> wishes = wService.findByUser_id(1);

		String hasWish = "false";

		if (!wishes.isEmpty()) {

			hasWish = "true";

			List<Products> products = new ArrayList<Products>();

			for (WishList wish : wishes) {
				products.add(pService.findById(wish.getP_id()));
			}

			m.addAttribute("products", products);
		}
		m.addAttribute("hasWish", hasWish);

		return "memberCenter/member-wishlist";

	}

	@GetMapping("/mywishlist/remove")
	public @ResponseBody void removewish(@RequestParam("p_id") int id) {

		// 要加上id
		wService.deleteByUser_idAndP_id(1, id);

	}

	// 防止重複走/add路線，之後加上購物車小圖示直接走這條路線
	@GetMapping("/mycart")
	public String showCart(Model model) {

		String hasPro = "true";

		// 要改id!!
		int cartnum = (int) cartService.countByUserid(1);
		model.addAttribute("cartnum", cartnum);

		int user_id = 1;

		List<Cart> carts = cartService.findByUserId(user_id); // 購物車裡全部user_id=3的商品裝進來

		if (carts.isEmpty()) {
			hasPro = "false";
		}

		for (Cart c : carts) {
			int p_id = c.getProduct_id();
			Products p = pService.findById(p_id);
			/*----計算折扣後價格-----*/
			double pPrice = Double.valueOf(p.getPrice());
			double actPrice = pPrice * p.getDiscount();
			int aPrice = (int) actPrice;

			c.setP_name(p.getP_name()); // Entity用@Transient偷帶資料過去
			c.setSub_name(p.getSub_name());
			c.setActualPrice(aPrice);
			c.setImagePath(p.getPhotosImagePath());
		}
		model.addAttribute("hasPro", hasPro);
		model.addAttribute("cart1", carts);
		return "memberCenter/member-cart";
	}

	/*-----刪除購物車內商品-----------*/
	/*------0203 家寶 start-------*/
	@GetMapping("/mycart/delete")
	public @ResponseBody void deleteById(@RequestParam("id") int cId) {
		cartService.deleteById(cId);
	}
	/*------0203 家寶 end-------*/

	/*------------------購物車內數量變更直接連動資料庫-------------------*/
	@GetMapping("/mycart/update")
	public @ResponseBody String updateById(@RequestParam("id") int cId, @RequestParam("quantity") int quantity) {
		Cart cart = cartService.findCartById(cId);
		// System.out.println(quantity);

		cart.setQuantity(quantity);
		cartService.save(cart);

		String result = "true";
		return result;
	}

	/*--------------------購物車頁面商品加到願望清單------------------*/
	/*--------------------0203 家寶 start------------------*/
	@GetMapping("/CartToWishList")
	public @ResponseBody void check(@RequestParam("p_id") int pId, @RequestParam("cId") int cId) {

		List<WishList> wish = wService.findByUser_idAndP_id(1, pId);

		cartService.deleteById(cId);

		if (wish.isEmpty()) {
			WishList wishList = new WishList();
			wishList.setUser_id(1);
			wishList.setP_id(pId);
			wService.save(wishList);
		}

	}
	/*--------------------0203 家寶 end------------------*/

	/*-------------------填寫寄送資料-------------------*/
	@GetMapping("/mycart/fillform")
	public String goToFillForm(Model model, HttpServletRequest request) {

		// 要改id!!
		int cartnum = (int) cartService.countByUserid(1);
		model.addAttribute("cartnum", cartnum);
		/*----預設載入頁面checked的值----*/
		/*--------0208 --------------*/
		if (request.getParameter("receiver") != null) {
			Orders o = new Orders();
			o.setReceiver(request.getParameter("receiver"));
			o.setReceiverPhone(request.getParameter("receiverPhone"));
			o.setAddress(request.getParameter("address"));
			o.setPayMethod(request.getParameter("payMethod"));
			model.addAttribute("order1", o);
		} else {
			Orders o = new Orders();
			o.setPayMethod("信用卡付款");
			model.addAttribute("order1", o);
		}
		/*----^^^^^^^^^^^^^^^^^^^-----*/
		return "memberCenter/member-order-fill";
	}

	/*-------------------訂單最後確認頁面-----------02/04--------*/
	@PostMapping("/mycart/go-preview")
	public String goToPreview(@ModelAttribute("order1") Orders ord, Model model) {

		// 要改id!!
		int cartnum = (int) cartService.countByUserid(1);
		model.addAttribute("cartnum", cartnum);

		int ordtotal_p = 0;
		System.out.println(ord.getPayMethod());

		List<Cart> carts = cartService.findByUserId(1);
		for (Cart c : carts) {

			Products p = pService.findById(c.getProduct_id());
			c.setP_name(p.getP_name());

			int q = c.getQuantity();
			/*---------折扣後單一價格---------*/
			double sp = Double.valueOf(p.getPrice()) * p.getDiscount();
			c.setActualPrice((int) sp);
			/*---------該商品數量＊價格---------*/
			int total = q * (int) sp;
			c.setTotalP(total);

			ordtotal_p += total; // 訂單總價
		}
		/*-------判斷是否要運費--------*/
		int fee = 0;

		for (Cart c : carts) {
			Products p = pService.findById(c.getProduct_id());
			String category = p.getCategory();
			if (category.equals("遊戲週邊")) {
				fee = 120;
			}
		}

		ord.setOrdTotalPrice(ordtotal_p + fee);
		ord.setFee(fee);
		ord.setUserId(1); // set userId進去 有會員系統後用session抓userId

		model.addAttribute("cart2", carts);
		model.addAttribute("order2", ord);

		return "memberCenter/member-order-confirm";
	}

	/*-------------------送出訂單並存入資料庫-------------------*/
	@PostMapping("/mycart/confirm")
	public String confirmOrder(@ModelAttribute("order2") Orders ord, Model model) throws MessagingException {

		// ord.setUserId(3); //整合後用session的user_id抓

		// 新建創立訂單時間並存入字串 now 之後拿來比對
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(new Date());
		ord.setOrderTime(now);

		// 先把order存入資料庫才有order的流水號(id)可以抓
		orderService.save(ord);
		List<Orders> orders = orderService.findByUserId(ord.getUserId());

		// cart裡的東西找出來轉存到detail 之後把cart裡的東西刪除
		List<Cart> carts = cartService.findByUserId(ord.getUserId());
		List<OrdersDetail> ods = new ArrayList<>();

		for (Orders o : orders) {
			if (o.getOrderTime().equals(now)) { // 拿訂單創立時間比對哪一個訂單是要存的
				for (Cart c : carts) {

					OrdersDetail od = new OrdersDetail();
					od.setOrderId(o.getId());
					od.setProductId(c.getProduct_id());
					od.setPayMethod(o.getPayMethod());
					System.out.println(o.getPayMethod());
					od.setQuantity(c.getQuantity());
					od.setOrderTime(now); // 虛擬
					Products p = pService.findById(c.getProduct_id());
					// 算折扣後價格
					double actualP = Double.valueOf(p.getPrice()) * p.getDiscount();
					od.setActualPrice((int) actualP);
					od.setProductName(p.getP_name()); // 虛擬
					od.setSinglePrice(p.getPrice());
					od.setTotalPrice((p.getPrice()) * (c.getQuantity()));
					odService.save(od);
					ods.add(od);
					System.out.println("SAVED!");

					// 該筆資料存完從購物車將之刪除
					cartService.deleteById(c.getId());
					System.out.println("Cart Items DELETED!");
				}
			}
		}

		/*--------更新商品銷售量------------02/03--------------------*/
		List<Orders> oder = orderService.findByUserIdAndOrderTime(1, now);// 找出這次訂單

		Long oid = oder.get(0).getId();
		List<OrdersDetail> orderDetail = odService.findByOrderId(oid);
		for (OrdersDetail od : orderDetail) {
			Products p = pService.findById(od.getProductId());
			int value = p.getSales_volume();
			int add = od.getQuantity();
			p.setSales_volume(value + add);

			/*-------更新庫存數量----02/05----------*/
			int newQuantity = p.getQuantity() - od.getQuantity();
			p.setQuantity(newQuantity);

			pService.save(p);
		}
		;

		// 要改id!!
		int cartnum = (int) cartService.countByUserid(1);
		model.addAttribute("cartnum", cartnum);

		OrdersDetail o = ods.get(0);
		model.addAttribute("ord", o);
		model.addAttribute("detail", ods);

		/*-------------------02/09------------*/

		String receiver = "leonpower117@gmail.com";
		String body = "您的訂單編號為" + oid.toString()
				+ "已可前往會員中心查詢<br/><br/><a href='http://localhost:8080/index'>http://localhost:8080/index</a>";
		String sub = "T-One商城感謝您的購買";

		emailService.sendHtmlEmail(receiver, sub, body);

		/*-----------------------------------*/

		return "memberCenter/member-order-detail";
	}

	/* 0212家寶 */
	@GetMapping("/feedback")
	public String feedback(Model m) {

		Feedback fb = new Feedback();

		m.addAttribute("feedback", fb);

		return "feedback/feedback-form";
	}

	/* 0212家寶 */
	@PostMapping("/feedback/success")
	public String savefeedback(Model m, @ModelAttribute("feedback") Feedback fb, Model model,
			@RequestParam("type") String type) {

		fb.setType(type);

		fbService.save(fb);

		return "feedback/feedback-success";
	}

	/*----------------2/12------------*/
	@GetMapping("/about")
	public String aboutTeam() {

		return "memberCenter/about-team";
	}

	/*-------------02/15----------*/
	@GetMapping("/status")
	public String orderStatus(Model m) {
		List<Orders> orders = orderService.findAllByDESC();
		m.addAttribute("orders", orders);

		return "memberCenter/member-order-status";
	}

	@GetMapping("/order/statchange")
	public @ResponseBody String orderStatus(@RequestParam("id") Long oId, @RequestParam("status") String ststus) {
		Orders order = orderService.findOrdersById(oId);
		order.setStatus(ststus);
		orderService.save(order);

		return "ok";
	}

	// -----0219-----
	@GetMapping("/status/search")
	public String orderSearch(Model m, @RequestParam("key") Long oId) {
		List<Orders> orders = orderService.findByIdd(oId);

		m.addAttribute("orders", orders);

		return "memberCenter/member-order-status";
	}
	/*----------^^^^^^^^^--------*/

	/*----------^^^^^^^^^--------*/
	/*------------02/17-------------------*/
	@GetMapping("/status/notprocess")
	public String statusSearch(Model m) {
		List<Orders> orders = new ArrayList<Orders>();

		List<Orders> o1 = orderService.findAllOrders();
		String s = "訂單處理中";
		for (Orders o : o1) {
			if (o.getStatus().equals(s)) {
				orders.add(o);
			}
		}

		m.addAttribute("orders", orders);

		return "memberCenter/member-order-status";
	}

	/*-------------------------------*/
	@GetMapping("/orders/income")
	public String income(Model m) {
		int totalIncome = 0;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String now = sdf.format(new Date());

		List<OrdersDetail> od = odService.findAllDetail();
		List<OrdersDetail> od2 = new ArrayList<OrdersDetail>();

		for (OrdersDetail o : od) {
			Long oid = o.getOrderId();
			String td = oid.toString().substring(0, 8);
			if (td.equals(now)) {
				totalIncome += (o.getActualPrice()) * (o.getQuantity());
				od2.add(o);
			}
		}
		m.addAttribute("OrderDetail", od2);
		m.addAttribute("income", totalIncome);

		return "memberCenter/member-order-income";
	}
}