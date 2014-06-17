<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Factuur menu" /> 
</jsp:include>
	<h1><span>Hoofdmenu factuur</span></h1>
	<%@ include file="messages.jsp" %>
	<form action="FactuurServlet.do" method="post">
		<%@ page import="domeinklassen.Factuur,java.util.ArrayList" %>
		<div>
			<h2><span>Overzicht Onbetaalde Facturen</span></h2>
			<input type="submit" name="knop" value="overzicht" />
		</div>
		<div>
			<h2><span>Nieuwe factuur aanmaken</span></h2>
			<input type="submit" name="knop" value="Kies" />
		</div>
	</form>
<%@ include file="footer.html" %>