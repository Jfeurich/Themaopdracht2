<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Voorraad Menu</title>
</head>
<body>
	<form action="VoorraadOverzichtServlet.do" method="post">
		<%@ page import="domeinklassen.Product,java.util.ArrayList" %>
		<div>
			<h2>Overzicht totale voorraad</h2>
			<input type="submit" name="knop" value="overzicht" />
		</div>
		<div>
			<h2>Nieuw product aanmaken</h2>
			<%
				Object nieuwmsg = request.getAttribute("nieuwmsg");
				if(nieuwmsg != null){
					out.println(nieuwmsg);
				}
			%>
			<table>
				<tr>
					<td>Naam:</td>
					<td><input type="text" name="naam" /></td>
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
			<%
				Object zoekmsg = request.getAttribute("zoekmsg");
				if(zoekmsg != null){
					out.println(zoekmsg);
				}
				Object gevonden = request.getAttribute("productgevonden");
				Object arraygevonden = request.getAttribute("arraygevonden");
				if(gevonden != null){
					Product hetProduct = (Product)gevonden;
					out.println("<p>Gevonden product:</p>");
					out.println("<p>" + hetProduct.toString() + "</p>");
					out.println("<input type=hidden name=product value=" + hetProduct.getArtikelNr() + " />");
					out.println("<p><input type=submit name=knop value=wijzig /></p>");
				}
				else if(arraygevonden != null){
					ArrayList<Product> lijst = (ArrayList<Product>)arraygevonden;
					out.println("<p>Gevonden product(en):</p>");
					out.println("<p>Selecteer een product om te wijzigen:</p>");
					for(Product p: lijst){
						out.println("<input type=radio name=product value=" + p.getArtikelNr() + " /><p>" + p.toString() + "</p>");			
					}
					out.println("<p><input type=submit name=knop value=wijzig /></p>");	
				}
			%>
			<table>
				<tr>
					<td>Naam:</td>
					<td><input type="text" name="zoeknaam" /></td>
				</tr>
				<tr>
					<td>Artikelnummer:</td>
					<td><input type="text" name="zoeknummer" /></td>
				</tr>
			</table>
			<input type="submit" name="knop" value="zoek" />
		</div>
	</form>
</body>
</html>