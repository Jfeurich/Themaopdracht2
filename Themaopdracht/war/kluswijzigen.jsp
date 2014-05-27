<jsp:include page="header.jsp" > 
 <jsp:param name="titel" value="Klus wijzigen" /> 
</jsp:include> 
<%@include file="datepicker.jsp" %>
	<form action="KlusWijzigenServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.Auto,domeinklassen.Klus,domeinklassen.Onderhoudsbeurt,domeinklassen.Reparatie,domeinklassen.GebruiktProduct,domeinklassen.Product" %>
		<div>
			<h1><span>16: Klus wijzigen</span></h1>
			<%
				Object error =  request.getAttribute("error");
				if(error != null){
					out.println("<h3><span>Error!</span></h3>");
					out.println("<p name=error class=error >" + error + "</p>");
				}
				else{
					Object msg = request.getAttribute("msg");
					if(msg != null){
						out.println("<h3 name=msg class=msg ><span>" + msg + "</span></h3>");
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
								out.println("<h2><span>Haal eerst gegevens van de klanten op</span></h2>");
								out.println("<input type=submit name=knop value=klanten");
							}
							else{				
								out.println("<h2><span>Haal de autos op van de geselecteerde klant</span></h2>");	
								out.println("<table>");
								out.println("<tr>");
									out.println("<th>Kies</th>");
									out.println("<th>Naam</th>");
									out.println("<th>Adres</th>");
									out.println("<th>Woonplaats</th>");
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
							out.println("<h2><span>Selecteer de auto waar aan is gewerkt</span></h2>");
							out.println("<table>");
							out.println("<tr>");
								out.println("<th>Kies</th>");
								out.println("<th>Kenteken</th>");
								out.println("<th>Merk</th>");
								out.println("<th>Type</th>");
								out.println("<th>Eigenaar</th>");
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
						out.println("<h2><span>Kies de klus die moet worden aangepast</span></h2>");
						out.println("<table>");
						out.println("<tr>");
							out.println("<th>Kies</th>");
							out.println("<th>Datum</th>");
							out.println("<th>Beschrijving</th>");
							out.println("<th>Status</th>");
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
									out.println("<th>Artikelnummer</th>");
									out.println("<th>Naam</th>");
									out.println("<th>Aantal</th>");
									out.println("<th>Prijs per stuk</th>");
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
					out.println("<h2><span>Wijzig de klus</span></h2>");
					out.println("<h3><span>Voeg eerst artikelen toe indien van toepassing</span></h3>");
					out.println("<input type=submit name=knop value=nieuwArtikel />");
					out.println("<h3><span>En voer dan eventuele andere wijzigingen in</span></h3>");
					out.println("<p class=kop >Datum</p>");
					out.println("<p>Huidige datum: " + deKlus.getFormattedDatum() + "</p>");
					out.println("<input type=text name=datum class=datepicker />");
					out.println("<table>");
					out.println("<tr><th>Status</th></tr>");
					out.println("<tr><td><input type=radio name=status checked=checked value=voltooid /></td><td>Voltooid</td></tr>");
					out.println("<tr><td><input type=radio name=status value=onvoltooid /></td><td>Onvoltooid</td></tr>");
					out.println("<tr><td><input type=radio name=status value=wachten op onderdelen /></td><td>Wachten op onderdelen</td></tr>");
					out.println("</table>");
					out.println("<p class=kop >Manuren toevoegen</p>");
					out.println("<p>Huidige manuren: " + deKlus.getManuren() + "</p>");
					out.println("<input type=text name=manuren />");
					out.println("<p class=kop >Beschrijving</p>");
					out.println("<textarea name=beschrijving>" + deKlus.getBeschrijving() + "</textarea>");
					out.println("<input type=hidden name=gekozenklus value=" + deKlus.getID() + " />");
					out.println("<input type=submit name=knop value=bevestig />");
				}
			%>
		</div>
	</form>
	<p><a href="kluswijzigen.jsp">Terug</a></p>
</body>
</html>