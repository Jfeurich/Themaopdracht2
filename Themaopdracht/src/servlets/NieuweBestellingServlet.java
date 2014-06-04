package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBBestelling;
import database.ConnectDBProduct;
import domeinklassen.BesteldProduct;
import domeinklassen.Bestelling;
import domeinklassen.Product;

public class NieuweBestellingServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection con = (Connection)req.getSession().getAttribute("verbinding");
		String knop = req.getParameter("knop");
		
		if(knop.equals("Done")){
			resp.sendRedirect("index.jsp");
		}
		else{
			//haal alle producten op
			if(knop.equals("MaakBestelling")){
				ConnectDBProduct productcon = new ConnectDBProduct(con);	
				ArrayList<Product> producten = productcon.getProducten();
				req.setAttribute("producten", producten);
				req.setAttribute("stap1", "done");
			}
			//kies producten met checkbox en haal op
			else if(knop.equals("KiesProducten")){
				String[] gekozenProducten = req.getParameterValues("gekozenProduct");
				//kijk of er een product is geselecteerd
				if(gekozenProducten != null){
					ArrayList<Product> teBestellenProducten = new ArrayList<Product>();
					ConnectDBProduct productcon = new ConnectDBProduct(con);
					for(int i = 0; i < gekozenProducten.length; i++){
						teBestellenProducten.add(productcon.zoekProduct(Integer.parseInt(gekozenProducten[i])));
					}
					req.setAttribute("teBestellenProducten", teBestellenProducten);
					req.setAttribute("stap2", "done");
				}
				else{
					req.setAttribute("error", "Selecteer minimaal 1 product!");
					ConnectDBProduct productcon = new ConnectDBProduct(con);	
					ArrayList<Product> producten = productcon.getProducten();
					req.setAttribute("producten", producten);
					req.setAttribute("stap1", "done");
				}
			}
			//de beruiker wil bestellen
			else if(knop.equals("Bestel")){
				ArrayList<BesteldProduct> deBesteldeProducten = new ArrayList<BesteldProduct>();
				ArrayList<Product> teBestellenProducten = new ArrayList<Product>();
				Bestelling deBestelling = new Bestelling();	//maak een lege bestelling aan
				boolean goed = true;
				String[] gewijzigdeproducten = req.getParameterValues("wijzig");
				String[] wijzigaantal =  req.getParameterValues("wijzigaantal");
				if(gewijzigdeproducten != null){
					for(int i = 0; i < gewijzigdeproducten.length; i++){
						//zet het product hoe dan ook in de lijst met teBestellenProducten(deze moet mee terug naar de jsp
						//als er iets fout gaat bij het maken van de bestelde producten
						int productid = Integer.parseInt(gewijzigdeproducten[i]);
						ConnectDBProduct productcon = new ConnectDBProduct(con);
						Product hetProduct = productcon.zoekProduct(productid);
						teBestellenProducten.add(hetProduct);
						try{	//kijk of een geldig aantal in is gevoerd en zo ja, maak een nieuw besteldproduct en zet
								//deze in de lijst met deBesteldeProducten
							int aantal = Integer.parseInt(wijzigaantal[i]);
							//check voor een negatief nummer en throw een NFE als dit het geval is
							if(aantal > 0){
								deBesteldeProducten.add(new BesteldProduct(hetProduct, aantal));
							}
							else{
								throw new NumberFormatException();
							}
						}
						catch(NumberFormatException e){
							goed = false;
							req.setAttribute("error", "Ongeldige waarde ingevoerd!");
						}	
					}
				}
				//als je mag bestellen, voeg de bestelling met de bestelde producten toe aan de database
				if(goed == true){
					deBestelling.setBesteldeProducten(deBesteldeProducten);
					ConnectDBBestelling bestellingcon = new ConnectDBBestelling(con);
					req.setAttribute("deBestelling", bestellingcon.nieuwBestelling(deBestelling));
					req.setAttribute("stap3", "done");
				}
				//als er een ongeldige waarde is ingevuld, stuur teBestellenProducten terug en rest naar stap 2
				else{
					req.setAttribute("teBestellenProducten", teBestellenProducten);
					req.setAttribute("stap2", "done");
				}
			}
			RequestDispatcher rd = req.getRequestDispatcher("nieuwebestelling.jsp");
			rd.forward(req, resp);	
		}
	}
}
