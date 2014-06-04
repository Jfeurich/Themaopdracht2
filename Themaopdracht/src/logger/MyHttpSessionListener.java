package logger;

import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import domeinklassen.User;

public class MyHttpSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent e) {
		try{
			Logger logger = Logger.getLogger("ATDlogger");
			logger.info("Session created: " + e.getSession().getId());
			ServletContext sc = e.getSession().getServletContext();
			Object o = sc.getAttribute("aantalGebruikers");
			int aantalGebruikers = (int)o;
			aantalGebruikers++;
			sc.setAttribute("aantalGebruikers", aantalGebruikers);
			logger.info("Aantal gebruikers: " + aantalGebruikers);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent e) {
		try{
			Logger logger = Logger.getLogger("ATDlogger");
			ServletContext sc = e.getSession().getServletContext();
			Object o = sc.getAttribute("aantalGebruikers");
			int aantalGebruikers = (int)o;
			Object g = e.getSession().getAttribute("gebruiker");
			if(g != null){
				User u = (User)g;
				logger.info("Gebruiker uitgelogd: " + u.getGebruikersnaam());
				e.getSession().setAttribute("gebruiker", null);
			}
			else if(aantalGebruikers > 0){
				aantalGebruikers--;
				sc.setAttribute("aantalGebruikers", (aantalGebruikers));
				logger.info("Aantal gebruikers: " + aantalGebruikers);
			}
			logger.info("Session destroyed: " + e.getSession().getId());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
