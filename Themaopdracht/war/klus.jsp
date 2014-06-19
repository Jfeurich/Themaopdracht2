<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Hoofdmenu klus" /> 
</jsp:include> 
<%@include file="datepicker.jsp" %>
	<%@ page import="domeinklassen.Klus,java.sql.Connection,database.ConnectDBKlus,domeinklassen.Onderhoudsbeurt,domeinklassen.Reparatie,domeinklassen.Product,domeinklassen.GebruiktProduct,java.util.ArrayList" %>
	<h1><span>Hoofdmenu Klus</span></h1>
	<%@ include file="messages.jsp" %>
	<form action="KlusServlet.do" method="post">
		<div>
			<h2><span>Nieuwe klus aanmaken</span></h2>
			<p><input type="submit" name="knop" value="nieuw" /></p>
		</div>
		<%
		Object o = request.getAttribute("gevondenklussen");
		if(o == null){
			%>
			<div>
				<h2><span>Zoek klussen</span></h2>
				<h4><span>Vul minimaal 1 zoekcriterium in</span></h4>
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
						<th>Merk auto:</th>
						<td><input type="text" name="zoekautomerk" /></td>
					</tr>
					<tr>
						<th>Type auto:</th>
						<td><input type="text" name="zoekautotype" /></td>
					</tr>
					<tr>
						<th>Status:</th>
						<td><input type="text" name="zoekstatus" /></td>
					</tr>
					<tr>
						<th>Na datum:</th>
						<td><input type="text" name="nadatum" class="datepicker" id="bdat"/></td>
					</tr>
					<tr>
						<th>En voor datum:</th>
						<td><input type="text" name="voordatum" class="datepicker" id="edat"/></td>
					</tr>
					<tr>
						<th>Beschrijving:</th>
						<td><input type="text" name="zoekbeschrijving" /></td>
					</tr>
				</table>
				<p><input type="submit" name="knop" value="Zoek" /></p>
			</div>
			<%
		}
		else{
			String s = (String)request.getAttribute("gezochtop");
			%>
			<div>
				<h2><span>Gevonden klussen</span></h2>
				<h4><span>Zoektermen:</span></h4>
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
						<td class="hoofdcel"><input type="radio" name="gekozenklus"
						<%if(eerste){ %> checked="checked" <% eerste=false;}%>
						value="<%=k.getID()%>" /></td>
						<%if(k instanceof Reparatie){%>
							<td class="hoofdcel">Reparatie</td>
						<%}
						else{%>
							<td class="hoofdcel">Onderhoud</td>
						<%}%>
						<td class="hoofdcel"><%=k.getFormattedDatum()%></td>
						<td class="hoofdcel"><%=k.getBeschrijving()%></td>
						<td class="hoofdcel"><%=k.getStatus()%></td>
					</tr>
					<%
					ArrayList<GebruiktProduct> producten = k.getGebruikteProducten();
					if(producten.size() > 0){
						%>
						<tr>
							<td class="tussenkop">Product:</td>
							<td class="tussenkop">Artikelnummer</td>
							<td class="tussenkop">Naam</td>
							<td class="tussenkop">Aantal</td>
							<td class="tussenkop">Prijs per stuk</td>
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
				<p><input type="submit" name="knop" value="wijzig" /><input type="submit" name="knop" value="annuleren" /></p>
			</div>
		<%}%>
	</form>
<%@ include file="footer.html" %>