<%@ include file="redirect.jsp" %>
<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Nieuwe gebruikersaccount" /> 
</jsp:include> 
	<%@ page import="domeinklassen.User,domeinklassen.Klant" %>
	<h1><span>17: Nieuwe Gebruikersaccount</span></h1>
	<%@ include file="messages.jsp" %> 
	<form action="NieuweGebruikersaccountServlet.do" method="post">
		<table>
			<tr>
				<th>Gebruikersnaam</th>
				<td><input type="text" name="username" value="${param.username}" /></td></tr>
			<tr>
				<th>Wachtwoord</th>
				<td><input type="password" name="password" /></td></tr>
			<tr>
				<th>Bevestig wachtwoord</th><td>
				<input type="password" name="password2" /></td></tr>
			<tr>
				<th>Type</th><td>
				<input type="radio" name="type" value="0" />0<input type="radio" name="type" value="1" />1<input type="radio" name="type" value="2" />2<input type="radio" name="type" value="3" />3</td></tr>
			<tr>
				<th>Emailadres</th><td>
				<input type="text" name="email" value="${param.email}" /></td></tr>
			<tr>
				<th>Bevestig emailadres</th>
				<td><input type="text" name="email2" /></td></tr>
			<tr>
				<th>Klantnummer</th><td>
				<input type="text" name="klantnummer" value="${param.klantnummer}" /></td></tr>
			<tr>
				<th>Naam</th><td>
				<input type="text" name="naam" value="${param.naam}" /></td></tr>
			<tr>
				<th>Adres</th><td>
				<input type="text" name="adres" value="${param.adres}" /></td></tr>
			<tr>
				<th>Woonplaats</th>
				<td><input type="text" name="plaats" value="${param.plaats}" /></td></tr>
			<tr>
				<th>Rekeningnummer</th><td>
				<input type="text" name="rekeningnummer" value="${param.rekeningnummer}" /></td></tr>
			<tr>
				<th>Telefoonnummer</th>
				<td><input type="text" name="telefoonnummer" value="${param.telefoonnummer}" /></td></tr>
		</table>
		<input type="submit" value="Maak user" name="knop"/>
		<input type="submit" value="Haal klant" name="knop"/> 
	</form>
<%@ include file="footer.html" %>