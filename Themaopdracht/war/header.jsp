<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<title>${param.titel}</title> 
	<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css" />
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
	<script src="https://jquery-ui.googlecode.com/svn-history/r3982/trunk/ui/i18n/jquery.ui.datepicker-nl.js"></script>
	<script>
		$(function() {
			$.datepicker.setDefaults($.datepicker.regional["nl"]);
			$( ".datepicker" ).datepicker( {
				dateFormat: "dd-mm-yy", 
				minDate: "01-01-2000",
				maxDate: "+5y",
				constrainInput: true
			} );
		});
	</script>
</head>
<body>
	<p><a href="index.jsp">Hoofdmenu</a></p>
	<div>
		<h2><span>24: Uitloggen</span></h2>
		<form action="LogoutServlet.do" method="post">
			<p><input type="submit" name="knop" value="Loguit" /></p>
		</form>
	</div>