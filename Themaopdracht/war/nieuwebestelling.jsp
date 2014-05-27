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
	<form action="NieuweBestellingServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Bestelling,domeinklassen.Product,domeinklassen.BesteldProduct" %>
		<div>
			<h1><span>11: Nieuwe bestelling aanmaken</span></h1>
			<%@ include page="messages.jsp" %>
			<%
			if(request.getAttribute("stap1") != null) {
				ArrayList<Product> producten = (ArrayList<Product>)request.getAttribute("producten");	
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
				<input type="submit" name="knop" value="KiesProducten" />
				<%
			}
			else if(request.getAttribute("stap2") != null){
				ArrayList<Product> teBestellenProducten = (ArrayList<Product>) request.getAttribute("teBestellenProducten");
				%>
				<h2><span>Kies de aantallen van de producten</span></h2>
				<table>
					<tr>
						<th>Artikelnummer</th>
						<th>Naam</th>
						<th>Eenheid</th>
						<th>Aantal aanwezig</th>
						<th>Minimum aanwezig</th>
						<th>Prijs per stuk</th>
						<th>Aantal te bestellen</th>		
					</tr>
					<%
					for(Product p : teBestellenProducten){
						%>
						<tr>
							<td><%=p.getArtikelNr()%></td>
							<td><%=p.getNaam()%></td>
							<td><%=p.getEenheid()%></td>
							<td><%=p.getMinimumAanwezig()%></td>
							<td><%=p.getAantal()%></td>
							<td name="prijsperstuk" ><%=p.getPrijsPerStuk()%></td>
							<td><input type="text" onkeyup="updatePrijs()" name="wijzigaantal" /></td>
						</tr>
						<input type="hidden" name="wijzig" value="<%=p.getArtikelNr()%>" />
					<%}%>
				</table>
				<p id="totaalprijs" >Totaalprijs: </p>
				<input type="submit" name="knop" value="Bestel" />
				<%
			}
			else if(request.getAttribute("stap3") != null){
				Bestelling deBestelling = (Bestelling) request.getAttribute("deBestelling");
				%>
				<h2><span>Kies de hoeveelheid van elk artikel</span></h2>
				<p><%=deBestelling.toString()%></p>
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
				<input type="submit" name="knop" value="Done" />
				<%
			}
			else{
				%>
				<input type="submit" name="knop" value="MaakBestellin"g />
				<%
			}
			%>
		</div>
	</form>
	<p><a href="nieuwebestelling.jsp">Terug</a></p>
<%@ include page="footer.html" %>