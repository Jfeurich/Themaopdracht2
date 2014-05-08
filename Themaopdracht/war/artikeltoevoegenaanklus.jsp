<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
//Resultaten uit het verleden bieden geen garanties voor de toekomst
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Artikel toevoegen aan klus</title>
</head>
<body>
	<form action="KlusWijzigenServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Klus,domeinklassen.Onderhoudsbeurt,domeinklassen.Reparatie,domeinklassen.Product" %>
		<div>
			<h2>Kies het gewenste artikel en klik op "VoegArtikelToe"</h2>
			<%
				Object error =  request.getAttribute("error");
				if(error != null){
					out.println("<h2>Foutmelding</h2>");
					out.println(error);
				}
				else{
					Object msg = request.getAttribute("msg");
					if(msg != null){
						out.println(msg);
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
							out.println("<td>X</td>");
							out.println("<td>Artikelnummer</td>");
							out.println("<td>Artikelnaam</td>");
							out.println("<td>Aantal anwezig</td>");
							out.println("<td>Eenheid</td>");
							out.println("<td>Minimum aantal</td>");
							out.println("<td>Prijs per stuk</td>");
							out.println("<td>Aantal</td>");
						out.println("</tr>");	
						boolean eerste = true;
						for(Product p : voorraadlijst){
							out.println("<tr>");
								out.println("<td><input type=radio name=product ");
								if(eerste){out.println("checked=checked ");eerste=false;}
								out.println("value=" + p.getArtikelNr() + " /></td>");
								out.println("<td>" + p.getArtikelNr() + "</td>");
								out.println("<td>" + p.getNaam() + "</td>");
								out.println("<td>" + p.getAantal() + "</td>");
								out.println("<td>" + p.getEenheid() + "</td>");
								out.println("<td>" + p.getMinimumAanwezig() + "</td>");
								out.println("<td>" + p.getPrijsPerStuk() + "</td>");
								out.println("<td><input type=text name=aantal  /></td>");
							out.println("</tr>");
						}
						out.println("</table>");
					}
					out.println("<td><input type=hidden name=gekozenklus value=" + deKlus.getID() + " /></td>");
					out.println("<input type=submit name=knop value=VoegArtikelToe />");
				}
			%>
		</div>
	</form>
	
</body>
</html>