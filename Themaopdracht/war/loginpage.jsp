<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Inloggen" /> 
</jsp:include> 
	<h1><span>LoginPage</span></h1> 
	<%@ include file="messages.jsp" %> 
	<form action="LoginServlet.do" method="post">
		<table>
			<tr>
				<th>Username</th><td><input type="text" name="username" value="${cookie.username.value}" /></td>
			</tr>
			<tr>
				<th>Password</th><td><input type="password" name="password" /></td>
			</tr>
		</table>
		<p><input type="submit" value="Login!" /></p>
		<a href="wwvergeten.jsp">Wachtwoord vergeten?</a>
	</form>
<%@ include file="footer.html" %>