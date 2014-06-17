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
	<form action="KlusWijzigenServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.Auto,domeinklassen.Klus,domeinklassen.Onderhoudsbeurt,domeinklassen.Reparatie,domeinklassen.GebruiktProduct,domeinklassen.Product" %>
		<h1><span>16: Klus wijzigen</span></h1>
		<%@ include file="messages.jsp"%>
		<%
		Klus deKlus = (Klus)o;
		%>
		<h2><span>Wijzig de klus</span></h2>
		<h3><span>Voeg eerst artikelen toe indien van toepassing</span></h3>
		<input type="submit" name="knop" value="nieuwArtikel" />
		<h3><span>En voer dan eventuele andere wijzigingen in</span></h3>
		<p class="kop" >Datum</p>
		<p>Huidige datum: <%=deKlus.getFormattedDatum()%></p>
		<input type="text" name="datum" class="datepicker" />
		<table>
			<tr><th>Status</th></tr>
			<tr><td><input type="radio" name="status" checked="checked" value="voltooid" /></td><td>Voltooid</td></tr>
			<tr><td><input type="radio" name="status" value="onvoltooid" /></td><td>Onvoltooid</td></tr>
			<tr><td><input type="radio" name="status" value="wachten op onderdelen" /></td><td>Wachten op onderdelen</td></tr>
		</table>
		<p class="kop" >Manuren toevoegen (Huidige uren: <%=deKlus.getManuren()%>)</p>
		<input type="text" name="manuren" />
		<p class="kop" >Beschrijving</p>
		<textarea name="beschrijving"><%=deKlus.getBeschrijving()%></textarea>
		<input type="hidden" name="gekozenklus" value="<%=deKlus.getID()%>" />
		<input type="submit" name="knop" value="bevestig" />
	</form>
	<p><a href="kluswijzigen.jsp">Terug</a></p>
<%@ include file="footer.html" %>