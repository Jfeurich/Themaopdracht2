<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<title>Overzicht BTW</title>
</head>
<body>
	<p><a href="index.html">Hoofdmenu</a></p>
	<h1><span>22: Overzicht BTW</span></h1>
	<form action="BTWServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Factuur,domeinklassen.Klus,domeinklassen.Auto,domeinklassen.Klant" %>
		<%
			Object error =  request.getAttribute("error");
			if(error != null){
				out.println("<h3><span>Error!</span></h3>");
				out.println("<p name=error class=error >" + error + "</p>");
			}
			else{
				Object msg = request.getAttribute("msg");
				if(msg != null){
					out.println("<h3 name=msg class=msg ><span>" + msg + "<span></h3>");
				}
			}
		%>
		<div>
		<h2><span>Geef aan voor welke kwartalen u een overzicht wilt</span></h2>
		<p class="kop" >Jaar: <input type="text" name="beginjaar" /></p>
		<p>
			<span><input type="radio" name="beginkwartaal" checked="checked" value="1" /></span>
			<span><input type="radio" name="beginkwartaal" value="2" /></span>
			<span><input type="radio" name="beginkwartaal" value="3" /></span>
			<span><input type="radio" name="beginkwartaal" value="4" /></span>
		</p>
		<p>t/m</p>
		<p class="kop" >Jaar: <input type="text" name="eindjaar" /></p>
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
			out.println("<h2><span>Alle betaalde facturen</span></h2>");
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