<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Overzicht parkeerplaats" /> 
</jsp:include> 
<%@include file="datepicker.jsp" %>
	<%@ page import="java.util.ArrayList, domeinklassen.Reservering" %>
	<form action="ParkeerplaatsOverzichtServlet.do" method="post">	
		<div>
			<h1><span>12: Overzicht parkeerplaats</span></h1>
			<%@ include file="messages.jsp"%>
		</div>
		<div>
			<table>
				<tr>
					<td>Begin datum:</td>
					<td><input type="text" name="begindat" class="datepicker" /></td>
					<td>Eind datum:</td>
					<td><input type="text" name="einddat" class="datepicker" /></td>
					<td><input type="submit" name="knop" value="Checkdatum" /></td>
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
				//werkt niet!
				int teller = 1;
				%><table><%
				//8 rijen
				for(int i = 1; i <= 8; i++){
					out.println("<tr>");
					//5 kolommen
					for(int j = 1; j <= 5; j++){
						boolean b = false;
						for(Reservering r : deReserveringen){
							//check of deze plek bij de reserveringen zit
							if(r.getDeParkeerplek() == teller){
								b = true;
							}
						}
						%><td><%
						if(b){
							%>BEZET<%
						}
						else{
							%><input type="submit" name="knop" value="<%=teller%>" /><%
						}
						%></td><%
						teller++;
					}
					%></td><%
				}
				%></table><%
			}
			else{
				%><p>Voer twee datums in om te checken welke parkeerplaatsen beschikbaar zijn.</p><%
			}
			%>
		</div>
	</form>
<%@ include file="footer.html" %>