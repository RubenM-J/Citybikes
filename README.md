# mobile-development-project-michielvanbilsen_rubenmoensjonkers
## Team

Aantal studenten: 1 / 2

Student 1: Michiel Vanbilsen

Student 2: Ruben Moens Jonkers

## Titel app

CityBikes

## Programmeertaal

Java

## Github link en branch

### Link

[Github link](https://github.com/PXLTINMobDev/mobile-development-project-michielvanbilsen_rubenmoensjonkers)

### Branch

Branch: **master**

## Thema's

* Mobiliteit (Onze app toont de gebruiker alle CityBike stations waar je fietsen kunt huren)
* Milieu (Door openbare fietsen makkelijk beschikbaar te maken voor iedereen hopen we de uitstoot te verminderen)
* Communicatie tussen burgers onderling (Zodra een fiets wordt teruggeplaatst in een station wordt dit in de app geupdate en zal een nieuwe gebruiker deze kunnen huren)


## Korte beschrijving

De CityBikes app is een app waarmee je alle huurfietsen in een stad kunt vinden. Je krijgt een kaart te zien van je huidige locatie en ziet meteen alle stations waar je fietsen kan huren. De doelgroep van onze applicatie is iedereen die zich wil/moet verplaatsen. Wij hopen door het gemak van deze app meer mensen aan te zetten om de fiets te nemen, en vooral de mensen in grote steden. Dit met als voornaamste doel de uitstoot te verminderen. De app is voorzien van een wereldkaart waar je alle stations op kunt vinden. Je kan ook zoeken stations in een bepaald land.

## Minimale eisen

Er is voldaan aan alle minimale eisen.

## Schermen

## Aantal schermen

Het aantal schermen in onze app bedraagt **6**

### Lijst van schermen

* Startscherm (Hierop vind je links naar je profiel, settings en de map)
* Settings
* Profile
* Google Map
* Master detail / Recycler List
* Detail View

## Lokale opslag / Shared Preferences

*Beschrijf op welke manier lokale opslag werd aangepakt in de app*

Door gebruik te maken van de strings file, wordt alle text in de app bijgehouden in 1 file. Dit zorgt ervoor dat er in de toekomst eventueel makkelijk bijkomende ondersteuning van meerdere talen kan worden geïmplementeerd. Het implementeren van meerdere talen hebben we geprobeerd alsook het kiezen van Imperial/Metric units. Deze preferenties worden dan ook lokaal opgeslagen in de SharedPreferences van de app.

## Extra's

### Extra 1

Location based services.
Route van huidige locatie tot een geselecteerd station.

### Beschrijving extra 1

In de Google maps API kan, bij het selecteren van een fietsstation, de turn-by-turn navigatie worden opgeroepen door rechtsbeneden op de blauwe pijl te drukken.

### Extra 2

Animation

### Beschrijving extra 2

Als je rechtsboven in de Map klikt, wordt je automatisch via een animatie naar je huidige positie op de kaart gebracht.

## Ondersteuning landscape en portrait / correct gebruik van Fragments

Ondersteunt de app zowel landscape als portrait mode? Wordt er correct gebruik gemaakt van Fragments? Beschrijf kort wat de stand van zaken is en hoe dit gerealiseerd werd.

De app onsersteunt zowel landscape als portrait mode in elk scherm. Dit werd mogelijk gemaakt door gebruik te maken van de layout-land folder toe te voegen aan het project en hierin de landscape layouts aan te maken. Bij de detail flow wordt er door middel van een if check gekeken als de app zich in landscape of portrait mode begeeft. Indien portrait wordt de activity weer gegeven terwijl in landscape wordt de detail fragment naast de recyclerview geplaatst.

## Web service / API

Wij gebruiken de CityBikes API: (http://api.citybik.es/v2/networks) Hieruit halen we de data die we nodig hebben om de locatie en het aantal fietsen van elk station op te halen.
Ook gebruiken we de google maps API: deze gebruiken we voor de interactive map waar je alle stations op te zien krijgt.

## Extra informatie

Hieronder is een demo video te vinden waarin de basisfunctionaliteit getoond wordt:
https://drive.google.com/file/d/1onu23yrzPmyS0t7W1eOlTxqCmXy1u7q2/view?usp=sharing
