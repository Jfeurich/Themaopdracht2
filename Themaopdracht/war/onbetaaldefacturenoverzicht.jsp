<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<title>Voorraad Menu</title>
	</head>
	<body>
		<form action="OverzichtFacturenNietBetaaldServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Factuur,domeinklassen.Klus" %>
		<%
			Object msg = request.getAttribute("msg");
			if(msg != null){
				out.println(msg);
			}
			ArrayList<Factuur> OverzichtFacturenNietBetaald =(ArrayList<Factuur>)request.getAttribute("OverzichtFacturenNietBetaald");
			Object factuurid = request.getAttribute("factuurid");
			if(factuurid != null){
				int id = Integer.parseInt((String) factuurid);
				out.println("<input type=hidden name=factuurid value=" + id + " />");
				out.println("<input type=radio name=betaalmiddel value=giro checked> Giro <br />");	
				out.println("<input type=radio name=betaalmiddel value=pin> Pin <br />");
				out.println("<input type=radio name=betaalmiddel value=contant> Contant <br />");
				out.println("<input type=submit name=knop value=betaal />");
			}
			else if(OverzichtFacturenNietBetaald != null){
			out.println("<h2>De onbetaalde facturen zijn:</h2>");
			out.println("<table>");
				out.println("<tr>");
					out.println("<td>Kies</td>");
					out.println("<td>FactuurID</td>");
					out.println("<td>AanmaakDatum</td>");
					out.println("<td>Korting</td>");
					out.println("<td>De Klus</td>");
					out.println("<td>Totaal</td>");
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
				out.println("De factuur is betaald!");
				out.println("<input type=submit name=knop value=overzicht />");
			}
			else{
				out.println("<h2>Error!</h2>");
				out.println("<p>Er zijn geen onbetaalde facturen</p>");
			}
			out.println("<a href=factuur.jsp>Terug naar hoofdmenu factuur</a>");
		%>
		</form>	
	</body>
</html>