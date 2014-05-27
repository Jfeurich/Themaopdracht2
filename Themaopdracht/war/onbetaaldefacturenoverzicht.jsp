<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Onbetaalde facturen" /> 
</jsp:include> 
	<form action="OnbetaaldeFacturenOverzichtServlet.do" method="post">
	<%@ page import="java.util.ArrayList,domeinklassen.Factuur,domeinklassen.Klus" %>
	<%@ include file="messages.jsp" %>
	<%
	ArrayList<Factuur> OverzichtFacturenNietBetaald =(ArrayList<Factuur>)request.getAttribute("OverzichtFacturenNietBetaald");
	Object factuurid = request.getAttribute("factuurid");
	if(factuurid != null){
		%>
		<h1><span>18: Factuur betalen:</span></h1>
		<input type="hidden" name="factuurid" value="<%=Integer.parseInt((String) factuurid)%>" />
		<input type="radio" name="betaalmiddel" value="giro" checked="checked" > Giro <br />
		<input type="radio" name="betaalmiddel" value="pin"> Pin <br />
		<input type="radio" name="betaalmiddel" value="contant"> Contant <br />
		<input type="submit" name="knop" value="betaal" />
		<%
	}
	else if(OverzichtFacturenNietBetaald != null){
		%>
		<h1><span>21: De onbetaalde facturen zijn:</span></h1>
		<table>
			<tr>
				<th>Kies</th>
				<th>FactuurID</th>
				<th>AanmaakDatum</th>
				<th>Korting</th>
				<th>De Klus</th>
				<th>Totaal</th>
			</tr>
			<%
			boolean eerste=true;
			for(Factuur f : OverzichtFacturenNietBetaald){
				%>
				<tr>
					<td><input type="radio" name="factuurid" 
					<%if(eerste){out.println(" checked=checked ");eerste=false;}%>
					value="<%=f.getID()%>" /></td>
					<td><%=f.getID()%></td>
					<td><%=f.getAanmaakDatumNetjes()%></td>
					<td><%=f.getKorting()%></td>
					<td><%=f.getTotaal()%></td>
				</tr>
		<%}%>
		</table>
		<input type="submit" name="knop" value="zoek" />
		<%
	}
	else if(request.getAttribute("stap1") != null){
		%>
		<h3 class=msg >De factuur is betaald!</h3>
		<input type="submit" name="knop" value="overzicht" />
		<%
	}
	else{
		%>
		<h3><span>Error!</span></h3>
		<p class="error">Er zijn geen onbetaalde facturen</p>
		<%
	}
	%>
	<p><a href="factuur.jsp" >Terug naar hoofdmenu factuur</a></p>
	</form>	
<%@ include file="footer.html" %>