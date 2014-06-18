<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Inloggen" /> 
</jsp:include> 
	<form action="LoginServlet.do" method="post">
		<h1><span>LoginPage</span></h1> 
		<%@ include file="messages.jsp" %> 
		<table>
			<tr>
				<th>Username</th><td><input type="text" name="username" value="${cookie.username.value}" /></td>
			</tr>
			<tr>
				<th>Password</th><td><input type="password" name="password" /></td>
			</tr>
		</table>
		<p><input type="submit" value="Login!" /> </p>
	</form>
<%@ include file="footer.html" %>