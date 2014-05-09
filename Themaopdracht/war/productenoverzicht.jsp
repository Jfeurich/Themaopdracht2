<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Voorraad Menu</title>
</head>
<body>
	<form action="ProductServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Product" %>
		<%
			Object error = request.getAttribute("error");
			if(error != null){
				out.println(error);
			}
			Object msg = request.getAttribute("msg");
			if(msg != null){
				out.println(msg);
			}
			ArrayList<Product> voorraadlijst =(ArrayList<Product>)request.getAttribute("voorraadlijst");
			if(voorraadlijst == null){
				out.println("<h2>Error!</h2>");
				out.println("<p>Er is geen voorraad ingevoerd!</p>");
			}
			else{
				out.println("<h2>27: Alle producten</h2>");
				out.println("<table>");
				out.println("<tr>");
					out.println("<td>Kies</td>");
					out.println("<td>Artikelnummer</td>");
					out.println("<td>Artikelnaam</td>");
					out.println("<td>Aantal anwezig</td>");
					out.println("<td>Eenheid</td>");
					out.println("<td>Minimum aantal</td>");
					out.println("<td>Prijs per stuk</td>");
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
					out.println("</tr>");
				}
				out.println("</table>");
				out.println("<input type=submit name=knop value=wijzig />");
				out.println("<input type=submit name=knop value=verwijder />");
			}
			out.println("<a href=voorraad.jsp>Terug naar hoofdmenu Product</a>");
		%>
	</form>
</body>
</html>