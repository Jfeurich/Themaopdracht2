<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<title>Overzicht gemaakte pagina's</title>
	</head>
	<body>
		<h1><span>Index van alle pagina's</span></h1>
		<div>
			<%@ page import="domeinklassen.User" %>
			<%
			Object u = request.getSession().getAttribute("gebruiker");
			if(u != null){
				User deGebruiker = (User)u;
				%>
				<p>Welkom, <%=deGebruiker.getGebruikersnaam()%>!</p>
				<%
			}
			%>
		</div>
		<h2><span>Achter elke link staat vermeld welke User-stories het is aan de hand van het ID.</span></h2>
		<div>
			<ul>
				<li><a href=loginpage.jsp>Inloggen(1)</a></li>
				<li><a href=nieuwereservering.jsp>Nieuwe reservering(3)</a></li>
				<li><a href=kluswijzigen.jsp>Klus wijzigen(5, 16)</a></li>
				<li><a href=nieuwefactuur.jsp>Nieuwe factuur(6)</a></li>
				<li><a href=registreer.jsp>Registreer nieuwe klant(7)</a></li>
				<li><a href=autotoevoegen.jsp>Auto toevoegen(8)</a></li>
				<li><a href=klus.jsp>Hoofdmenu klussen(9)</a></li>
				<li><a href=nieuwebestelling.jsp>Nieuwe bestelling(11)</a></li>
				<li><a href=parkeerplaatsoverzicht.jsp>Overzicht parkeerplaats(12)</a></li>
				<li><a href=product.jsp>Hoofdmenu producten(14, 27)</a></li>
				<li><a href=nieuwegebruikersaccount.jsp>Registreer nieuwe gebruikersaccount(17)</a></li>
				<li><a href=factuur.jsp>Overzicht Facturen(18, 21)</a></li>
				<li><a href=btwoverzicht.jsp>Overzicht BTW(22)</a></li>
				<li><a href=nieuwebrief.jsp>Nieuwe herinneringsbrief(23)</a></li>
				<li><a href=reserveringannuleren.jsp>Een parkeer reservering annuleren(25)</a></li>
				<li><a href=klusannuleren.jsp>Een klus annuleren(26)</a></li>
				<li><a href=nieuweklus.jsp>Nieuwe klus(28)</a></li>
				<li><a href=overzichtwerkplaatsplanning.jsp>Overzicht planning werkplaats(28)</a></li>
			</ul>
		</div>
<%@ include file="footer.html" %>