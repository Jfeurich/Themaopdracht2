<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" /> 
	<title>Register Page</title> 
</head> 
<body>
	<form action="RegistreerServlet.do" method="get"> 
		<div id="messagebox"> 
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
			<h1>7: Registreer</h1>
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
</body>
 </html>