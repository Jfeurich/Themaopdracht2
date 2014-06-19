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
	<form action="FactuurServlet.do" method="post">
	<%@ page import="java.util.ArrayList,domeinklassen.Factuur,domeinklassen.Klus" %>
	<%@ include file="messages.jsp" %>
	<%
	if(factuurid != null){
		%>
		<h1><span>18: Factuur betalen:</span></h1>
		<p><input type="hidden" name="factuurid" value="<%=(String)factuurid%>" />
		<input type="radio" name="betaalmiddel" value="giro" checked="checked" >Giro</>
		<input type="radio" name="betaalmiddel" value="pin">Pin</>
		<input type="radio" name="betaalmiddel" value="contant">Contant</>
		<input type="submit" name="knop" value="Bevestig betaling" /></p>
		<%
	}
	else{
		ArrayList<Factuur> facturen = (ArrayList<Factuur>)o;
		%>
		<h1><span>21: De onbetaalde facturen zijn:</span></h1>
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
		<p><input type="submit" name="knop" value="Factuur betalen" /></p>
		<p><input type="submit" name="knop" value="Reminder sturen" /></p>
		<%
	}
	%>
	</form>	
<%@ include file="footer.html" %>