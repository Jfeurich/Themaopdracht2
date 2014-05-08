Beste groepsgenoten:

Als je iets verandert moet je de verandering committen naar de master branch!
Control + "#" opent het commit menu.
Voeg commentaar toe.
Kies voor "Commit and push" zodat de veranderingen ook doorgevoerd worden in de master branch.
Ik moet nog kijken of ik de testbranch default kan maken voor de veranderingen.

Laat eventueel in de ruimte onderaan deze readme comments achter voor elkaar.
Lees dit bestand dus als eerst bij werken aan de themaopdracht.

bij het syncen van je locale bestanden: 

in GIT kies voor sync, als het goed is staat er een blauw icoontje naast.
Als git je zegt dat je nog locale dingen moet committen en het zijn metadata bestanden klik met je rechtermuisknop 
op de de te committen bestanden in de lijst en kiest voor discard changes. Is het een bestand dat wel geupdate moet worden
dan moet je dit natuurlijk comitten.

in eclipse:

wil je veranderingen uploaden, klik dan op het project of op het veranderde bestand met je rechtermuisknop
ga naar team > commit ("control" + "#") en in dat menu voeg je commentaar toe. Kies voor commit + push en niet alleen voor
commit.

// Ruimte voor commentaar //

27-04 Angie: Eens kijken of dit werkt...
27-04 Jay: het werkt
28-04 Angie: Deelse implementatie van de database. Zie de DatabaseREADME voor instructies.
30-04 Paul: Het is een wonder, het werkt

8-5 Paul:
Af:
Auto toevoegen
Nieuwe Bestelling
Overzicht niet-betaalde facturen
Status factuur wijzigen
Hoofdmenu product
Overzicht van voorraad producten
Product wijzigen

Niet werkend:
Klus wijzigen, alles werkt tot: op de knop status wordt gedrukt om de status aan te passen, error: 
java.lang.NullPointerException
	servlets.KlusWijzigenServlet.doPost(KlusWijzigenServlet.java:60)
Nieuwe Factuur, alles werkt tot: op de knop bevestig wordt gedrukt om de nieuwe factuur te maken, error:
java.lang.NumberFormatException: null
	java.lang.Integer.parseInt(Unknown Source)
	java.lang.Integer.parseInt(Unknown Source)
	servlets.NieuweFactuurServlet.doPost(NieuweFactuurServlet.java:95)
Nieuwe Klus, de nieuwe klus wordt aangemaakt, alleen soms komt er een error message dat de klus niet kan worden aangemaakt,
maar de klus is dan wel toegevoegd aan de database