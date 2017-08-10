package com.example.service;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements Single Page Application logic for dealing with navigation
 * using the HTML5 History API.
 *
 * Inspiration: https://github.com/bripkens/connect-history-api-fallback/
 */
public class HistoryApiFallbackFilter implements Filter {

	private static final String REENTRANCY_KEY = HistoryApiFallbackFilter.class.getName();

	protected static String FORWARD_PATH_CONFIG_PARAMETER = "forwardPath";
	protected static String forwardPath = null;

	/** */
	private boolean isGet(String method) {
		return method.equals("GET");
	}

	/** */
	private boolean hasHeader(String header) {
		return header != null && header.length() > 0;
	}

	/** */
	private boolean isApplicationJson(String header) {
		return header.contains("application/json");
	}

	/** */
	private boolean acceptsHtml(String header) {
		return header.contains("text/html") || header.contains("*/*");
	}

	/** */
	private boolean pathIncludesDot(String path) {
		return path != null && path.indexOf('.') != -1;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		forwardPath = filterConfig.getInitParameter(FORWARD_PATH_CONFIG_PARAMETER);
		if (forwardPath == null) {
			throw new ServletException("Please set the '" + FORWARD_PATH_CONFIG_PARAMETER + "' servlet filter config as part of the " + REENTRANCY_KEY);
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String method = ((HttpServletRequest) request).getMethod().toUpperCase();
		String accept = ((HttpServletRequest) request).getHeader("Accept").toLowerCase();
		String requestURI = ((HttpServletRequest) request).getRequestURI();

		String path = ((HttpServletRequest) request).getServletPath();
		
		Object reentrancyKey = request.getAttribute(REENTRANCY_KEY);

		boolean doFilter = false;

		if (reentrancyKey != null ||
			!isGet(method) ||
			!hasHeader(accept) ||
			isApplicationJson(accept) ||
			!acceptsHtml(accept) ||
			isFilteredPath(path) ||
			pathIncludesDot(requestURI)) {

			doFilter = true;
		}
		if (doFilter) {
			chain.doFilter(request, response);
		} else {
			// Prevent the next request from hitting this filter
			request.setAttribute(REENTRANCY_KEY, Boolean.TRUE);
			request.getRequestDispatcher(forwardPath).forward(request, response);
		}
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	private boolean isFilteredPath(String path) {
		if (path.startsWith("/api")) return true;
		else return false;
	}
}