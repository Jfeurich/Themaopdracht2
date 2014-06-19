<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Overzicht Auto's" /> 
</jsp:include> 
	<form action="AutoServlet.do" method="post">
		<%@ page import="java.util.ArrayList,java.sql.Connection,database.ConnectDBAuto,domeinklassen.Auto,domeinklassen.Klant" %>
		<h1><span>Overzicht auto's</span></h1>
		<%@ include file="messages.jsp" %>
		<%
		Connection con = (Connection)session.getAttribute("verbinding");
		ConnectDBAuto acon = new ConnectDBAuto(con);	
		ArrayList<Auto> autos = acon.getAutos();
		ArrayList<Auto> nonactief = acon.getAutosNietActief();
		if(autos.size() > 0){
			%>
			<h2><span>Auto's</span></h2>
			<table>
			<tr>
				<th>X</th>
				<th>Kenteken</th>
				<th>Merk</th>
				<th>Type</th>
				<th>Eigenaar</th>
			</tr>
			<%
			boolean eerste=true;
			for(Auto a : autos){
				%>
				<tr>
					<td><input type="radio" name="kiesauto" 
					<%if(eerste){ %> checked="checked" <% eerste=false;}%>
					value="<%=a.getID()%>" /></td>
					<td><%=a.getKenteken()%></td>
					<td><%=a.getMerk()%></td>
					<td><%=a.getType()%></td>
					<td><%=a.getEigenaar().getNaam()%></td>
				</tr>
			<%}%>
			</table>
			<p><input type="hidden" name="actief" id="actief" value="ja" />	</p>
		<%
		}else{%>
			<p><input type="hidden" name="actief" id="actief" value="nee" />	</p>
		<%}
		if(nonactief.size() > 0){	
		%>	
		<h3><span>Non-actieve auto's:</span></h3>
		<table>
		<tr>
			<th>X</th>
				<th>Kenteken</th>
				<th>Merk</th>
				<th>Type</th>
				<th>Eigenaar</th>
			</tr>
			<%
			boolean eerste = true;
			for(Auto a : nonactief){%>
				<tr>
					<td><input type="radio" onclick="setNonActief()" name="kiesauto" 
					<%if(eerste){if(autos.size()==0){ %> checked="checked" <%}eerste=false;}%>
					value="<%=a.getID()%>" /></td>
					<td><%=a.getKenteken()%></td>
					<td><%=a.getMerk()%></td>
					<td><%=a.getType()%></td>
					<td><%=a.getEigenaar().getNaam()%></td>
				</tr>
			<%}%>
		</table>
		<%}%>
		<script type="text/javascript">
			function setNonActief(){
				var s = document.getElementById("actief");
          		s.value = "nee";
			}
			function setActief(){
				var s = document.getElementById("actief");
          		s.value = "ja";
			}
		</script>
		<p><input type="submit" name="knop" value="Zet actief/non-actief" /></p>
	</form>
<%@ include file="footer.html" %>