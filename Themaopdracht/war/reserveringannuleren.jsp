<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Reservering Annuleren" /> 
</jsp:include> 
	<%@ page import="java.util.ArrayList,domeinklassen.Reservering, domeinklassen.Auto, domeinklassen.Klant, domeinklassen.User" %>
	<h1><span>Een Reservering Annuleren</span></h1>
	<%@ include file="messages.jsp" %>
	<form action="ReserveringAnnulerenServlet.do" method="post"> 
		<%	
		Object o = request.getAttribute("reservering");
		if(o != null){
			Reservering r = (Reservering) o;
		%>
			<h2><span>Bevestig annuleren van de reservering</span></h2>
			<div>
				<table>
					<tr>
						<th>Begin datum</th>
						<th>Eind datum</th>
						<th>Parkeerplek</th>
						<th>Kenteken van Auto</th>
					</tr>
					<tr>
						<td><%= r.getBegDat()%></td>
						<td><%= r.getEindDat() %></td>
						<td><%= r.getDeParkeerplek() %></td>
						<td><%= r.getAuto().getKenteken() %></td>
					</tr>
				</table>
				<p><input type="hidden" value="<%=request.getAttribute("reserveringID")%>" name="gekozenReservering" />
				<input type="submit" value="Bevestig" name="knop" /></p>
			</div>
		<%			
		}
		else{
			Object reserveringen = request.getAttribute("deReserveringen");
			if(reserveringen != null){
				ArrayList<Reservering> deReserveringen = (ArrayList<Reservering>) reserveringen;
			%>
				<h2>Kies een reservering</h2>
				<div>
					<table>
						<tr>
							<th>X</th>
							<th>Begin datum</th>
							<th>Eind datum</th>
							<th>Parkeerplek</th>
							<th>Kenteken van Auto</th>
						</tr>
						<%
						for(Reservering r: deReserveringen){
							boolean eerste=true;
						%>
							<tr>
								<td><input type="radio" name="gekozenReservering" 
								<%if(eerste){ %> checked="checked" <% eerste=false;}%>
								value="<%=r.getID()%>" /></td>
								<td><%= r.getBegDat()%></td>
								<td><%= r.getEindDat() %></td>
								<td><%= r.getDeParkeerplek() %></td>
								<td><%= r.getAuto().getKenteken() %></td>
							</tr>
						<%
						}
						%>	
					</table>
					<p><input type="submit" value="Kies reservering" name="knop" /></p>
				</div>
			<%
			}
			else{
				User gebruiker = (User) request.getSession().getAttribute("gebruiker");
				if(gebruiker.getType() == 3){
				%>
					<h2>Haal uw reserveringen op</h2>
					<div>
						<input type="submit" value="Haal reserveringen op" name="knop" />
					</div>
				<%
				}
				else{
					Object kl = request.getAttribute("klanten");
					if(kl != null){
						ArrayList<Klant> klanten = (ArrayList<Klant>) kl;
					%>
						<h2>Kies een klant</h2>
						<div>
							<table>
								<tr>
									<th>X</th>
									<th>Klantnummer</th>
									<th>Naam</th>
								</tr>
								<%
								for(Klant k : klanten){
									boolean eerste=true;
								%>
									<tr>
										<td><input type="radio" name="gekozenklant" 
										<%if(eerste){ %> checked="checked" <% eerste=false;}%>
										value="<%=k.getKlantnummer()%>" /></td>
										<td><%=k.getKlantnummer() %></td>
										<td><%=k.getNaam() %></td>
									</tr>
								<%
								}
								%>
							</table>
							<p><input type="submit" value="Kies klant" name="knop" /></p>
						</div>
					<%
					}
					else{
					%>
						<h2>Haal klanten op</h2>
						<div>
							<p><input type="submit" value="Haal op" name="knop" /></p>
						</div>
					<%
					}
				}
			}
		}
		%>
	</form>
<%@ include file="footer.html" %>