<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Nieuwe Reservering</title>
</head>
<body>
	<p><a href="index.html">Hoofdmenu</a></p>
	<form action="NieuweReserveringServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.Auto" %>
		<div>
			<h2>3: Nieuwe Reservering aanmaken</h2>
			<%
				Object error =  request.getAttribute("error");
				if(error != null){
					out.println("<h3>Error!</h3>");
					out.println(error);
				}
				else{
					Object msg = request.getAttribute("msg");
					if(msg != null){
						out.println("<h3>" + msg + "</h3>");
					}
				}
				Object auto = request.getAttribute("deAuto");
				if (auto != null){
					Auto deAuto = (Auto)auto;
					out.println("<h3>Reservering voor:</h3>");
					out.println("<p>" + deAuto.toString() + "</p>");
					out.println("<table>");
						out.println("<tr>");
							out.println("<td>Begindatum</td>");
							out.println("<td><input type=text name=begindatum placeholder=dd-mm-jjjj /></td>");
						out.println("/<tr>");
						out.println("<tr>");
							out.println("<td>Einddatum</td>");
							out.println("<td><input type=text name=einddatum placeholder=dd-mm-jjjj /></td>");
						out.println("/<tr>");
					out.println("</table>");
					out.println("<input type=hidden name=deAuto value=" + deAuto.getID() +" />");
					out.println("<input type=submit name=knop value=maakReservering />");
				}
				else{
					ArrayList<Auto> autos = (ArrayList<Auto>)request.getAttribute("autos");
					if(autos != null){
						out.println("<h3>Selecteer de auto waar de reservering voor wordt gemaakt</h3>");
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
							out.println("<h3>Haal de autos op van de geselecteerde klant</h3>");	
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
							out.println("<h3>Haal eerst gegevens van de klanten op</h3>");
							out.println("<input type=submit name=knop value=klanten");
						}
					}
				}
			%>
		</div>
	</form>
</body>
</html>