<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Reservering bevestigen" /> 
</jsp:include> 
	<%@ page import="java.util.ArrayList,domeinklassen.Reservering" %>
	<h1><span>Een Parkeer Reservering Bevestigen</span></h1>
	<%@ include file="messages.jsp" %>
	<form action="ReserveringBevestigenServlet.do" method="get"> 
		<%			
		Object o = request.getAttribute("reserveringen");
		if(o == null){
			%>
			<h2><span>Zoek een reservering via auto ID</span></h2>
			<p><input type="text" name="zoekviaID" /></p>
			<p><input type="submit" value="zoek" name="knop"></input></p>
			<%
		}
		else{
			ArrayList<Reservering> reserveringen = (ArrayList<Reservering>)o;
			%>
			<h2><span>Kies een reservering</span></h2>
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
					<%if(eerste){ %> checked="checked" <% eerste=false;}%>
					value="<%=r.getID()%>" /></td>
					<td><%=r.getDeParkeerplek()%></td>
					<td><%=r.getBegDatNetjes()%></td>
					<td><%=r.getEindDatNetjes()%></td>
				</tr>
				<%
			} 
			%>
	 		</table>
			<p><input type="submit" name="knop" value="bevestigen" /></p> 
			<%
		}
		%>
	</form>
<%@ include file="footer.html" %>