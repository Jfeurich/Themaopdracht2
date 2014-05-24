package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IngelogdFilter implements Filter{
	
	public void init(FilterConfig arg0) throws ServletException {
		/* Filter is being placed into service, do nothing. */
	}
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {	

        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        HttpSession session = request.getSession(false);

        String requestPath = request.getRequestURI();

        if((needsAuthentication(requestPath) && session.getAttribute("gebruiker") == null) || session == null) { 
            response.sendRedirect(request.getContextPath() + "/loginpage.jsp"); 
        } 
        else {
            chain.doFilter(req, resp); 
        }
	}
	
	public void destroy() {
		/* Filter is being taken out of service, do nothing. */
	}

    private boolean needsAuthentication(String url) {
        String[] validNonAuthenticationUrls = { "LoginServlet.do", "RegistreerServlet.do" };
        for(String validUrl : validNonAuthenticationUrls) {
            if (url.endsWith(validUrl)) {
                return false;
            }
        }
        return true;
    }
}