package rt.task2;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rt.task2.data.domain.User;

@WebFilter("/SimpleServletFilter")
public class DispatchServletFilter implements Filter {
    private FilterConfig filterConfig;
    private static ArrayList<String> pages;

    public DispatchServletFilter() {
	if (pages == null) {
	    pages = new ArrayList<String>();
	}
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
	this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	    throws IOException, ServletException {

	if (filterConfig.getInitParameter("active").equalsIgnoreCase("true")) {

	    HttpServletRequest req = (HttpServletRequest) request;
	    String path = req.getRequestURI().substring(req.getContextPath().length());

	    // css и js файлы пропускаем
	    if (path.matches("/css/.{0,}") || path.matches("/js/.{0,}")) {
		chain.doFilter(request, response);
		return;
	    }
	    // если страница регистрации пропускаем
	    if (path.endsWith("/registration.jsp") || path.endsWith("/registration")) {
		chain.doFilter(request, response);
		return;
	    }
	    // если страница авторизации пропускаем
	    if (path.endsWith("/login.jsp") || path.endsWith("/login") || path.endsWith("/logout")) {
		chain.doFilter(request, response);
		return;
	    }
	    // Если открывается главная страница, то выполняем проверку
	    HttpSession session = req.getSession(true);
	    User sid = (User) session.getAttribute("sid");
	    if (sid == null) {
		ServletContext ctx = filterConfig.getServletContext();
		RequestDispatcher dispatcher = ctx.getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
		return;
	    } else {
		chain.doFilter(request, response);
		return;
	    }
	}
	chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
	filterConfig = null;
    }

}
