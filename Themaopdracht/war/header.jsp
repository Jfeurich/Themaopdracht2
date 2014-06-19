<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<title>${param.titel}</title> 
	<link rel="stylesheet" href="stylesheet.css" />
</head>
<body> 
	<div id="head">
		<%@ page import="domeinklassen.User" %>
		<div id="titel">
			<span id="hoofdtitel">Auto Totaal Dienst Utrecht</span><br />
			<span id="ondertitel">Plezier met rijden!</span>
			<span><img src="Logo.png" id="logo" alt="Logo" /></span>
		</div>
		<div id="accountinfo">
			<%
			User u = (User) request.getSession().getAttribute("gebruiker");
			if(u != null){
			%>
				<p>Welkom <% out.print(u.getGebruikersnaam()); %>!</p>
				<p><a href="mijnaccount.jsp">Mijn Account</a></p>
				<form action="LogoutServlet.do" method="post">
					<p><input type="submit" name="knop" value="Loguit" /></p>
				</form>
			<%
			}
			else{
			%>
				<p><a href="loginpage.jsp">Inloggen</a></p>
				<p><a href="registreer.jsp">Registeren</a></p>
			<%	
			}
			%>
		</div>
	</div>
	<div id="menu">
	<ul>	
		<%
		if(u != null){
		%>
			<li> <a href="index.jsp">Homepage</a>
			</li>
			<%
			if(u.getType() == 0 || u.getType() == 3){
			%>
			<li> <a href="#">Auto</a>
				<%
				if(u.getType() == 0){
				%>
				<ul><li><a href="overzichtautos.jsp">Overzicht auto's</a></li></ul>
				<%
				}
				%>
				<ul><li><a href="autotoevoegen.jsp">Auto toevoegen</a></li></ul>
			</li>
			<%
			}
			if(u.getType() == 0 || u.getType() == 1){
			%>
			<li> <a href="#">Garage</a>
				<ul><li><a href="klus.jsp">Hoofdmenu klussen</a></li></ul>
				<ul><li><a href="overzichtwerkplaatsplanning.jsp">Overzicht planning werkplaats</a></li></ul>
			</li>
			<%
			}
			if(u.getType() != 1){
			%>
			<li> <a href="#">Parkeerplaats</a>
				<ul><li><a href="parkeerplaatsoverzicht.jsp">Parkeerplaats reserveren</a></li></ul>
				<%
				if(u.getType() == 0 || u.getType() == 2){
				%>
				<ul><li><a href="reserveringbevestigen.jsp">Parkeerplaatsreservering bevestigen</a></li></ul>
				<%
				}
				%>
				<ul><li><a href="reserveringannuleren.jsp">Parkeerplaatsreservering annuleren</a></li></ul>
			</li>
			<%
			}
			if(u.getType() == 0){
			%>
			<li> <a href="#">Administratie</a>
				<ul><li><a href="nieuwegebruikersaccount.jsp">Registreer nieuwe gebruikersaccount</a></li></ul>
				<ul><li><a href="accountwijzigen.jsp">Gebruikersacount wijzigen</a></li></ul>
				<ul><li><a href="factuur.jsp">Hoofdmenu Facturen</a></li></ul>
				<ul><li><a href="btwoverzicht.jsp">Overzicht BTW</a></li></ul>
				<ul><li><a href="nieuwebrief.jsp">Nieuwe herinneringsbrief</a></li></ul>
			</li>
			<li> <a href="#">Producten</a>
				<ul><li><a href="product.jsp">Hoofdmenu producten</a></li></ul>
				<ul><li><a href="bestelling.jsp">Overzicht bestellingen</a></li></ul>
				<ul><li><a href="nieuwebestelling.jsp">Nieuwe bestelling</a></li></ul>
			</li>
			<%
			}
		}
		else{
		%>
			<li> <a href="loginpage.jsp">Inloggen</a>
			</li>
		<%
		}
		%>
	</ul>
	</div>
	<div id="content">
		