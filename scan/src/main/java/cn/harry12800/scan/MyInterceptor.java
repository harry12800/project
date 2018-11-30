package cn.harry12800.scan;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor implements HandlerInterceptor {
	private List<Integer> errorCodeList = Arrays.asList(404, 403, 500, 501);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (errorCodeList.contains(response.getStatus())) {
			response.sendRedirect("/index.html");
			return false;
		}
		// TODO Auto-generated method stub
		System.out.println("------preHandle------");
		// 获取session
		HttpSession session = request.getSession(true);
		// 判断用户ID是否存在，不存在就跳转到登录界面
		String servletPath = request.getServletPath();
		System.err.println("+++++++++++++++：" + servletPath);
		// session.removeAttribute("userId");
		System.out.println(session.getAttribute("userId"));
		//		session.setAttribute("userId", "zr0014");
		if (session.getAttribute("userId") == null) {
			System.out.println("------:跳转到login页面！");
			//			session.setAttribute("url", servletPath);
			//			String url = myAppConfig.imssoUrl + "/feige.html?app_id=725218e83cb24e18&state=tesote&redirect_uri="
			//					+ myAppConfig.ssoCallback;
			response.sendRedirect("/doc/login.html");
			// response.sendRedirect(request.getContextPath() + "/login");
			return false;
		} else {
			session.setAttribute("userId", session.getAttribute("userId"));
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}