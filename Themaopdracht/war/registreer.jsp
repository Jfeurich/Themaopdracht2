<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Registreren" /> 
</jsp:include> 
	<form action="RegistreerServlet.do" method="get"> 
		<div>
			<h1>7: Registreer</h1>
			<%@ include file="messages.jsp" %> 
			<table>
				<tr>
					<th>Gebruikersnaam</th>
					<td><input type="text" name="gebrnaam" /></td>
				</tr>
				<tr>
					<th>Naam</th>
					<td><input type="text" name="nm" /></td>
				</tr>
				<tr>
					<th>Wachtwoord</th>
					<td><input type="password" name="ww1" /></td>
				</tr>
				<tr>
					<th>Wachtwoord controle</th>
					<td><input type="password" name="ww2" /></td>
				</tr>
				<tr>
					<th>Email</th>
					<td><input type="text" name="mail1" /></td>
				</tr>
				<tr>
					<th>Bevestig email</th>
					<td><input type="text" name="mail2" /></td>
				</tr>
				<tr>
					<th>Adres</th>
					<td><input type="text" name="adr" /></td>
				</tr>
				<tr>
					<th>Woonplaats</th>
					<td><input type="text" name="wp" /></td>
				</tr>
				<tr>
					<th>Telefoonnummer</th>
					<td><input type="text" name="telnr" /></td>
				</tr>
				<tr>
					<th>Rekeningnummer</th>
					<td><input type="text" name="rnr" /></td>
				</tr>
			</table>
			<input type="submit" value="Registreer" name="knop" />
		</div>
	</form>
<%@ include file="footer.html" %>