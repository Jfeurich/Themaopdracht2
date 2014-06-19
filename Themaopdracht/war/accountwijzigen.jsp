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
	<%@ page import="java.util.ArrayList,domeinklassen.Klant,database.ConnectDBUser,java.sql.Connection" %>
	<h1><span>Gegevens van een gebruikersaccount wijzigen</span></h1>
	<%@ include file="messages.jsp" %> 
	<form action="AccountServlet.do" method="post" >
		<% 
		Object o = request.getSession().getAttribute("wijzig"); 
		if(o == null){
			Connection con = (Connection)session.getAttribute("verbinding");
			ConnectDBUser ucon = new ConnectDBUser(con);
			ArrayList<User> gebruikers = ucon.getUsers();
			ArrayList<User> nonactievegebruikers = ucon.getUsersNonActief();
			%>
			<h2><span>Kies de gebruiker waar u de gegevens van wilt wijzigen</span></h2>
			<h3><span>Actieve gebruikers:</span></h3>
			<table>
				<tr>
					<th>X</th>
					<th>Gebruikersnaam</th>
					<th>Emailadres</th>
				</tr>
				<%
				boolean eerste = true;
				for(User u : gebruikers){%>
					<tr>
						<td><input type="radio" onclick="setActief()" name="kiesgebruiker" 
						<%if(eerste){eerste = false;%> checked="checked" <%}%> 
						value="<%=u.getID()%>" /></td>
						<td><%=u.getGebruikersnaam()%></td>
						<td><%=u.getEmail()%></td>
						
					</tr>
				<%}%>
			</table>
			<%
			if(nonactievegebruikers.size() != 0){	
			%>	
			<h3><span>Non-actieve gebruikers:</span></h3>
			<table>
				<tr>
					<th>X</th>
					<th>Gebruikersnaam</th>
					<th>Emailadres</th>
				</tr>
				<%
				for(User u : nonactievegebruikers){%>
					<tr>
						<td><input type="radio" onclick="setNonActief()" name="kiesgebruiker" value="<%=u.getID()%>" /></td>
						<td><%=u.getGebruikersnaam()%></td>
						<td><%=u.getEmail()%></td>			
					</tr>
				<%}%>
			</table>
			<%}%>
			<p><input type="hidden" name="actief" id="actief" value="ja" />
			<input type="submit" name="knop" value="Kies gebruiker" /></p>
			<script type="text/javascript">
				function setNonActief(){
					var s = document.getElementById("actief");
	          		s.value = "nee";
				}
				function setActief(){
					var s = document.getElementById("actief");
	          		s.value = "ja";
				}
			</script>
		<%}
		else{
			if(request.getAttribute("controle") != null){
			%>
				<div>
					<h2><span>Klik op "Bevestig" om de wijzigingen op te slaan</span></h2>
					<p><input type="submit" value="Bevestig" name="knop" />
					<input type="hidden" name="bevestigwachtwoord" value="<%=gebruiker.getWachtwoord()%>" /></p>
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
						<%}
						if(request.getAttribute("deactiveren") != null){%>
							<tr>
								<th>Account deactiveren</th>
								<td><input type="checkbox" name="deactivate" /></td>
							</tr>
						<%}
						else{
						%>
							<tr>
								<th>Account activeren</th>
								<td><input type="checkbox" name="activate" /></td>
							</tr>
						<%}%>
					</table>
					<p><input type="submit" value="Account aanpassen" name="knop" /></p>
				</div>	
		<%}}%>
	</form>
<%@ include file="footer.html" %>