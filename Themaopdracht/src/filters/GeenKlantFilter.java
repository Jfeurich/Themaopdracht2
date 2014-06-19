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

import domeinklassen.User;

public class GeenKlantFilter implements Filter{
	public void init(FilterConfig arg0) throws ServletException {
		/* Filter is being placed into service, do nothing. */
	}
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        HttpSession session = request.getSession(false);
        //stuur niet-ingelogde gebruikers uit IngelogdFilter door
        if((session == null || session.getAttribute("gebruiker") == null)) { 
        	chain.doFilter(req, resp);
        }
	    else{
			try{
				User u = (User)request.getSession().getAttribute("gebruiker");
				if (u.getType() == 3) {
					request.setAttribute("msg", "Log eerst in met een gebruikersaccount die toestemming heeft om op deze pagina te komen!");
					request.getRequestDispatcher("/index.jsp").forward(req, resp);
				} 
				else {
					chain.doFilter(req, resp);
				}
			}
			catch(NullPointerException e){
	            response.sendRedirect(request.getContextPath() + "/loginpage.jsp"); 
	            System.out.println("redirect");		
			}
	       }
	}
	public void destroy() {
		/* Filter is being taken out of service, do nothing. */
	}
}
