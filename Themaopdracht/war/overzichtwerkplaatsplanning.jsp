<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Overzicht werkplaats" /> 
</jsp:include> 
	<%@ page import="java.util.ArrayList, domeinklassen.Klus" %>
	<form action="OverzichtWerkplaatsPlanningServlet.do" method="post">
		<h1><span>12: Overzicht werkplaats</span></h1>
		<%@ include file="messages.jsp" %>
		<%	
		Object o = request.getAttribute("gevondenKlussen");
		if(o == null){
			%>
			<h2><span>Zoek klussen gepland na vandaag<span></h2>
			<p><input type="submit" name="knop" value="zoek" /></p>
			<%
		}
		else{
			ArrayList<Klus> deKlussen = (ArrayList<Klus>)o;
			%>
			<h2><span> Ingeplande klussen </span></h2>
			<table>
			<tr>
				<th>Datum</th>
				<th>Beschrijving</th>
				<th>Auto</th>
			</tr>
			<tr>
			<% 
			for(Klus k : deKlussen){
				%>
				<td><%=k.getDatum()%></td>
				<td><%=k.getBeschrijving()%></td>
				<td><%=k.getAuto().getKenteken()%></td>
				<%
			}
			%>
			</tr>
			</table>
			<%
		}
		%>
	</form>
	<p><a href="nieuweklus.jsp">Plan een nieuwe klus in</a></p>
</body>
</html>