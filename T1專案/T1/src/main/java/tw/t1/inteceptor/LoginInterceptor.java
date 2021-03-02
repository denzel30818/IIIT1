package tw.t1.inteceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		log.info("被攔截的請求路徑 : " + request.getRequestURI()); // 印出被攔截的路徑

		HttpSession session = request.getSession(); // 取得當前請求的 session 物件

		Object currentUser = session.getAttribute("currentUser"); // 取得登入時建立的 session 屬性值

		Object currentManager = session.getAttribute("currentManager");

		if (currentUser != null | currentManager != null) { // 使用者或管理者存在就通過攔截器
			return true;
		}

		response.sendRedirect("/login");
		return false;
	}
}
