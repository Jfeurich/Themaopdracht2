<%@ include file="redirect.jsp" %>
<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Klus annuleren" /> 
</jsp:include> 
	<form action="KlusAnnulerenServlet.do" method="get"> 
	<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.Auto,domeinklassen.Klus,domeinklassen.Onderhoudsbeurt,domeinklassen.Reparatie,domeinklassen.GebruiktProduct,domeinklassen.Product" %>
		<h1>26: Een klus Annuleren</h1>
		<%@ include file="messages.jsp"%>
		<%		
	   	Object gekozen = request.getAttribute("deKlus");
		if(gekozen == null){
			Object o = request.getAttribute("klussen");
			if(o == null){
				%>
				<table>
					<tr>
						<th>Zoek een klus via ID</th>
						<td><input type="text" name="zoekviaID" /></td>
					</tr>
					<tr>
						<th><input type="submit" value="zoek" name="knop" ></input></th>
					</tr>
				</table>
				<%
			}
			else{
				ArrayList<Klus> klussen = (ArrayList<Klus>)o;
				%>
				<h2><span>Kies de klus die geannuleerd moet worden: </span></h2>
				<table>
				<tr>
					<th>Kies</th>
					<th>Datum</th>
					<th>Beschrijving</th>
					<th>Status</th>
				</tr>
				<%
				boolean eerste=true; 
		 		for(Klus k : klussen ){
		 		%>
					<tr>
						<td><input type="radio" name="gekozenklus" 
						<%if(eerste){out.println("checked=checked ");eerste=false;}%>
						value="<%=k.getID()%>" /></td>
						<td><%=k.getFormattedDatum()%></td>
						<td><%=k.getBeschrijving()%> </td>
						<td><%=k.getStatus()%></td>
					</tr>
				<%}%>
		 		</table>
				<input type="submit" name="knop" value="annuleer" />
				<%
			}
		}
		%>
	</form>
<%@ include file="footer.html" %>