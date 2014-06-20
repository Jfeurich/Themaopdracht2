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
		
		if(knop.equals("Wijzigingen opslaan")/*Voor eigen account*/ || knop.equals("Account aanpassen")/*Voor administratie*/){
			boolean magWijzigen = true;	//check op geldig nummer bij telefoonnummer
			//Sla wijzigingen op in een HashMap. De key geeft aan welke waarde is gewijzigd, het value wat de nieuwe waarde moet worden.
			Map<String, String> w = new HashMap<String, String>();
			boolean isKlant = false;
			if(gebruiker.getType() == 3){
				isKlant = true;
			}
			if(knop.equals("Account aanpassen")){
				//Als de administratie aan het werk is wordt bij gehouden welke account moet worden gewijzigd (andere gebruikers wijzigen alleen hun eigen account)
				//De actra waarde (de)activate kan worden gewijzigd, en de administratie wordt terug gestuurd naar accountwijzigen ipv mijnaccount.
				rd = req.getRequestDispatcher("accountwijzigen.jsp");
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
			else if(knop.equals("Wijzigingen opslaan")){
				//Alleen de gebruiker zelf mag zijn/haar wachtwoord wijzigen.
				String ww = req.getParameter("wachtwoord");
				if(!ww.equals("")){
					w.put("ww", ww);
				}
			}
			//Ga nu de andere mogelijke velden af en kijk wat er nog meer is gewijzigd.
			String em = req.getParameter("email");
			if(!em.equals("")){
				w.put("em", em);
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
		}
		else if(knop.equals("Bevestig")){
			String ww = req.getParameter("bevestigwachtwoord");	//automatisch ingevuld voor administratie
			Object wijzigingen = session.getAttribute("wijzigingen");
			if(ww.equals(gebruiker.getWachtwoord())){
				//check of eigen account moet worden gewijzigd of de administratie bezig is aan andermans account
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
				//Doorloop HashMap met wijzigingen, wijzig aan de hand van de keys alle gekozen waarden in het bijbehorende value
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
