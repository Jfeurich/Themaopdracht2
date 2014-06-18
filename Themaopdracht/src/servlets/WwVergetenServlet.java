package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDB;
import database.ConnectDBUser;
import domeinklassen.User;

public class WwVergetenServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	//Methode om een random String aan te maken
	private static final String opties = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private Random rnd = new Random();
	public String randomString(){
	   StringBuilder sb = new StringBuilder(10);
	   for( int i = 0; i < (10-1); i++ ) 					
	      sb.append(opties.charAt(rnd.nextInt(opties.length())));
	   return sb.toString();
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException { 
		RequestDispatcher rd = req.getRequestDispatcher("wwvergeten.jsp");
		String username = req.getParameter("username");
		String knopje = req.getParameter("knop");
		boolean mailverzenden = false;
		String email = null;
		String nieuwWw = null;
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", 465);
		props.put("mail.smtp.ssl.enable", true);
		Session mailSession = Session.getInstance(props);
		
		if(knopje != null){
			//check of het veld is ingevuld
			if(!username.equals("")){
				try{
					//maak verbinding met de database haal de user op
					ConnectDB database = new ConnectDB();
					Connection con = database.maakVerbinding();
					ConnectDBUser usercon = new ConnectDBUser(con);
					User u = usercon.getUser(username);
					//als de user bestaat wordt het wachtwoord gewijzigd
					if(u != null){
						nieuwWw = randomString();
						email = u.getEmail();
						u.setWachtwoord(nieuwWw);
						usercon.updateUser(u);
						mailverzenden = true;
					}
					else{
						req.setAttribute("error", "Username bestaat niet");
					}
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
			else{
				req.setAttribute("error", "Voer een username in");
			}
		}
		
		//Email verzenden met het nieuwe wachtwoord
		if(mailverzenden == true){
			try{
				MimeMessage msg = new MimeMessage(mailSession);
				msg.setFrom(new InternetAddress("testvoorwebapps@gmail.com", "Test"));
				msg.setRecipients(Message.RecipientType.TO, email);
				msg.setSubject("Wachtwoorde vergeten");
				msg.setSentDate(Calendar.getInstance().getTime());
				msg.setText("Beste " + username + "\nDit is uw nieuwe wachtwoord: "
						+ nieuwWw);
				Transport.send(msg, "testvoorwebapps@gmail.com", "Wachtwoord0");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		rd.forward(req, resp); 
	}
}
