<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Auto toevoegen</title>
</head>
<body>
	<form action="AutoToevoegenServlet.do" method="post">
		<%@ page import="java.util.ArrayList, domeinklassen.Klant" %>
		<div>
			<h2>Auto toevoegen</h2>
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
				if(request.getAttribute("stap1") != null){
					ArrayList<Klant> klanten = (ArrayList<Klant>)request.getAttribute("klanten");	
					out.println("<h2>Kies klant</h2>");
					out.println("<table>");
					out.println("<tr>");
						out.println("<td>X</td>");
						out.println("<td>Naam</td>");
						out.println("<td>Achternaam</td>");
						out.println("<td>Klantnummer</td>");
					out.println("</tr>");
					for(Klant k : klanten){
						out.println("<tr>");
							out.println("<td><input type=checkbox name=gekozenKlant value=" + p.getKlantNr() + " /></td>");
							out.println("<td>" + p.getNaam() + "</td>");
							out.println("<td>" + p.getAchternaam() + "</td>");
						out.println("</tr>");
					}
					out.println("</table>");
					out.println("<input type=hidden name=autovanklant value=" + klanten.getID() + " />");
					out.println("<input type=submit name=knop value=KiesKlant />");	
				}
				else if(request.getAttribute("stap2") != null){
					Auto deAuto = (Auto) request.getAttribute("deAuto");
					out.println(deBestelling.toString() + "<br />");
					out.println("<table>");
						out.println("<tr>");
							out.println("<td>Kenteken</td>");
							out.println("<td>Merk</td>");
							out.println("<td>Type</td>");
						out.println("</tr>");	
						out.println("<tr>");
							out.println("<td><input type=text name=kenteken /></td>");
							out.println("<td><input type=text name=merk /></td>");
							out.println("<td><input type=text name=type /></td>");
						out.println("</tr>");
						}
					out.println("</table>");
					out.println("<input type=submit name=knop value=Done />");
				}
				else{
					out.println("<input type=submit name=knop value=VoegAutoToe />");
				}
			%>
		</div>
	</form>
</body>
</html>