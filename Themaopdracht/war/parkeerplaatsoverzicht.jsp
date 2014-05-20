<jsp:include page="header.jsp" > 
 <jsp:param name="titel" value="Overzicht parkeerplaats" /> 
</jsp:include> 
	<%@ page import="java.util.ArrayList, domeinklassen.Reservering" %>
	<form action="ParkeerplaatsOverzichtServlet.do" method="post">	
		<div>
			<h1><span>12: Overzicht parkeerplaats</span></h1>
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
			%>
		</div>
		<div>
			<table>
				<tr>
					<td>Begin datum:</td>
					<td><input type=text name=begindat /></td>
					<td>Begin datum:</td>
					<td><input type=text name=einddat /></td>
					<td><input type=submit name=knop value="Checkdatum" /></td>
				</tr>
			</table><br />
			<%
				//De datum waarop gezocht is weergeven
			%>
		</div>
		<div>
			<%	
				ArrayList<Reservering> deReserveringen = (ArrayList<Reservering>) request.getSession().getAttribute("gevondenReserveringen");
				if(deReserveringen != null){
					System.out.println("stap1");
					//werkt niet!
					int rij = (int) getServletContext().getAttribute("parkeerplaatsRij");
					System.out.println("rij: " + rij);
					int kolom = (int) getServletContext().getAttribute("parkeerplaatsKolom");
					System.out.println("kolom: " + kolom);
					int teller = 0;
					out.println("<table>");
					for(int i = 1; i <= rij; i++){
						out.println("<tr>");
						for(int j = 1; j <= kolom; j++){
							boolean b = false;
							for(Reservering r : deReserveringen){
								//check of deze plek bij de reserveringen zit
								if(r.getDeParkeerplek() == teller){
									b = true;
								}
							}
							out.println("<td>");
							if(b){
								out.println("BEZET");
							}
							else{
								out.println("<input type=submit name=knop value=" + teller + " />");
							}
							out.println("</td>");
							System.out.println("teller: " + teller);
							teller++;
						}
						out.println("</td>");
					}
					out.println("</table>");
				}
				else{
					out.println("Voer twee datums in om te checken welke parkeerplaatsen beschikbaar zijn.");
				}
			%>
		</div>
	</form>
</body>
</html>