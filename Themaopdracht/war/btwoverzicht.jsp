<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Overzicht BTW" /> 
</jsp:include> 
	<%@ page import="java.util.ArrayList,domeinklassen.Factuur,domeinklassen.Klus,domeinklassen.Auto,domeinklassen.Klant" %>
	<h1><span>Overzicht BTW</span></h1>
	<%@ include file="messages.jsp" %>
	<form action="BTWServlet.do" method="post">
		<h2><span>Geef aan voor welke kwartalen u een overzicht wilt</span></h2>
		<h4><span>Van:</span></h4>
		<table>
			<tr>
				<th>Jaar</th><td class="hoofdcel"><input type="text" name="beginjaar" /></td>
				<td class="tussenkop">1e kwartaal</td><td><input type="radio" name="beginkwartaal" checked="checked" value="1" /></td>
				<td class="tussenkop">2e kwartaal</td><td><input type="radio" name="beginkwartaal" value="2" /></td>
				<td class="tussenkop">3e kwartaal</td><td><input type="radio" name="beginkwartaal" value="3" /></td>
				<td class="tussenkop">4e kwartaal</td><td><input type="radio" name="beginkwartaal" value="4" /></td>
			</tr>
		</table>		
		<h4><span>Tot:</span></h4>
		<table>
			<tr>
				<th>Jaar</th><td class="hoofdcel" ><input type="text" name="eindjaar" /></td>
				<td class="tussenkop">1e kwartaal</td><td><input type="radio" name="eindkwartaal" checked="checked" value="1" /></td>
				<td class="tussenkop">2e kwartaal</td><td><input type="radio" name="eindkwartaal" value="2" /></td>
				<td class="tussenkop">3e kwartaal</td><td><input type="radio" name="eindkwartaal" value="3" /></td>
				<td class="tussenkop">4e kwartaal</td><td><input type="radio" name="eindkwartaal" value="4" /></td>
			</tr>
		</table>
		<p><input type="submit" name="knop" value="overzicht" /></p>
	</form>
	<div>
	<%
	Object o = request.getAttribute("facturen");
	if(o != null){
		ArrayList<Factuur> facturen =(ArrayList<Factuur>)o;
		%>
		<h2><span>Alle betaalde facturen</span></h2>
		<table>
		<tr>
			<th>Betaaldatum</th>
			<th>Klant</th>
			<th>Bedrag</th>
			<th>%BTW</th>
			<th>BTW</th>
		</tr>
		<%
		for(Factuur f : facturen){
			%>
			<tr>
				<td><%=f.getBetaalDatumNetjes()%></td>
				<td><%=f.getDeKlus().getAuto().getEigenaar().getNaam()%></td>
				<td><%=f.getTotaal()%></td>
				<td>21%</td>
				<td><%=f.getBTW(21)%></td>
			</tr>
			<%
		}
		%>
		</table>
	<%}%>
	</div>
<%@ include file="footer.html" %>