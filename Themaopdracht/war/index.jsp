<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Homepage" /> 
</jsp:include> 
	<h1><span>Homepage</span></h1>
	<div>
		<%@ page import="domeinklassen.User, domeinklassen.Auto, domeinklassen.Klant, java.text.SimpleDateFormat, java.util.ArrayList" %>
		<%@ include file="messages.jsp" %>
	</div>
	<%
	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	User u = (User) request.getSession().getAttribute("gebruiker");
	if(u.getType() == 3){
	%>
		<h2>Mijn herinneringen</h2>
		<div>
			Uw laatste bezoek was: <br />
			<%= u.getDeKlant().getLaatsteBezoek() %> <br />
			<% String s; 
			if(u.getDeKlant().isRegelmatig() == true){ 
			s = " wel ";}else{s = " geen ";}%>
			U bent <%= s %> regelmatige klant. <br />
			De volgende auto's hebben onderhoud nodig: <br />
			<%
			ArrayList<Auto> autosOnderhoudNodig = u.getDeKlant().onderhoudNodig();
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
				</table>
				<%
				}
			}
			%>
		</div>
		<h2><span>Mijn auto's</span></h2>
		<div>
			<table>
			<tr>
				<th>Kenteken</th>
				<th>Merk</th>
				<th>Type</th>
				<th>Laatste klus</th>
			</tr>
			<%
			for(Auto a : u.getDeKlant().getAutos()){
				%>
				<tr>
					<td><%=a.getKenteken()%></td>
					<td><%=a.getMerk()%></td>
					<td><%=a.getType()%></td>
					<td><%=df.format(a.laatsteKlus()) %></td>
				</tr>
			<%}%>
			</table>
		</div>
	<%
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