<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Hoofdmenu klus" /> 
</jsp:include> 
	<h1><span>Hoofdmenu Klus</span></h1>
	<%@ include file="messages.jsp" %>
	<form action="KlusServlet.do" method="post">
		<%@ page import="domeinklassen.Klus,java.sql.Connection,database.ConnectDBKlus,domeinklassen.Onderhoudsbeurt,domeinklassen.Reparatie,domeinklassen.Product,domeinklassen.GebruiktProduct,java.util.ArrayList" %>
		<div>
			<h2><span>28: Nieuwe klus aanmaken</span></h2>
			<input type="submit" name="knop" value="nieuw" />
		</div>
		<div>
			<h2><span>9: Overzicht alle klussen</span></h2>
			<p>Selecteer een klus om deze te wijzigen of annuleren</p>
			<%
			Connection con = (Connection)session.getAttribute("verbinding");
			ConnectDBKlus kcon = new ConnectDBKlus(con);
			ArrayList<Klus> klussen = kcon.getKlussen();
			%>
			<table>
			<tr>
				<th>Kies</th>
				<th>Type</th>
				<th>Datum</th>
				<th>Beschrijving</th>
				<th>Status</th>
			</tr>
			<%
			boolean eerste=true;
			for(Klus k : klussen ){
				%>
				<tr>
					<td><input type=radio name=gekozenklus 
					<%if(eerste){out.println("checked=checked ");eerste=false;}%>
					value="<%=k.getID()%>" /></td>
					<%if(k instanceof Reparatie){%>
						<td>Reparatie</td>
					<%}
					else{%>
						<td>Onderhoud</td>
					<%}%>
					<td><%=k.getFormattedDatum()%></td>
					<td><%=k.getBeschrijving()%></td>
					<td><%=k.getStatus()%></td>
				</tr>
				<%
				ArrayList<GebruiktProduct> producten = k.getGebruikteProducten();
				if(producten.size() > 0){
					%>
					<tr>
						<th>Product:</th>
						<th>Artikelnummer</th>
						<th>Naam</th>
						<th>Aantal</th>
						<th>Prijs per stuk</th>
					</tr>
					<%
					for(GebruiktProduct gp : producten){
						Product hetProduct = gp.getHetProduct();
						%>
						<tr>
							<td></td>
							<td><%=hetProduct.getArtikelNr()%></td>
							<td><%=hetProduct.getNaam()%></td>
							<td><%=gp.getAantal()%></td>
							<td><%=hetProduct.getPrijsPerStuk()%></td>
						</tr>
						<%
					}
				}
			}
			%>
			</table>
		</div>
		<div>
			<h2><span>16: Klus wijzigen</span></h2>
			<input type="submit" name="knop" value="wijzig" />
		</div>
		<div>
			<h2><span>26: Klus annuleren</span></h2>
			<input type="submit" name="knop" value="annuleren" />
		</div>
	</form>
<%@ include file="footer.html" %>