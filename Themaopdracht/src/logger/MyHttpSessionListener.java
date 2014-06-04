package logger;

import java.sql.Connection;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import database.ConnectDB;
import domeinklassen.User;

public class MyHttpSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent e) {
		try{
			//maak verbinding met database
			ConnectDB database = new ConnectDB();
			Connection con = database.maakVerbinding();
			e.getSession().setAttribute("verbinding", con);
			ServletContext sc = e.getSession().getServletContext();
			//aantal gebruikers ++
			Object o = sc.getAttribute("aantalGebruikers");
			int aantalGebruikers = (int)o;
			aantalGebruikers++;
			sc.setAttribute("aantalGebruikers", aantalGebruikers);
			//logger
			Logger logger = Logger.getLogger("ATDlogger");
			logger.info("Session created: " + e.getSession().getId());
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
			int aantalGebruikers = (int)sc.getAttribute("aantalGebruikers");
			//verwijder gebruiker
			Object g = e.getSession().getAttribute("gebruiker");
			if(g != null){
				User u = (User)g;
				logger.info("Gebruiker uitgelogd: " + u.getGebruikersnaam());
				e.getSession().setAttribute("gebruiker", null);
			}
			//stel aantal gebruikers in
			else if(aantalGebruikers > 0){
				aantalGebruikers--;
				sc.setAttribute("aantalGebruikers", (aantalGebruikers));
				logger.info("Aantal gebruikers: " + aantalGebruikers);
			}
			logger.info("Session destroyed: " + e.getSession().getId());
			//verbreek verbinding met database
			ConnectDB database = new ConnectDB();
			Connection con = (Connection)e.getSession().getAttribute("verbinding");
			database.sluitVerbinding(con);
			e.getSession().setAttribute("verbinding", null);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
