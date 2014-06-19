<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Registreren" /> 
</jsp:include> 
	<h1><span>Welkom bij ATB!</span></h1>
	<h2><span>Maak hier uw gebruikersaccount aan</span></h2>
	<%@ include file="messages.jsp" %> 
	<form action="RegistreerServlet.do" method="post"> 
		<table>
			<tr>
				<th>Gebruikersnaam</th>
				<td><input type="text" name="gebrnaam" value="${param.gebrnaam}" /></td>
			</tr>
			<tr>
				<th>Naam</th>
				<td><input type="text" name="nm" value="${param.nm}" /></td>
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
				<td><input type="text" name="mail1" value="${param.mail1}" /></td>
			</tr>
			<tr>
				<th>Bevestig email</th>
				<td><input type="text" name="mail2" /></td>
			</tr>
			<tr>
				<th>Adres</th>
				<td><input type="text" name="adr" value="${param.adr}" /></td>
			</tr>
			<tr>
				<th>Woonplaats</th>
				<td><input type="text" name="wp" value="${param.wp}" /></td>
			</tr>
			<tr>
				<th>Telefoonnummer</th>
				<td><input type="text" name="telnr" value="${param.telnr}" /></td>
			</tr>
			<tr>
				<th>Rekeningnummer</th>
				<td><input type="text" name="rnr" value="${param.rnr}" /></td>
			</tr>
		</table>
		<p><input type="submit" value="Registreer" name="knop" /></p>
	</form>
<%@ include file="footer.html" %>