<jsp:include page="header.jsp" > 
 <jsp:param name="titel" value="Nieuwe klus" /> 
</jsp:include> 
<%@include file="datepicker.jsp" %>
	<form action="NieuweKlusServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.Auto" %>
		<div>
			<h1><span>28: Nieuwe klus aanmaken</span></h1>
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
				Object gekozen = request.getAttribute("deAuto");
				if(gekozen == null){
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
							out.println("<td>Kies</td>");
							out.println("<td>Kenteken</td>");
							out.println("<td>Merk</td>");
							out.println("<td>Type</td>");
							out.println("<td>Eigenaar</td>");
						out.println("</tr>");
						boolean eerste = true;
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
						out.println("<input type=submit name=knop value=kiesauto />");
					}
				}
				else{
					Auto deAuto = (Auto)gekozen;
					out.println("<h2><span>Vul het formulier in en klik op 'nieuw' om de nieuwe klus aan te maken</span></h2>");
					out.println("<table>");
						out.println("<tr>");
							out.println("<th>Onderhoudsbeurt</th>");
							out.println("<th>Reparatie</th>");
						out.println("</tr>");
						out.println("<tr>");
							out.println("<td><input type=radio name=type value=onderhoudsbeurt /></td>");
							out.println("<td><input type=radio name=type checked=checked value=reparatie /></td>");
						out.println("</tr>");
						out.println("<tr>");
							out.println("<th>Datum: </th>");
							out.println("<td><input type=text name=datum class=datepicker /></td>");
						out.println("</tr>");
						out.println("</table>");
					out.println("<textarea name=beschrijving placeholder=Omschrijving van de klus></textarea>");
					out.println("<input type=hidden name=autovanklus value=" + deAuto.getID() + " />");
					out.println("<input type=submit name=knop value=nieuw />");		
				}
			%>
		</div>
	</form>
	<p><a href="nieuweklus.jsp">Terug</a></p>
</body>
</html>