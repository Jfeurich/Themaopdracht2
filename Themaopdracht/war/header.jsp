<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<title>${param.titel}</title> 
	<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css" />
	<link rel="stylesheet" href="stylesheet.css" />
</head>
<body>
	<p><a href="index.jsp">Hoofdmenu</a></p>
	<div id="head">
		Auto Totaal Dienst Utrecht
		<div id="accountinfo">
			<form action="LogoutServlet.do" method="post">
				<p><input type="submit" name="knop" value="Loguit" /></p>
			</form>
		</div>
	</div>
	<div id="menu">
	<ul>
		<li> <a href="#">Klant</a>
			<ul>
				<li><a href="parkeerplaatsoverzicht.jsp">Nieuwe reservering</a></li>
			</ul>
			<ul>
				<li><a href=""></a></li>
			</ul>
		</li>
		<li> <a href="#">Parkeerplaats</a>
			<ul>
				<li><a href=""></a></li>
			</ul>
			<ul>
				<li><a href="">Werkplaats</a></li>
			</ul>
		</li>
		<li> <a href="#">Administratie</a>
			<ul>
				<li><a href="nieuwebestelling.jsp">Nieuwe bestelling</a></li>
			</ul>
			<ul>
				<li><a href=""></a></li>
			</ul>
		</li>
	</ul>
	</div>
	<div id="content">