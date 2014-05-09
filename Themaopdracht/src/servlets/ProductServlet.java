package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDB;
import database.ConnectDBProduct;
import domeinklassen.Product;

public class ProductServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ConnectDB database = new ConnectDB();
		Connection con = database.maakVerbinding();
		String knop = req.getParameter("knop");
		
		ConnectDBProduct conn = new ConnectDBProduct(con);	
		ArrayList<Product> deVoorraad = conn.getProducten();
		RequestDispatcher rd = req.getRequestDispatcher("productenoverzicht.jsp");	//stuur bij default naar voorraadoverzicht
		
		//forward voorraadlijst naar de overzicht-pagina.
		if(knop.equals("overzicht")){
			req.setAttribute("voorraadlijst", deVoorraad);
		}
		
		//maak een nieuw product aan
		else if(knop.equals("nieuw")){
			String nm = req.getParameter("naam");
			String ma = req.getParameter("minaantal");
			String eh = req.getParameter("eenheid");
			String pps = req.getParameter("pps");
			ArrayList<String> velden = new ArrayList<String>();
			velden.add(nm); velden.add(ma); velden.add(eh); velden.add("pps");
			
			//check of nodige velden in zijn gevuld (artikelnummer bestaat niet meer omdat de database die straks gaat aanmaken)
			boolean allesIngevuld = true;
			for(String s : velden){
				if(s.equals("")){
					allesIngevuld = false;
					req.setAttribute("error", "Vul alle velden in!");
					break;
				}
			}
			//als gegevens ingevuld
			if(allesIngevuld){			
				try{	//check voor geldige nummers
					//maak product aan in database en haal op
					Product nieuw = conn.nieuwProduct(nm, Integer.parseInt(ma), eh, Double.parseDouble(pps));
					//stuur toString() van nieuwe product terug
					String terug = "Nieuw product aangemaakt: " + nieuw.toString();
					req.setAttribute("msg", terug);
				}
				catch(Exception ex){
					System.out.println(ex);
					req.setAttribute("error", "Voer geldige nummers in!");
				}
			}
			rd = req.getRequestDispatcher("product.jsp");
		}
		
		//zoek product op naam of artikelnummer
		else if(knop.equals("zoek")){
			String nm = req.getParameter("zoeknaam");
			String anr = req.getParameter("zoeknummer");	
			boolean zoekNaam = !nm.equals("");
			boolean zoekNummer = !anr.equals("");

			//in principe wordt het antwoord gegeven dat het product niet op de voorraadlijst staat
			if(zoekNaam || zoekNummer){
				req.setAttribute("zoekmsg", "Dit product staat niet op de voorraadlijst.");	
			}
			//check welke zoekterm er in is gevoerd
			if(zoekNummer){
				//check voor geldig artikelnummer (int)
				if(!anr.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
					req.setAttribute("error", "Vul een geldig artikelnummer in!");
				}
				else{
					int nummer = Integer.parseInt(anr);
					Product p = conn.zoekProduct(nummer);
					//wijzig antwoord naar gevonden en geef product mee
					req.setAttribute("zoekmsg", "Product gevonden!");		
					req.setAttribute("productgevonden", p);	
				}
			}
			else if(zoekNaam){
				//wijzig antwoord naar gevonden en geef product(en) mee
				ArrayList<Product> producten = conn.zoekProduct(nm);
				req.setAttribute("zoekmsg", "Product(en) gevonden!");	
				req.setAttribute("arraygevonden", producten);	
			}				
			else{
				req.setAttribute("error", "Vul een zoekcriterium in!");
			}
			rd = req.getRequestDispatcher("product.jsp");
		}
		
		//wijzig gezochte product
		else if(knop.equals("wijzig")){
			String productnummer = req.getParameter("product");
			Product hetProduct = conn.zoekProduct(Integer.parseInt(productnummer));
			req.setAttribute("product", hetProduct);
			rd = req.getRequestDispatcher("wijzigproduct.jsp");
		}		
		else if(knop.equals("verwijder")){
			String p = req.getParameter("product");
			if(conn.verwijderProduct(Integer.parseInt(p))){
				req.setAttribute("msg", "Product met succes verwijderd.");
			}
			else{
				req.setAttribute("error", "Kon product niet verwijderen!");
			}
			deVoorraad = conn.getProducten();
			req.setAttribute("voorraadlijst", deVoorraad);
		}	
		database.sluitVerbinding(con);
		rd.forward(req, resp);	
	}
}