<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
//GITHUB Y U NOT WORK
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Nieuwe Factuur</title>
</head>
<body>
	<form action="NieuweFactuurServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.Auto,domeinklassen.Klus" %>
		<div>
			<h2>Nieuwe Factuur aanmaken</h2>
			<%
				Object error =  request.getAttribute("error");
				if(error != null){
					out.println("<h2>Foutmelding</h2>");
					out.println(error);
				}
				else{
					Object msg = request.getAttribute("msg");
					if(msg != null){
						out.println(msg);
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
								for(Klant k : klanten){
									out.println("<tr>");
										out.println("<td><input type=radio name=gekozenklant value=" + k.getKlantnummer() + " /></td>");
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
							for(Auto a : autos){
								out.println("<tr>");
									out.println("<td><input type=radio name=gekozenauto value=" + a.getID() + " /></td>");
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
							out.println("<td>Kies</td>");
							out.println("<td>Datum</td>");
							out.println("<td>Beschrijving</td>");
							out.println("<td>Status/td>");
						out.println("</tr>");
						for(Klus k : klussen ){
							out.println("<tr>");
								out.println("<td><input type=radio name=gekozenklus value=" + k.getID() + " /></td>");
								out.println("<td>" + k.getDatum() + "</td>");
								out.println("<td>" + k.getBeschrijving() + "</td>");
								out.println("<td>" + k.getStatus() + "</td>");
							out.println("</tr>");
						}
						out.println("</table>");
						out.println("<input type=submit name=knop value=nieuw />");
						
					}
					else{
						out.println("<h2>Voer kortingspercentage in</h2>");
						out.println("<input type=text name= />");
					}
					
				}
				else{
					Klus deKlus = (Klus)gekozen;
					out.println("<h2>Is dit de klus waarvoor een factuur moet worden aangemaakt?</h2>");
					out.println("<h1>Hier moet ik nog een manier gaan vinden om de factuur af te drukken</h1>");
					out.println("<input type=submit name=knop value=bevestig />");
				}
			%>
		</div>
	</form>
</body>
</html>