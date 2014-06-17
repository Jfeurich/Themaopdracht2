<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Hoofdmenu klus" /> 
</jsp:include> 
<%@include file="datepicker.jsp" %>
	<h1><span>Hoofdmenu Klus</span></h1>
	<%@ include file="messages.jsp" %>
	<form action="KlusServlet.do" method="post">
		<%@ page import="domeinklassen.Klus,java.sql.Connection,database.ConnectDBKlus,domeinklassen.Onderhoudsbeurt,domeinklassen.Reparatie,domeinklassen.Product,domeinklassen.GebruiktProduct,java.util.ArrayList" %>
		<div>
			<h2><span>28: Nieuwe klus aanmaken</span></h2>
			<input type="submit" name="knop" value="nieuw" />
		</div>
		<%
		Object o = request.getAttribute("gevondenklussen");
		if(o == null){
			%>
			<div>
				<h2><span>Zoek klussen</span></h2>
				<table>
					<tr>
						<th>ID klus:</th>
						<td><input type="text" name="zoekid" /></td>
					</tr>
					<tr>
						<th>ID auto:</th>
						<td><input type="text" name="zoekautoid" /></td>
					</tr>
					<tr>
						<th>Kenteken auto:</th>
						<td><input type="text" name="zoekautoken" /></td>
					</tr>
					<tr>
						<th>Status:</th>
						<td><input type="text" name="zoekstatus" /></td>
					</tr>
					<tr>
						<th>Na datum:</th>
						<td><input type="text" name="nadatum" class="datepicker" /></td>
					</tr>
					<tr>
						<th>En voor datum:</th>
						<td><input type="text" name="voordatum" class="datepicker" /></td>
					</tr>
					<tr>
						<th>Beschrijving:</th>
						<td><input type="text" name="zoekbeschrijving" /></td>
					</tr>
				</table>
				<input type="submit" name="knop" value="Zoek" />
			</div>
			<%
		}
		else{
			String s = (String)request.getAttribute("gezochtop");
			%>
			<div>
				<h2><span>9: Overzicht gevonden klussen</span></h2>
				<h3><span>Zoektermen:</span></h3>
				<p><%=s%></p>
				<input type="submit" name="knop" value="Nieuwe zoektermen" />
				<p>Selecteer een klus om deze te wijzigen of annuleren</p>
				<%	
				ArrayList<Klus> klussen = (ArrayList<Klus>)o;
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
		<%} %>
	</form>
<%@ include file="footer.html" %>