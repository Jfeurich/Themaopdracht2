<%@ page import="domeinklassen.User"%>
<% 	
User gebruiker = (User)session.getAttribute("gebruiker");
if(gebruiker.getType() != 0){
	response.sendRedirect("index.jsp");
}
%>
<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Accountgegevens wijzigen" /> 
</jsp:include> 
	<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.User,database.ConnectDBUser,java.sql.Connection" %>
	<h1><span>Gegevens van een gebruikersaccount wijzigen</span></h1>
	<form action="AccountServlet.do" method="post" >
		<%@ include file="messages.jsp" %> 
		<% 
		Object o = request.getSession().getAttribute("wijzig"); 
		if(o == null){
			Connection con = (Connection)session.getAttribute("verbinding");
			ConnectDBUser ucon = new ConnectDBUser(con);
			ArrayList<User> gebruikers = ucon.getUsers();
			%>
			<h2><span>Kies de gebruiker waar u de gegevens van wilt wijzigen</span></h2>
			<table>
				<tr>
					<td>X</td>
					<th>Gebruikersnaam</th>
					<th>Emailadres</th>
				</tr>
				<%
				boolean eerste = true;
				for(User u : gebruikers){%>
					<tr>
						<td><input type="radio" name="kiesgebruiker" 
						<%if(eerste){eerste = false;out.print("checked=checked ");}%> 
						value="<%=u.getID()%>" /></td>
						<td><%=u.getGebruikersnaam()%></td>
						<td><%=u.getEmail()%></td>
					</tr>
				<%}%>
			</table>
			<input type="submit" name="knop" value="Kies gebruiker" />	
		<%}
		else{
			if(request.getAttribute("controle") != null){
			%>
				<div>
				<h2><span>Klik op "Bevestig" om de wijzigingen op te slaan</span></h2>
				<input type="submit" value="Bevestig" name="knop" />
				<input type="hidden" name="bevestigwachtwoord" value="<%=gebruiker.getWachtwoord()%>" />	
				</div>			
			<%}
			else{
				User u = (User)o;
			%>
				<div>
					<h2><span>Vul de velden in die u wilt wijzigen en klik op "Account aanpassen"</span></h2>
					<table>
						<tr>
							<th>Email: <%=u.getEmail()%></th>
							<td><input type="text" name="email" /></td>
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
					<input type="submit" value="Account aanpassen" name="knop" />	
				</div>	
		<%}}%>
	</form>
<%@ include file="footer.html" %>