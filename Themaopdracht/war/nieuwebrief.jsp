<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Nieuwe herinneringsbrief" /> 
</jsp:include> 
	<form action="BriefServlet.do" method="post">
		<%@ page import="java.util.ArrayList,java.sql.Connection,database.ConnectDBKlant,domeinklassen.Klant" %>
		<h1><span>23: Nieuwe herinneringsbrief</span></h1>
		<%@ include file="messages.jsp" %>
		<%
		Object klant = request.getAttribute("deKlant");
		if(klant != null){
			Klant deKlant = (Klant)klant;
			%>
			<h2><span>Schrijf de brief en klik op "NieuweBrief" om hem te versturen</span></h2>
			<textarea name="reden">Beste <%=deKlant.getNaam()%>, 

Het is ons op gevallen dat u sinds <%=deKlant.getLaatsteBezoek()%> niet meer langs bent geweest bij ATD. 
Hopelijk kunnen wij u in de toekomst weer van dienst zijn.

Met vriendelijke groet, 

De medewerkers van AutoTotaalDienst Utrecht
			</textarea>
			<input type="hidden" name="klantnummer" value="<%=deKlant.getKlantnummer()%>" />
			<input type="submit" name="knop" value="NieuweBrief" />
			<%
		}
		else{
			Connection con = (Connection)session.getAttribute("verbinding");
			ConnectDBKlant klantcon = new ConnectDBKlant(con);	
			ArrayList<Klant> klanten = klantcon.getKlanten();
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
		%>
	</form>
<%@ include file="footer.html" %>