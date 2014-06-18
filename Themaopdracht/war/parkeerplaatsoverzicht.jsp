<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Overzicht parkeerplaats" /> 
</jsp:include> 
<%@include file="datepicker.jsp" %>
	<%@ page import="java.util.ArrayList,domeinklassen.Klant,domeinklassen.Auto,domeinklassen.User,java.text.SimpleDateFormat, domeinklassen.Reservering, java.text.DateFormat, java.text.SimpleDateFormat" %>	
		<%
		if(request.getSession().getAttribute("parkeerplek") != null && request.getSession().getAttribute("beginDat") != null && request.getSession().getAttribute("eindDat") != null){
		%>
		<form action="NieuweReserveringServlet.do" method="post">
			<h1><span>3: Nieuwe Reservering aanmaken</span></h1>
			<%@ include file="messages.jsp" %>
			<%
			Object auto = request.getAttribute("deAuto");
			if (auto != null){
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				Auto deAuto = (Auto)auto;
				%>
				<h2><span>Reservering voor:</span></h2>
				<p><%=deAuto.toString()%></p>
				<p>Parkeerplek: <%=request.getSession().getAttribute("parkeerplek")%><p/>
				<p>Van: <%=df.format(request.getSession().getAttribute("beginDat"))%></p>
				<p>Tot: <%=df.format(request.getSession().getAttribute("eindDat"))%></p>
				<input type="hidden" name="deAuto" value="<%=deAuto.getID()%>" />
				<input type="submit" name="knop" value="maakReservering" />
				<%
			}
			else{
				ArrayList<Auto> autos = null;
				Object g = session.getAttribute("gebruiker");
				if(g != null){
					User deGebruiker = (User)g;
					if(deGebruiker.getType() == 3){
						autos = deGebruiker.getDeKlant().getAutos();
					}
					else{
						autos = (ArrayList<Auto>) request.getAttribute("autos");
					}
				}
				if(autos != null){
					%>
					<h2><span>Selecteer de auto waar de reservering voor wordt gemaakt</span></h2>
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
					<input type="submit" name="knop" value="kiesAuto" />
					<%
				}
				else{
					ArrayList<Klant> klanten = (ArrayList<Klant>)request.getAttribute("klanten");
					if(klanten != null){	
						%>
						<h2><span>Haal de autos op van de geselecteerde klant</span></h2>
						<table>
						<tr>
							<td>Kies</td>
							<td>Naam</td>
							<td>Adres</td>
							<td>Woonplaats</td>
						</tr>
						<%
						boolean eerste=true;
						for(Klant k : klanten){
							%>
							<tr>
								<td><input type="radio" name="gekozenklant" 
								<%if(eerste){out.println("checked=checked ");eerste=false;}%>
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
					else{
						%>
						<h2><span>Haal eerst gegevens van de klanten op</span></h2>
						<input type="submit" name="knop" value="klanten" />
						<%
					}
				}
			}
			%>
	</form>
	<%
	}
	else{
	%>
	<form action="ParkeerplaatsOverzichtServlet.do" method="post">
		<h1><span>12: Overzicht parkeerplaats</span></h1>
		<%@ include file="messages.jsp"%>
		<div>	
			<table>
				<tr>
					<td>Begin datum:</td>
					<td><input type="text" name="begindat" class="datepicker" /></td>
					<td>Eind datum:</td>
					<td><input type="text" name="einddat" class="datepicker" /></td>
					<td><input type="submit" name="knop" value="Checkdatum" /></td>
				</tr>
			</table>
		</div>
		<div id="parkeerplaatsoverzicht">
			<%	
			Object o = request.getSession().getAttribute("gevondenReserveringen");
			if(o != null){
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				String begDat = df.format(request.getSession().getAttribute("beginDat"));
				String eindDat = df.format(request.getSession().getAttribute("eindDat"));
				%>
				<h4>Begin datum: <%= begDat %></h4>
				<h4>Eind datum: <%= eindDat %></h4>
				<%
				ArrayList<Reservering> deReserveringen = (ArrayList<Reservering>)o;
				int teller = 1;
				%><table><%
				//8 rijen
				for(int i = 1; i <= 8; i++){
					%>
					<tr>
					<%
					//5 kolommen
					for(int j = 1; j <= 5; j++){
						boolean b = false;
						for(Reservering r : deReserveringen){
							//check of deze plek bij de reserveringen zit
							if(r.getDeParkeerplek() == teller){
								b = true;
							}
						}
						%><td><%
						if(b){
							%><img src="autoo.jpg" alt="Auto" height="165" width="88" /><%
						}
						else{
							%><input class="parkeerbutton" type="submit" name="knop" value="<%=teller%>" /><%
						}
						%></td><%
						teller++;
					}
					%></tr><%
				}
				%></table><%
			}
			else{
			%>
				<p>Voer twee datums in om te checken welke parkeerplaatsen beschikbaar zijn.</p>
			<%
			}
			%>
		</div>
	</form>
	<%
	}
	%>
<%@ include file="footer.html" %>