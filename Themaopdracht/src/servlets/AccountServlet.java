package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBUser;
import domeinklassen.User;
import domeinklassen.Klant;

public class AccountServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection con = (Connection)req.getSession().getAttribute("verbinding");
		String knop = req.getParameter("knop");
		RequestDispatcher rd = req.getRequestDispatcher("mijnaccount.jsp");
		
		User gebruiker = (User)req.getSession().getAttribute("gebruiker");
		if(knop.equals("Wijzigingen opslaan")){
			Map<String, String> w = new HashMap<String, String>();
			String gn = req.getParameter("gebruikersnaam");
			if(!gn.equals("")){
				w.put("gn", gn);
			}
			String em = req.getParameter("email");
			if(!em.equals("")){
				w.put("em", em);
			}
			String ww = req.getParameter("wachtwoord");
			if(!ww.equals("")){
				w.put("ww", ww);
			}
			if(gebruiker.getType() == 3){
				String nm = req.getParameter("naam");
				if(!nm.equals("")){
					w.put("nm", nm);
				}
				String adr = req.getParameter("adr");
				if(!adr.equals("")){
					w.put("adr", adr);
				}
				String wp = req.getParameter("wp");
				if(!wp.equals("")){
					w.put("wp", wp);
				}
				String tel = req.getParameter("telnr");
				if(!tel.equals("")){
					w.put("tel", tel);
				}
				String rek = req.getParameter("rnr");
				if(!rek.equals("")){
					w.put("rek", rek);
				}
			}
			if(w.size() != 0){
				req.setAttribute("controle", "actief");
			}
			else{
				req.setAttribute("msg", "Geen wijzigingen aanwezig");
			}
		}
		else if(knop.equals("Account aanpassen")){
			
			rd = req.getRequestDispatcher("accountwijzigen.jsp");
		}
		else if(knop.equals("Bevestig")){
			String ww = req.getParameter("bevestigwachtwoord");
			Object wijzigingen = req.getAttribute("wijzigingen");
			if(ww.equals(gebruiker.getWachtwoord())){
				Klant k = null;
				if(gebruiker.getType() == 3){
					k = gebruiker.getDeKlant();
				}
				Map<String, String> w = (HashMap<String, String>)wijzigingen;
				for (Map.Entry<String, String> entry : w.entrySet()) {
				    String key = entry.getKey();
				    String value = entry.getValue();
				    if(value != null && !value.equals("")){
				    	if(key.equals("gn")){
				    		gebruiker.setGebruikersnaam(value);
				    	}
				    	else if(key.equals("em")){
				    		gebruiker.setEmail(value);
				    	}
				    	else if(key.equals("ww")){
				    		gebruiker.setWachtwoord(value);
				    	}
				    	if(gebruiker.getType() == 3){
					    	if(key.equals("naam")){
					    		
					    	}
					    	else if(key.equals("adr")){
						    		
						    }
					    	else if(key.equals("wp")){
						    		
						    }
					    	else if(key.equals("tel")){
						    		
						    }
					    	else if(key.equals("rek")){
						    		
						    }
				    	}
				    }
				}
				ConnectDBUser ucon = new ConnectDBUser(con);
				ucon.updateUser(gebruiker);
				req.setAttribute("msg", "Wijzigingen opgeslagen!");
				req.setAttribute("controle", null);
			}
			else{
				req.setAttribute("error", "Wachtwoord klopt niet!");
				req.setAttribute("wijzigingen", wijzigingen);
				req.setAttribute("controle", "actief");
			}
		}
		rd.forward(req, resp);
	}
}
