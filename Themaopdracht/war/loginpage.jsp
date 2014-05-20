<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
	<html xmlns="http://www.w3.org/1999/xhtml"> 
	<head> 
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" /> 
		<title>Login</title> 
	</head> 
	<body> 
		<h1><span>LoginPage</span></h1>
		<form action="LoginServlet.do" method="post"> 
			<div> 
				<% 
					Object msg = request.getAttribute("msg"); 
					if (msg != null) { 
						out.println("<h3 class=msg ><span>" + msgs + "</span></h3>"); 
					} 
				%> 
			</div> 
			<div> 
				<table>
				<tr><th>Username</th><td><input type="text" name="username"/></td></tr>
				<tr><th>Password</th><td><input type="password" name="password" /></td></tr>
				</table>
				<input type="submit" value="Login!" /> 
			</div> 
		</form>
	</body> 
</html>