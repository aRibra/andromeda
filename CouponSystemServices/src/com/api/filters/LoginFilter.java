package com.api.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.couponsystem.beans.ClientType;
import com.couponsystem.helper.classes.ClientBucket;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/LoginFilter")
public class LoginFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public LoginFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) req).getSession(false);
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		System.out.println("in login");

		if (session == null) {
			chain.doFilter(req, res);
			
		} else if (session != null
				&& request.getRequestURI().contains("client_page.html")) {
			chain.doFilter(req, res);
		} else if (session != null) {

			String uri = request.getRequestURI();
			ClientType clientType = null;

			ClientBucket clientBucket = (ClientBucket) session
					.getAttribute("clientBucket");
			clientType = clientBucket.getClient().getClientType();

			switch (clientType) {
			case ADMIN:
				if (uri.contains("/admin_service")) {
					chain.doFilter(req, res);
				}
				response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				break;

			case COMPANY:
				if (uri.contains("/company_service")) {
					chain.doFilter(req, res);
				}
				response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				break;

			case CUSTOMER:
				if (uri.contains("/customer_service")) {
					chain.doFilter(req, res);
				}
				response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				break;

			default:
				response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				break;
			}

		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
