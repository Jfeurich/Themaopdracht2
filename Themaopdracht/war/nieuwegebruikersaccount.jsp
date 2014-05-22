<jsp:include page="header.jsp" > 
 <jsp:param name="titel" value="Nieuwe gebruikersaccount" /> 
</jsp:include> 
		<h1><span>Nieuwe Gebruikersaccount</span></h1>
		<form action="NieuweGebruikersaccountServlet.do" method="post"> 
			<div> 
				<%@ page import="domeinklassen.User,domeinklassen.Klant" %>
				<% 
				Object error =  request.getAttribute("error");
				if(error != null){
					out.println("<h3><span>Error!</span></h3>");
					out.println("<p name=error class=error >" + error + "</p>");
				}
				else{
					Object msg = request.getAttribute("msg");
					if(msg != null){
						out.println("<h3 name=msg class=msg ><span>" + msg + "</span></h3>");
					}
				} 

				%> 
			</div> 
			<div> 
				<table>
				<tr><th>Gebruikersnaam</th><td><input type="text" name="username" /></td></tr>
				<tr><th>Wachtwoord</th><td><input type="password" name="password" /></td></tr>
				<tr><th>Bevestig wachtwoord</th><td><input type="password" name="password2" /></td></tr>
				<tr><th>Type</th><td><input type="radio" name="type" value="0" />0<input type="radio" name="type" value="1" />1<input type="radio" name="type" value="2" />2<input type="radio" name="type" value="2"3 />3</td></tr>
				<tr><th>Emailadres</th><td><input type="text" name="email" /></td></tr>
				<tr><th>Bevestig emailadres</th><td><input type="text" name="email2" /></td></tr>
				<tr><th>Klantnummer</th><td><input type="text" name="klantnummer" /></td></tr>
				<tr><th>Naam</th><td><input type="text" name="naam" /></td></tr>
				<tr><th>Adres</th><td><input type="text" name="adres" /></td></tr>
				<tr><th>Woonplaats</th><td><input type="text" name="plaats" /></td></tr>
				<tr><th>Rekeningnummer</th><td><input type="text" name="rekeningnummer" /></td></tr>
				<tr><th>Telefoonnummer</th><td><input type="text" name="telefoonnummer" /></td></tr>
				</table>
				<input type="submit" value="Maak user" name="knop"/>
				<input type="submit" value="Haal Klant" name="knop"/> 
			</div> 
		</form>
	</body> 
</html>