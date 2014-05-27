<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Auto toevoegen" /> 
</jsp:include> 
	<form action="AutoToevoegenServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Klant" %>
		<div>
			<h1><span>8: Auto toevoegen</span></h1>
			<%@ include file="messages.jsp" %>
			<% 	
			Object klant = request.getAttribute("deKlant");
			Object lijst = request.getAttribute("klanten");
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
			else if(lijst != null){
				ArrayList<Klant> klanten = (ArrayList<Klant>)lijst;	
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
						<td><input type=radio name=autovanklant 
						<%if(eerste){out.println("checked=checked ");eerste=false;} %>
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
			else{
				%>
				<h2><span>Haal eerst de gegevens van de klanten op</span></h2>
				<input type=submit name=knop value=ZoekKlanten />
				<%			
			}
			%>
		</div>
	</form>
<p><a href="autotoevoegen.jsp">Terug</a></p>
<%@ include file="footer.html" %>