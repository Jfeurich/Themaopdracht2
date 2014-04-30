<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Nieuwe bestelling</title>
</head>
<body>
	<form action="NieuweBestellingServlet.do" method="post">
		<%@ page import="java.util.ArrayList, domeinklassen.Bestelling, domeinklassen.Product" %>
		<div>
			<h2>Nieuwe bestelling aanmaken</h2>
			<% 				
				Object error =  request.getAttribute("error");
				if(error != null){
					out.println("<h2>Error!</h2>");
					out.println(error);
				}
				else{
					Object msg = request.getAttribute("msg");
					if(msg != null){
						out.println(msg);
					}
				}
				Object be = request.getAttribute("deBestelling");
				Object teBP = request.getAttribute("teBestellenProducten");
				if(be == null){
					out.println("<input type=submit name=knop value=maakBestelling />");
				}
				else if(teBP == null){
					ArrayList<Product> producten = (ArrayList<Product>)request.getAttribute("producten");				
					out.println("<h2>Kies de te bestellen producten</h2>");	
					out.println("<table>");
					out.println("<tr>");
						out.println("<td>Artikelnummer</td>");
						out.println("<td>Naam</td>");
						out.println("<td>Minimum aanwezig</td>");
						out.println("<td>Aantal aanwezig</td>");
					out.println("</tr>");
					for(Product p : producten){
						out.println("<tr>");
							out.println("<td><input type=checkbox name=gekozenproduct value=" + p.getArtikelNr() + " /></td>");
							out.println("<td>" + p.getNaam() + "</td>");
							out.println("<td>" + p.getMinimumAanwezig() + "</td>");
							out.println("<td>" + p.getAantal() + "</td>");
						out.println("</tr>");
					}
					out.println("</table>");
					out.println("<input type=submit name=knop value=kiesProducten />");	
				}
				else{
					ArrayList<Product> teBestellenProducten = (ArrayList<Product>)teBP;
					Bestelling deBestelling = (Bestelling)be;
					out.println("<h2>Kies de aantallen van de producten</h2>");
					out.println("<table>");
						out.println("<tr>");
							out.println("<td>Artikelnummer</td>");
							out.println("<td>Naam</td>");
							out.println("<td>Eenheid</td>");
							out.println("<td>Aantal aanwezig</td>");
							out.println("<td>Minimum aanwezig</td>");
							out.println("<td>Prijs per stuk</td>");
							out.println("<td>Aantal te bestellen</td>");				
						out.println("</tr>");
						for(Product p : teBestellenProducten){
							out.println("<tr>");
								out.println("<td>" + p.getArtikelNr() + "</td>");
								out.println("<td>" + p.getNaam() + "</td>");
								out.println("<td>" + p.getEenheid() + "</td>");
								out.println("<td>" + p.getMinimumAanwezig() + "</td>");
								out.println("<td>" + p.getAantal() + "</td>");
								out.println("<td>" + p.getPrijsPerStuk() + "</td>");
								out.println("<td><input type= name=aantalTeBestellen/></td>");
							out.println("</tr>");
						}
					out.println("</table>");
					//out.println("<input type=hidden name=autovanklus value=" + deAuto.getID() + " />");
					out.println("<input type=submit name=knop value=bestel />");		
				}
			%>
		</div>
	</form>
</body>
</html>