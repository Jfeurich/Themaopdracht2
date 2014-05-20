<jsp:include page="header.jsp" > 
 <jsp:param name="titel" value="Wijzig product" /> 
</jsp:include> 
	<form action="WijzigProductServlet.do" method="post">
		<%@ page import="domeinklassen.Product" %>
		<div>
			<h1><span>14: Product wijzigen</span></h1>
			<%
				Object error =  request.getAttribute("error");
				if(error != null){
					out.println("<h3><span>Error!</span></h3>");
					out.println("<p name=error class=error >" + error + "</p>");
				}
				else{
					Object msg = request.getAttribute("msg");
					if(msg != null){
						out.println("<h3 name=msg class=msg >" + msg + "</h3>");
					}
				}
				Product p = null;
				Object hetProduct = request.getAttribute("product");
				if(hetProduct != null){
					p = (Product)hetProduct;
				}
				if(p != null){
					out.println("<h2><span>Voer nieuwe waarden in voor de gewenste velden</span></h2>");
					out.println("<table>");
						out.println("<tr>");
							out.println("<th>Artikelnummer</th>");
							out.println("<th>Artikelnaam</th>");
							out.println("<th>Aantal anwezig</th>");
							out.println("<th>Eenheid</th>");
							out.println("<th>Minimum aantal</th>");
							out.println("<th>Prijs per stuk</th>");
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
					out.println("<input type=hidden name=product value=" + p.getArtikelNr() + " />");
					out.println("<input type=submit name=knop value=wijzig />");
					out.println("<input type=submit name=knop value=verwijder />");
				}
				else{
					out.println("<h1><span>Deze pagina is pas bruikbaar als een product uit de voorraad is geselecteerd!</span></h1>");
				}
			%>
		</div>
	</form>
	<p><a href="product.jsp">Terug naar hoofdmenu product</a></p>
</body>
</html>