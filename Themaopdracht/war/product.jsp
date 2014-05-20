<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Hoofdmenu Product</title>
</head>
<body>
	<p><a href="index.html">Hoofdmenu</a></p>
	<h1><span>Hoofdmenu Product</span></h1>
	<form action="ProductServlet.do" method="post">
		<%@ page import="domeinklassen.Product,java.util.ArrayList" %>
		<div>
			<h2><span>Overzicht alle producten</span></h2>
			<input type="submit" name="knop" value="overzicht" />
		</div>
		<div>
			<h2><span>Nieuw product aanmaken</span></h2>
			<%
				Object error =  request.getAttribute("error");
				if(error != null){
					out.println("<h3><span>Error!</span></h3>");
					out.println("<p name=error class=error >" + error + "</p>");
				}
				else{
					Object msg = request.getAttribute("msg");
					if(msg != null){
						out.println("<h3 name=msg class=msg >" + msg + "</h3>");
					}
				}
			%>
			<table>
				<tr>
					<th>Naam:</th>
					<td><input type="text" name="naam" /></td>
				</tr>
				<tr>
					<th>Minimum aantal:</th>
					<td><input type="text" name="minaantal" /></td>
				</tr>
				<tr>
					<th>Eenheid:</th>
					<td><input type="text" name="eenheid" /></td>
				</tr>
				<tr>
					<th>Prijs per stuk:</th>
					<td><input type="text" name="pps" /></td>
				</tr>
			</table>
			<input type="submit" name="knop" value="nieuw" />
		</div>
		<div>
			<h2><span>Zoek product</span></h2>
			<%
				Object zoekmsg = request.getAttribute("zoekmsg");
				if(zoekmsg != null){
					out.println("<h3 class=msg ><span>" + zoekmsg + "</span></h3>");
				}
				Object gevonden = request.getAttribute("productgevonden");
				Object arraygevonden = request.getAttribute("arraygevonden");
				if(gevonden != null){
					Product hetProduct = (Product)gevonden;
					out.println("<p>" + hetProduct.toString() + "</p>");
					out.println("<input type=hidden name=product value=" + hetProduct.getArtikelNr() + " />");
					out.println("<p><input type=submit name=knop value=wijzig /></p>");
				}
				else if(arraygevonden != null){
					ArrayList<Product> lijst = (ArrayList<Product>)arraygevonden;
					boolean eerste = true;
					for(Product p: lijst){
						out.println("<p><input type=radio name=product ");
						if(eerste){out.println("checked=checked ");eerste=false;}
						out.println("value=" + p.getArtikelNr() + " />" + p.toString() + "</p>");			
					}
					out.println("<p><input type=submit name=knop value=wijzig /></p>");	
				}
			%>
			<table>
				<tr>
					<th>Naam:</th>
					<td><input type="text" name="zoeknaam" /></td>
				</tr>
				<tr>
					<th>Artikelnummer:</th>
					<td><input type="text" name="zoeknummer" /></td>
				</tr>
			</table>
			<input type="submit" name="knop" value="zoek" />
		</div>
	</form>
</body>
</html>