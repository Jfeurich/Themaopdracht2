<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<title>${param.titel}</title> 
	<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css" />
	<link rel="stylesheet" href="stylesheet.css" />
</head>
<body> 
	<div id="head">
		<div id="titel">
			<span id="hoofdtitel">Auto Totaal Dienst Utrecht</span><br />
			<span id="ondertitel">Plezier met rijden!</span>
		</div>
		<div id="accountinfo">
			<form action="LogoutServlet.do" method="post">
				<p><input type="submit" name="knop" value="Loguit" /></p>
			</form>
		</div>
	</div>
	<div id="menu">
	<ul>
		<%@ page import="domeinklassen.User" %>
		<%
		User u = (User) request.getSession().getAttribute("gebruiker");
		if(u != null){
		%>
			<li> <a href="index.jsp">Homepage</a>
			</li>
			<li> <a href="#">Auto</a>
				<ul><li><a href="autotoevoegen.jsp">Auto toevoegen</a></li></ul>
			</li>
			<li> <a href="#">Garage</a>
				<%
				if(u.getType() == 0 || u.getType() == 1){
				%>
				<ul><li><a href=klus.jsp>Hoofdmenu klussen</a></li></ul>
				<ul><li><a href=klusannuleren.jsp>Een klus annuleren</a></li></ul>
				<ul><li><a href=overzichtwerkplaatsplanning.jsp>Overzicht planning werkplaats</a></li></ul>
				<%
				}
				%>
			</li>
			<%
			if(u.getType() != 1){
			%>
			<li> <a href="#">Parkeerplaats</a>
				<ul><li><a href=parkeerplaatsoverzicht.jsp>Overzicht parkeerplaats</a></li></ul>
				<%
				if(u.getType() == 0 || u.getType() == 2){
				%>
				<ul><li><a href=reserveringannuleren.jsp>Een parkeer reservering annuleren</a></li></ul>
				<%
				}
				%>
			</li>
			<%
			}
			if(u.getType() == 0){
			%>
			<li> <a href="#">Administratie</a>
				<ul><li><a href=registreer.jsp>Registreer nieuwe klant</a></li></ul>
				<ul><li><a href=nieuwegebruikersaccount.jsp>Registreer nieuwe gebruikersaccount</a></li></ul>
				<ul><li><a href=nieuwebrief.jsp>Nieuwe herinneringsbrief</a></li></ul>
				<ul><li><a href=factuur.jsp>Overzicht Facturen</a></li></ul>
				<ul><li><a href=btwoverzicht.jsp>Overzicht BTW</a></li></ul>
			</li>
			<li> <a href="#">Producten</a>
				<ul><li><a href=nieuwebestelling.jsp>Nieuwe bestelling</a></li></ul>
				<ul><li><a href=product.jsp>Hoofdmenu producten</a></li></ul>
			</li>
			<%
			}
		}
		else{
		%>
			<li> <a href="loginpage.jsp">Login</a>
			</li>
		<%
		}
		%>
	</ul>
	</div>
	<div id="content">
		