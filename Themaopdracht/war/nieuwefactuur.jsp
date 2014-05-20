<jsp:include page="header.jsp" > 
 <jsp:param name="titel" value="Nieuwe factuur" /> 
</jsp:include> 
	<form action="NieuweFactuurServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.Auto,domeinklassen.Klus,domeinklassen.Factuur" %>
		<div>
			<h2>6: Nieuwe Factuur aanmaken</h2>
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
				Object fact = request.getAttribute("deFactuur");
				if (fact != null){
					Factuur deFactuur = (Factuur)fact;
					out.println("<h2>De factuur</h2>");
					Klus k = deFactuur.getDeKlus();
					Auto a = k.getAuto();
					Klant kl = a.getEigenaar();
					out.println("<p>Klant: " + kl.getNaam() + "</p>");
					out.println("<p>Auto: " + a.getMerk() + "</p>");
					out.println("<p>Klus: " + k.getBeschrijving() + "</p>");
					out.println("<h2>Voer kortingspercentage in</h2>");
					out.println("<input type=text name=korting />");
					out.println("<input type=hidden name=factuurvoorkorting value=" + deFactuur.getID() +" />");
					out.println("<input type=submit name=knop value=bevestig />");
				}
				else{
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
							out.println("<h2>Selecteer de auto waar aan is gewerkt</h2>");
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
						out.println("<h2>Kies de klus waarvan een factuur moet worden aangemaakt</h2>");
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
							out.println("<input type=hidden name=status value=" + k.getStatus() + " />");
						}
						out.println("</table>");	
						out.println("<input type=submit name=knop value=nieuw />");						
					}	
				}
			%>
		</div>
	</form>
	<p><a href="factuur.jsp">Terug naar hoofdmenu factuur</a></p>
</body>
</html>