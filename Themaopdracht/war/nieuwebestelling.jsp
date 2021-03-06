<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Nieuwe bestelling" /> 
</jsp:include> 
	<script type="text/javascript">
		function updatePrijs(){
			var totaal=parseFloat("0");
			var array=document.getElementsByName("wijzigaantal");
		    var aantal=[].slice.call(array);
			var array2=document.getElementsByName("prijsperstuk");
		    var pps=[].slice.call(array2);
			
			for(var i=parseFloat("0"); i<aantal.length; i++){
				var text=aantal[i].value;
				var prijs=pps[i].innerHTML;
				var plus=parseFloat(text);
				if(!isNaN(plus)){
					if(plus > 0){
						totaal += plus * parseFloat(prijs);
					}
				}
			}
			document.getElementById("totaalprijs").innerHTML="Totaalprijs: " + totaal + " euro";
		}
	</script>
	<%@ page import="java.util.ArrayList,java.sql.Connection,database.ConnectDBProduct,domeinklassen.Bestelling,domeinklassen.Product,domeinklassen.BesteldProduct" %>
	<h1><span>Nieuwe bestelling aanmaken</span></h1>
	<%@ include file="messages.jsp" %>
	<form action="NieuweBestellingServlet.do" method="post">
		<%
		if(request.getAttribute("stap1") != null){
			ArrayList<Product> teBestellenProducten = (ArrayList<Product>) request.getAttribute("teBestellenProducten");
			%>
			<h2><span>Kies de aantallen van de producten</span></h2>
			<table>
				<tr>
					<th>Artikelnummer</th>
					<th>Naam</th>
					<th>Eenheid</th>
					<th>Minimum aanwezig</th>
					<th>Aantal aanwezig</th>
					<th>Prijs per stuk</th>
					<th>Aantal te bestellen</th>		
				</tr>
				<%
				for(Product p : teBestellenProducten){
					%>
					<tr>
						<td><input type="hidden" name="wijzig" value="<%=p.getArtikelNr()%>" /><%=p.getArtikelNr()%></td>
						<td><%=p.getNaam()%></td>
						<td><%=p.getEenheid()%></td>
						<td><%=p.getMinimumAanwezig()%></td>
						<td><%=p.getAantal()%></td>
						<td name="prijsperstuk" ><%=p.getPrijsPerStuk()%></td>
						<td><input type="text" onkeyup="updatePrijs()" name="wijzigaantal" /></td>
					</tr>
				<%}%>
			</table>
			<p id="totaalprijs" >Totaalprijs: </p>
			<p><input type="submit" name="knop" value="Bestel" /></p>
			<%
		}
		else if(request.getAttribute("stap2") != null){
			Bestelling deBestelling = (Bestelling) request.getAttribute("deBestelling");
			%>
			<h2><span>Klik op "Done" om de bestelling te versturen</span></h2>
			<table>
				<tr>
					<th>Artikelnummer</th>
					<th>Naam</th>
					<th>Eenheid</th>
					<th>Aantal</th>
					<th>Prijs per stuk</th>		
				</tr>
				<%
				for(BesteldProduct bp: deBestelling.getBesteldeProducten()){
				%>
					<tr>
						<td><%=bp.getProduct().getArtikelNr()%></td>
						<td><%=bp.getProduct().getNaam()%></td>
						<td><%=bp.getProduct().getEenheid()%></td>
						<td><%=bp.getHoeveelheid()%></td>
						<td><%=bp.getProduct().getPrijsPerStuk()%></td>
					</tr>
				<%}%>
			</table>
			<p><input type="submit" name="knop" value="Done" /></p>
			<%
		}
		else{
			Connection con = (Connection)session.getAttribute("verbinding");
			ConnectDBProduct productcon = new ConnectDBProduct(con);	
			ArrayList<Product> producten = productcon.getProducten();	
			%>
			<h2><span>Kies de te bestellen producten</span></h2>
			<table>
			<tr>
				<th>X</th>
				<th>Naam</th>
				<th>Minimum aanwezig</th>
				<th>Aantal aanwezig</th>
			</tr>
			<%
			for(Product p : producten){
			%>
				<tr>
					<td><input type="checkbox" name="gekozenProduct" value="<%=p.getArtikelNr()%>" /></td>
					<td><%=p.getNaam()%></td>
					<td><%=p.getMinimumAanwezig()%></td>
					<td><%=p.getAantal()%></td>
				</tr>
			<%}%>
			</table>
			<p><input type="submit" name="knop" value="KiesProducten" /></p>
			<%		
		}
		%>
	</form>
	<p><a href="nieuwebestelling.jsp">Terug</a></p>
<%@ include file="footer.html" %>