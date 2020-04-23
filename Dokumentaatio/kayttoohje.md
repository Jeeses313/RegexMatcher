# Käyttöohje  

### Asennus  
1. Sovellus vaatii [Javan](https://www.java.com/en/) suorittamiseen.
2. Lataa suoritettava jar [tästä](https://github.com/Jeeses313/RegexMatcher/releases/latest/download/RegexMatcher.jar).  
3. Sovellus on valmis suoritettavaksi.  

### Käynnistys ja käyttö  
1. Avaa komentokehote.  
2. Käytä komentoa ```cd``` siirtyäksesi hakemistoon, jossa ladattu jar on.  
3. Käynnistä sovellus komennolla ```java -jar RegexMatcher.jar```.  
4. Anna sovellukselle säännöllinen lauseke ja merkkijonoja, joiden säännöllisen lausekkeen määrittelemään kieleen kuulumisen haluat tarkistaa.  
5. Sovellus tulostaa tarkemmat ohjeet sen käynnistyessä ja ohjeet saa tulostettua milloin tahansa antamalla syötteen ```-i```.  
6. Sovelluksen voi sulkea milloin tahansa antamalla syötteen ```-q```.  

### Komentorivitoiminnot 

#### Testaus

Testit komennolla:

```
mvn test
```

Testikattavuusraportti komennolla:

```
mvn jacoco:report
```  
tai yksityisten metodien kanssa:  
```
mvn javadoc:javadoc -Dshow=private
```  

Kattavuusraportti löytyy: _target/site/jacoco/index.html_

#### Suoritettavan jarin generointi

Komento:

```
mvn package
```

Jar löytyy: _target_

#### JavaDoc

JavaDoc komennolla:

```
mvn javadoc:javadoc
```

JavaDoc löytyy: _target/site/apidocs/index.html_

#### Checkstyle

Tiedostossa [checkstyle.xml](https://github.com/Jeeses313/RegexMatcher/blob/master/RegexMatcher/checkstyle.xml) määrittellyt tarkistukset komennolla:

```
mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset löytyy: _target/site/checkstyle.html_
