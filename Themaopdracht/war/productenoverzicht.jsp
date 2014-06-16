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
	<form action="ProductServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Product" %>
		<h1><span>27: Alle producten</span></h1>
		<h2>Selecteer een product om te wijzigen of klik op WerkVoorraadBij om een bestelling te maken van alle producten onder de minimumvoorraad</h2>
		<%@ include file="messages.jsp" %>
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
				<%if(eerste){out.println("checked=checked ");eerste=false;}%>
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
		<input type="submit" name="knop" value="WerkVoorraadBij" />
		<input type="submit" name="knop" value="wijzig" />
		<input type="submit" name="knop" value="verwijder" />
	</form>
	<p><a href="product.jsp">Terug naar hoofdmenu product</a></p>
<%@ include file="footer.html" %>