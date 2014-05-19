<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Nieuwe herinneringsbrief</title>
</head>
<body>
	<p><a href="index.html">Hoofdmenu</a></p>
	<form action="BriefServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.Herinneringsbrief" %>
		<div>
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
				Object klant = request.getAttribute("deKlant");
				Object lijst = request.getAttribute("klanten");
				if(klant != null){
					out.println("<h2>23: Maak een nieuwe herinneringsbrief</h2>");
					out.println("<textarea name=reden placeholder=RedenVoorBrief ></textarea>");
					out.println("<input type=hidden name=klantnummer value=" + klant + " />");
					out.println("<input type=submit name=knop value=NieuweBrief />");
				}
				else if(lijst != null){
					ArrayList<Klant> klanten = (ArrayList<Klant>)lijst;	
					out.println("<h2>Kies klant</h2>");
					out.println("<table>");
					out.println("<tr>");
						out.println("<td>X</td>");
						out.println("<td>Klantnummer</td>");
						out.println("<td>Naam</td>");
						out.println("<td>Laatste bezoek</td>");
						out.println("<td>Laatste brief</td>");
					out.println("</tr>");
					boolean eerste=true;
					for(Klant k : klanten){
						out.println("<tr>");
							out.println("<td><input type=radio name=gekozenklant ");
							if(eerste){out.println("checked=checked ");eerste=false;}
							out.println("value=" + k.getKlantnummer() + " /></td>");
							out.println("<td>" + k.getKlantnummer() + "</td>");
							out.println("<td>" + k.getNaam() + "</td>");
							out.println("<td>" + k.getLaatsteBezoek() + "</td>");
							out.println("<td>" + k.getLaatsteBrief() + "</td>");
						out.println("</tr>");
					}
					out.println("</table>");
					out.println("<input type=submit name=knop value=KiesKlant />");	
				}
				else{
					out.println("<input type=submit name=knop value=ZoekKlanten />");				
				}
			%>
		</div>
	</form>
</body>
</html>