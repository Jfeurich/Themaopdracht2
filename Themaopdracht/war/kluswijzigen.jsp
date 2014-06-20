<%
Object o = request.getAttribute("deKlus");
if(o == null){
	response.sendRedirect("klus.jsp");
	return;
}
%>
<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Klus wijzigen" /> 
</jsp:include> 
	<%@include file="datepicker.jsp" %>
	<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.Auto,domeinklassen.Klus,domeinklassen.Onderhoudsbeurt,domeinklassen.Reparatie,domeinklassen.GebruiktProduct,domeinklassen.Product" %>
	<h1><span>Klus wijzigen</span></h1>
	<%@ include file="messages.jsp"%>
	<form action="KlusWijzigenServlet.do" method="post">
		<%
		Klus deKlus = (Klus)o;
		%>
		<h2><span>Wijzig de klus</span></h2>
		<h4><span>Voeg eerst artikelen toe indien van toepassing</span></h4>
		<input type="submit" name="knop" value="nieuwArtikel" />
		<h4><span>En vul dan eventuele andere wijzigingen in</span></h4>
		<table>
			<tr><th>Datum</th><td class="hoofdcel"><%=deKlus.getFormattedDatum()%></td></tr>
			<tr><td class="tussenkop">Nieuwe datum</td><td><input type="text" name="datum" class="datepicker" id="dat"/></td></tr>
		</table>
		<table>
			<tr><th>Status</th></tr>
			<tr><td class="tussenkop">Voltooid</td><td><input type="radio" name="status" checked="checked" value="voltooid" /></td></tr>
			<tr><td class="tussenkop">Onvoltooid</td><td><input type="radio" name="status" value="onvoltooid" /></td></tr>
			<tr><td class="tussenkop">Wachten op onderdelen</td><td><input type="radio" name="status" value="wachten op onderdelen" /></td></tr>
		</table>
		<table>
			<tr><th>Manuren</th><td class="hoofdcel"><%=deKlus.getManuren()%></td></tr>
			<tr><td class="tussenkop">Uren toevoegen</td><td><input type="text" name="manuren" /></td></tr>
		</table>
		<h4><span>Beschrijving</span></h4>
		<textarea name="beschrijving" rows="40" cols="40"><%=deKlus.getBeschrijving()%></textarea>
		<p><input type="hidden" name="gekozenklus" value="<%=deKlus.getID()%>" /></p>
		<p><input type="submit" name="knop" value="Bevestig" /></p>
	</form>
<%@ include file="footer.html" %>