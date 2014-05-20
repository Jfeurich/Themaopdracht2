<jsp:include page="header.jsp" > 
 <jsp:param name="titel" value="Factuur menu" /> 
</jsp:include> j
	<h1><span>Hoofdmenu factuur</span></h1>
	<form action="OnbetaaldeFacturenOverzichtServlet.do" method="post">
		<%@ page import="domeinklassen.Factuur,java.util.ArrayList" %>
		<div>
			<h2><span>Overzicht Onbetaalde Facturen</span></h2>
			<input type="submit" name="knop" value="overzicht" />
		</div>
	</form>
	<h2><span><a href="nieuwefactuur.jsp">Nieuwe factuur aanmaken</a></span></h2>
</body>
</html>