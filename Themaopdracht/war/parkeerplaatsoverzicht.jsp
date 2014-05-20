<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Overzicht parkeerplaats</title>
</head>
<body>
	<p><a href="index.html">Hoofdmenu</a></p>
	<form action="ParkeerplaatsOverzichtServlet.do" method="post">
		
		<div>
			<h1><span>12: Overzicht parkeerplaats</span></h1>
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
				int rij = (int) getServletContext().getAttribute("parkeerplaatsRij");
				int kolom = (int) getServletContext().getAttribute("parkeerplaatsKolom");
				int teller = 0;
				out.println("<table>");
				for(int i = 1; i <= rij; i++){
					out.println("<tr>");
					for(int j = 1; j <= kolom; j++){
						out.println("<td>");
						out.println("<input type=submit name=knop value=" + teller + " />");
						out.println("</td>");
						teller++;
					}
					out.println("</td>");
				}
				out.println("</table>");
			%>
		</div>
	</form>
</body>
</html>