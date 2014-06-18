package logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener {
	public void contextInitialized(ServletContextEvent sce) {
		try{
			ServletContext sc = sce.getServletContext();
			synchronized(sc){
				//hou totale gebruikers bij
				int aantalGebruikers = 0;
				sc.setAttribute("aantalGebruikers", aantalGebruikers);
				//logger
				Logger logger = Logger.getLogger("ATDlogger");
				try {
					FileHandler fh = new FileHandler("%h\\Documents\\GitHub\\Themaopdracht2\\Themaopdracht\\src\\logger\\log.%u.%g.txt",  128 * 128, 10, true);
					fh.setFormatter(new MyFormatter());
					logger.addHandler(fh);
				} 
				catch (IOException ioe){ 
					ioe.printStackTrace(); 
				}
				logger.setLevel(Level.ALL);
				logger.info("Logger initialized, 1 gebruiker");
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void contextDestroyed(ServletContextEvent sce){}
}


