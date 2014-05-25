<jsp:include page="header.jsp" > 
 <jsp:param name="titel" value="Nieuwe herinneringsbrief" /> 
</jsp:include> 
	<h1><span>23: Nieuwe herinneringsbrief</span></h1>
	<form action="BriefServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.Herinneringsbrief" %>
		<div>
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
				Object klant = request.getAttribute("deKlant");
				Object lijst = request.getAttribute("klanten");
				if(klant != null){
					out.println("<h2><span>Maak de brief</span></h2>");
					out.println("<textarea name=reden placeholder=RedenVoorBrief ></textarea>");
					out.println("<input type=hidden name=klantnummer value=" + klant + " />");
					out.println("<input type=submit name=knop value=NieuweBrief />");
				}
				else if(lijst != null){
					ArrayList<Klant> klanten = (ArrayList<Klant>)lijst;	
					out.println("<h2><span>Kies klant</span></h2>");
					out.println("<table>");
					out.println("<tr>");
						out.println("<th>X</th>");
						out.println("<th>Klantnummer</th>");
						out.println("<th>Naam</th>");
						out.println("<th>Laatste bezoek</th>");
						out.println("<th>Laatste brief</th>");
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