<%
Object gekozen = request.getAttribute("deKlus");
if(gekozen == null){
	response.sendRedirect("klus.jsp");
	return;
}
%>
<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Aritkel toevoegen aan klus" /> 
</jsp:include> 
	<form action="KlusWijzigenServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Klus,domeinklassen.Onderhoudsbeurt,domeinklassen.Reparatie,domeinklassen.Product" %>
		<h1><span>5: Kies de gewenste artikel(en) en klik op "VoegToe"</span></h1>
		<%@ include file="messages.jsp" %>
		<h2><span>Wijzig de klus</span></h2>
		<%
		Object o = request.getAttribute("voorraadlijst");
		if(o == null){
			%>
			<h3><span>Error!</span></h3>
			<p class="error" >Er is geen voorraad ingevoerd!</p>
			<%
		}
		else{
			Klus deKlus = (Klus)gekozen;
			ArrayList<Product> voorraadlijst = (ArrayList<Product>)o;
			%>
			<h2><span>Artikelen die momenteel op voorraad zijn</span></h2>
			<table>
			<tr>
				<th>X</th>
				<th>Artikelnummer</th>
				<th>Artikelnaam</th>
				<th>Aantal aanwezig</th>
				<th>Eenheid</th>
				<th>Aantal</th>
			</tr>
			<%
			for(Product p : voorraadlijst){
				%>
				<tr>
					<td><input type="checkbox" name="product" value="<%=p.getArtikelNr()%>" /></td>
					<td><%=p.getArtikelNr()%></td>
					<td><%=p.getNaam()%></td>
					<td><%=p.getAantal()%></td>
					<td><%=p.getEenheid()%></td>
					<td><input type="text" name="aantal"  /></td>
				</tr>
				<input type="hidden" name="alleProducten" value="<%=p.getArtikelNr()%>" />
				<input type="hidden" name="voorraad" value="<%=p.getAantal()%>" />
				<%
			}
			%>
			</table>
			<p><input type="hidden" name="gekozenklus" value="<%=deKlus.getID()%>" />
			<input type="submit" name="knop" value="VoegToe" /></p>
			<%
		}
		%>
	</form>
<%@ include file="footer.html" %>