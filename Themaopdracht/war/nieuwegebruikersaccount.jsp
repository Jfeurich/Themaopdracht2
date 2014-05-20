<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
	<html xmlns="http://www.w3.org/1999/xhtml"> 
	<head> 
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" /> 
		<title>Nieuwe Gebruikersaccount</title> 
	</head> 
	<body> 
		<h1><span>Nieuwe Gebruikersaccount</span></h1>
		<form action="NieuweGebruikersaccount.do" method="post"> 
			<div> 
				<%@ page import="java.util.logging.Logger" %>
				<% 
					Object msgs = request.getAttribute("msgs"); 
					if (msgs != null) { 
						out.println("<h3 class=msg ><span>" + msgs + "</span></h3>"); 
					} 
					String username = "";
					/*for(Cookie c: request.getCookies()){
						if(c.getName().equals("username")){
							username = c.getValue();
							break;
						}
					}*/
				%> 
			</div> 
			<div> 
				<table>
				<tr><th>Username</th><td><input type="text" name="username" value="<%= username %>"/></td></tr>
				<tr><th>Password</th><td><input type="password" name="password" /></td></tr>
				<tr><th>Email</th><td><input type="text" name="email" /></td></tr>
				<tr><th>Klantnummer</th><td><input type="text" name="klantnummer" /></td></tr>
				<tr><th>Naam</th><td><input type="text" name="naam" /></td></tr>
				<tr><th>Adres</th><td><input type="text" name="adres" /></td></tr>
				<tr><th>Plaats</th><td><input type="text" name="plaats" /></td></tr>
				<tr><th>Rekeningnummer</th><td><input type="text" name="rekeningnummer" /></td></tr>
				<tr><th>Telefoonnummer</th><td><input type="text" name="telefoonnummer" /></td></tr>
				</table>
				<input type="submit" name="knop" value="maak" /> 
			</div> 
		</form>
	</body> 
</html>