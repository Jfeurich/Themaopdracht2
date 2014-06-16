<%@ page import="domeinklassen.User"%>
<% 	
User u = (User)session.getAttribute("gebruiker");
if(u.getType() != 0){
	response.sendRedirect("index.jsp");
}
%>
<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Accountgegevens wijzigen" /> 
</jsp:include> 
	<%@ page import="java.util.ArrayList,domeinklassen.Klant" %>
	<h1><span>Gegevens van een gebruikersaccount wijzigen</span></h1>
	<form action="AccountServlet.do" method="post" >
		<input type="hidden" name="bevestigwachtwoord" value="<%=u.getWachtwoord()%>" />
	</form>
<%@ include file="footer.html" %>