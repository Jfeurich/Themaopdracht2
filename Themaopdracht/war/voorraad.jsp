<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Voorraad Menu</title>
</head>
<body>
	<form action="VoorraadOverzichtServlet.do" method="post">
		<%@ page import="domeinklassen.Product" %>
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
				Object gevonden = request.getAttribute("productgevonden");
				if(gevonden != null){
					Product hetProduct = (Product)gevonden;
					out.println("Gevonden product:");
					out.println(hetProduct.toString());
					request.setAttribute("product", hetProduct);
					RequestDispatcher rd = request.getRequestDispatcher("VoorraadOverzichtServlet.java");
					rd.forward(request, response);
					out.println("<input type=submit name=knop value=wijzig />");
				}
				else{
					Object zoekmsg = request.getAttribute("zoekmsg");
					if(zoekmsg != null){
						out.println(zoekmsg);
					}
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