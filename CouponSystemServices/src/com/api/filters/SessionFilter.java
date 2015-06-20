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
import javax.ws.rs.FormParam;

@WebFilter("/SessionFilter")
public class SessionFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public SessionFilter() {

	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {

	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) req).getSession(false);
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		 String uri = request.getRequestURI();
		 String logIn = request.getParameter("login") == null ? "" : request
		 .getParameter("login");
		 response.setHeader("Cache-Control",
		 "private, no-store, no-cache, must-revalidate");
		
		 if (uri.equals("/CouponSystemServices/login.html") && session !=
		 null) {
		
		 // TODO: if session attribute is set and user is logged in,
		 // redirect to client page with his info.
		
		 if (session.getAttribute("loggedIn").equals(Boolean.TRUE)) {
		 // TODO: here return json object
		 // TODO: and if client requested another services he cant do, an
		 // error message should appea.
		 response.sendRedirect(request.getContextPath()
		 + "/client_page.html");
		 }
		
		 } else if (uri.equals("/CouponSystemServices/login.html")
		 && session == null) {
		
		 chain.doFilter(req, res);
		
		 } else if (logIn.equals("login")) {
		
		 chain.doFilter(req, res);
		
		 } else if (session == null) {
		 response.sendRedirect(request.getContextPath() + "/login.html");
		 }

		System.out.println("before hand ...");
		chain.doFilter(request, response);
		System.out.println("after hand ...");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
