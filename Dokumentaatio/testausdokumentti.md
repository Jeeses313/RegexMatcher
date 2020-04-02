# Testausdokumentti  

### Mitä on testattu, miten tämä tehtiin  
JUnitilla on testattu kaikkien luokkien toimintaa erilaisissa tapauksissa, kuten NFA:n muodostavalle luokalle on annettu erilaisia säännöllisiä lausekkeita ja katsottu palauttaako se toimivan automaatin ja 
merkkijonojen säännöllisen kieleen tarkistamisen suorittavalle luokalle on annettu erilaisia automaatteja ja katsottu, että se tarkistaa merkkijonot oikein. Testikattavuuden voi katsoa dokumentti kansiossa olevasta jacoco kansiosta.  
Myös automaatin rakennetta on testattu antamalla ohjelmalle säännöllinen lauseke, tulostamalla se ja piirtämällä tulosteen mukainen automaatti. Tällä tavoin sai myös suunniteltua ohjelman toimintaa ja löytyi ainakin yksi virhe DFA:n muodostukseen liittyen, josta alla enemmän.  

### Minkälaisilla syötteillä testaus tehtiin  
Testausta on tehty monilla erilaisilla säännöllisillä lausekkeilla, jotka sisältävät yhden tai usemapia merkkejä, joiden toteutus eroaa toisistaan.  
Esim. ., a*, a+, [a1-9]*, (ab|cd)* ja a|b.  
Myös monia erilaisia ei valideja säännöllisiä lausekkeita on myös testattu.  
Esim. ]a, [a, [a-.], [a-9], (*a), +a ja @.  

### Miten testit voidaan toistaa  
Testit voidaan toistaa lataamalla projekti ja suorittamalla testit antamalla komentokehotteelle komento ```mvn test``` tai suorittamalla ohjelman.  

### Ohjelman toiminnan empiirisen testauksen tulosten esittäminen graafisessa muodossa  
#### Automaattien rakenteen testaus  
Kun ohjelmalle antaa syötteen [a-z]*a, se antaa tiloja ja niistä lähteviä siirtymiä seuraavan kuvan mukaisesti.  
![alt text](https://github.com/Jeeses313/RegexMatcher/blob/master/Dokumentaatio/kuvat/saannollinenlauseke1.png)  
Kuvassa oikealla ylhäällä on automaatti, joka muodostettiin aiemmin, kun yritettiin muodostaa DFA:ta. Automaatti ei ole DFA, koska samasta tilasta(0,1,3), lähtee siirtymä a:lla kahteen eri tilaan. 
Virhe johtui siitä, että DFA:ta tehdessä ei osattu yhdistää samaksi DFA:n tilaksi tiloja, joihin siirrytään samalla kirjaimella, mutta eivät ole yhteydessä toisiinsa tyhjien merkkien siirtymillä. 
Nyt DFA:n muodostuksessa käydään ensin läpi kaikki siirtymät ja kerätään saman merkin siirtymien maalitilat samaan DFA:n tilaan ennen kuin katsotaan, mihin muihin tiloihin tyhjillä siirtymillä päästään.  
Virhe myös tarkistetaan JUnitilla antamalla kahdelle merkkijonojen tarkistajalle, joista toinen tarkistaa NFA:n ja toinen DFA:n tarkistusmenetelmällä, säännöllisen lausekkeen mukaan muodostettu DFA. Tämän 
jälkeen tarkastajille annetaan tarkistettavaksi merkkijono "a". NFA:n menetelmää käyttävä kertoo, että merkkijono kuuluu kieleen kuten pitääkin. DFA:n menetelmää käyttävä kertoo, että merkkijono ei kuulu kieleen, koska 
se yrittää edetä a-z siirtymää pitkin ja huomaa, että tila ei ole hyväksyvä. DFA:n menetelmää käyttäessä ei peruuteta, koska sille ei pitäisi olla tarvetta ja siirtymä a-z on aina siirtymien listassa ensimmäisenä, koska 
automaatti muodostetaan käymällä säännöllisen lausekkeen merkkejä järjestyksessä.  

#### Suorituskykytestaus  
##### Automaattien muodostus  
NFA:n ja DFA:n muodostamisnopeuksien vertailu on turhaa, koska ohjelma vaatii NFA:n muodostuksen DFA:n muodostamiseksi, mutta erilaisten säännöllisten lausekkeiden muuttaminen automaateiksi voi tuottaa kiinnostavia tuloksia.  
ToDo 

##### Merkkijonojen tarkistaminen  
On selvää, että DFA on NFA:ta nopeampi, mutta DFA:n muodostuksessa kestää kauemmin. Tämän takia on hyvä tutkia miten paljon/millaisia merkkijonoja pitää tarkistaa, että DFA:n muodostaminen on nopeampaa.  
ToDo

