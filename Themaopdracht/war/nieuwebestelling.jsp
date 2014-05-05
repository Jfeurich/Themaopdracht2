<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title>Nieuwe bestelling</title>
</head>
<body>
	<form action="NieuweBestellingServlet.do" method="post">
		<%@ page import="java.util.ArrayList,domeinklassen.Bestelling,domeinklassen.Product,domeinklassen.BesteldProduct" %>
		<div>
			<h2>Nieuwe bestelling aanmaken</h2>
			<% 				
				Object error =  request.getAttribute("error");
				if(error != null){
					out.println("<h2>Error!</h2>");
					out.println(error);
				}
				else{
					Object msg = request.getAttribute("msg");
					if(msg != null){
						out.println(msg);
					}
				}
				if(request.getAttribute("stap1") != null) {
					ArrayList<Product> producten = (ArrayList<Product>)request.getAttribute("producten");	
					out.println("<h2>Kies de te bestellen producten</h2>");
					out.println("<table>");
					out.println("<tr>");
						out.println("<td>X</td>");
						out.println("<td>Naam</td>");
						out.println("<td>Minimum aanwezig</td>");
						out.println("<td>Aantal aanwezig</td>");
					out.println("</tr>");
					for(Product p : producten){
						out.println("<tr>");
							out.println("<td><input type=checkbox name=gekozenProduct value=" + p.getArtikelNr() + " /></td>");
							out.println("<td>" + p.getNaam() + "</td>");
							out.println("<td>" + p.getMinimumAanwezig() + "</td>");
							out.println("<td>" + p.getAantal() + "</td>");
						out.println("</tr>");
					}
					out.println("</table>");
					out.println("<input type=submit name=knop value=KiesProducten />");	
				}
				else if(request.getAttribute("stap2") != null){
					ArrayList<Product> teBestellenProducten = (ArrayList<Product>) request.getAttribute("teBestellenProducten");
					out.println("<h2>Kies de aantallen van de producten</h2>");
					out.println("<table>");
						out.println("<tr>");
							out.println("<td>Artikelnummer</td>");
							out.println("<td>Naam</td>");
							out.println("<td>Eenheid</td>");
							out.println("<td>Aantal aanwezig</td>");
							out.println("<td>Minimum aanwezig</td>");
							out.println("<td>Prijs per stuk</td>");
							out.println("<td>Aantal te bestellen</td>");				
						out.println("</tr>");
						for(Product p : teBestellenProducten){
							out.println("<tr>");
								out.println("<td>" + p.getArtikelNr() + "</td>");
								out.println("<td>" + p.getNaam() + "</td>");
								out.println("<td>" + p.getEenheid() + "</td>");
								out.println("<td>" + p.getMinimumAanwezig() + "</td>");
								out.println("<td>" + p.getAantal() + "</td>");
								out.println("<td name=prijsperstuk >" + p.getPrijsPerStuk() + "</td>");
								out.println("<td><input type=text onkeyup=updatePrijs() name=wijzigaantal /></td>");
							out.println("</tr>");
							out.println("<input type=hidden name=wijzig value=" + p.getArtikelNr() + " />");
						}
					out.println("</table>");
					out.println("<p id=totaalprijs >Totaalprijs: </p>");
					out.println("<input type=submit name=knop value=Bestel />");		
				}
				else if(request.getAttribute("stap3") != null){
					Bestelling deBestelling = (Bestelling) request.getAttribute("deBestelling");
					out.println(deBestelling.toString() + "<br />");
					out.println("<table>");
						out.println("<tr>");
							out.println("<td>Artikelnummer</td>");
							out.println("<td>Naam</td>");
							out.println("<td>Eenheid</td>");
							out.println("<td>Aantal</td>");
							out.println("<td>Prijs per stuk</td>");				
						out.println("</tr>");
						for(BesteldProduct bp: deBestelling.getBesteldeProducten()){
							out.println("<tr>");
								out.println("<td>" + bp.getProduct().getArtikelNr() + "</td>");
								out.println("<td>" + bp.getProduct().getNaam() + "</td>");
								out.println("<td>" + bp.getProduct().getEenheid() + "</td>");
								out.println("<td>" + bp.getHoeveelheid() + "</td>");
								out.println("<td>" + bp.getProduct().getPrijsPerStuk() + "</td>");	
							out.println("</tr>");
						}
					out.println("</table>");
					out.println("<input type=submit name=knop value=Done />");
				}
				else{
					out.println("<input type=submit name=knop value=MaakBestelling />");
				}
			%>
		</div>
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
						totaal += plus * parseFloat(prijs);
					}
				}
				document.getElementById("totaalprijs").innerHTML="Totaalprijs: " + totaal + " euro";
			}
		</script>
	</form>
</body>
</html>