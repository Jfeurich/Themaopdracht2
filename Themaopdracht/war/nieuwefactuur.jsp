<%
Object o = request.getAttribute("klanten");
if(request.getAttribute("stap1") == null){
	if(o == null){
		response.sendRedirect("factuur.jsp");
		return;
	}
}
%>
<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Nieuwe factuur" /> 
</jsp:include> 
	<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.Auto,domeinklassen.Klus,domeinklassen.Factuur" %>
	<h1><span>Nieuwe Factuur aanmaken</span></h1>
	<%@ include file="messages.jsp" %>
	<form action="NieuweFactuurServlet.do" method="post">
		<%
		Object fact = request.getAttribute("deFactuur");
		if (fact != null){
			Factuur deFactuur = (Factuur)fact;
			Klus k = deFactuur.getDeKlus();
			Auto a = k.getAuto();
			Klant kl = a.getEigenaar();
			%>
			<h2><span>De factuur</span></h2>
			<p>Klant: <%=kl.getNaam()%></p>
			<p>Auto: <%=a.getMerk()%></p>
			<p>Klus: <%=k.getBeschrijving()%></p>
			<h4><span>Voer kortingspercentage in</span></h4>
			<p><input type="text" name="korting" /></p>
			<p><input type="hidden" name="factuurvoorkorting" value="<%=deFactuur.getID()%>" />
			<input type="submit" name="knop" value="bevestig" /></p>
			<%
		}
		else{
			ArrayList<Klus> klussen = (ArrayList<Klus>)request.getAttribute("klussen");
			if(klussen == null){
				ArrayList<Auto> autos = (ArrayList<Auto>)request.getAttribute("autos");
				if(autos == null){
					ArrayList<Klant> klanten = (ArrayList<Klant>)o;	
					%>
					<h2><span>Haal de autos op van de geselecteerde klant</span></h2>	
					<table>
					<tr>
						<th>Kies</th>
						<th>Naam</th>
						<th>Adres</th>
						<th>Woonplaats</th>
					</tr>
					<%
					boolean eerste=true;
					for(Klant k : klanten){
						%>
						<tr>
							<td><input type="radio" name="gekozenklant"
							<%if(eerste){ %> checked="checked" <% eerste=false;}%>
							value="<%=k.getKlantnummer()%>" /></td>
							<td><%=k.getNaam()%></td>
							<td><%=k.getAdres()%></td>
							<td><%=k.getPlaats()%></td>
						</tr>
					<%}%>
					</table>
					<p><input type="submit" name="knop" value="autos" /></p>
					<%
				}
				else{
					%>
					<h2>Selecteer de auto waar aan is gewerkt</h2>
					<table>
						<tr>
							<th>Kies</th>
							<th>Kenteken</th>
							<th>Merk</th>
							<th>Type</th>
							<th>Eigenaar</th>
						</tr>
					<%
					boolean eerste=true;
					for(Auto a : autos){
						%>
						<tr>
							<td><input type="radio" name="gekozenauto" 
							<%if(eerste){out.println(" checked=checked ");eerste=false;}%>
							value="<%=a.getID()%>" /></td>
							<td><%=a.getKenteken()%></td>
							<td><%=a.getMerk()%></td>
							<td><%=a.getType()%></td>
							<td><%=a.getEigenaar().getNaam()%></td>
						</tr>
					<%}%>
					</table>
					<p><input type="submit" name="knop" value="klus" /></p>
					<%
				}
			}
			else{
				%>
				<h2>Kies de klus waarvan een factuur moet worden aangemaakt</h2>
				<table>
					<tr>
						<th>Kies</th>
						<th>Datum</th>
						<th>Beschrijving</th>
						<th>Status</th>
					</tr>
				<%
				boolean eerste=true;
				for(Klus k : klussen ){
					String status = k.getStatus();
					%>
					<tr>
						<td><input type="radio" name="gekozenklus"
						<%if(eerste){out.println(" checked=checked ");eerste=false;}%>
						value="<%=k.getID()%>" /></td>
						<td><%=k.getFormattedDatum()%></td>
						<td><%=k.getBeschrijving()%></td>
						<td><%=status%></td>
					</tr>
				<%}%>
				</table>
				<p><input type="submit" name="knop" value="nieuw" /></p>
			<%}	
		}%>
	</form>
<%@ include file="footer.html" %>