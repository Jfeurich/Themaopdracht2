<%@ include file="redirect.jsp" %>
<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Overzicht alle producten" /> 
</jsp:include> 
	<form action="ProductServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Product" %>
		<%@ include file="messages.jsp" %>
		<%
		Object o = request.getAttribute("voorraadlijst");
		if(o == null){
			%>
			<h1><span>Error!</span></h1>
			<p class="error">Er is geen voorraad ingevoerd!</p>
			<%
		}
		else{
			ArrayList<Product> voorraadlijst =(ArrayList<Product>)o;
			%>
			<h1><span>27: Alle producten</span></h1>
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
			<input type="submit" name="knop" value="wijzig" />
			<input type="submit" name="knop" value="verwijder" />
			<%
		}
		%>
	</form>
	<p><a href="product.jsp">Terug naar hoofdmenu product</a></p>
<%@ include file="footer.html" %>