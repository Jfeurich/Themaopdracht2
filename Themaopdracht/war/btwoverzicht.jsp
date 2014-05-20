<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Overzicht BTW</title>
</head>
<body>
	<p><a href="index.html">Hoofdmenu</a></p>
	<form action="BTWServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Factuur,domeinklassen.Klus,domeinklassen.Auto,domeinklassen.Klant" %>
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
		%>
		<div>
		<h1>22: Overzicht BTW</h1>
		<h2>Geef aan voor welke kwartalen u een overzicht wilt</h2>
		<p>Jaar: <input type="text" name="beginjaar" /></p>
		<p>
			<span><input type="radio" name="beginkwartaal" checked="checked" value="1" /></span>
			<span><input type="radio" name="beginkwartaal" value="2" /></span>
			<span><input type="radio" name="beginkwartaal" value="3" /></span>
			<span><input type="radio" name="beginkwartaal" value="4" /></span>
		</p>
		<p>t/m</p>
		<p>Jaar: <input type="text" name="eindjaar" />
		</p>
		<p>
			<span><input type="radio" name="eindkwartaal" checked="checked" value="1" /></span>
			<span><input type="radio" name="eindkwartaal" value="2" /></span>
			<span><input type="radio" name="eindkwartaal" value="3" /></span>
			<span><input type="radio" name="eindkwartaal" value="4" /></span>
		</p>
		<input type="submit" name="knop" value="overzicht" />
		</div>
	</form>
		<div>
		<%
			ArrayList<Factuur> facturen =(ArrayList<Factuur>)request.getAttribute("facturen");
			if(facturen != null){
				out.println("<h2>Alle betaalde facturen</h2>");
				out.println("<table>");
				out.println("<tr>");
					out.println("<th>Betaaldatum</th>");
					out.println("<th>Klant</th>");
					out.println("<th>Bedrag</th>");
					out.println("<th>%BTW</th>");
					out.println("<th>BTW</th>");
				out.println("</tr>");	
				for(Factuur f : facturen){
					String naam = f.getDeKlus().getAuto().getEigenaar().getNaam();
					out.println("<tr>");
						out.println("<td>" + f.getBetaalDatum() + "</td>");
						out.println("<td>" + naam + "</td>");
						out.println("<td>" + f.getTotaal() + "</td>");
						out.println("<td>21%</td>");
						out.println("<td>" + f.getBTW(21) + "</td>");
					out.println("</tr>");
				}
				out.println("</table>");
			}
		%>
		</div>
</body>
</html>