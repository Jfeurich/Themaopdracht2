<jsp:include page="header.jsp" > 
 <jsp:param name="titel" value="Hoofdmenu klus" /> 
</jsp:include> 
	<h1><span>Hoofdmenu Klus</span></h1>
	<form action="KlusServlet.do" method="post">
		<%@ page import="domeinklassen.Klus,domeinklassen.Onderhoudsbeurt,domeinklassen.Reparatie,domeinklassen.Product,domeinklassen.GebruiktProduct,java.util.ArrayList" %>
		<div>
			<h2><span>28: Nieuwe klus aanmaken</span></h2>
			<input type="submit" name="knop" value="nieuw" />
		</div>
		<div>
			<h2><span>9: Overzicht alle klussen</span></h2>
			<%
				Object ar = request.getAttribute("klussen");
				if(ar != null){
					ArrayList<Klus> klussen = (ArrayList<Klus>)ar;
					out.println("<table>");
					out.println("<tr>");
						out.println("<th>Kies</th>");
						out.println("<th>Type</th>");
						out.println("<th>Datum</th>");
						out.println("<th>Beschrijving</th>");
						out.println("<th>Status</th>");
					out.println("</tr>");
					boolean eerste=true;
					for(Klus k : klussen ){
						out.println("<tr>");
							out.println("<td><input type=radio name=gekozenklus ");
							if(eerste){out.println("checked=checked ");eerste=false;}
							out.println("value=" + k.getID() + " /></td>");
							if(k instanceof Reparatie){
								out.println("<td>Reparatie</td>");
							}
							else{
								out.println("<td>Onderhoud</td>");
							}
							out.println("<td>" + k.getFormattedDatum() + "</td>");
							out.println("<td>" + k.getBeschrijving() + "</td>");
							out.println("<td>" + k.getStatus() + "</td>");
						out.println("</tr>");
						ArrayList<GebruiktProduct> producten = k.getGebruikteProducten();
						if(producten.size() > 0){
							out.println("<tr>");
								out.println("<th>Product:</th>");
								out.println("<th>Artikelnummer</th>");
								out.println("<th>Naam</th>");
								out.println("<th>Aantal</th>");
								out.println("<th>Prijs per stuk</th>");
							out.println("</tr>");
							for(GebruiktProduct gp : producten){
								Product hetProduct = gp.getHetProduct();
								out.println("<tr>");
									out.println("<td></td>");
									out.println("<td>" + hetProduct.getArtikelNr() + "</td>");
									out.println("<td>" + hetProduct.getNaam() + "</td>");
									out.println("<td>" + gp.getAantal() + "</td>");
									out.println("<td>" + hetProduct.getPrijsPerStuk() + "</td>");
								out.println("</tr>");
							}
						}
					}
					out.println("</table>");
					out.println("<h2><span>16: Klus wijzigen</span></h2>");
					out.println("<input type=submit name=knop value=wijzig />");
				}
				else{
					out.println("<input type=submit name=knop value=overzicht />");
				}
			%>
		</div>
	</form>
</body>
</html>