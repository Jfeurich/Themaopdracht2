<%
Object o = request.getAttribute("voorraadlijst");
if(o == null){
	response.sendRedirect("product.jsp");
	return;
}
%>
<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Overzicht alle producten" /> 
</jsp:include> 
	<%@ page import="java.util.ArrayList,domeinklassen.Product" %>
	<h1><span>Overzicht alle producten</span></h1>
	<%@ include file="messages.jsp" %>
	<form action="ProductServlet.do" method="post">
	<h2><span>klik op WerkVoorraadBij om een bestelling te maken van alle producten onder de minimumvoorraad</span></h2>
	<h2><span>Selecteer een product om dit te wijzigen of op non-actief te zetten</span></h2>
		<table>
			<tr>
				<th>Kies</th>
				<th>Artikelnummer</th>
				<th>Artikelnaam</th>
				<th>Aantal anwezig</th>
				<th>Eenheid</th>
				<th>Minimum aantal</th>
				<th>Prijs per stuk</th>
			</tr>
		<%
		ArrayList<Product> voorraadlijst =(ArrayList<Product>)o;
		boolean eerste = true;
		for(Product p : voorraadlijst){
			%>
			<tr>
				<td><input type="radio" name="product" 
				<%if(eerste){ %> checked="checked" <% eerste=false;}%>
				value="<%=p.getArtikelNr()%>" /></td>
				<td><%=p.getArtikelNr()%></td>
				<td><%=p.getNaam()%></td>
				<td><%=p.getAantal()%></td>
				<td><%=p.getEenheid()%></td>
				<td><%=p.getMinimumAanwezig()%></td>
				<td><%=p.getPrijsPerStuk()%></td>
			</tr>
		<%}%>
		</table>
		<p><input type="submit" name="knop" value="WerkVoorraadBij" />
		<input type="submit" name="knop" value="wijzig" />
		<input type="submit" name="knop" value="verwijder" /></p>
	</form>
<%@ include file="footer.html" %>