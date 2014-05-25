<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" /> 
	<title>Klus annuleren Page</title> 
</head> 
<body>
	<form action="KlusAnnulerenServlet.do" method="get"> 
	<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.Auto,domeinklassen.Klus,domeinklassen.Onderhoudsbeurt,domeinklassen.Reparatie,domeinklassen.GebruiktProduct,domeinklassen.Product" %>
		<div>
			<h1>Een klus Annuleren</h1>
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
	   	Object gekozen = request.getAttribute("deKlus");
		if(gekozen == null){
			ArrayList<Klus> klussen = (ArrayList<Klus>)request.getAttribute("klussen");
			if(klussen == null){
				out.println("<table>");
				out.println("<tr>");
					out.println("<th>Zoek een klus via ID</th>");
					out.println("<td><input type=text name=zoekviaID /></td>");
				out.println("</tr>");
				out.println("<tr>");
					out.println("<th><input type=submit value=zoek name=knop></input></th>");
				out.println("</tr>");
				out.println("</table>");
			}
			else if(klussen != null){
					out.println("<h2><span>Kies de klus die geannuleerd moet worden: </span></h2>");
					out.println("<table>");
					out.println("<tr>");
						out.println("<th>Kies</th>");
						out.println("<th>Datum</th>");
						out.println("<th>Beschrijving</th>");
						out.println("<th>Status</th>");
					out.println("</tr>");
					boolean eerste=true; 
			 		for(Klus k : klussen ){
						out.println("<tr>");
							out.println("<td><input type=radio name=gekozenklus ");
							if(eerste){out.println("checked=checked ");eerste=false;}
							out.println("value=" + k.getID() + " /></td>");
							out.println("<td>" + k.getDatum() + "</td>");
							out.println("<td>" + k.getBeschrijving() + "</td>");
							out.println("<td>" + k.getStatus() + "</td>");
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