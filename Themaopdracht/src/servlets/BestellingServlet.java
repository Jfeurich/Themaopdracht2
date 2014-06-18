package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBBestelling;
import database.ConnectDBProduct;
import domeinklassen.Bestelling;
import domeinklassen.Product;

public class BestellingServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection con = (Connection)req.getSession().getAttribute("verbinding");
		String knop = req.getParameter("knop");
		ConnectDBBestelling bcon = new ConnectDBBestelling(con);
		
		if(knop.equals("Zoek")){
			String id = req.getParameter("zoekid");
			String dat = req.getParameter("zoekdatum");
			String nm = req.getParameter("zoeknaam");
			String eh = req.getParameter("zoekeenheid");
			String anr = req.getParameter("zoeknummer");	
			ConnectDBProduct conn = new ConnectDBProduct(con);
			ArrayList<Bestelling> lijst = new ArrayList<Bestelling>();
			ArrayList<Product> terug = new ArrayList<Product>();
			if(!id.equals("")){
				try{
					lijst.add(bcon.zoekBestelling(Integer.parseInt(id)));
				}catch(NumberFormatException e){
					req.setAttribute("error", "Vul een geldig nummer in bij ID!");
				}
			}
			if(!dat.equals("")){
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				try{
					for(Bestelling b : bcon.getBestellingenOpDatum(df.parse(dat))){
						lijst.add(b);
					}
				}catch(Exception e){
					req.setAttribute("error", "Vul een geldige datum in!");
				}
			}
			if(!anr.equals("")){
				try{
					int nummer = Integer.parseInt(anr);
					terug.add(conn.zoekProduct(nummer));
				}catch(NumberFormatException e){
					req.setAttribute("error", "Vul een geldig artikelnummer in!");
				}
			}
			if(!nm.equals("")){
				//wijzig antwoord naar gevonden en geef product(en) mee
				for(Product p : conn.zoekProductNaam(nm)){
					terug.add(p);
				}
			}	
			if(!eh.equals("")){
				//wijzig antwoord naar gevonden en geef product(en) mee
				for(Product p : conn.zoekProductEenheid(eh)){
					terug.add(p);
				}
			}	
			if(terug.size() != 0){
				for(Product p : terug){
					for(Bestelling b : bcon.getBestellingenVanProduct(p.getArtikelNr())){
						lijst.add(b);
					}
				}		
			}
			if(lijst.size() == 0){
				req.setAttribute("zoekmsg", "Geen bestellingen gevonden met ingevulde criteria");
			}
			else{
				
				req.setAttribute("zoekmsg", "Bestelling(en) gevonden!");	
				req.setAttribute("arraygevonden", lijst);				
			}
		}
		
		//markeer geselecteerde bestelling als geleverd
		else if(knop.equals("Boek in")){
			try{
				String id = req.getParameter("gekozenbestelling");
				Bestelling b = bcon.zoekBestelling(Integer.parseInt(id));
				b.setIsGeleverd(true);
				b.setVerwachteDatum(new Date());
				bcon.updateBestelling(b);
				req.setAttribute("msg", "Bestelling gemarkeerd als geleverd!");
			} catch(Exception e){
				req.setAttribute("error", "Niet mogelijk om bestelling te wijzigen!");
			}
		}			
		req.getRequestDispatcher("bestelling.jsp").forward(req, resp);	
	}
}