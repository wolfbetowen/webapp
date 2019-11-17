package com.epam.webapp.filter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/jsp/*"},
        initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})
public class UnauthorizedAccessFilter implements Filter {

  private String indexPath;

  public void init(FilterConfig fConfig) throws ServletException {
    indexPath = fConfig.getInitParameter("INDEX_PATH");
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
          throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
    chain.doFilter(request, response);
  }

  public void destroy() {
  }
}
