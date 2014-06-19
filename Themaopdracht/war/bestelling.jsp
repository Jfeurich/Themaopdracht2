<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Overzicht bestellingen" /> 
</jsp:include> 
<%@include file="datepicker.jsp" %>
<%@ page import="domeinklassen.Product,java.util.ArrayList,domeinklassen.Bestelling,domeinklassen.BesteldProduct,java.sql.Connection,database.ConnectDBBestelling" %>
	<h1><span>Overzicht bestellingen</span></h1>
	<%@ include file="messages.jsp" %>
	<form action="BestellingServlet.do" method="post">
		<div>
			<h2><span>Zoek een bestelling</span></h2>
			<%
			Object zoekmsg = request.getAttribute("zoekmsg");
			if(zoekmsg != null){
				%><h3 class=msg ><span><%=zoekmsg%></span></h3><%
			}
			Object arraygevonden = request.getAttribute("arraygevonden");
			if(arraygevonden != null){
				%>
				<h3>Selecteer een niet-geleverde bestelling om deze in te boeken</h3>
				<table>
					<tr>
						<th>X</th>
						<th>Nummer</th>
						<th>Kosten</th>
						<th>Leverdatum</th>
					</tr>
				<%
				ArrayList<Bestelling> lijst = (ArrayList<Bestelling>)arraygevonden;
				boolean eerste = true;
				for(Bestelling b : lijst){
					%>
					<tr>
						<td class="hoofdcel"><%if(!b.getIsGeleverd()){%><input type="radio" name="gekozenbestelling" 
						<%if(eerste){ %> checked="checked" <% eerste=false;}%>
						value="<%=b.getBestelNummer()%>" /><%}%></td>
						<td class="hoofdcel"><%=b.getBestelNummer()%></td>
						<td class="hoofdcel"><%=b.getTotaal()%></td>
						<td class="hoofdcel"><%=b.datum()%></td>
					</tr>
					<tr>
						<td class="tussenkop">Naam</td>
						<td class="tussenkop">Artikelnummer</td>
						<td class="tussenkop">Aantal</td>
						<td class="tussenkop">Prijs per stuk</td>
					</tr>
					<%
					for(BesteldProduct bp : b.getBesteldeProducten()){
						Product hetProduct = bp.getProduct();
						%>
						<tr>
							<td><%=hetProduct.getNaam()%></td>
							<td><%=hetProduct.getArtikelNr()%></td>
							<td><%=bp.getHoeveelheid()%></td>
							<td><%=hetProduct.getPrijsPerStuk()%></td>
						</tr>
					<%}
				}
				%>
				</table>
				<p><input type="submit" name="knop" value="Boek in" /></p>
			<%}%>
			<table>
				<tr>
					<th>Nummer:</th>
					<td><input type="text" name="zoekid" /></td>
				</tr>
				<tr>
					<th>Datum:</th>
					<td><input type="text" name="zoekdatum" class="datepicker"/></td>
				</tr>
				<tr>
					<th>Artikelnummer product:</th>
					<td><input type="text" name="zoeknummer" /></td>
				</tr>
				<tr>
					<th>Naam product:</th>
					<td><input type="text" name="zoeknaam" /></td>
				</tr>
				<tr>
					<th>Eenheid product:</th>
					<td><input type="text" name="zoekeenheid" /></td>
				</tr>
			</table>
			<p><input type="submit" name="knop" value="Zoek" /></p>
		</div>
		<div>
			<%
			Connection con = (Connection)session.getAttribute("verbinding");
			ConnectDBBestelling bcon = new ConnectDBBestelling(con);
			ArrayList<Bestelling> nietgeleverd = bcon.getBestellingenNietGeleverd();
			ArrayList<Bestelling> geleverd = bcon.getBestellingenGeleverd();
			if(nietgeleverd.size() != 0){
				%>
				<h2><span>Niet-geleverde bestellingen</span></h2>
				<h3>Selecteer een bestelling om deze in te boeken</h3>
				<table>
					<tr>
						<th>X</th>
						<th>Nummer</th>
						<th>Kosten</th>
						<th>Leverdatum</th>
					</tr>
				<%
				boolean eerstenietgeleverd = true;
				for(Bestelling b : nietgeleverd){
					%>
					<tr>
						<td class="hoofdcel"><input type="radio" name="gekozenbestelling" 
						<%if(eerstenietgeleverd){ %> checked="checked" <% eerstenietgeleverd=false;}%>
						value="<%=b.getBestelNummer()%>" /></td>
						<td class="hoofdcel"><%=b.getBestelNummer()%></td>
						<td class="hoofdcel"><%=b.getTotaal()%></td>
						<td class="hoofdcel"><%=b.datum()%></td>
					</tr>
					<tr>
						<td class="tussenkop">Naam</td>
						<td class="tussenkop">Artikelnummer</td>
						<td class="tussenkop">Aantal</td>
						<td class="tussenkop">Prijs per stuk</td>
					</tr>
					<%
					for(BesteldProduct bp : b.getBesteldeProducten()){
						Product hetProduct = bp.getProduct();
						%>
						<tr>
							<td><%=hetProduct.getNaam()%></td>
							<td><%=hetProduct.getArtikelNr()%></td>
							<td><%=bp.getHoeveelheid()%></td>
							<td><%=hetProduct.getPrijsPerStuk()%></td>
						</tr>
					<%}
				}
				%>
				</table>
				<p><input type="submit" name="knop" value="Boek in" /></p>					
			<%}%>
		</div>
		<div>
			<%if(geleverd.size() != 0){ %>
				<h2><span>Geleverde bestellingen</span></h2>
				<table>
					<tr>
						<th></th>
						<th>Nummer</th>
						<th>Kosten</th>
						<th>Leverdatum</th>
					</tr>
				<%
				for(Bestelling b : geleverd){
					%>
					<tr>
						<td class="hoofdcel"></td>
						<td class="hoofdcel"><%=b.getBestelNummer()%></td>
						<td class="hoofdcel"><%=b.getTotaal()%></td>
						<td class="hoofdcel"><%=b.datum()%></td>
					</tr>
					<tr>
						<td class="tussenkop">Naam</td>
						<td class="tussenkop">Artikelnummer</td>
						<td class="tussenkop">Aantal</td>
						<td class="tussenkop">Prijs per stuk</td>
					</tr>
					<%
					for(BesteldProduct bp : b.getBesteldeProducten()){
						Product hetProduct = bp.getProduct();
						%>
						<tr>
							<td><%=hetProduct.getNaam()%></td>
							<td><%=hetProduct.getArtikelNr()%></td>
							<td><%=bp.getHoeveelheid()%></td>
							<td><%=hetProduct.getPrijsPerStuk()%></td>
						</tr>
					<%}
				}%>
				</table>	
			<%}%>
		</div>
	</form>
<%@ include file="footer.html" %>