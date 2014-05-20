<jsp:include page="header.jsp" > 
 <jsp:param name="titel" value="Overzicht alle producten" /> 
</jsp:include> 
	<form action="ProductServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Product" %>
		<%
			Object error =  request.getAttribute("error");
			if(error != null){
				out.println("<h3><span>Error!</span></h3>");
				out.println("<p name=error class=error >" + error + "</p>");
			}
			else{
				Object msg = request.getAttribute("msg");
				if(msg != null){
					out.println("<h3 name=msg class=msg ><span>" + msg + "</span></h3>");
				}
			}
			ArrayList<Product> voorraadlijst =(ArrayList<Product>)request.getAttribute("voorraadlijst");
			if(voorraadlijst == null){
				out.println("<h1><span>Error!<span></h1>");
				out.println("<p>Er is geen voorraad ingevoerd!</p>");
			}
			else{
				out.println("<h1><span>27: Alle producten</span></h1>");
				out.println("<table>");
				out.println("<tr>");
					out.println("<th>Kies</th>");
					out.println("<th>Artikelnummer</th>");
					out.println("<th>Artikelnaam</th>");
					out.println("<th>Aantal anwezig</th>");
					out.println("<th>Eenheid</th>");
					out.println("<th>Minimum aantal</th>");
					out.println("<th>Prijs per stuk</th>");
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
		%>
	</form>
	<p><a href="product.jsp">Terug naar hoofdmenu product</a></p>
</body>
</html>