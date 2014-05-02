package servlets;

import java.io.IOException;
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
		String knop = req.getParameter("knop");
		if(knop.equals("Done")){
			resp.sendRedirect("homepage.jsp");
		}
		else{
			if(knop.equals("MaakBestelling")){
				ConnectDBProduct productcon = new ConnectDBProduct();	
				ArrayList<Product> producten = productcon.getProducten();
				req.setAttribute("producten", producten);
				req.setAttribute("stap1", "done");
			}
			else if(knop.equals("KiesProducten")){
				ConnectDBProduct productcon = new ConnectDBProduct();
				String[] gekozenProducten = req.getParameterValues("gekozenProduct");
				ArrayList<Product> teBestellenProducten = new ArrayList<Product>();
				for(int i = 0; i < gekozenProducten.length; i++){
					teBestellenProducten.add(productcon.zoekProduct(Integer.parseInt(gekozenProducten[i])));
				}
				req.setAttribute("teBestellenProducten", teBestellenProducten);
				req.setAttribute("stap2", "done");
			}
			else if(knop.equals("Bestel")){
				ArrayList<BesteldProduct> deBesteldeProducten = new ArrayList<BesteldProduct>();
				ArrayList<Product> teBestellenProducten = new ArrayList<Product>();
				Bestelling deBestelling = new Bestelling();
				ConnectDBProduct productcon = new ConnectDBProduct();
				boolean goed = true;
				for(int i = 1; i <= productcon.hoogsteArtNr(); i ++){
					if(req.getParameter("" + i) != null){
						teBestellenProducten.add(productcon.zoekProduct(i));
						try{
							int aantal = Integer.parseInt(req.getParameter("" + i));
							deBesteldeProducten.add(new BesteldProduct(productcon.zoekProduct(i), aantal));
						}
						catch(Exception e){
							goed = false;
							req.setAttribute("msg", "Ongeldige waarde ingevoerd!");
						}
					}
				}
				if(goed == true){
					deBestelling.setBesteldeProducten(deBesteldeProducten);
					ConnectDBBestelling bestellingcon = new ConnectDBBestelling();
					req.setAttribute("deBestelling", bestellingcon.nieuwBestelling(deBestelling));
					req.setAttribute("stap3", "done");
				}
				//als er een ongeldige waarde is ingevuld
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
