# Toteutusdokumentti  

### Ohjelman yleisrakenne  
Ohjelmassa on 11 luokkaa, jotka voidaan jakaa pääluokaksi, tietorakenteiksi, käyttöliittymäksi, ohjelman päätoiminnan toteuttaviksi luokiksi ja apuluokiksi.  

* Pääluokkana toimii [RegexMatcher.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/RegexMatcher.java), jolla ohjelma käynnistetään.  
* Tietorakenteina toimivat [Stack.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/Stack.java) ja [List.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/List.java)  
* Ohjelman päätoiminnan toteuttaa [Matcher.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/Matcher.java), jota käytetään merkkijonojen annetun säännöllisen lausekkeen kieleen 
kuulumisen tarkistamiseen. Luokan käyttämän verkon/automaatin muodostamiseen käytetään luokkia [NFAfactory.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/NFAfactory.java) ja 
[DFAfactory.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/DFAfactory.java). Kaikkia kolmea luokkaa avustaa myös verkon/automaatin rakenteessa käytetyt luokat 
[Node.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/Node.java), [DFAnode.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/DFAnode.java) ja 
[Edge.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/Edge.java).  
* Käyttöliittymän toteuttaa [TextUserInterface.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/TextUserInterface.java) ja 
sen käyttöön oikean [matcherin](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/Matcher.java) antaa luokka 
[MatcherFactory.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/MatcherFactory.java).  

Siis ohjelman käynnistyttyä [TextUserInterface.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/TextUserInterface.java) tulostaa ohjeet ohjelman käyttämiselle ja pyytää säännöllistä lauseketta. Säännöllinen lauseke 
annetaan [MatcherFactory.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/MatcherFactory.java):lle, joka antaa sen eteenpäin [NFAfactory.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/NFAfactory.java):lle, 
joka muodostaa automaatin, NFA:n, joka annetaan [Matcher.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/Matcher.java):lle. Halutessa muodostettu automaatti voidaan antaa myös 
[DFAfactory.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/DFAfactory.java):lle joka muodostaa siitä uuden automaatin, DFA:n, joka annetaan taas [Matcher.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/Matcher.java):lle. 
Tämän jälkeen [TextUserInterface.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/TextUserInterface.java) pyytää merkkijonoja, jotka annetaan [Matcher.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/Matcher.java):lle, joka 
palauttaa totuusarvon, jonka avulla [TextUserInterface.java](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/TextUserInterface.java) voi kertoa, kuuluuko annettu merkkijono aiemmin annetun säännöllisen lausekkeen kieleen.

### Saavutetut aika- ja tilavaativuudet  

#### [NFA:n muodostus](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/NFAfactory.java)  
NFA:ta muodostaessa käydään annettua säännöllistä lauseketta läpi ja tehdään tiloja vastaantulevien merkkien mukaisesti.  
```
for(char in expression)
	makeNode(char)
```  
Pisteet ja hakasulkujen sisällä olevat väliviivat tekevät siirtymiä enintään sallittujen merkkien verran, jolloin siirtymien tekemisen aikavaativuus on O(1). Näin NFA:n muodostamisen aikavaativuus on O(n), jossa n on säännöllisen lausekkeen pituus.  
Jokaista säännöllisen lausekkeen merkistä tehdään uusi tila ja jokaisesta tilasta voi olla sallittujen merkkien verran siirtymiä toiseen tilaan tai yksittäisiä siirtymiä useampaan tilaan, esim. sulkujen, tai, tähden ja plussan takia, jolloin tilojen ja siirtymen tilavaativuus on O(n), jossa n on säännöllisen lausekkeen pituus. 
Sulkujen ja tai-osioiden muistiin käytettävät tilat ovat myös O(n), koska pahimmassa tapauksessa jokainen tila voi aloittaa sulkuosion ja olla tai-osion loppu, jolloin ne talletetaan listaan, eli NFA:n muodostamisen tilavaativuus on O(n).  

#### [NFA:n käyttö](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/Matcher.java)  
NFA:ta käyttäessä merkkijonojen kieleen kuulumisen tarkistamiseen käydään läpi annettua merkkijonoa ja syvyyshaulla automaatin tiloja.  
```
boolean seacrch(string, index, state)
	if(index == string.length)
		if(automate.get(state).isEnd())
			return true
	result = false
	for(edge of automate.get(state).edgeList)
		if(edge.char == string.charAt(index))
			result = search(string, index + 1, edge.goalState)
		else if(edge.char == empty_char)
			result = search(string, index, edge.goalState)
		if(result)
			break
	return result
```  
Koska haussa käydään kaikki annetun merkkijonon kirjaimet ja pahimmassa tapauksessa kaikki automaatin tilat, jotka on muodostettu annetun säännöllisen lausekkeen mukaan, aikavaativuus voisi olla O(n+m). 
Tässä käytetyssä syvyyshaussa tosin ei katsota, onko tilassa jo käyty ja koska automaatti on NFA, siinä on siirtymiä tyhjillä merkeillä, joten jokaista merkkiä on mahdollista käydä kaikissa tiloissa, jolloin NFA:n käyttämisen 
aikavaativuus on O(n*m), jossa n on annetun merkkijonon pituus ja m on annetun säännöllisen lausekkeen pituus.  
Koska syvyyshaun eteneminen kasvatta metodien pinoa ja pahimmassa tapauksessa merkkijonon kieleen kuulumisen tarkistuksessa käydään kaikissa tiloissa ilman peruuttamista, joissakin tiloissa voidaan käydä useampiakin kertoja 
tyhjien merkkien siirtymien takia, NFA:n käyttämisen tilavaativuus on O(m), jossa m on annetun säännöllisen lausekkeen pituus, kun ajatellaan automaatin olevan valmiina käytettävissä.

#### [DFA:n muodostus](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/DFAfactory.java)  
ToDo

#### [DFA:n käyttö](https://github.com/Jeeses313/RegexMatcher/blob/master/src/main/java/regexmatcher/Matcher.java)  
DFA:ta käyttäessä merkkijonojen kieleen kuulumisen tarkistamiseen käydään läpi annettua merkkijonoa, automaatin tiloja ja tilojen vieruslistoja. Vieruslistoista etsitään siirtymää, jonka merkki on sama kuin merkkijonossa tarkistettava merkki.  
Koska kyseessä on DFA, jokaista merkkijonon merkkiä kohden käydään yhdessä tilassa, koska ei ole siirtymiä tyhjillä merkeillä. Samasta syystä tilojen vieruslistoilla on vain yksi siirtymä sallittuja merkkejä kohden, eli siirtymillä on vakio maksimi. 
```
for(char in string)
	for(edge in nodes.get(currentNode).edgeList)
		if(edge.char == char)
			return true
```  
Näin aikavaativuus on O(n), jossa n on annetun merkkijonon pituus.  
Koska tässä ei käytetä apuna listoja yms., vaan vain kokonaislukuja ja totuusarvoja, kun ajatellaan automaatin olevan valmiina käytettävissä, tilavaativuus on O(1).  

### Työn mahdolliset puutteet ja parannusehdotukset  

### Lähteet  
* [https://en.wikipedia.org/wiki/Regular_expression](https://en.wikipedia.org/wiki/Regular_expression)  
* [https://en.wikipedia.org/wiki/Nondeterministic_finite_automaton](https://en.wikipedia.org/wiki/Nondeterministic_finite_automaton)  
* Introduction to the theory of computation - Michael Sipser  
* Tietorakenteet ja algoritmit - Antti Laaksonen