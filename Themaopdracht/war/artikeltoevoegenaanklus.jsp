<jsp:include page="header.jsp" > 
 <jsp:param name="titel" value="Aritkel toevoegen aan klus" /> 
</jsp:include> 
	<form action="KlusWijzigenServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Klus,domeinklassen.Onderhoudsbeurt,domeinklassen.Reparatie,domeinklassen.Product" %>
		<div>
			<h1><span>5: Kies de gewenste artikel(en) en klik op "VoegToe"</span></h1>
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
				Object gekozen = request.getAttribute("deKlus");
				if(gekozen != null){
					Klus deKlus = (Klus)gekozen;
					ArrayList<Product> voorraadlijst = (ArrayList<Product>)request.getAttribute("voorraadlijst");
					out.println("<h2><span>Wijzig de klus</span></h2>");
					if(voorraadlijst == null){
						out.println("<h3><span>Error!</span></h3>");
						out.println("<p class=error >Er is geen voorraad ingevoerd!</p>");
					}
					else{
						out.println("<h2><span>Artikelen die momenteel op voorraad zijn</span></h2>");
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