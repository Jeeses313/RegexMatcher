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
ja automaattien muodostamiseen on käytetty säännöllisiä lausekkeita, joissa on 1, 10 ja 100 kertaa peräkkäin a*, a+, a?, (a, jonka jälkeen 1, 10 ja 100 kertaa ), ja a|, jonka jälkeen yksi a.  

### Miten testit voidaan toistaa  
Testit voidaan toistaa lataamalla projekti ja suorittamalla testit antamalla komentokehotteelle komento ```mvn test```, suorittamalla ohjelman tai suorittamalla suorituskykytestaukseen tarkoitetut luokat.  

### Ohjelman toiminnan empiirisen testauksen tulosten esittäminen graafisessa muodossa  
#### Automaattien rakenteen testaus  
##### DFA: muodostus  
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

Testeissä muodostetaan erilaisia toistuvia merkkiyhdistelmiä sisältäviä säännöllisiä lausekkeita 1, 10 ja 100 merkkiyhdistelmän kokoisina ja jokaisesta säännöllisestä lausekkeesta muodostetaan tuhat kertaa NFA ja DFA ja sitten lasketaan näiden tuhannen muodostamiseen kuluvan 
ajan mediaanin. Testeissä käytetään mediaania, koska Javan toiminta voi aiheuttaa/aiheuttaa hyvin suuria eroja ajoissa, mikä kasvattaisi keskiarvoa paljon. Testeistä saadaan seuraavat tulokset:  

```  
NFA: Brackets(1): 2900ns
DFA: Brackets(1): 6200ns
NFA: Brackets(10): 6100ns
DFA: Brackets(10): 15100ns
NFA: Brackets(100): 16600ns
DFA: Brackets(100): 149500ns
------------------------
NFA: Star(1): 800ns
DFA: Star(1): 3000ns
NFA: Star(10): 3200ns
DFA: Star(10): 47900ns
NFA: Star(100): 10400ns
DFA: Star(100): 126600ns
------------------------
NFA: Plus(1): 600ns
DFA: Plus(1): 900ns
NFA: Plus(10): 1600ns
DFA: Plus(10): 11600ns
NFA: Plus(100): 10700ns
DFA: Plus(100): 2020900ns
------------------------
NFA: Question mark(1): 700ns
DFA: Question mark(1): 700ns
NFA: Question mark(10): 1500ns
DFA: Question mark(10): 9000ns
NFA: Question mark(100): 10100ns
DFA: Question mark(100): 1834400ns
------------------------
NFA: Or(1): 700ns
DFA: Or(1): 600ns
NFA: Or(10): 2000ns
DFA: Or(10): 1500ns
NFA: Or(100): 11100ns
DFA: Or(100): 24300ns 
```  

Tuloksista huomataan, että NFA:n muostukseen kuluva aikaa kasvaa lienaarisesti tai lineaarista hitaammin. Tuloksista huomaa myös, että sulkuja sisältävien lausekkeiden muuttaminen automaatiksi kuluttaa eniten aikaa. Tämä 
johtuu siitä, että sulkuja käyttäessä lausekkeista on tullut pidempiä, koska kahden merkin, eli a*, a+ ja a?, tuleekin kolme, (a), eli käsitellään enemmän merkkejä.  

DFA:n tuloksista oudoimpana on yhden ja kymmenen merkkiyhdistelmän tai-osion sisältävät lausekkeet, koska ne ovat jostain syystä nopeampia kuin NFA:n muodostus, vaikka DFA:ta muodostaessa aina muodostetaan NFA ensin. Tämä voi johtua Javan toiminnasta ja siitä, että käytetään mediaania. 
Muuten DFA:n muodostamiseen kuluva aika kasvaa lienaarisesti tai lineaarista hitaammin, mutta plussia ja kysymysmerkkejä sisältävien lausekkeiden ajat kasvavat todella paljon, ~10000ns -> ~2000000ns verrattuna ~20000ns -> 130000ns. Kysymysmerkkejä sisältävien pitkä kesto johtuu 
pitkistä tyhjien merkkien siirtymäketjuista, jolloin DFA:ta muodostaessa joudutaan käymään paljon tiloja läpi jokaisen siirtymän jälkeen, että saataisiin muodostettua yhdistetty tila. Plussia sisältävillä näin ei pitäisi käydä, kun taas tähtiä sisältävillä, joiden aika on paljon lyhyempi, näin pitäisi käydä, eli testeissä 
saattaa tapahtua Javan aiheuttaamaa häiriötä.  

##### [Merkkijonojen tarkistaminen](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/MatcherPerformanceTest.java)  
On selvää, että DFA on NFA:ta nopeampi, mutta DFA:n muodostuksessa kestää kauemmin. Tämän takia on hyvä tutkia miten paljon/millaisia merkkijonoja pitää tarkistaa, että DFA:n muodostaminen on nopeampaa.  

Testeissä mudostetaan erilaisista säännöllisistä lausekkeista NFA ja DFA ja tarkistetaan molemmilla erilaisia merkkijonoja. Muodostus ja tarkistukset tehdään tuhat kertaa ja näistä lasketaan automaatin muodostuksen ja tarkistusten yhteen kuluvan ajan mediaani. 
Testeissä käytetään mediaania, koska Javan toiminta voi aiheuttaa/aiheuttaa hyvin suuria eroja ajoissa, mikä kasvattaisi keskiarvoa paljon. Testeistä saadaan seuraavat tulokset:  

```  
NFA: Simple expression: 345100ns
DFA: Simple expression: 39500ns
Difference(NFA-DFA): 305600
------------------------
NFA: Simple expression with or: 27400ns
DFA: Simple expression with or: 32900ns
Difference(NFA-DFA): -5500
------------------------
NFA: Difficult expression: 233400ns
DFA: Difficult expression: 127300ns
Difference(NFA-DFA): 106100
------------------------
NFA: Difficult expression with or: 48400ns
DFA: Difficult expression with or: 118600ns
Difference(NFA-DFA): -70200  
```  

Tuloksista huomataan, että NFA:n käyttäminen on hitaampaa kuin DFA:n muodostaminen ja sen käyttäminen silloin, kun säännöllisessä lausekkeessa on tai-osioita. Tämä johtuu siitä, että tarkistus NFA:lla toimii syvyyshaun tavoin ja tai-osioiden takia joudutaan tekemään paljon 
peruuttamista, koska automaatissa on useita siirtymä vaihtoehtoja, kun DFA:lla on aina vain yksi vaihtoehto. Muulloin on paljon nopeampaa olla muodostamatta DFA:ta ja käyttää vain NFA:ta merkkijonojen tarkistamiseen.  




















