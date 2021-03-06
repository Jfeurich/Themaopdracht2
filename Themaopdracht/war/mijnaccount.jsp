<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Mijn Account" /> 
</jsp:include> 
	<%@ page import="domeinklassen.User, domeinklassen.Klant" %>
	<h1><span>Overzicht van uw gebruikersaccount</span></h1>
	<%@ include file="messages.jsp" %> 
	<form action="AccountServlet.do" method="post"> 
		<% User u = (User)session.getAttribute("gebruiker"); 
		if(request.getAttribute("controle") != null){
			%>
			<h2><span>Vul uw huidige wachtwoord in en klik op "Bevestig" om de wijzigingen op te slaan</span></h2>
			<p><input type="password" name="bevestigwachtwoord" /></p>
			<input type="submit" value="Bevestig" name="knop" />
			<%
		}
		else{
		%>
			<h2><span>Vul de velden in die u wilt wijzigen en klik op "Wijzigingen opslaan"</span></h2>
			<table>
				<tr>
					<th>Gebruikersnaam: <%=u.getGebruikersnaam()%></th>
					<td class="hoofdcel"></td>
				</tr>
				<tr>
					<th>Email: <%=u.getEmail()%></th>
					<td><input type="text" name="email" /></td>
				</tr>
				<tr>
					<th>Wachtwoord</th>
					<td><input type="password" name="wachtwoord" /></td>
				</tr>
				<%if(u.getType() == 3){ 
					Klant k = u.getDeKlant();
					%>
					<tr>
						<th>Naam: <%=k.getNaam()%></th>
						<td><input type="text" name="naam" /></td>
					</tr>
					<tr>
						<th>Adres: <%=k.getAdres()%></th>
						<td><input type="text" name="adr" /></td>
					</tr>
					<tr>
						<th>Woonplaats: <%=k.getPlaats()%></th>
						<td><input type="text" name="wp" /></td>
					</tr>
					<tr>
						<th>Telefoonnummer: <%=k.getTelefoonnummer()%></th>
						<td><input type="text" name="telnr" /></td>
					</tr>
					<tr>
						<th>Rekeningnummer: <%=k.getRekeningnummer()%></th>
						<td><input type="text" name="rnr" /></td>
					</tr>
				<%}%>
			</table>
			<p><input type="submit" value="Wijzigingen opslaan" name="knop" /></p>
		<%}%>
	</form>
<%@ include file="footer.html" %>