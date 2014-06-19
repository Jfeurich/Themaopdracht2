<%
Object o = request.getAttribute("OverzichtFacturenNietBetaald");
Object factuurid = request.getAttribute("factuurid");
if(o == null && factuurid == null){
	response.sendRedirect("factuur.jsp");
	return;
}
%>
<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Onbetaalde facturen" /> 
</jsp:include> 
	<%@ page import="java.util.ArrayList,domeinklassen.Factuur,domeinklassen.Klus" %>
	<%@ include file="messages.jsp" %>
	<form action="FactuurServlet.do" method="post">
	<%
	if(factuurid != null){
		%>
		<h1><span>Factuur betalen</span></h1>
		<p><input type="hidden" name="factuurid" value="<%=(String)factuurid%>" />
		Giro<input type="radio" name="betaalmiddel" value="giro" checked="checked" />
		Pin<input type="radio" name="betaalmiddel" value="pin" />
		Contant<input type="radio" name="betaalmiddel" value="contant" /></p>
		<p><input type="submit" name="knop" value="Bevestig betaling" /></p>
		<%
	}
	else{
		ArrayList<Factuur> facturen = (ArrayList<Factuur>)o;
		%>
		<h1><span>Overzicht onbetaaalde facturen</span></h1>
		<h2><span>Selecteer een factuur om deze te betalen of een herinneringsbrief naar de klant te sturen</span></h2>
		<table>
			<tr>
				<th>Kies</th>
				<th>FactuurID</th>
				<th>AanmaakDatum</th>
				<th>Korting</th>
				<th>Totaal</th>
			</tr>
			<%
			boolean eerste=true;
			for(Factuur f : facturen){
				%>
				<tr>
					<td><input type="radio" name="factuurid" 
					<%if(eerste){ %> checked="checked" <% eerste=false;}%>
					value="<%=f.getID()%>" /></td>
					<td><%=f.getID()%></td>
					<td><%=f.getAanmaakDatumNetjes()%></td>
					<td><%=f.getKorting()%>%</td>
					<td><%=f.getTotaal()%></td>
				</tr>
		<%}%>
		</table>
		<p><input type="submit" name="knop" value="Factuur betalen" /><input type="submit" name="knop" value="Reminder sturen" /></p>
		<%
	}
	%>
	</form>	
<%@ include file="footer.html" %>