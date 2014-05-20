<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
//Resultaten uit het verleden bieden geen garanties voor de toekomst
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Artikel toevoegen aan klus</title>
</head>
<body>
	<p><a href="index.html">Hoofdmenu</a></p>
	<form action="KlusWijzigenServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Klus,domeinklassen.Onderhoudsbeurt,domeinklassen.Reparatie,domeinklassen.Product" %>
		<div>
			<h2>5: Kies de gewenste artikel(en) en klik op "VoegToe"</h2>
			<%
				Object error =  request.getAttribute("error");
				if(error != null){
					out.println("<h3>Error!</h3>");
					out.println("<p name=error >" + error + "</p>");
				}
				else{
					Object msg = request.getAttribute("msg");
					if(msg != null){
						out.println("<h3 name=msg>" + msg + "</h3>");
					}
				}
				Object gekozen = request.getAttribute("deKlus");
				if(gekozen != null){
					Klus deKlus = (Klus)gekozen;
					ArrayList<Product> voorraadlijst = (ArrayList<Product>)request.getAttribute("voorraadlijst");
					out.println("<h2>Wijzig de klus</h2>");
					if(voorraadlijst == null){
						out.println("<h3>Error!</h3>");
						out.println("<p>Er is geen voorraad ingevoerd!</p>");
					}
					else{
						out.println("<h3>Artikelen die momenteel op voorraad zijn</h3>");
						out.println("<table>");
						out.println("<tr>");
							out.println("<th>X</th>");
							out.println("<th>Artikelnummer</th>");
							out.println("<th>Artikelnaam</th>");
							out.println("<th>Aantal aanwezig</th>");
							out.println("<th>Eenheid</th>");
							out.println("<th>Aantal</th>");
						out.println("</tr>");	
						for(Product p : voorraadlijst){
							out.println("<tr>");
								out.println("<td><input type=checkbox name=product value=" + p.getArtikelNr() + " /></td>");
								out.println("<td>" + p.getArtikelNr() + "</td>");
								out.println("<td>" + p.getNaam() + "</td>");
								out.println("<td>" + p.getAantal() + "</td>");
								out.println("<td>" + p.getEenheid() + "</td>");
								out.println("<td><input type=text name=aantal  /></td>");
								out.println("<input type=hidden name=alleProducten value=" + p.getArtikelNr() + " />");
								out.println("<input type=hidden name=voorraad value=" + p.getAantal() + " />");
							out.println("</tr>");
						}
						out.println("</table>");
					}
					out.println("<td><input type=hidden name=gekozenklus value=" + deKlus.getID() + " /></td>");
					out.println("<input type=submit name=knop value=VoegToe />");
				}
			%>
		</div>
	</form>
</body>
</html>