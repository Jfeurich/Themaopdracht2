<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<title>Factuur Menu</title>
	</head>
	<body>
		<p><a href="index.html">Hoofdmenu</a></p>
		<form action="OnbetaaldeFacturenOverzichtServlet.do" method="post">
			<%@ page import="domeinklassen.Factuur,java.util.ArrayList" %>
			<div>
				<h2>Overzicht Onbetaalde Facturen</h2>
				<input type="submit" name="knop" value="overzicht" />
			</div>
		</form>
		<p><a href="nieuwefactuur.jsp">Nieuwe factuur aanmaken</a></p>
	</body>
</html>