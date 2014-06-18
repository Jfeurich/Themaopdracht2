<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Inloggen" /> 
</jsp:include> 
	<form action="WwVergetenServlet.do" method="post">
		<h1><span>Wachtwoord vergeten</span></h1> 
		<%@ include file="messages.jsp" %> 
		<h2><span>Voer hier je username in</span></h2>
		<table>
			<tr>
				<th>Username</th><td><input type="text" name="username" /></td>
			</tr>
		</table>
		<p><input type="submit" value="Nieuw wachtwoord" name="knop" /> </p>
	</form>
<%@ include file="footer.html" %>