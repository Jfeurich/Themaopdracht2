<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<title>Voorraad Menu</title>
	</head>
	<body>
		<%@ page import="java.util.ArrayList,domeinklassen.Factuur" %>
		<%
			Object msg = request.getAttribute("msg");
			if(msg != null){
				out.println(msg);
			}
			ArrayList<Factuur> OverzichtFacturenNietBetaald =(ArrayList<Factuur>)request.getAttribute("OverzichtFacturenNietBetaald");
			if(OverzichtFacturenNietBetaald == null){
				out.println("<h2>Error!</h2>");
				out.println("<p>Er zijn geen onbetaalde facturen</p>");
			}
			else{
			out.println("<h2>De onbetaalde facturen zijn:</h2>");
			out.println("<table>");
				out.println("<tr>");
					out.println("<td>Kies</td>");
					out.println("<td>FactuurID</td>");
					out.println("<td>AanmaakDatum</td>");
					out.println("<td>BetaalDatum</td>");
					out.println("<td>Kortin</td>");
					out.println("<td>De Klus</td>");
					out.println("<td>Totaal</td>");
				out.println("</tr>");	
				for(Factuur f : OverzichtFacturenNietBetaald){
					out.println("<tr>");
						out.println("<td><input type=radio name=product value=" + f.getID() + " /></td>");
						out.println("<td>" + f.getID() + "</td>");
						out.println("<td>" + p.getAanmaakDatum() + "</td>");
						out.println("<td>" + p.getBetaalDatum() + "</td>");
						out.println("<td>" + p.getKorting() + "</td>");
						out.println("<td>" + p.getDeKlus() + "</td>");
						out.println("<td>" + p.getTotaal() + "</td>");
					out.println("</tr>");
				}
				out.println("</table>");
			}
			out.println("<a href=factuur.jsp>Terug naar hoofdmenu factuur</a>");
		%>	
	</body>
</html>