package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IngelogdFilter implements Filter{
	
	public void init(FilterConfig arg0) throws ServletException {
		/* Filter is being placed into service, do nothing. */
	}
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {	

        HttpServletRequest request = (HttpServletRequest)req;
        String requestPath = request.getRequestURI();
        HttpSession session = request.getSession(false);
        HttpServletResponse response = (HttpServletResponse)resp;

        if((session == null || session.getAttribute("gebruiker") == null)) { 
        	if(!requestPath.endsWith("registreer.jsp")&& !requestPath.endsWith("loginpage.jsp")){
	            requestPath = requestPath.substring(14, requestPath.length());
	        	response.addCookie(new Cookie("vorigepagina", requestPath));
	        	response.sendRedirect("loginpage.jsp"); 
	        }
        	else{
        		chain.doFilter(req, resp);
        	}
        }
        else if((requestPath.endsWith("registreer.jsp") || requestPath.endsWith("loginpage.jsp")) && session.getAttribute("gebruiker") != null){
        	response.sendRedirect("index.jsp");
        }
        else{
        	chain.doFilter(req, resp);
        }
	}
	
	public void destroy() {
		/* Filter is being taken out of service, do nothing. */
	}
}