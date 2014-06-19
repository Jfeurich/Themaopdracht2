<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Auto toevoegen" /> 
</jsp:include> 
	<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.User,java.sql.Connection,database.ConnectDBKlant" %>
	<h1><span>Een nieuwe auto toevoegen</span></h1>
	<%@ include file="messages.jsp" %>
	<form action="AutoToevoegenServlet.do" method="post">
		<% 	
		User u = (User)session.getAttribute("gebruiker");
		Object klant = request.getAttribute("deKlant");
		if(u.getType() == 3){
			klant = u.getDeKlant().getKlantnummer();
		}
		if(klant != null){
			%>
			<h2><span>Voer de gegevens van de auto in en klik op "VoegAutoToe"</span></h2>
			<table>
				<tr>
					<th>Kenteken</th>
					<th>Merk</th>
					<th>Type</th>
				</tr>
				<tr>
					<td><input type="text" name="kenteken" /></td>
					<td><input type="text" name="merk" /></td>
					<td><input type="text" name="type" /></td>
				</tr>
			</table>
			<p><input type="hidden" name="klantnummer" value="<%=klant%>" />
			<input type="submit" name="knop" value="VoegAutoToe" /></p>
			<% 
		}
		else{
			Connection con = (Connection)session.getAttribute("verbinding");
			ConnectDBKlant kcon = new ConnectDBKlant(con);
			ArrayList<Klant> klanten = kcon.getKlanten();	
			%>
			<h2><span>Kies de eigenaar van de auto</span></h2>
			<table>
			<tr>
				<th>X</th>
				<th>Klantnummer</th>
				<th>Naam</th>
			</tr>
			<%
			boolean eerste=true;
			for(Klant k : klanten){
				%>
				<tr>
					<td><input type="radio" name="autovanklant"
					<%if(eerste){%> checked="checked"  <% eerste=false;} %>
					value="<%=k.getKlantnummer()%>" /></td>
					<td><%=k.getKlantnummer()%></td>
					<td><%=k.getNaam()%></td>
				</tr>
				<%
			}
			%>
			</table>
			<p><input type="submit" name="knop" value="KiesKlant" /></p>
			<%
		}
		%>
	</form>
<%@ include file="footer.html" %>