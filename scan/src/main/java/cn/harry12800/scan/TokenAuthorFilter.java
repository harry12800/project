package cn.harry12800.scan;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***************
 * token验证拦截
 * @author bamboo zjcjava@163.com
 * @time 2017-08-01
 */
//@Component
//@WebFilter(urlPatterns = { "/api/v/*" }, filterName = "tokenAuthorFilter")
public class TokenAuthorFilter implements Filter {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory
			.getLogger(TokenAuthorFilter.class);

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//		HttpServletRequest req = (HttpServletRequest) request;
		//		HttpServletResponse rep = (HttpServletResponse) response;
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}