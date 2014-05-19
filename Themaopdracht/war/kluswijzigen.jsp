<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Status Klus wijzigen</title>
</head>
<body>
	<p><a href="index.html">Hoofdmenu</a></p>
	<form action="KlusWijzigenServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.Auto,domeinklassen.Klus,domeinklassen.Onderhoudsbeurt,domeinklassen.Reparatie,domeinklassen.GebruiktProduct,domeinklassen.Product" %>
		<div>
			<h2>16: Klus wijzigen</h2>
			<%
				Object error =  request.getAttribute("error");
				if(error != null){
					out.println("<h3>Error!</h3>");
					out.println("<p name=error >" + error + "</p>");
				}
				else{
					Object msg = request.getAttribute("msg");
					if(msg != null){
						out.println("<h3 name=msg>" + msg + "</h3>");
					}
				}
				Object gekozen = request.getAttribute("deKlus");
				if(gekozen == null){
					ArrayList<Klus> klussen = (ArrayList<Klus>)request.getAttribute("klussen");
					if(klussen == null){
						ArrayList<Auto> autos = (ArrayList<Auto>)request.getAttribute("autos");
						if(autos == null){
							ArrayList<Klant> klanten = (ArrayList<Klant>)request.getAttribute("klanten");
							if(klanten == null){
								out.println("<h2>Haal eerst gegevens van de klanten op</h2>");
								out.println("<input type=submit name=knop value=klanten");
							}
							else{				
								out.println("<h2>Haal de autos op van de geselecteerde klant</h2>");	
								out.println("<table>");
								out.println("<tr>");
									out.println("<td>Kies</td>");
									out.println("<td>Naam</td>");
									out.println("<td>Adres</td>");
									out.println("<td>Woonplaats</td>");
								out.println("</tr>");
								boolean eerste=true;
								for(Klant k : klanten){
									out.println("<tr>");
										out.println("<td><input type=radio name=gekozenklant ");
										if(eerste){out.println("checked=checked ");eerste=false;}
										out.println("value=" + k.getKlantnummer() + " /></td>");
										out.println("<td>" + k.getNaam() + "</td>");
										out.println("<td>" + k.getAdres() + "</td>");
										out.println("<td>" + k.getPlaats() + "</td>");
									out.println("</tr>");
								}
								out.println("</table>");
								out.println("<input type=submit name=knop value=autos />");
							}
						}
						else{
							out.println("<h2>Selecteer de auto waar aan is gewerkt</h2>");
							out.println("<table>");
							out.println("<tr>");
								out.println("<td>Kies</td>");
								out.println("<td>Kenteken</td>");
								out.println("<td>Merk</td>");
								out.println("<td>Type</td>");
								out.println("<td>Eigenaar</td>");
							out.println("</tr>");
							boolean eerste=true;
							for(Auto a : autos){
								out.println("<tr>");
									out.println("<td><input type=radio name=gekozenauto ");
									if(eerste){out.println("checked=checked ");eerste=false;}
									out.println("value=" + a.getID() + " /></td>");
									out.println("<td>" + a.getKenteken() + "</td>");
									out.println("<td>" + a.getMerk() + "</td>");
									out.println("<td>" + a.getType() + "</td>");
									out.println("<td>" + a.getEigenaar().getNaam() + "</td>");
								out.println("</tr>");
							}
							out.println("</table>");
							out.println("<input type=submit name=knop value=klus />");
						}
					}
					else{
						out.println("<h2>Kies de klus waarvan de status moet worden aangepast</h2>");
						out.println("<table>");
						out.println("<tr>");
							out.println("<td>Kies</td>");
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
								out.println("<td>" + k.getDatum() + "</td>");
								out.println("<td>" + k.getBeschrijving() + "</td>");
								out.println("<td>" + k.getStatus() + "</td>");
							out.println("</tr>");
							ArrayList<GebruiktProduct> producten = k.getGebruikteProducten();
							if(producten.size() > 0){
								out.println("<tr>");
									out.println("<td>Artikelnummer</td>");
									out.println("<td>Naam</td>");
									out.println("<td>Aantal</td>");
									out.println("<td>Prijs per stuk</td>");
								out.println("</tr>");
								for(GebruiktProduct gp : producten){
									Product hetProduct = gp.getHetProduct();
									out.println("<tr>");
										out.println("<td>" + hetProduct.getArtikelNr() + "</td>");
										out.println("<td>" + hetProduct.getNaam() + "</td>");
										out.println("<td>" + gp.getAantal() + "</td>");
										out.println("<td>" + hetProduct.getPrijsPerStuk() + "</td>");
									out.println("</tr>");
								}
							}
						}
						out.println("</table>");
						out.println("<input type=submit name=knop value=wijzig />");					
					}
				
				}
				else{
					Klus deKlus = (Klus)gekozen;
					out.println("<h2>Wijzig de klus</h2>");
					out.println("<h3>Voeg eerst artikelen toe indien van toepassing</h3>");
					out.println("<input type=submit name=knop value=nieuwArtikel />");
					out.println("<h3>En voer dan eventuele andere wijzigingen in</h3>");
					out.println("<p>Datum</p>");
					out.println("<p>Huidige datum: " + deKlus.getDatum() + "</p>");
					out.println("<input type=text name=datum placeholder=dd-mm-jjjj />");
					out.println("<tr>Status</tr>");
					out.println("<tr><td><input type=radio name=status checked=checked value=voltooid /></td<td>Voltooid</td></tr>");
					out.println("<tr><td><input type=radio name=status value=onvoltooid /></td<td>Onvoltooid</td></tr>");
					out.println("<tr><td><input type=radio name=status value=wachten op onderdelen /></td<td>Wachten op onderdelen</td></tr>");
					out.println("</table>");
					out.println("<p>Manuren toevoegen</p>");
					out.println("<p>Huidige manuren: " + deKlus.getManuren() + "</p>");
					out.println("<input type=text name=manuren />");
					out.println("<p>Beschrijving</p>");
					out.println("<textarea name=beschrijving>" + deKlus.getBeschrijving() + "</textarea>");
					out.println("<td><input type=hidden name=gekozenklus value=" + deKlus.getID() + " /></td>");
					out.println("<input type=submit name=knop value=bevestig />");
				}
			%>
		</div>
	</form>
	<p><a href="kluswijzigen.jsp">Terug</a></p>
</body>
</html>