<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Klus wijzigen" /> 
</jsp:include> 
<%@include file="datepicker.jsp" %>
	<form action="KlusWijzigenServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.Auto,domeinklassen.Klus,domeinklassen.Onderhoudsbeurt,domeinklassen.Reparatie,domeinklassen.GebruiktProduct,domeinklassen.Product" %>
		<div>
			<h1><span>16: Klus wijzigen</span></h1>
			<%@ include file="messages.jsp"%>
			<%
			Object gekozen = request.getAttribute("deKlus");
			if(gekozen == null){
				ArrayList<Klus> klussen = (ArrayList<Klus>)request.getAttribute("klussen");
				if(klussen == null){
					ArrayList<Auto> autos = (ArrayList<Auto>)request.getAttribute("autos");
					if(autos == null){
						ArrayList<Klant> klanten = (ArrayList<Klant>)request.getAttribute("klanten");
						if(klanten == null){
							%>
							<h2><span>Haal eerst gegevens van de klanten op</span></h2>
							<input type="submit" name="knop" value="klanten" >
							<%
						}
						else{		
							%>
							<h2><span>Haal de autos op van de geselecteerde klant</span></h2>	
							<table>
							<tr>
								<th>Kies</th>
								<th>Naam</th>
								<th>Adres</th>
								<th>Woonplaats</th>
							</tr>
							<%
							boolean eerste=true;
							for(Klant k : klanten){
								%>
								<tr>
									<td><input type="radio" name="gekozenklant" 
									<% if(eerste){out.println("checked=checked ");eerste=false;} %>
									value="<%=k.getKlantnummer()%>" /></td>
									<td><%=k.getNaam()%></td>
									<td><%=k.getAdres()%></td>
									<td><%=k.getPlaats()%></td>
								</tr>
							<%}%>
							</table>
							<input type="submit" name="knop" value="autos" />
							<%
						}
					}
					else{
						%>
						<h2><span>Selecteer de auto waar aan is gewerkt</span></h2>
						<table>
						<tr>
							<th>Kies</th>
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
								<td><input type="radio" name="gekozenauto" 
								<%if(eerste){out.println("checked=checked ");eerste=false;}%>
								value="<%=a.getID()%>" /></td>
								<td><%=a.getKenteken()%></td>
								<td><%=a.getMerk()%></td>
								<td><%=a.getType()%></td>
								<td><%=a.getEigenaar().getNaam()%></td>
							</tr>
						<%}%>
						</table>
						<input type="submit" name="knop" value="klus" />
						<%
					}
				}
				else{
					%>
					<h2><span>Kies de klus die moet worden aangepast</span></h2>
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
							<%if(eerste){out.println(" checked=checked ");eerste=false;}%>
							value="<%=k.getID()%>" /></td>
							<td><%=k.getDatum()%></td>
							<td><%=k.getBeschrijving()%></td>
							<td><%=k.getStatus()%></td>
						</tr>
						<%
						ArrayList<GebruiktProduct> producten = k.getGebruikteProducten();
						if(producten.size() > 0){
							%>
							<tr>
								<th>Artikelnummer</th>
								<th>Naam</th>
								<th>Aantal</th>
								<th>Prijs per stuk</th>
							</tr>
							<%
							for(GebruiktProduct gp : producten){
								Product hetProduct = gp.getHetProduct();
								%>
								<tr>
									<td><%=hetProduct.getArtikelNr()%></td>
									<td><%=hetProduct.getNaam()%></td>
									<td><%=gp.getAantal()%></td>
									<td><%=hetProduct.getPrijsPerStuk()%></td>
								</tr>
								<%
							}
						}
					}
					%>
					</table>
					<input type="submit" name="knop" value="wijzig" />	
					<%				
				}
			
			}
			else{
				Klus deKlus = (Klus)gekozen;
				%>
				<h2><span>Wijzig de klus</span></h2>
				<h3><span>Voeg eerst artikelen toe indien van toepassing</span></h3>
				<input type="submit" name="knop" value="nieuwArtikel" />
				<h3><span>En voer dan eventuele andere wijzigingen in</span></h3>
				<p class="kop" >Datum</p>
				<p>Huidige datum: <%=deKlus.getFormattedDatum()%></p>
				<input type="text" name="datum" class="datepicker" />
				<table>
				<tr><th>Status</th></tr>
				<tr><td><input type="radio" name="status" checked="checked" value="voltooid" /></td><td>Voltooid</td></tr>
				<tr><td><input type="radio" name="status" value="onvoltooid" /></td><td>Onvoltooid</td></tr>
				<tr><td><input type="radio" name="status" value="wachten op onderdelen" /></td><td>Wachten op onderdelen</td></tr>
				</table>
				<p class="kop" >Manuren toevoegen</p>
				<p>Huidige manuren: <%=deKlus.getManuren()%></p>
				<input type="text" name="manuren" />
				<p class="kop" >Beschrijving</p>
				<textarea name="beschrijving"><%=deKlus.getBeschrijving()%></textarea>
				<input type="hidden" name="gekozenklus" value="<%=deKlus.getID()%>" />
				<input type="submit" name="knop" value="bevestig" />
				<%
			}
			%>
		</div>
	</form>
	<p><a href="kluswijzigen.jsp">Terug</a></p>
<%@ include file="footer.html" %>