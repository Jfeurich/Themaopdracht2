<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Homepage" /> 
</jsp:include> 
	<h1><span>Index van alle pagina's</span></h1>
	<div>
		<%@ page import="domeinklassen.User, domeinklassen.Auto, domeinklassen.Klant, java.text.SimpleDateFormat, java.util.ArrayList" %>
		<%@ include file="messages.jsp" %>
	</div>
	<%
	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	User u = (User) request.getSession().getAttribute("gebruiker");
	if(u.getType() == 3){
	%>
		<h2>Mijn herinneringen</h2>
		<div>
			De volgende auto's hebben onderhoud nodig: <br />
			<%
			ArrayList<Auto> autosOnderhoudNodig = u.getDeKlant().onderhoudNodig();
			if(autoOnderhoudNodig.size() == 0){
				%>Geen van uw auto's hebben onderhoud nodig<%
			}
			else{
				%>
				<table>
					<tr>
						<th>Kenteken</th>
						<th>Merk</th>
						<th>Type</th>
						<th>Laatste klus</th>
					</tr>
				<%
				for(Auto a: autosOnderhoudNodig){
				%>
					<tr>
						<td><%=a.getKenteken()%></td>
						<td><%=a.getMerk()%></td>
						<td><%=a.getType()%></td>
						<td><%=df.format(a.laatsteKlus()) %></td>
					</tr>
				</table>
				<%
			}
			%>
		</div>
		<div>
			
		</div>
		<h2><span>Mijn auto's</span></h2>
		<div>
			<table>
			<tr>
				<td>Kenteken</td>
				<td>Merk</td>
				<td>Type</td>
				<td>Laatste klus</td>
			</tr>
			<%
			for(Auto a : u.getDeKlant().getAutos()){
				%>
				<tr>
					<td><%=a.getKenteken()%></td>
					<td><%=a.getMerk()%></td>
					<td><%=a.getType()%></td>
					<td><%=df.format(a.laatsteKlus()) %></td>
				</tr>
			<%}%>
			</table>
		</div>
	<%
	}
	%>
<%@ include file="footer.html" %>