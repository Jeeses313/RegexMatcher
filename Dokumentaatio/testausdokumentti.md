# Testausdokumentti  

### Mitä on testattu, miten tämä tehtiin  
JUnitilla on testattu kaikkien luokkien toimintaa erilaisissa tapauksissa, kuten NFA:n muodostavalle luokalle on annettu erilaisia säännöllisiä lausekkeita ja katsottu palauttaako se toimivan automaatin ja 
merkkijonojen säännöllisen kieleen tarkistamisen suorittavalle luokalle on annettu erilaisia automaatteja ja katsottu, että se tarkistaa merkkijonot oikein. Testikattavuuden voi katsoa dokumentti kansiossa olevasta jacoco kansiosta.  
Automaatin rakennetta on testattu antamalla ohjelmalle säännöllinen lauseke, tulostamalla se ja piirtämällä tulosteen mukainen automaatti. Tällä tavoin sai myös suunniteltua ohjelman toimintaa ja löytyi ainakin yksi virhe DFA:n muodostukseen liittyen, josta alla enemmän.  
Suorituskykytestaus on toteutettu luokilla [MatcherPerformanceTest.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/MatcherPerformanceTest.java) ja [FactoryPerformanceTest.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/FactoryPerformanceTest.java).   

### Minkälaisilla syötteillä testaus tehtiin  
JUnitilla testausta on tehty monilla erilaisilla säännöllisillä lausekkeilla, jotka sisältävät yhden tai usemapia merkkejä, joiden toteutus eroaa toisistaan.  
Esim. ., a*, a+, a?, [a1-9]*, (ab|cd)* ja a|b.  
Myös monia erilaisia ei valideja säännöllisiä lausekkeita on myös testattu.  
Esim. ]a, [a, [a-.], [a-9], (*a), +a, ?a ja @.  

Suorituskykytestausksessa merkkijonojen tarkistamiseen on käytetty säännöllisiä lausekkeita a*b*c*, ab*c|a*bc|abc*, ([a-z]*[0-9])+ ja (abc|[0-9]*|a*b*c*)+ 
ja automaattien muodostamiseen on käytetty säännöllisiä lausekkeita, joissa on 1, 10, 100 ja 1000 kertaa peräkkäin a*, a+, a?, (a, jonka jälkeen 1, 10 ja 100 kertaa ), ja a|, jonka jälkeen yksi a.  

### Miten testit voidaan toistaa  
Testit voidaan toistaa lataamalla projekti ja suorittamalla testit antamalla komentokehotteelle komento ```mvn test```, suorittamalla ohjelman tai suorittamalla suorituskykytestaukseen tarkoitetut luokat.  

### Ohjelman toiminnan empiirisen testauksen tulosten esittäminen graafisessa muodossa  
#### Automaattien rakenteen testaus  
##### NFA:n muodostus  
Erilaisten säännöllisten lausekkeiden muuttaminen automaateiksi [NFAfactory.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/util/NFAfactory.java):lla.  
![alt text](https://github.com/Jeeses313/RegexMatcher/blob/master/Dokumentaatio/kuvat/saannollinenlauseke3.png)  
Tarkemmat tiedot automaatin muodostamisesta löytyvät luokan dokumentaatiosta ja koodista.  

##### DFA:n muodostus  
Kun ohjelmalle antaa syötteen [a-z]*a, se antaa tiloja ja niistä lähteviä siirtymiä seuraavan kuvan mukaisesti.  
![alt text](https://github.com/Jeeses313/RegexMatcher/blob/master/Dokumentaatio/kuvat/saannollinenlauseke1.png)  
Kuvassa oikealla ylhäällä on automaatti, joka muodostettiin aiemmin, kun yritettiin muodostaa DFA:ta. Automaatti ei ole DFA, koska samasta tilasta(0,1,3), lähtee siirtymä a:lla kahteen eri tilaan. 
Virhe johtui siitä, että DFA:ta tehdessä ei osattu yhdistää samaksi DFA:n tilaksi tiloja, joihin siirrytään samalla kirjaimella, mutta eivät ole yhteydessä toisiinsa tyhjien merkkien siirtymillä.  

Ongelma on ratkaistu siten, että DFA:n muodostuksessa käydään ensin läpi kaikki siirtymät ja kerätään saman merkin siirtymien maalitilat samaan DFA:n tilaan ennen kuin katsotaan, mihin muihin tiloihin tyhjillä siirtymillä päästään.  

Virhe myös tarkistetaan JUnitilla antamalla kahdelle merkkijonojen tarkistajalle, joista toinen tarkistaa NFA:n ja toinen DFA:n tarkistusmenetelmällä, yllä olevan säännöllisen lausekkeen mukaan muodostettu DFA. Tämän 
jälkeen tarkastajille annetaan tarkistettavaksi merkkijono "a". NFA:n menetelmää käyttävä kertoo, että merkkijono kuuluu kieleen kuten pitääkin. DFA:n menetelmää käyttävä kertoo, että merkkijono ei kuulu kieleen, koska 
se yrittää edetä a-z siirtymää pitkin ja huomaa, että tila ei ole hyväksyvä. DFA:n menetelmää käyttäessä ei peruuteta, koska sille ei pitäisi olla tarvetta ja siirtymä a-z on aina siirtymien listassa ensimmäisenä, koska 
automaatti muodostetaan käymällä säännöllisen lausekkeen merkkejä järjestyksessä.  

##### NFA:n tyhjät silmukat  
Kun ohjelmalle antaa syötteen (a*b*c*)+, se antaa tiloja ja niistä lähteviä siirtymiä seuraavan kuvan mukaisesti.  
![alt text](https://github.com/Jeeses313/RegexMatcher/blob/master/Dokumentaatio/kuvat/saannollinenlauseke2.png)  
Kuvassa ilmenee ongelma, joka haittaa NFA:n käyttöä merkkijonojen kieleen kuulumisen tarkistamista. Automaatissa on tyhjä silmukka, jota 
pitkin NFA:ta käyttäessä tarkistuksen syvyyshaku kulkee, kunnes tapahtuu StackOverflowError metodipinon kasvaessa paljon.  

Tappoja korjata ongelma/pohdintaa ongelman korjaamiseksi:  

Ongelman voisi jokseenkin välttää käyttämällä tarkistuksessa leveyshakua, vaikka sekin menisi silmukkaa ympäri, mutta silloin, kun merkkijono kuuluu kieleen, se voi 
lopulta vastatata oikein. Esim. kuvassa ei ohitettaisia tilaa 9, vaan tarkistettaisiin ensin 1 ja sitten 9.  

Toinen osittainen korjaus tähän syvyyshakua käyttäessä on tehdä sulkujen ja plussan jälkeen ensin siirtymä uuteen tilaan ja sen jälkeen siirtymä takaisin vanhaan, jolloin käydään ensin tilassa 9 ja 
sitten 1. Tämä ei kuitenkaan ratkaisee ongelmaa sulkujen ja tähden kanssa, koska siirtymä uuteen tilaan ei lähde nykyisestä tilasta vaan vanhasta, jolla on jo ennestää siirtymiä, jotka johtavat silmukkaan. 
Tämänkin voi kuitenkin osittain korjata muuttamalla vanhan tilan siirtymien järjestystä, jolloin ensin testataan siirtymää uuteen tilaan. Kumpikaan korjauksista ei korjaa ongelmaa silloin, kun merkkijono 
ei kuulu kieleen. Tämän taas voi korjata try-catch:lla nappaamalla StackOverflowError, jolloin ylivuodon tapahtuessa kerrotaan, että merkkijono ei kuulu kieleen. Ratkaisu ei ole paras mahdollinen, mutta toimii näissä tapauksissa. 
Ongelma ei kuitenkaan häviä, jos säännöllisessä lausekkeessa on tai-osio sulkujen sisällä, jolloin tarkitukseen kuluu todella paljon aikaa, koska haun peruuttaessa 
tai-osion aiheuttamaan risteykseen, käydään kaikki siirtymät läpi ja kaikissa edetään kunnes tapahtuu ylivuoto. Aikaa kuluu vielä enemmän, koska risteykseen saavutaan usein, koska se on osaa silmukkaa. 
Tähänkin osittainen korjaus on ensimmäisen ylivuodon jälkeen laittaa muistiin, että tarkistus on rikki, jolloin syvyyshaku lopetetaan ja kerrotaan, että merkkijono ei kuulu kieleen, mikä taas voi johtaa joissakin tapauksissa siihen, että annetaan väärä vastaus.  

Lopullisena ja toimivana korjauksena käytetään listaa, johon laitetaan sellaisia tiloja muistiin, joissa on käyty, kuten syvyyshauissa yleensäkin saatetaan tehdä. Tiloja laitetaan muistiin, vain silloin kun niihin siirrytään tyhjällä merkkillä ja 
muisti tyhjennetään aina, kun kuljetaan ei tyhjän merkin siirtymää pitkin. Tila lisätään muistiin myös silloin, kun ei tyhjän merkin siirtymä 
johti umpikujaan, ettei tai-osioiden saman kirjaimen siirtymät, esim. (ab|ac)*, aiheuta silmukkaan juuttumista listan tyhjentämisen takia. Näin vältetään silmukassa kulkeminen, ylivuodon aiheuttama virhe, ja jo käytyihin tiloihin pääsemättömyys, kun niihin pitäisi oikeasti päästä.  

Virhe tarkistetaan JUnitilla antamalla merkkijonon tarkistajalle kolme ylläolevan pohdinnan mukaisesta säännöllisestä lausekkeesta muodostetut automaatit ja tarkistetaan erilaisia merkkijonoja NFA:n tarkistusmenetelmällä.  

#### Suorituskykytestaus  
##### [Automaattien muodostus](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/FactoryPerformanceTest.java)  
NFA:n ja DFA:n muodostamisnopeuksien vertailu on turhaa, koska ohjelma vaatii NFA:n muodostuksen DFA:n muodostamiseksi, mutta erilaisten säännöllisten lausekkeiden muuttaminen automaateiksi voi tuottaa kiinnostavia tuloksia.  

Testeissä muodostetaan erilaisia toistuvia merkkiyhdistelmiä sisältäviä säännöllisiä lausekkeita 1, 10, 100 ja 1000 merkkiyhdistelmän kokoisina ja jokaisesta säännöllisestä lausekkeesta muodostetaan tuhat kertaa NFA ja DFA ja sitten lasketaan näiden tuhannen muodostamiseen kuluvan 
ajan mediaanin. Testeissä käytetään mediaania, koska Javan toiminta voi aiheuttaa/aiheuttaa hyvin suuria eroja ajoissa, mikä kasvattaisi keskiarvoa paljon. Testeistä saadaan seuraavat tulokset:  

```  
NFA: Brackets(1): 3600ns
DFA: Brackets(1): 6900ns
NFA: Brackets(10): 7300ns
DFA: Brackets(10): 19700ns
NFA: Brackets(100): 16300ns
DFA: Brackets(100): 162300ns
NFA: Brackets(1000): 95300ns
DFA: Brackets(1000): 4762600ns
------------------------
NFA: Star(1): 400ns
DFA: Star(1): 1700ns
NFA: Star(10): 1100ns
DFA: Star(10): 7200ns
NFA: Star(100): 6600ns
DFA: Star(100): 80700ns
NFA: Star(1000): 65800ns
DFA: Star(1000): 6478900ns
------------------------
NFA: Plus(1): 500ns
DFA: Plus(1): 600ns
NFA: Plus(10): 1100ns
DFA: Plus(10): 8300ns
NFA: Plus(100): 7300ns
DFA: Plus(100): 1360900ns
NFA: Plus(1000): 71700ns
DFA: Plus(1000): 986163100ns
------------------------
NFA: Question mark(1): 500ns
DFA: Question mark(1): 500ns
NFA: Question mark(10): 1100ns
DFA: Question mark(10): 7100ns
NFA: Question mark(100): 8600ns
DFA: Question mark(100): 1497900ns
NFA: Question mark(1000): 72400ns
DFA: Question mark(1000): 856037800ns
------------------------
NFA: Or(1): 600ns
DFA: Or(1): 500ns
NFA: Or(10): 1500ns
DFA: Or(10): 1100ns
NFA: Or(100): 7800ns
DFA: Or(100): 17400ns
NFA: Or(1000): 75900ns
DFA: Or(1000): 1101400ns 
```  

![alt text](https://github.com/Jeeses313/RegexMatcher/blob/master/Dokumentaatio/kuvat/suorituskykytestaus1.png)  
![alt text](https://github.com/Jeeses313/RegexMatcher/blob/master/Dokumentaatio/kuvat/suorituskykytestaus3.png)  

Tuloksista huomataan, että NFA:n muostukseen kuluva aikaa kasvaa lienaarisesti tai lineaarista hitaammin. Tuloksista huomaa myös, että sulkuja sisältävien lausekkeiden muuttaminen NFA:ksi kuluttaa eniten aikaa, mikä 
johtuu siitä, että sulkuja käyttäessä lausekkeista on tullut pidempiä, koska kahden merkin, eli a*, a+ ja a?, tuleekin kolme, (a), eli käsitellään enemmän merkkejä.  

DFA:n tuloksista oudoimpana on yhden ja kymmenen merkkiyhdistelmän tai-osion sisältävät lausekkeet, koska ne ovat jostain syystä nopeampia kuin NFA:n muodostus, vaikka DFA:ta muodostaessa aina muodostetaan NFA ensin. Tämä voi johtua Javan toiminnasta ja siitä, että käytetään mediaania. 
Muuten DFA:n muodostamiseen kuluva aika kasvaa lienaarisesti tai lineaarista hitaammin, mutta plussia ja kysymysmerkkejä sisältävien lausekkeiden ajat kasvavat todella paljon, kun 1000 merkin säännöllisten lausekkeiden muuttamiseen menee lähes 1s muutaman millisekuntin sijaan. Kysymysmerkkejä sisältävien pitkä kesto johtuu 
pitkistä tyhjien merkkien siirtymäketjuista, jolloin DFA:ta muodostaessa joudutaan käymään paljon tiloja läpi jokaisen siirtymän jälkeen, että saataisiin muodostettua yhdistetty tila. Plussia sisältävillä näin ei pitäisi käydä, kun taas tähtiä sisältävillä, joiden aika on paljon lyhyempi, näin pitäisi käydä, eli testeissä 
saattaa tapahtua Javan aiheuttaamaa häiriötä.  

![alt text](https://github.com/Jeeses313/RegexMatcher/blob/master/Dokumentaatio/kuvat/suorituskykytestaus4.png)  

Kuten kuvasta näkyy, kysymysmerkkejä sisältävän säännöllisen lausekkeen NFA:ssa a-siirtymien jälkeen päästään tyhjien merkkien siirtymien kautta kaikkiin nykyistä tilaa seuraaviin tilohin, joihin ei ole siirtymää a:lla. 
Tähtiä sisältävässä tapahtuu samoin, kun taas plussia sisältävässä päästään vain a-siirtymää edeltävään tilaan ja yhteen seuraavaan tilaan.

##### [Merkkijonojen tarkistaminen](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/MatcherPerformanceTest.java)  
On selvää, että DFA on NFA:ta nopeampi, mutta DFA:n muodostuksessa kestää kauemmin. Tämän takia on hyvä tutkia miten paljon/millaisia merkkijonoja pitää tarkistaa, että DFA:n muodostaminen on nopeampaa.  

Testeissä mudostetaan erilaisista säännöllisistä lausekkeista NFA ja DFA ja tarkistetaan molemmilla erilaisia merkkijonoja. Merkkijonot ovat kaikki a-d ja 0-3 merkkien yhdistelmät testin suluissa olevan numeron osoittamaan pituuteen saakka, eli merkkijonoja on (8^n)+(8^n-1)+...+(8^0). 
Muodostus ja tarkistukset tehdään tuhat kertaa ja näistä lasketaan automaatin muodostuksen ja tarkistusten yhteen kuluvan ajan mediaani. 
Testeissä käytetään mediaania, koska Javan toiminta voi aiheuttaa/aiheuttaa hyvin suuria eroja ajoissa, mikä kasvattaisi keskiarvoa paljon. Testeistä saadaan seuraavat tulokset:  

```  
NFA: Simple expression(1): 2400ns
DFA: Simple expression(1): 13200ns
Difference(NFA-DFA): -10800
NFA: Simple expression(2): 5000ns
DFA: Simple expression(2): 4600ns
Difference(NFA-DFA): 400
NFA: Simple expression(3): 33100ns
DFA: Simple expression(3): 5100ns
Difference(NFA-DFA): 28000
NFA: Simple expression(4): 123700ns
DFA: Simple expression(4): 21200ns
Difference(NFA-DFA): 102500
NFA: Simple expression(5): 680200ns
DFA: Simple expression(5): 85400ns
Difference(NFA-DFA): 594800
NFA: Simple expression(6): 5297500ns
DFA: Simple expression(6): 692300ns
Difference(NFA-DFA): 4605200
NFA: Simple expression(7): 42524900ns
DFA: Simple expression(7): 5882500ns
Difference(NFA-DFA): 36642400
------------------------
NFA: Simple expression with or(1): 1600ns
DFA: Simple expression with or(1): 8100ns
Difference(NFA-DFA): -6500
NFA: Simple expression with or(2): 2500ns
DFA: Simple expression with or(2): 8300ns
Difference(NFA-DFA): -5800
NFA: Simple expression with or(3): 13300ns
DFA: Simple expression with or(3): 7200ns
Difference(NFA-DFA): 6100
NFA: Simple expression with or(4): 100000ns
DFA: Simple expression with or(4): 20700ns
Difference(NFA-DFA): 79300
NFA: Simple expression with or(5): 736800ns
DFA: Simple expression with or(5): 71500ns
Difference(NFA-DFA): 665300
NFA: Simple expression with or(6): 5799700ns
DFA: Simple expression with or(6): 563100ns
Difference(NFA-DFA): 5236600
NFA: Simple expression with or(7): 46769900ns
DFA: Simple expression with or(7): 5053000ns
Difference(NFA-DFA): 41716900
------------------------
NFA: Difficult expression(1): 1500ns
DFA: Difficult expression(1): 38800ns
Difference(NFA-DFA): -37300
NFA: Difficult expression(2): 5700ns
DFA: Difficult expression(2): 36400ns
Difference(NFA-DFA): -30700
NFA: Difficult expression(3): 54900ns
DFA: Difficult expression(3): 40600ns
Difference(NFA-DFA): 14300
NFA: Difficult expression(4): 630500ns
DFA: Difficult expression(4): 120000ns
Difference(NFA-DFA): 510500
NFA: Difficult expression(5): 6054300ns
DFA: Difficult expression(5): 815400ns
Difference(NFA-DFA): 5238900
NFA: Difficult expression(6): 57443400ns
DFA: Difficult expression(6): 7624100ns
Difference(NFA-DFA): 49819300
NFA: Difficult expression(7): 527586800ns
DFA: Difficult expression(7): 69705300ns
Difference(NFA-DFA): 457881500
------------------------
NFA: Difficult expression with or(1): 1600ns
DFA: Difficult expression with or(1): 41400ns
Difference(NFA-DFA): -39800
NFA: Difficult expression with or(2): 6100ns
DFA: Difficult expression with or(2): 41300ns
Difference(NFA-DFA): -35200
NFA: Difficult expression with or(3): 54900ns
DFA: Difficult expression with or(3): 44000ns
Difference(NFA-DFA): 10900
NFA: Difficult expression with or(4): 526800ns
DFA: Difficult expression with or(4): 86400ns
Difference(NFA-DFA): 440400
NFA: Difficult expression with or(5): 4970500ns
DFA: Difficult expression with or(5): 451100ns
Difference(NFA-DFA): 4519400
NFA: Difficult expression with or(6): 43841100ns
DFA: Difficult expression with or(6): 3531400ns
Difference(NFA-DFA): 40309700
NFA: Difficult expression with or(7): 380261800ns
DFA: Difficult expression with or(7): 29502000ns
Difference(NFA-DFA): 350759800
```  

![alt text](https://github.com/Jeeses313/RegexMatcher/blob/master/Dokumentaatio/kuvat/suorituskykytestaus2.png)  

Tuloksista huomataan, että NFA:n käyttäminen on tehokkaampaa, kun tarkistettavia merkkijonoja on vähän, mikä on aika selvää, kun DFA:n muodostamiseen menee enemmän aikaa, mutta sen käyttäminen on nopeampaa. 
Vaikeammilla säännöllisillä lausekkeilla NFA:n ja DFA:n ero kasvaa huomattavasti merkkijonojen määrän kasvaessa, mikä voi johtua siitä, että merkkijonojen pituudet kasvavat, jolloin NFA:n tarkistuksessa joudutaan tekemään enemmän tyhjien siirtymien läpikäyntiä ja peruutusta.  

Erikoisena tuloksena on kuitenkin se, että vaikea säännöllinen lauseke ilman tai-osioita on vaativampi kuin vaikea säännöllinen lauseke niiden kanssa, koska tai-osioiden takia täytyy tehdä paljon peruutusta ja tarkistaa eri haaroja. Yksinkertaisilla 
säännöllisillä lausekkeilla tai-osion sisältävä on kuitenkin vaativampi, eli tulokseen on voinut vaikuttaa valitut säännölliset lausekkeet tai Javan toiminta.



















