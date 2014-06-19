<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Factuur menu" /> 
</jsp:include>
	<%@ page import="domeinklassen.Factuur,java.util.ArrayList" %>
	<h1><span>Hoofdmenu factuur</span></h1>
	<%@ include file="messages.jsp" %>
	<form action="FactuurServlet.do" method="post">
		<div>
			<h2><span>Overzicht Onbetaalde Facturen</span></h2>
			<input type="submit" name="knop" value="Naar overzicht" />
		</div>
		<div>
			<h2><span>Nieuwe factuur aanmaken</span></h2>
			<input type="submit" name="knop" value="Nieuwe factuur" />
		</div>
	</form>
<%@ include file="footer.html" %>