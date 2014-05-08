<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Nieuwe klus</title>
</head>
<body>
	<form action="NieuweKlusServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.Auto" %>
		<div>
			<h2>Nieuwe klus aanmaken</h2>
			<% 				
				Object error =  request.getAttribute("error");
				if(error != null){
					out.println("<h2>Error!</h2>");
					out.println(error);
				}
				else{
					Object msg = request.getAttribute("msg");
					if(msg != null){
						out.println(msg);
					}
				}
				Object gekozen = request.getAttribute("deAuto");
				if(gekozen == null){
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
					out.println("<h2>Vul het formulier in en klik op 'voeg toe' om de nieuwe klus aan te maken</h2>");
					out.println("<table>");
						out.println("<tr>");
							out.println("<td>Onderhoudsbeurt</td>");
							out.println("<td>Reparatie</td>");
						out.println("</tr>");
						out.println("<tr>");
							out.println("<td><input type=radio name=type value=onderhoudsbeurt /></td>");
							out.println("<td><input type=radio name=type value=reparatie /></td>");
						out.println("</tr>");
						out.println("<tr>");
							out.println("<td>Datum: </td>");
							out.println("<td><input type=text name=datum placeholder=dd-mm-jjjj /></td>");
						out.println("</tr>");
						out.println("</table>");
					out.println("<textarea name=beschrijving placeholder=Omschrijving van de klus></textarea>");
					out.println("<input type=hidden name=autovanklus value=" + deAuto.getID() + " />");
					out.println("<input type=submit name=knop value=nieuw />");		
				}
			%>
		</div>
	</form>
</body>
</html>