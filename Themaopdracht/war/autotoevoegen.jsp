<jsp:include page="header.jsp" > 
 <jsp:param name="titel" value="Auto toevoegen" /> 
</jsp:include> 
	<form action="AutoToevoegenServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Klant" %>
		<div>
			<h1><span>8: Auto toevoegen</span></h1>
			<% 		
				Object error =  request.getAttribute("error");
				if(error != null){
					out.println("<h3><span>Error!</span></h3>");
					out.println("<p name=error class=error >" + error + "</p>");
				}
				else{
					Object msg = request.getAttribute("msg");
					if(msg != null){
						out.println("<h3 name=msg class=msg ><span>" + msg + "<span></h3>");
					}
				}
				Object klant = request.getAttribute("deKlant");
				Object lijst = request.getAttribute("klanten");
				if(klant != null){
					out.println("<h2><span>Voer de gegevens van de auto in</span></h2>");
					out.println("<table>");
						out.println("<tr>");
							out.println("<th>Kenteken</th>");
							out.println("<th>Merk</th>");
							out.println("<th>Type</th>");
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
					out.println("<h2><span>Kies klant</span></h2>");
					out.println("<table>");
					out.println("<tr>");
						out.println("<th>X</th>");
						out.println("<th>Klantnummer</th>");
						out.println("<th>Naam</th>");
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
					out.println("<h2><span>Haal eerst de gegevens van de klanten op</span></h2>");
					out.println("<input type=submit name=knop value=ZoekKlanten />");				
				}
			%>
		</div>
	</form>
	<p><a href="autotoevoegen.jsp">Terug</a></p>
</body>
</html>