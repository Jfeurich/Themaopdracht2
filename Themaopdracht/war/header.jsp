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
		<span>24: Uitloggen</span>
		<form action="LogoutServlet.do" method="post">
			<p><input type="submit" name="knop" value="Loguit" /></p>
		</form>
	</div>