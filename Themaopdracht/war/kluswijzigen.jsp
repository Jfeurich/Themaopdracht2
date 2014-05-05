<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Status Klus wijzigen</title>
</head>
<body>
	<form action="StatusWijzigenKlusServlet.do" method="post">
		<%@ page import="domeinklassen.Klus" %>
		<div>
			<h2>Status Klus wijzigen</h2>
			<% 				
			Object error =  request.getAttribute("error");
			if(error != null){
				out.println("<h2>Error!</h2>");
				out.println(error);
			}
			else{
				Object msg = request.getAttribute("msg");
				if(msg != null){
					out.println(msg);
				}
			}
			if(request.getAttribute("stap1") != null){
				out.println("De status van de klus is gewijzigd");
				out.println("<input type=submit name=knop value=Done />");
			}
			%>
		</div>
	</form>
</body>
</html>