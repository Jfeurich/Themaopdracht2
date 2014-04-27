<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Voorraad Menu</title>
</head>
<body>
	<form action="VoorraadOverzichtServlet.do" method="get">
		<div>
			<h2>Overzicht totale voorraad</h2>
			<input type="submit" name="knop" value="overzicht" />
		</div>
		<div>
			<h2>Nieuw product aanmaken</h2>
			<table>
				<tr>
					<td>Naam:</td>
					<td><input type="text" name="naam" /></td>
				</tr>
				<tr>
					<td>Artikelnummer:</td>
					<td><input type="text" name="artikelnr" /></td>
				</tr>
				<tr>
					<td>Minimum aantal:</td>
					<td><input type="text" name="minaantal" /></td>
				</tr>
				<tr>
					<td>Eenheid:</td>
					<td><input type="text" name="eenheid" /></td>
				</tr>
				<tr>
					<td>Prijs per stuk:</td>
					<td><input type="text" name="pps" /></td>
				</tr>
			</table>
			<input type="submit" name="knop" value="nieuw" />
		</div>
		<div>
			<h2>Zoek product</h2>
			<table>
				<tr>
					<td>Naam:</td>
					<td><input type="text" name="zoeknaam" /></td>
				</tr>
				<tr>
					<td>Artikelnummer:</td>
					<td><input type="text" name="zoekartikelnr" /></td>
				</tr>
			</table>
			<input type="submit" name="knop" value="zoek" />
		</div>
	</form>
</body>
</html>