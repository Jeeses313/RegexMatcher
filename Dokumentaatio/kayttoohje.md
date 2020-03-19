# Käyttöohje  

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

#### JavaDoc

JavaDoc komennolla:

```
mvn javadoc:javadoc
```

JavaDoc löytyy: _target/site/apidocs/index.html_