<jsp:include page="header.jsp" > 
 <jsp:param name="titel" value="Overzicht werkplaats" /> 
</jsp:include> 
	<%@ page import="java.util.ArrayList, domeinklassen.Klus" %>
	<form action="OverzichtWerkplaatsPlanning.do" method="post">	
		<div>
			<h1><span>12: Overzicht werkplaats</span></h1>
			<% 	
				Object error =  request.getAttribute("error");
				if(error != null){
					out.println("<h3><span>Error!</span></h3>");
					out.println("<p name=error class=error >" + error + "</p>");
				}
				else{
					Object msg = request.getAttribute("msg");
					if(msg != null){
						out.println("<h3 name=msg class=msg ><span>" + msg + "</span></h3>");
					}
				}
			%>
		</div>

		<div>
			<%	
			ArrayList<Klus> deKlussen = (ArrayList<Klus>) request.getSession().getAttribute("gevondenKlussen");
			for(Klus k : deKlussen){
				if(deKlussen == null){
					out.println("<h1><span>Error!<span></h1>");
					out.println("<p>Er staan geen klussen ingepland</p>");
				}
				else{
					out.println("<h1><span> Ingeplande klussen </span></h1>");
					out.println("<table>");
					out.println("<tr>");
						out.println("<th>Datum</th>");
						out.println("<th>Beschrijving</th>");
						out.println("<th>Auto</th>");
					out.println("</tr>");
					out.println("<tr>");
						out.println("<td>" + k.getDatum() + "</td>");
						out.println("<td>" + k.getBeschrijving() + "</td>");
						out.println("<td>" + k.getAuto().getKenteken() + "</td>");
					out.println("</tr>");
					out.println("</table>");
				}
			}
			%>
		</div>
	</form>
	<p><a href="nieuweklus.jsp">Plan een nieuwe klus in</a></p>
</body>
</html>