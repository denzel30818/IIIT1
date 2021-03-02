package tw.t1.controller;

import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.t1.dao.OrdersDetailRepository;
import tw.t1.dao.ProductsRepository;
import tw.t1.entity.OrdersDetail;
import tw.t1.entity.Products;
import tw.t1.entity.User;
import tw.t1.service.EmailSenderService;
import tw.t1.service.UserService;

@RestController
public class UserUtityController {

	@Autowired
	private UserService userService;
	@Autowired
	private EmailSenderService emailSendService;
	@Autowired
	private OrdersDetailRepository ordersDetailRepository;
	@Autowired
	private ProductsRepository productsRepository;

	// 修改密碼驗證
	@PostMapping("/updatePassword")
	public boolean checkOldPassword(User user, HttpSession session) {

		User currentUser = (User) session.getAttribute("currentUser");

		if (user.getUserPassword().equals(currentUser.getUserPassword())) {
			return true;
		}
		return false;
	}

	// 黑名單
	@PostMapping("/banUser")
	public void banUser(@RequestParam("id") int id) {

		User user = userService.findById(id);
		user.setBanned("y");
		userService.save(user);
	}

	// 解鎖黑名單
	@PostMapping("/unbanUser")
	public void unbanUser(@RequestParam("id") int id) {

		User user = userService.findById(id);
		user.setBanned("n");
		userService.save(user);
	}

	// 送生日賀卡、T幣
	@PostMapping("/congratulate")
	public void congratulate(@RequestParam("id") int id) throws MessagingException {

		User user = userService.findById(id);
		if (user.getCongratulated().equals("n")) {
			user.setPoint(user.getPoint() + 1000);
			user.setCongratulated("y");
			emailSendService.sendEmailWithInlineResource("leonpower117@gmail.com", "T-one祝您生日快樂", user.getNickName()
					+ "，生日快樂!<br/><br/>這邊獻上1000T幣的紅包，感謝您一直以來的支持。<br/><br/>新的一年還請多多指教，我們會持續為您提供最好的服務!<br/><br/>T-one遊戲論壇 敬上",
					"C:/SpringBoot/workspace/T1/src/main/resources/static/images/manager/HappyBirthday.jpg");
			userService.save(user);
		}
	}

	// 一鍵發送生日賀卡、T幣
	@PostMapping("/congratulateAll")
	public void congratulateAll() throws MessagingException {

		List<User> users = userService.findUserByBirthDateWithUnbannedThisMonth();
		for (User user : users) {
			if (user.getCongratulated().equals("n")) {
				user.setPoint(user.getPoint() + 1000);
				user.setCongratulated("y");
				emailSendService.sendEmailWithInlineResource("leonpower117@gmail.com", "T-one祝您生日快樂", user.getNickName()
						+ "，生日快樂!<br/><br/>這邊獻上1000T幣的紅包，感謝您一直以來的支持。<br/><br/>新的一年還請多多指教，我們會持續為您提供最好的服務!<br/><br/>T-one遊戲論壇 敬上",
						"C:/SpringBoot/workspace/T1/src/main/resources/static/images/manager/HappyBirthday.jpg");
				userService.save(user);
			}
		}
	}

	@PostMapping("/ordersDetail")
	public List<OrdersDetail> showDetails(@RequestParam("id") long id) {

		List<OrdersDetail> ordersDetails = ordersDetailRepository.findByOrderId(id);
		for (int i = 0; i < ordersDetails.size(); i++) {
			Products tempP = productsRepository.findProductsById(ordersDetails.get(i).getProductId()); // 取得單項產品明細
			ordersDetails.get(i).setProductName(tempP.getP_name()); // 取得產品名稱
			ordersDetails.get(i).setSinglePrice((int) (tempP.getPrice() * tempP.getDiscount())); // 折扣後單價
			ordersDetails.get(i)
					.setTotalPrice((int) (ordersDetails.get(i).getQuantity() * tempP.getPrice() * tempP.getDiscount())); // 取得總價
			if (tempP.getCategory().equals("遊戲週邊")) {
				ordersDetails.get(i).setFee(120);
			}
		}

		return ordersDetails;
	}
}
