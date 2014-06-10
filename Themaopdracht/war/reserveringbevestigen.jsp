<%@ include file="redirect.jsp" %>
<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Reservering bevestigen" /> 
</jsp:include> 
	<form action="ReserveringBevestigenServlet.do" method="get"> 
	<%@ page import="java.util.ArrayList,domeinklassen.Reservering" %>
		<h1>Een Parkeer Reservering Bevestigen</h1>
		<%@ include file="messages.jsp" %>
		<%			
		Object o = request.getAttribute("reserveringen");
		if(o == null){
			%>
			<h2>Zoek een reservering via auto ID</h2>
			<p><input type=text name=zoekviaID /></p>
			<p><input type=submit value=zoek name=knop></input></p>
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
			<input type="submit" name="knop" value="bevestigen" /> 
			<%
		}
		%>
	</form>
<%@ include file="footer.html" %>