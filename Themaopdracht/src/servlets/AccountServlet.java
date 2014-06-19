package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.ConnectDBKlant;
import database.ConnectDBUser;
import domeinklassen.Klant;
import domeinklassen.User;

public class AccountServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection con = (Connection)req.getSession().getAttribute("verbinding");
		String knop = req.getParameter("knop");
		RequestDispatcher rd = req.getRequestDispatcher("mijnaccount.jsp");
		
		User gebruiker = (User)req.getSession().getAttribute("gebruiker");
		HttpSession session = req.getSession();
		
		if(knop.equals("Wijzigingen opslaan") || knop.equals("Account aanpassen")){
			boolean magWijzigen = true;
			Map<String, String> w = new HashMap<String, String>();
			boolean isKlant = false;
			if(gebruiker.getType() == 3){
				isKlant = true;
			}
			if(knop.equals("Account aanpassen")){
				User u = (User)session.getAttribute("wijzig");
				if(u.getType() == 3){
					isKlant = true;
					if(req.getParameter("deactivate") != null){
						w.put("deactivateklant", "ja");
					}
					else if(req.getParameter("activate") != null){
						w.put("activateklant", "ja");
					}
				}
				else{
					if(req.getParameter("deactivate") != null){
						w.put("deactivate", "ja");
					}
					else if(req.getParameter("activate") != null){
						w.put("activate", "ja");
					}
				}
			}
			String em = req.getParameter("email");
			if(!em.equals("")){
				w.put("em", em);
			}
			if(knop.equals("Wijzigingen opslaan")){
				String ww = req.getParameter("wachtwoord");
				if(!ww.equals("")){
					w.put("ww", ww);
				}
			}
			if(isKlant){
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
					try{
						Integer.parseInt(tel);
						w.put("tel", tel);
					} catch(NumberFormatException e){
						req.setAttribute("error", "Vul een geldig telefoonnummer in!");
						magWijzigen = false;
					}
				}
				String rek = req.getParameter("rnr");
				if(!rek.equals("")){
					w.put("rek", rek);
				}
			}
			if(w.size() != 0 && magWijzigen){
				req.setAttribute("controle", "actief");
				session.setAttribute("wijzigingen", w);
			}
			else if(magWijzigen){
				req.setAttribute("msg", "Geen wijzigingen aanwezig");
			}
			if(knop.equals("Account aanpassen")){			
				rd = req.getRequestDispatcher("accountwijzigen.jsp");
			}
		}
		else if(knop.equals("Bevestig")){
			String ww = req.getParameter("bevestigwachtwoord");
			Object wijzigingen = session.getAttribute("wijzigingen");
			if(ww.equals(gebruiker.getWachtwoord())){
				Object o = session.getAttribute("wijzig");
				if(o != null){
					gebruiker = (User)o;
					rd = req.getRequestDispatcher("accountwijzigen.jsp");
				}
				Klant k = null;
				if(gebruiker.getType() == 3){
					k = gebruiker.getDeKlant();
				}
				ConnectDBUser ucon = new ConnectDBUser(con);
				String wijzignaam = gebruiker.getGebruikersnaam();
				Map<String, String> w = (HashMap<String, String>)wijzigingen;
				for (Entry<String, String> entry : w.entrySet()) {
				    String key = entry.getKey();
				    String value = entry.getValue();
			    	if(key.equals("em")){
			    		gebruiker.setEmail(value);
			    	}
			    	else if(key.equals("ww")){
			    		gebruiker.setWachtwoord(value);
			    	}
			    	else if(key.equals("deactivate")){
			    		ucon.verwijderUser(gebruiker.getID());
			    	}
			    	else if(key.equals("activate")){
			    		ucon.activeerUser(gebruiker.getID());
			    	}
			    	if(gebruiker.getType() == 3){
			    		if(key.equals("deactivateklant")){
			    			ucon.verwijderUserIsKlant(gebruiker.getID(), gebruiker.getDeKlant().getKlantnummer());
			    		}
			    		else if(key.equals("activateklant")){
				    		ucon.activeerUserIsKlant(gebruiker.getID(), gebruiker.getDeKlant().getKlantnummer());
				    	}
			    		else if(key.equals("naam")){
				    		k.setNaam(value);
				    	}
				    	else if(key.equals("adr")){
					    	k.setAdres(value);
					    }
				    	else if(key.equals("wp")){
					    	k.setPlaats(value);
					    }
				    	else if(key.equals("tel")){
					    	k.setTelefoonnummer(Integer.parseInt(value));	
					    }
				    	else if(key.equals("rek")){
					    	k.setRekeningnummer(value);
					    }
			    	}
				}
				ucon.updateUser(gebruiker);
				if(k != null){
					ConnectDBKlant kcon = new ConnectDBKlant(con);
					kcon.updateKlant(k);
				}
				if(o == null){
					User u = ucon.getUser(wijzignaam);
					session.setAttribute("gebruiker", u);
					resp.addCookie(new Cookie("username", wijzignaam));
				}
				else{
					session.removeAttribute("wijzig");
				}
				req.setAttribute("msg", "Wijzigingen opgeslagen!");
				req.setAttribute("controle", null);
				session.removeAttribute("wijzigingen");
			}
			else{
				req.setAttribute("error", "Wachtwoord klopt niet!");
				req.setAttribute("wijzigingen", wijzigingen);
				req.setAttribute("controle", "actief");
			}
		}
		else if(knop.equals("Kies gebruiker")){
			String geb = req.getParameter("kiesgebruiker");
			ConnectDBUser ucon = new ConnectDBUser(con);
			User u = ucon.zoekUser(Integer.parseInt(geb));
			session.setAttribute("wijzig", u);
			String actief = req.getParameter("actief");
			if(actief.equals("ja")){
				req.setAttribute("deactiveren", "mogelijk");
			}
			rd = req.getRequestDispatcher("accountwijzigen.jsp");
		}
		rd.forward(req, resp);
	}
}
