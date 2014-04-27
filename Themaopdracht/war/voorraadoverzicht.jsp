<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Voorraad Menu</title>
</head>
<body>
		<%@ page import="java.util.ArrayList,domeinklassen.Product" %>
		<%
			ArrayList<Product> voorraadlijst =(ArrayList<Product>)request.getAttribute("voorraadlijst");
			if(voorraadlijst == null){
				out.println("<h2>Error!</h2>");
				out.println("<p>Er is geen voorraad ingevoerd!</p>");
			}
			else{
				out.println("<h2>De huidige voorraad</h2>");
				out.println("<table>");
				out.println("<tr>");
					out.println("<td>Artikelnummer</td>");
					out.println("<td>Artikelnaam</td>");
					out.println("<td>Aantal anwezig</td>");
					out.println("<td>Eenheid</td>");
					out.println("<td>Minimum aantal</td>");
					out.println("<td>Prijs per stuk</td>");
				out.println("</tr>");	
				for(Product p : voorraadlijst){
					out.println("<tr>");
						out.println("<td>" + p.getArtikelNr() + "</td>");
						out.println("<td>" + p.getNaam() + "</td>");
						out.println("<td>" + p.getAantal() + "</td>");
						out.println("<td>" + p.getEenheid() + "</td>");
						out.println("<td>" + p.getMinimumAanwezig() + "</td>");
						out.println("<td>" + p.getPrijsPerStuk() + "</td>");
					out.println("</tr>");
				}
				out.println("</table>");
			}
		%>
</body>
</html>