<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Overzicht werkplaats" /> 
</jsp:include> 
<%@include file="datepicker.jsp" %>
<%@ page import="java.util.ArrayList,domeinklassen.Klus,domeinklassen.Reparatie,domeinklassen.Onderhoudsbeurt,domeinklassen.Auto" %>
	<form action="OverzichtWerkplaatsPlanningServlet.do" method="post">
		<h1><span>Overzicht werkplaats</span></h1>
		<%@ include file="messages.jsp" %>
		<%	
		Object o = request.getAttribute("gevondenklussen");
		if(o == null){
			%>
			<h2><span>Zoek klussen gepland voor de komende maand<span></h2>
			<p><input type="submit" name="knop" value="Komende maand" /></p>
			<h2><span>Zoek klussen gepland tussen de ingevoerde data<span></h2>
			<p>Na datum:<input type="text" name="nadatum" class="datepicker" /> en voor datum:<input type="text" name="voordatum" class="datepicker" /></p>
			<p><input type="submit" name="knop" value="Zoek" /></p>
			<%
		}
		else{
			%>		
			<h2><span>Geplande klussen</span></h2>
			<%
			Object tekst = request.getAttribute("gezochtop");
			if(tekst != null){
				%>
				<h3><span>Tussen data</span></h3>
				<p><%=(String)tekst%></p>
				<%
			}
			ArrayList<Klus> klussen = (ArrayList<Klus>)o;
			%>
			<table>
			<tr>
				<th>ID</th>
				<th>Kenteken</th>
				<th>Type</th>
				<th>Datum</th>
				<th>Beschrijving</th>
				<th>Status</th>
			</tr>
			<%
			for(Klus k : klussen ){
				%>
				<tr>
					<td><%=k.getID()%></td>
					<td><%=k.getAuto().getKenteken()%></td>
					<%if(k instanceof Reparatie){%>
						<td>Reparatie</td>
					<%}
					else{%>
						<td>Onderhoud</td>
					<%}%>
					<td><%=k.getFormattedDatum()%></td>
					<td><%=k.getBeschrijving()%></td>
					<td><%=k.getStatus()%></td>
				</tr>			
			<%}
		}%>
		</table>
	</form>
<%@ include file="footer.html" %>