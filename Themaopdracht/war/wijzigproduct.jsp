<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Wijzig product</title>
</head>
<body>
	<form action="WijzigProductServlet.do" method="post">
		<%@ page import="domeinklassen.Product" %>
		<div>
			<h2>Product wijzigen</h2>
			<%
				Object error =  request.getAttribute("error");
				if(error != null){
					out.println(error);
				}
				else{
					Object msg = request.getAttribute("msg");
					if(msg != null){
						out.println(msg);
					}
				}
				Product p = null;
				Object hetProduct = request.getAttribute("product");
				if(hetProduct != null){
					p = (Product)hetProduct;
				}
				if(p != null){
					out.println("<input type=hidden name=product value=" + p.getArtikelNr() + " />");
					out.println("<h3>Voer nieuwe waarden in voor de gewenste velden</h3>");
					out.println("<table>");
						out.println("<tr>");
							out.println("<td>Artikelnummer</td>");
							out.println("<td>Artikelnaam</td>");
							out.println("<td>Aantal anwezig</td>");
							out.println("<td>Eenheid</td>");
							out.println("<td>Minimum aantal</td>");
							out.println("<td>Prijs per stuk</td>");
						out.println("</tr>");	
						out.println("<tr>");
							out.println("<td>" + p.getArtikelNr() + "</td>");
							out.println("<td>" + p.getNaam() + "</td>");
							out.println("<td>" + p.getAantal() + "</td>");
							out.println("<td>" + p.getEenheid() + "</td>");
							out.println("<td>" + p.getMinimumAanwezig() + "</td>");
							out.println("<td>" + p.getPrijsPerStuk() + "</td>");
						out.println("</tr>");
						out.println("<tr>");
							out.println("<td>" + p.getArtikelNr() + "</td>");
							out.println("<td><input type=text name=naam /></td>");
							out.println("<td><input type=text name=aantal /></td>");
							out.println("<td><input type=text name=eenheid /></td>");
							out.println("<td><input type=text name=minaantal /></td>");
							out.println("<td><input type=text name=pps /></td>");
						out.println("</tr>");
					out.println("</table>");
					out.println("<input type=submit name=knop value=wijzig />");
				}
				else{
					out.println("<h3>Deze pagina is pas bruikbaar als een product uit de voorraad is geselecteerd!</h3>");
					out.println("<a href=voorraad.jsp>Terug naar hoofdmenu voorraad</a>");
				}
			%>
		</div>
	</form>
</body>
</html>