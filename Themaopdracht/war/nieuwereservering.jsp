<jsp:include page="header.jsp" > 
 <jsp:param name="titel" value="Nieuwe reservering" /> 
</jsp:include> 
	<form action="NieuweReserveringServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.Auto,domeinklassen.User" %>
		<div>
			<h1><span>3: Nieuwe Reservering aanmaken</span></h1>
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
				Object auto = request.getAttribute("deAuto");
				if (auto != null){
					Auto deAuto = (Auto)auto;
					out.println("<h2><span>Reservering voor:</span></h2>");
					out.println("<p>" + deAuto.toString() + "</p>");
					out.println("<table>");
						out.println("<tr>");
							out.println("<th>Begindatum</th>");
							out.println("<td><input type=text class=datepicker name=begindatum /></td>");
						out.println("/<tr>");
						out.println("<tr>");
							out.println("<th>Einddatum</th>");
							out.println("<td><input type=text class=datepicker name=einddatum /></td>");
						out.println("/<tr>");
					out.println("</table>");
					out.println("<input type=hidden name=deAuto value=" + deAuto.getID() +" />");
					out.println("<input type=submit name=knop value=maakReservering />");
				}
				else{
					ArrayList<Auto> autos = null;
					Object g = request.getSession().getAttribute("gebruiker");
					if(g != null){
						User deGebruiker = (User)g;
						if(deGebruiker.getType() == 3){
							autos = deGebruiker.getDeKlant().getAutos();
						}
					}
					else{
						autos = (ArrayList<Auto>)request.getAttribute("autos");
					}
					if(autos != null){
						out.println("<h2><span>Selecteer de auto waar de reservering voor wordt gemaakt</span></h2>");
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
						out.println("<input type=submit name=knop value=kiesAuto />");
					}
					else{
						ArrayList<Klant> klanten = (ArrayList<Klant>)request.getAttribute("klanten");
						if(klanten != null){				
							out.println("<h2><span>Haal de autos op van de geselecteerde klant</span></h2>");	
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
						else{
							out.println("<h2><span>Haal eerst gegevens van de klanten op</span></h2>");
							out.println("<input type=submit name=knop value=klanten");
						}
					}
				}
			%>
		</div>
	</form>
</body>
</html>