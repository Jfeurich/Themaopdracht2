<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Overzicht BTW" /> 
</jsp:include> 
	<form action="BTWServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Factuur,domeinklassen.Klus,domeinklassen.Auto,domeinklassen.Klant" %>
		<div>
		<h1><span>22: Overzicht BTW</span></h1>
		<%@ include file="messages.jsp" %>
		<h2><span>Geef aan voor welke kwartalen u een overzicht wilt</span></h2>
		<p class="kop" >Jaar: <input type="text" name="beginjaar" /></p>
		<p>
			<span><input type="radio" name="beginkwartaal" checked="checked" value="1" /></span>
			<span><input type="radio" name="beginkwartaal" value="2" /></span>
			<span><input type="radio" name="beginkwartaal" value="3" /></span>
			<span><input type="radio" name="beginkwartaal" value="4" /></span>
		</p>
		<p>t/m</p>
		<p class="kop" >Jaar:<input type="text" name="eindjaar" /></p>
		<p>
			<span><input type="radio" name="eindkwartaal" checked="checked" value="1" /></span>
			<span><input type="radio" name="eindkwartaal" value="2" /></span>
			<span><input type="radio" name="eindkwartaal" value="3" /></span>
			<span><input type="radio" name="eindkwartaal" value="4" /></span>
		</p>
		<input type="submit" name="knop" value="overzicht" />
		</div>
	</form>
	<div>
	<%
	ArrayList<Factuur> facturen =(ArrayList<Factuur>)request.getAttribute("facturen");
	if(facturen != null){
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
		<%
	}
	%>
	</div>
<%@ include file="footer.html" %>