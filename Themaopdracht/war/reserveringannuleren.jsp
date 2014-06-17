<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Reservering Annuleren" /> 
</jsp:include> 
	<form action="ReserveringAnnulerenServlet.do" method="get"> 
	<%@ page import="java.util.ArrayList,domeinklassen.Reservering, domeinklassen.Auto, domeinklassen.User" %>
		<h1>Een Reservering Annuleren</h1>
		<%@ include file="messages.jsp" %>
		<%	
		Object o = request.getAttribute("reservering");
		if(o != null){
			Reservering r = (Reservering) o;
		%>
			<h2>Bevestig annuleren van de reservering</h2>
			<div>
				<table>
					<tr>
						<th>Begin datum</th>
						<th>Eind datum</th>
						<th>Parkeerplek</th>
						<th>Kenteken van Auto</th>
					</tr>
					<tr>
						<td><%= r.getBegDat()%></td>
						<td><%= r.getEindDat() %></td>
						<td><%= r.getDeParkeerplek() %></td>
						<td><%= r.getAuto().getKenteken() %></td>
					</tr>
				</table>
				<input type="submit" value="bevestig" name="knop">
			</div>
		<%			
		}
		else{
			Object reserveringen = request.getAttribute("deReserveringen");
			if(reserveringen != null){
				ArrayList<Reservering> deReserveringen = (ArrayList<Reservering>) reserveringen;
			%>
				<h2></h2>
				<div>
					<table>
						<tr>
							<th>X</th>
							<th>Begin datum</th>
							<th>Eind datum</th>
							<th>Parkeerplek</th>
							<th>Kenteken van Auto</th>
						</tr>
						<%
						for(Reservering r: deReserveringen){
							boolean eerste=true;
						%>
							<tr>
								<td><input type="radio" name="gekozenReservering" 
								<%if(eerste){out.println("checked=checked ");eerste=false;}%>
								value="<%=r.getID()%>" /></td>
								<td><%= r.getBegDat()%></td>
								<td><%= r.getEindDat() %></td>
								<td><%= r.getDeParkeerplek() %></td>
								<td><%= r.getAuto().getKenteken() %></td>
							</tr>
						<%
						}
						%>	
					</table>
					<input type="submit" value="Kies reservering" name="knop">
				</div>
			<%
			}
			else{
				
			}
		}
		//Object o = request.getAttribute("reserveringen");
		if(o == null){
			%>
			<h2>Zoek een reservering via auto ID</h2>
			<p><input type="text" name="zoekviaID" /></p>
			<p><input type="submit" value="zoek" name="knop"></input></p>
			<%
		}
		else{
			ArrayList<Reservering> reserveringen = (ArrayList<Reservering>)o;
			%>
			<h2><span>Kies de reservering die geannuleerd moet worden:</span></h2>
			<table>
			<tr>
				<th>Kies</th>
				<th>De parkeerplek</th>
				<th>Begin datum</th>
				<th>Eind datum</th>
			</tr>
			<%
			boolean eerste=true; 
	 		for(Reservering r : reserveringen){
	 			%>
				<tr>
					<td><input type="radio" name="gekozenreservering" 
					<%if(eerste){out.println(" checked=checked ");eerste=false;}%>
					value="<%=r.getID()%>" /></td>
					<td><%=r.getDeParkeerplek()%></td>
					<td><%=r.getBegDatNetjes()%></td>
					<td><%=r.getEindDatNetjes()%></td>
				</tr>
				<%
			} 
			%> 
	 		</table>
			<input type="submit" name="knop" value="annuleer" />	
			<%
		} 
		%>
	</form>
<%@ include file="footer.html" %>