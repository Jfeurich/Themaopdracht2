<jsp:include page="header.jsp" > 
	<jsp:param name="titel" value="Hoofdmenu product" /> 
</jsp:include> 
	<%@ page import="domeinklassen.Product,java.util.ArrayList" %>
	<h1><span>Hoofdmenu Product</span></h1>
	<%@ include file="messages.jsp" %>
	<form action="ProductServlet.do" method="post">
		<div>
			<h2><span>Overzicht alle producten</span></h2>
			<input type="submit" name="knop" value="overzicht" />
		</div>
		<div>
			<h2><span>Overzicht producten onder minimumvoorraad</span></h2>
			<input type="submit" name="knop" value="OnderVoorraad" />
		</div>
		<div>
			<h2><span>Nieuw product aanmaken</span></h2>
			<table>
				<tr>
					<th>Naam:</th>
					<td><input type="text" name="naam" /></td>
				</tr>
				<tr>
					<th>Minimum aantal:</th>
					<td><input type="text" name="minaantal" /></td>
				</tr>
				<tr>
					<th>Eenheid:</th>
					<td><input type="text" name="eenheid" /></td>
				</tr>
				<tr>
					<th>Prijs per stuk:</th>
					<td><input type="text" name="pps" /></td>
				</tr>
			</table>
			<p><input type="submit" name="knop" value="nieuw" /></p>
		</div>
		<div>
			<h2><span>Zoek een producten</span></h2>
			<h4><span>Vul minimaal 1 zoekcriterium in</span></h4>
			<%
				Object zoekmsg = request.getAttribute("zoekmsg");
				if(zoekmsg != null){
					%><h3 class=msg ><span><%=zoekmsg%></span></h3><%
				}
				Object arraygevonden = request.getAttribute("arraygevonden");
				if(arraygevonden != null){
					ArrayList<Product> lijst = (ArrayList<Product>)arraygevonden;
					boolean eerste = true;
					for(Product p: lijst){
						%>
						<p><input type="radio" name="product" 
						<%if(eerste){ %> checked="checked" <% eerste=false;}%>
						value="<%=p.getArtikelNr()%>" /><%=p.toString()%></p>
						<%			
					}
					%><p><input type="submit" name="knop" value="wijzig" /></p><%
				}
			%>
			<table>
				<tr>
					<th>Artikelnummer:</th>
					<td><input type="text" name="zoeknummer" /></td>
				</tr>
				<tr>
					<th>Naam:</th>
					<td><input type="text" name="zoeknaam" /></td>
				</tr>
				<tr>
					<th>Eenheid:</th>
					<td><input type="text" name="zoekeenheid" /></td>
				</tr>
			</table>
			<p><input type="submit" name="knop" value="zoek" /></p>
		</div>
	</form>
<%@ include file="footer.html" %>