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
					 Object msgs = request.getAttribute("msgs"); 
					 if (msgs != null) { 
						 out.println(msgs); 
					 } 
					 
					 Object error = request.getAttribute("error"); 
					 if (error != null) { 
						 out.println(error); 
					 } 
				 %> 
			</div> 
 			<div>
 			<center>
 			<h1>Registreer</h1>
 			<TABLE border="0">
 				<TR>
 					<TD>Gebruikersnaam</TD>
 					<TD align="left">
 						<input type="text" name="gebrnaam" />
 					</TD>
 				</TR>
 				<TR>
 					<TD>Naam</TD>
 					<TD align="left">
 						<input type="text" name="nm" />
 					</TD>
 				</TR>
 				<TR>
 					<TD>Wachtwoord</TD>
 					<TD align="left">
 						<input type="password" name="ww1" />
 					</TD>
 				</TR>
 				<TR>
 					<TD>Wachtwoord controle</TD>
 					<TD align="left">
 						<input type="password" name="ww2" />
 					</TD>
 				</TR>
 				<TR>
 					<TD>E-mail</TD>
 					<TD align="left">
 						<input type="text" name="mail1" />
 					</TD>
 				</TR>
 				<TR>
 					<TD>E-mail controle</TD>
 					<TD align="left">
 						<input type="text" name="mail2" />
 					</TD>
 				</TR>
 				<TR>
 					<TD>Adres</TD>
 					<TD align="left">
 						<input type="text" name="adr" />
 					</TD>
 				</TR>
 				<TR>
 					<TD>Woonplaats</TD>
 					<TD align="left">
 						<input type="text" name="wp" />
 					</TD>
 				</TR>
 				</TR>
 					<TR>
 					<TD>Telefoonnummer</TD>
 					<TD align="left">
 						<input type="text" name="telnr" />
 					</TD>
 				</TR>
 					<TR>
 					<TD>Rekeningnummer</TD>
 					<TD align="left">
 						<input type="text" name="rnr" />
 					</TD>
 				</TR>
 				</TABLE>
 				<input type="submit" value="Registreer" name="knop" />
 				</center>
 			</div>
 		</form>
 	</body>
 </html>