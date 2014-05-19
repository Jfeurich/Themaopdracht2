<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Hoofdmenu Klus</title>
</head>
<body>
	<p><a href="index.html">Hoofdmenu</a></p>
	<form action="KlusServlet.do" method="post">
		<%@ page import="domeinklassen.Klus,domeinklassen.Onderhoudsbeurt,domeinklassen.Reparatie,domeinklassen.Product,domeinklassen.GebruiktProduct,java.util.ArrayList" %>
		<div>
			<h2>28: Nieuwe klus aanmaken</h2>
			<input type="submit" name="knop" value="nieuw" />
		</div>
		<div>
			<h2>9: Overzicht alle klussen</h2>
			<%
				Object ar = request.getAttribute("klussen");
				if(ar != null){
					ArrayList<Klus> klussen = (ArrayList<Klus>)ar;
					out.println("<table>");
					out.println("<tr>");
						out.println("<td>Kies</td>");
						out.println("<td>Type</td>");
						out.println("<td>Datum</td>");
						out.println("<td>Beschrijving</td>");
						out.println("<td>Status</td>");
					out.println("</tr>");
					boolean eerste=true;
					for(Klus k : klussen ){
						out.println("<tr>");
							out.println("<td><input type=radio name=gekozenklus ");
							if(eerste){out.println("checked=checked ");eerste=false;}
							out.println("value=" + k.getID() + " /></td>");
							if(k instanceof Reparatie){
								out.println("<td>Reparatie</td>");
							}
							else{
								out.println("<td>Onderhoud</td>");
							}
							out.println("<td>" + k.getDatum() + "</td>");
							out.println("<td>" + k.getBeschrijving() + "</td>");
							out.println("<td>" + k.getStatus() + "</td>");
						out.println("</tr>");
						ArrayList<GebruiktProduct> producten = k.getGebruikteProducten();
						if(producten.size() > 0){
							out.println("<tr>");
								out.println("<td></td>");
								out.println("<td>Artikelnummer</td>");
								out.println("<td>Naam</td>");
								out.println("<td>Aantal</td>");
								out.println("<td>Prijs per stuk</td>");
							out.println("</tr>");
							for(GebruiktProduct gp : producten){
								Product hetProduct = gp.getHetProduct();
								out.println("<tr>");
									out.println("<td></td>");
									out.println("<td>" + hetProduct.getArtikelNr() + "</td>");
									out.println("<td>" + hetProduct.getNaam() + "</td>");
									out.println("<td>" + gp.getAantal() + "</td>");
									out.println("<td>" + hetProduct.getPrijsPerStuk() + "</td>");
								out.println("</tr>");
							}
						}
					}
					out.println("</table>");
					out.println("<h2>16: Klus wijzigen</h2>");
					out.println("<input type=submit name=knop value=wijzig />");
				}
				else{
					out.println("<input type=submit name=knop value=overzicht />");
				}
			%>
		</div>
	</form>
</body>
</html>