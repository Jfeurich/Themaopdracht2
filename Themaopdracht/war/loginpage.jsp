<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Inloggen" /> 
</jsp:include> 
	<form action="LoginServlet.do" method="post">
		<h1><span>LoginPage</span></h1> 
		<%@ include page="messages.jsp" %> 
		<div> 
			<table>
				<tr>
					<th>Username</th>
					<td><input type="text" name="username"/></td>
				</tr>
				<tr>
					<th>Password</th>
					<td><input type="password" name="password" /></td>
				</tr>
			</table>
			<input type="submit" value="Login!" /> 
		</div> 
	</form>
<%@ include page="footer.html" %>