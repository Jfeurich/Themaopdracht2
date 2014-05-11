<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Auto toevoegen</title>
</head>
<body>
	<p><a href="index.html">Hoofdmenu</a></p>
	<form action="AutoToevoegenServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.Auto" %>
		<div>
			<h2>8: Auto toevoegen</h2>
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
				Object klant = request.getAttribute("deKlant");
				Object lijst = request.getAttribute("klanten");
				if(klant != null){
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
					out.println("</table>");
					out.println("<input type=hidden name=klantnummer value=" + klant + " />");
					out.println("<input type=submit name=knop value=VoegAutoToe />");
				}
				else if(lijst != null){
					ArrayList<Klant> klanten = (ArrayList<Klant>)lijst;	
					out.println("<h2>Kies klant</h2>");
					out.println("<table>");
					out.println("<tr>");
						out.println("<td>X</td>");
						out.println("<td>Klantnummer</td>");
						out.println("<td>Naam</td>");
					out.println("</tr>");
					boolean eerste=true;
					for(Klant k : klanten){
						out.println("<tr>");
							out.println("<td><input type=radio name=autovanklant ");
							if(eerste){out.println("checked=checked ");eerste=false;}
							out.println("value=" + k.getKlantnummer() + " /></td>");
							out.println("<td>" + k.getKlantnummer() + "</td>");
							out.println("<td>" + k.getNaam() + "</td>");
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
	<p><a href="autotoevoegen.jsp">Terug</a></p>
</body>
</html>