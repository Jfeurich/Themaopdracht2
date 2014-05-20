<jsp:include page="header.jsp" > 
 <jsp:param name="titel" value="Onbetaalde facturen" /> 
</jsp:include> 
	<form action="OnbetaaldeFacturenOverzichtServlet.do" method="post">
	<%@ page import="java.util.ArrayList,domeinklassen.Factuur,domeinklassen.Klus" %>
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
		ArrayList<Factuur> OverzichtFacturenNietBetaald =(ArrayList<Factuur>)request.getAttribute("OverzichtFacturenNietBetaald");
		Object factuurid = request.getAttribute("factuurid");
		if(factuurid != null){
			int id = Integer.parseInt((String) factuurid);
			out.println("<h1><span>18: Factuur betalen:</span></h1>");
			out.println("<input type=hidden name=factuurid value=" + id + " />");
			out.println("<input type=radio name=betaalmiddel value=giro checked> Giro <br />");	
			out.println("<input type=radio name=betaalmiddel value=pin> Pin <br />");
			out.println("<input type=radio name=betaalmiddel value=contant> Contant <br />");
			out.println("<input type=submit name=knop value=betaal />");
		}
		else if(OverzichtFacturenNietBetaald != null){
		out.println("<h1><span>21: De onbetaalde facturen zijn:</span></h1>");
		out.println("<table>");
			out.println("<tr>");
				out.println("<th>Kies</th>");
				out.println("<th>FactuurID</th>");
				out.println("<th>AanmaakDatum</th>");
				out.println("<th>Korting</th>");
				out.println("<th>De Klus</th>");
				out.println("<th>Totaal</th>");
			out.println("</tr>");	
			boolean eerste=true;
			for(Factuur f : OverzichtFacturenNietBetaald){
				out.println("<tr>");
					out.println("<td><input type=radio name=factuurid ");
					if(eerste){out.println("checked=checked ");eerste=false;}
					out.println("value=" + f.getID() + " /></td>");
					out.println("<td>" + f.getID() + "</td>");
					out.println("<td>" + f.getAanmaakDatum() + "</td>");
					out.println("<td>" + f.getKorting() + "</td>");
					out.println("<td>" + f.getTotaal() + "</td>");
				out.println("</tr>");
			}
			out.println("</table>");
			out.println("<input type=submit name=knop value=zoek />");
		}
		else if(request.getAttribute("stap1") != null){
			out.println("<h3 class=msg >De factuur is betaald!</h3>");
			out.println("<input type=submit name=knop value=overzicht />");
		}
		else{
			out.println("<h3><span>Error!</span></h3>");
			out.println("<p>Er zijn geen onbetaalde facturen</p>");
		}
	%>
	<p><a href="factuur.jsp" >Terug naar hoofdmenu factuur</a></p>
	</form>	
</body>
</html>