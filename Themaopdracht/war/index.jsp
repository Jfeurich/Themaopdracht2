<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<title>Overzicht gemaakte pagina's</title>
	</head>
	<body>
		<div>
			<%@ page import="domeinklassen.User" %>
			<%
				//moet nog aangepast worden zodat de naam van de gebruiker uit de sessie wordt gehaald
				User deGebruiker = (User) request.getSession().getAttribute("gebruiker");
				out.println("Welkom " + deGebruiker.getGebruikersnaam());
			%>
		</div>
		<div>
			Achter elke link staat vermeld welke User-storys het is aan de hand van het ID.
		</div>
		<div>
			<ul>
				<li><a href=autotoevoegen.jsp>Auto toevoegen(8)</a></li>
				<li><a href=factuur.jsp>Overzicht Facturen(18, 21)</a></li>
				<li><a href=kluswijzigen.jsp>Klus wijzigen(5, 16)</a></li>
				<li><a href=nieuwebestelling.jsp>Nieuwe bestelling(11)</a></li>
				<li><a href=nieuwefactuur.jsp>Nieuwe factuur(6)</a></li>
				<li><a href=nieuweklus.jsp>Nieuwe klus(28)</a></li>
				<li><a href=product.jsp>Hoofdmenu producten(14, 27)</a></li>
				<li><a href=nieuwereservering.jsp>Nieuwe reservering(3)</a></li>
				<li><a href=klus.jsp>Hoofdmenu klussen(9)</a></li>
				<li><a href=btwoverzicht.jsp>Overzicht BTW(22)</a></li>
				<li><a href=nieuwebrief.jsp>Nieuwe herinneringsbrief(23)</a></li>
			</ul>
		</div>
		<div>
			<form action="LogoutServlet.do" method="post">
				<input type=submit name=knop value=Loguit />
			</form>
		</div>
	</body>
</html>