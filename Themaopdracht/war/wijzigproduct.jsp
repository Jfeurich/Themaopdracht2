<%
Object hetProduct = request.getAttribute("product");
if(hetProduct == null){
	response.sendRedirect("product.jsp");
	return;
}
%>
<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Wijzig product" /> 
</jsp:include> 
	<%@ page import="domeinklassen.Product" %>
	<form action="WijzigProductServlet.do" method="post">
		<h1><span>14: Product wijzigen</span></h1>
		<%@ include file="messages.jsp" %>
		<%
		Product p = (Product)hetProduct;
		%>
		<h2><span>Voer nieuwe waarden in voor de gewenste velden</span></h2>
		<table>
			<tr>
				<th>Artikelnummer</th>
				<th>Artikelnaam</th>
				<th>Aantal anwezig</th>
				<th>Eenheid</th>
				<th>Minimum aantal</th>
				<th>Prijs per stuk</th>
			</tr>
			<tr>
				<td><input type="hidden" name="product" value="<%=p.getArtikelNr()%>" /><%=p.getArtikelNr()%></td>
				<td><%=p.getNaam()%></td>
				<td><%=p.getAantal()%></td>
				<td><%=p.getEenheid()%></td>
				<td><%=p.getMinimumAanwezig()%></td>
				<td><%=p.getPrijsPerStuk()%></td>
			</tr>
			<tr>
				<td><%=p.getArtikelNr()%></td>
				<td><input type=text name=naam /></td>
				<td><input type=text name=aantal /></td>
				<td><input type=text name=eenheid /></td>
				<td><input type=text name=minaantal /></td>
				<td><input type=text name=pps /></td>
			</tr>
		</table>	
		<input type="submit" name="knop" value="Wijzigingen opslaan" />
		<%if(p.isActief()){%>
			<input type="submit" name="knop" value="Deactiveren" />
		<%}else{ %>
			<input type="submit" name="knop" value="Activeren" />
		<%}%>
	</form>
	<p><a href="product.jsp">Terug naar hoofdmenu product</a></p>
<%@ include file="footer.html" %>