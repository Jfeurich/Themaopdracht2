<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<title>Onbetaalde facturen</title>
</head>
<body>
	<p><a href="index.html">Hoofdmenu</a></p>
	<form action="OnbetaaldeFacturenOverzichtServlet.do" method="post">
	<%@ page import="java.util.ArrayList,domeinklassen.Factuur,domeinklassen.Klus" %>
	<%
		Object error =  request.getAttribute("error");
		if(error != null){
			out.println("<h3>Error!</h3>");
			out.println("<p name=error >" + error + "</p>");
		}
		else{
			Object msg = request.getAttribute("msg");
			if(msg != null){
				out.println("<h3 name=msg>" + msg + "</h3>");
			}
		}
		ArrayList<Factuur> OverzichtFacturenNietBetaald =(ArrayList<Factuur>)request.getAttribute("OverzichtFacturenNietBetaald");
		Object factuurid = request.getAttribute("factuurid");
		if(factuurid != null){
			int id = Integer.parseInt((String) factuurid);
			out.println("<h2>18: Factuur betalen:</h2>");
			out.println("<input type=hidden name=factuurid value=" + id + " />");
			out.println("<input type=radio name=betaalmiddel value=giro checked> Giro <br />");	
			out.println("<input type=radio name=betaalmiddel value=pin> Pin <br />");
			out.println("<input type=radio name=betaalmiddel value=contant> Contant <br />");
			out.println("<input type=submit name=knop value=betaal />");
		}
		else if(OverzichtFacturenNietBetaald != null){
		out.println("<h2>21: De onbetaalde facturen zijn:</h2>");
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
			out.println("De factuur is betaald!");
			out.println("<input type=submit name=knop value=overzicht />");
		}
		else{
			out.println("<h2>Error!</h2>");
			out.println("<p>Er zijn geen onbetaalde facturen</p>");
		}
	%>
	<p><a href="factuur.jsp" >Terug naar hoofdmenu factuur</a></p>
	</form>	
</body>
</html>