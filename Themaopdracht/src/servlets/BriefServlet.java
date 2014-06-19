package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBHerinneringsbrief;
import database.ConnectDBKlant;
import database.ConnectDBUser;
import domeinklassen.Klant;
import domeinklassen.User;

public class BriefServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection con = (Connection)req.getSession().getAttribute("verbinding");
		String knop = req.getParameter("knop");
		ConnectDBKlant kcon = new ConnectDBKlant(con);
	
		//zoek de gekozen klant op in de database en geef terug aan de jsp
		if(knop.equals("Selecteer klant")){	
			String klantnr = req.getParameter("gekozenklant");
			Klant deKlant = kcon.zoekKlant(Integer.parseInt(klantnr));
			req.setAttribute("deKlant", deKlant);
		}
		//als de gebruiker een brief wil toevoegen...
		else if(knop.equals("Versturen")){
			String reden = req.getParameter("reden");
			String klantnr = req.getParameter("klantnummer");
			int klantid = Integer.parseInt(klantnr);
			Klant deKlant = kcon.zoekKlant(Integer.parseInt(klantnr));
			try{
				ConnectDBUser ucon = new ConnectDBUser(con);
				User u = ucon.zoekUserVanKlant(deKlant);
				Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", 465);
				props.put("mail.smtp.ssl.enable", true);
				Session mailSession = Session.getInstance(props);
				MimeMessage msg = new MimeMessage(mailSession);
				msg.setFrom(new InternetAddress("testvoorwebapps@gmail.com", "Test"));
				msg.setRecipients(Message.RecipientType.TO, u.getEmail());
				msg.setSubject("Wachtwoorde vergeten");
				msg.setSentDate(Calendar.getInstance().getTime());
				msg.setText(reden);
				Transport.send(msg, "testvoorwebapps@gmail.com", "Wachtwoord0");
				ConnectDBHerinneringsbrief hcon = new ConnectDBHerinneringsbrief(con);
				hcon.nieuweBrief(klantid, reden);
				req.setAttribute("msg", "Brief met succes verstuurd!");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			req.setAttribute("msg", "Brief versturen mislukt!");
			req.setAttribute("deKlant", deKlant);
		}
		req.getRequestDispatcher("nieuwebrief.jsp").forward(req, resp);
	}
}
