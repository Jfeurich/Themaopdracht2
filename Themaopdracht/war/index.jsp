<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Homepage" /> 
</jsp:include> 
	<%@ page import="domeinklassen.Reservering, domeinklassen.User, domeinklassen.Auto, domeinklassen.Klant, 
	domeinklassen.Klus, domeinklassen.GebruiktProduct, domeinklassen.Product, domeinklassen.Reparatie, 
	java.text.SimpleDateFormat, java.util.ArrayList, database.ConnectDBReservering, java.sql.Connection" %>
	<h1><span>Homepage</span></h1>
	<%@ include file="messages.jsp" %>
	<%
	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	User u = (User) request.getSession().getAttribute("gebruiker");
	if(u.getType() == 3){
	Klant deKlant = u.getDeKlant();
	%>
		<h2>Mijn herinneringen</h2>
		<div>
			Uw laatste bezoek was: <br />
			<%= deKlant.getLaatsteBezoek() %> <br />
			<% String s; 
			if(deKlant.isRegelmatig() == true){ 
			s = " een ";}else{s = " geen ";}%>
			U bent <%= s %> regelmatige klant. <br />
			De volgende auto's hebben onderhoud nodig: <br />
			<%
			ArrayList<Auto> autosOnderhoudNodig = deKlant.onderhoudNodig();
			if(autosOnderhoudNodig.size() == 0){
				%>Geen van uw auto's hebben onderhoud nodig<%
			}
			else{
				%>
				<table>
					<tr>
						<th>Kenteken</th>
						<th>Merk</th>
						<th>Type</th>
						<th>Laatste klus</th>
					</tr>
				<%
				for(Auto a: autosOnderhoudNodig){
				%>
					<tr>
						<td><%=a.getKenteken()%></td>
						<td><%=a.getMerk()%></td>
						<td><%=a.getType()%></td>
						<td><%=df.format(a.laatsteKlus()) %></td>
					</tr>
				<%
				}
				%>
				</table>
				<%
			}
			%>
		</div>
		<h2><span>Mijn auto's</span></h2>
		<%
		if(deKlant.getDeAutos().size() == 0){
		%>
			U heeft nog geen auto's in het systeem staan, voeg deze toe door <a href="autotoevoegen.jsp">hier</a> te klikken.
		<%
		}
		else{
		%>
		<form action="NieuweKlusServlet.do" method="post">
			<div>
				<table>
				<tr>
					<th>X</th>
					<th>Kenteken</th>
					<th>Merk</th>
					<th>Type</th>
					<th>Laatste klus</th>
				</tr>
				<%
				boolean eersteauto=true;
				for(Auto a : deKlant.getDeAutos()){
				%>
					<tr>
						<td><input type="radio" name="gekozenauto" <%if(eersteauto){eersteauto=false;%>checked="checked" <%} %>value="<%=a.getID()%>" /></td>
						<td><%=a.getKenteken()%></td>
						<td><%=a.getMerk()%></td>
						<td><%=a.getType()%></td>
						<td><%=df.format(a.laatsteKlus()) %></td>
					</tr>
				<%
				}
				%>
				</table>
			</div>
			<input type="submit" name="knop" value="Nieuwe klus" />
		</form>
		<h2>Mijn klussen</h2>
		<div>
		<%
		ArrayList<Klus> aankomendeKlussen = deKlant.getAankomendeKlussen();
		if(aankomendeKlussen.size() == 0){
		%>
			U heeft geen aankomende klussen
		<%
		}
		else{
			Object nw = request.getAttribute("nieuweklus");
			if(nw != null){
				aankomendeKlussen.add((Klus)nw);
			}
		%>
		<form action="KlusWijzigenServlet.do" method="post">
			<table>
				<tr>
					<th>X</th>
					<th>Kenteken</th>
					<th>Type</th>
					<th>Datum</th>
					<th>Beschrijving</th>
					<th>Status</th>
				</tr>
				<%
				for(Klus k : aankomendeKlussen ){
					boolean eerste=true;
					%>
					<tr>
						<td class="hoofdcel"><input type="radio" name="gekozenklus" 
							<%if(eerste){eerste=false;%> checked="checked" <%}%>value="<%=k.getID()%>" />
						</td>
						<td class="hoofdcel"><%=k.getAuto().getKenteken() %></td>
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
				<input type="submit" name="knop" value="Annuleren" />
			</form>
		<%
		}
		%>
		</div>
		<h2>Mijn parkeerplaatsreserveringen</h2>
		<div>
		<%
		Connection con = (Connection)session.getAttribute("verbinding");
		ConnectDBReservering rescon = new ConnectDBReservering(con);
		ArrayList<Reservering> deReserveringen = rescon.getAankomendeReservering(deKlant.getDeAutos());	
		if(deReserveringen.size() == 0){
			%>
			<p>U heeft geen reserveringen voor de parkeerplaats.</p>
			<%
		}
		else{
			%>
			<table>
				<tr>
					<th>Begin datum</th>
					<th>Eind datum</th>
					<th>Parkeerplek</th>
					<th>Auto</th>
				</tr>			
				<%
				for(Reservering r: deReserveringen){
				%>
					<tr>
						<td><%= r.getBegDatNetjes() %></td>
						<td><%= r.getEindDatNetjes()%></td>
						<td><%= r.getDeParkeerplek() %></td>
						<td><%= r.getAuto().getKenteken() %></td>
					</tr>
				<%
				}
				%>
			</table>
			<%
		}
		%>
	</div>
	<%
		}
	}
	else{
		switch(u.getType()){
		case 0: %><h2>U bent ingelogd als: Administratie </h2> 
		<div>
			U heeft hiermee alle  rechten binnen dit systeem.
		</div>
		<%; break;
		case 1: %><h2>U bent ingelogd als: Monteur </h2> 
		<div>
			U heeft hiermee de volgende rechten:
			<ul>
				<li>De gebruikte artikelen aan een klus toevoegen</li>
				<li>De manuren aan een klus toe kunnen voegen</li>
				<li>De de status van een klus veranderen</li>
			</ul>
		</div>
		<%; break;
		case 2: %><h2>U bent ingelogd als: Parkeergaragebeheerder </h2> 
		<div>
			U heeft hiermee de volgende rechten:
			<ul>
				<li>Een parkeerplaatsreservering bevestigen</li>
				<li>Een parkeerplaatsreservering maken</li>
				<li>Een parkeerplaatsreservering annuleren</li>
			</ul>
		</div>
		<%; break;
		}
	}
	%>
<%@ include file="footer.html" %>