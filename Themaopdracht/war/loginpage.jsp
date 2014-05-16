<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
	<html xmlns="http://www.w3.org/1999/xhtml"> 
	<head> 
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" /> 
		<title>Login</title> 
	</head> 
	<body> 
		<h2>LoginPage</h2>
		<form action="LoginServlet.do" method="post"> 
			<div> 
				<%@ page import="java.util.logging.Logger" %>
				<% 
					Object msgs = request.getAttribute("msgs"); 
					if (msgs != null) { 
						out.println(msgs); 
						Logger.getLogger("me.logger").warning((String) msgs); 
					} 
					String username = null;
					for(Cookie c: request.getCookies()){
						if(c.getName().equals("username")){
							username = c.getValue();
							break;
						}
					}
					
				%> 
			</div> 
			<div> 
				Username<input type="text" name="username" value="<%= username %>"/> <br />
				Password<input type="password" name="password" /> <br />
				<input type="submit" value="Login!" /> 
			</div> 
		</form>
	</body> 
</html>