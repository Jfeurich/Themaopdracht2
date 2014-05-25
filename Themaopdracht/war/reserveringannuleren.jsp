<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" /> 
	<title>Parkeer reservering annuleren Page</title> 
</head> 
<body>
	<form action="ReserveringAnnulerenServlet.do" method="get"> 
	<%@ page import="java.util.ArrayList,domeinklassen.Reservering" %>
		<div>
			<h1>Een Parkeer Reservering Annuleren</h1>
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
	   	Object gekozen = request.getAttribute("deReservering");
		if(gekozen == null){
			ArrayList<Reservering> reserveringen = (ArrayList<Reservering>)request.getAttribute("reserveringen");
			if(reserveringen == null){
				out.println("<table>");
				out.println("<tr>");
					out.println("<th>Zoek een parkeer reservering via auto ID</th>");
					out.println("<td><input type=text name=zoekviaID /></td>");
				out.println("</tr>");
				out.println("<tr>");
					out.println("<th><input type=submit value=zoek name=knop></input></th>");
				out.println("</tr>");
				out.println("</table>");
			}
			else if(reserveringen != null){
					out.println("<h2><span>Kies de reservering die geannuleerd moet worden: </span></h2>");
					out.println("<table>");
					out.println("<tr>");
						out.println("<th>Kies</th>");
						out.println("<th>De parkeerplek</th>");
						out.println("<th>Begin datum</th>");
						out.println("<th>Eind datum</th>");
					out.println("</tr>");
					boolean eerste=true; 
			 		for(Reservering r : reserveringen){
						out.println("<tr>");
							out.println("<td><input type=radio name=gekozenreservering ");
							if(eerste){out.println("checked=checked ");eerste=false;}
							out.println("value=" + r.getID() + " /></td>");
							out.println("<td>" + r.getDeParkeerplek() + "</td>");
							out.println("<td>" + r.getBegDat() + "</td>");
							out.println("<td>" + r.getEindDat() + "</td>");
						out.println("</tr>");
					} 
			 		out.println("</table>");
					out.println("<input type=submit name=knop value=annuleer />"); 	
			}
		}
		%>
		</div>
	</form>
</body>
</html>