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

		// TODO: // send success=false message in json
		// JSONObject jo = new JSONObject();
		// jo.put("firstName", "John");
		// jo.put("lastName", "Doe");
		//
		// JSONArray ja = new JSONArray();
		// ja.put(jo);
		//
		// JSONObject mainObj = new JSONObject();
		// mainObj.put("employees", ja);

		if (request.getMethod().equals("GET")
				|| request.getMethod().equals("get")) {
			//response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			//response.setHeader(arg0, arg1); [error code : SC_METHOD_NOT_ALLOWED]
		} else {

			String clientType = request.getParameter("client-type");
			String clientName = request.getParameter("clientName");
			String clientPassword = request.getParameter("clientPassword");

			if (clientType.isEmpty() || clientName.isEmpty()
					|| clientPassword.isEmpty()) {
				response.sendRedirect(request.getContextPath() + "/login.html");
			}

			chain.doFilter(req, res);
			
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
