<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Nieuwe herinneringsbrief" /> 
</jsp:include> 
	<form action="BriefServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.Herinneringsbrief" %>
		<h1><span>23: Nieuwe herinneringsbrief</span></h1>
		<%@ include file="messages.jsp" %>
		<%
		Object klant = request.getAttribute("deKlant");
		Object lijst = request.getAttribute("klanten");
		if(klant != null){
			%>
			<h2><span>Maak de brief</span></h2>
			<textarea name="reden" placeholder="RedenVoorBrief" ></textarea>
			<input type="hidden" name="klantnummer" value="<%=(String)klant%>" />
			<input type="submit" name="knop" value="NieuweBrief" />
			<%
		}
		else if(lijst != null){
			ArrayList<Klant> klanten = (ArrayList<Klant>)lijst;	
			%>
			<h2><span>Kies klant</span></h2>
			<table>
			<tr>
				<th>X</th>
				<th>Klantnummer</th>
				<th>Naam</th>
				<th>Laatste bezoek</th>
				<th>Laatste brief</th>
			</tr>
			<%
			boolean eerste=true;
			for(Klant k : klanten){
				%>
				<tr>
					<td><input type="radio" name="gekozenklant" 
					<%if(eerste){out.println("checked=checked ");eerste=false;}%>
					value="<%=k.getKlantnummer()%>" /></td>
					<td><%=k.getKlantnummer()%></td>
					<td><%=k.getNaam()%></td>
					<td><%=k.getLaatsteBezoek()%></td>
					<td><%=k.getLaatsteBrief()%></td>
				</tr>
			<%}%>
			</table>
			<input type="submit" name="knop" value="KiesKlant" />
			<%
		}
		else{
			%>
			<p><input type="submit" name="knop" value="ZoekKlanten" /></p>
			<%
		}
		%>
	</form>
<%@ include file="footer.html" %>