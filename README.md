# colorGame

## Kuvaus

Colorgame on peli jossa pelaajan on tarkoitus veikata neljän värin järjestys. 
Peli kertoo kuinka monta väriä edellisistä veikkauksista on oikein ja montako väriä esiintyy vastauksessa toisessa paikassa.
Peli päättyy kun pelaaja veikkaa oikean väririvin tai käyttä kaikki veikkauksensa.

GameMain-luokka aloittaa ohjelman suorituksen. Kun pelaaja aloittaa pelin nappia painamalla, siirtyy päätoiminta GameUI-luokalle.
GameUI-luokka sisältää pelin käyttöliittymän ja GameLogic-luokka sisältää pelin sisäisen logiikan.
Pelaajan pelatessa ja nappeja painellessa, lähettää GameUI tietoa tästä GameLogicille. 
GameLogic puolestaan kertoo GameUI:lle, miten peli etenee ja miten käyttöliittymää päivitetään pelin edistyessä.


Ohjelma on kehitetty ja testattu virtuaalisessa kehitysympäristössä. Käytetty versio on Turun yliopiston tarjoama Java development VM 2022-01-09 (mikä oli myös kurssi suosituksessa).

