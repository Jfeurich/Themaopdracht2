package filters;

import java.io.IOException; 

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domeinklassen.User;

public class GeenGaragebeheerderFilter implements Filter{
	public void init(FilterConfig arg0) throws ServletException {
		/* Filter is being placed into service, do nothing. */
	}
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
		try{
			User u = (User)request.getSession().getAttribute("gebruiker");
			if (u.getType() == 2) {
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
	public void destroy() {
		/* Filter is being taken out of service, do nothing. */
	}
}