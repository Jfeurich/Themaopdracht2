<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Inloggen" /> 
</jsp:include> 
	<h1><span>Wachtwoord vergeten</span></h1> 
	<%@ include file="messages.jsp" %> 
	<form action="WwVergetenServlet.do" method="post">
		<h2><span>Vul uw gebruikersnaam in en klik op "Nieuw wachtwoord"</span></h2>
		<h4><span>U ontvangt dan een nieuw wachtwoord op het door u opgegeven emailadres</span></h4>
		<table>
			<tr>
				<th>Gebruikersnaam:</th><td><input type="text" name="username" /></td>
			</tr>
		</table>
		<p><input type="submit" value="Nieuw wachtwoord" name="knop" /> </p>
	</form>
<%@ include file="footer.html" %>