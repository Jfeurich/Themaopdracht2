<%
Object o = request.getAttribute("klanten");
if(request.getAttribute("stap1") == null && o == null){
	response.sendRedirect("klus.jsp");
	return;
}
%>
<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Nieuwe klus" /> 
</jsp:include> 
<%@include file="datepicker.jsp" %>
	<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.Auto" %>
	<h1><span>Nieuwe klus aanmaken</span></h1>
	<%@ include file="messages.jsp" %>
	<form action="NieuweKlusServlet.do" method="post">
		<%
		Object gekozen = request.getAttribute("deAuto");
		if(gekozen == null){
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
				<h2><span>Selecteer de auto waar aan is gewerkt</span></h2>
				<table>
					<tr>
						<th>Kies</th>
						<th>Kenteken</th>
						<th>Merk</th>
						<th>Type</th>
						<th>Eigenaar</th>
					</tr>
				<%
				boolean eerste = true;
				for(Auto a : autos){
					%>
					<tr>
						<td><input type="radio" name="gekozenauto"
						<%if(eerste){ %> checked="checked" <% eerste=false;}%>
						value="<%=a.getID()%>" /></td>
						<td><%=a.getKenteken()%></td>
						<td><%=a.getMerk()%></td>
						<td><%=a.getType()%></td>
						<td><%=a.getEigenaar().getNaam()%></td>
					</tr>
				<%}%>
				</table>
				<p><input type="submit" name="knop" value="kiesauto" /></p>
				<%
			}
		}
		else{
			Auto deAuto = (Auto)gekozen;
			%>
			<h2><span>Vul het formulier in en klik op 'nieuw' om de nieuwe klus aan te maken</span></h2>
			<table>
				<tr>
					<th>Reparatie</th>
					<td><input type="radio" name="type" checked="checked" value="reparatie" /></td>
				</tr>
				<tr>
					<th>Onderhoudsbeurt</th>
					<td><input type="radio" name="type" value="onderhoudsbeurt" /></td>
				</tr>
				<tr>
					<th>Datum</th>
					<td><input type="text" name="datum" class="datepicker" id="dat"/></td>
				</tr>
				</table>
			<textarea name="beschrijving" placeholder="Omschrijving van de klus" rows="40" cols="40"></textarea>
			<p><input type="hidden" name="autovanklus" value="<%=deAuto.getID()%>" />
			<input type="submit" name="knop" value="nieuw" /></p>
		<%}%>
	</form>
<%@ include file="footer.html" %>