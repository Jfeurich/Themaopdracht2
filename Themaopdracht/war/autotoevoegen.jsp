<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Auto toevoegen" /> 
</jsp:include> 
	<form action="AutoToevoegenServlet.do" method="post">
	<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.User,java.sql.Connection,database.ConnectDBKlant" %>
		<h1><span>8: Auto toevoegen</span></h1>
		<%@ include file="messages.jsp" %>
		<% 	
		User u = (User)session.getAttribute("gebruiker");
		Object klant = request.getAttribute("deKlant");
		if(u.getType() == 3){
			klant = u.getDeKlant().getKlantnummer();
		}
		if(klant != null){
			%>
			<h2><span>Voer de gegevens van de auto in</span></h2>
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
			<input type="hidden" name="klantnummer" value="<%=klant%>" />
			<input type="submit" name="knop" value=VoegAutoToe />
			<% 
		}
		else{
			Connection con = (Connection)session.getAttribute("verbinding");
			ConnectDBKlant kcon = new ConnectDBKlant(con);
			ArrayList<Klant> klanten = kcon.getKlanten();	
			%>
			<h2><span>Kies klant</span></h2>
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
					<%if(eerste){out.println(" checked=checked ");eerste=false;} %>
					value="<%=k.getKlantnummer()%>" /></td>
					<td><%=k.getKlantnummer()%></td>
					<td><%=k.getNaam()%></td>
				</tr>
				<%
			}
			%>
			</table>
			<input type=submit name=knop value=KiesKlant />
			<%
		}
		%>
	</form>
<%@ include file="footer.html" %>