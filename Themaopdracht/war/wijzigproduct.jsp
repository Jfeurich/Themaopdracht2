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
	<h1><span>Product wijzigen</span></h1>
	<%@ include file="messages.jsp" %>
	<form action="WijzigProductServlet.do" method="post">
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
				<td class="hoofdcel"><input type="hidden" name="product" value="<%=p.getArtikelNr()%>" /><%=p.getArtikelNr()%></td>
				<td class="hoofdcel"><%=p.getNaam()%></td>
				<td class="hoofdcel"><%=p.getAantal()%></td>
				<td class="hoofdcel"><%=p.getEenheid()%></td>
				<td class="hoofdcel"><%=p.getMinimumAanwezig()%></td>
				<td class="hoofdcel"><%=p.getPrijsPerStuk()%></td>
			</tr>
			<tr>
				<td><%=p.getArtikelNr()%></td>
				<td><input type="text" name="naam" /></td>
				<td><input type="text" name="aantal" /></td>
				<td><input type="text" name="eenheid" /></td>
				<td><input type="text" name="minaantal" /></td>
				<td><input type="text" name="pps" /></td>
			</tr>
		</table>	
		<p><input type="submit" name="knop" value="Wijzigingen opslaan" />
		<%if(p.isActief()){%>
			<input type="submit" name="knop" value="Deactiveren" />
		<%}else{ %>
			<input type="submit" name="knop" value="Activeren" />
		<%}%></p>
	</form>
<%@ include file="footer.html" %>