# Määrittelydokumentti
### Toteutettavia algoritmeja ja tietorakenteita
Ohjelmassa tarvitaan algoritmia säännöllisen lausekkeen automaatiksi muuttamiseksi.  
Automaatti toteutetaan verkkona, eli tarvitaan vieruslista tietorakennettakin.  
Aiemman lisäksi tarvitaan algoritmi, jolla tarkistetaan kuuluvatko annettut merkkijonot säännöllisen lausekkeen kieleen, jolla siis edetään aluksi tehdyssä verkossa syvyyshaun tai leveyshaun tavoin.  
Listan lisäksi voidaan tarvita pinoa verkon muodostamiseen. 
### Ratkaistava ongelma ja miksi valittu
Valitsin ratkaistavaksi ongelmaksi säännöllisten lauseiden tulkin, koska aihe on kiinnostava, kuten monet muutkin aiheet, ja minulla oli valmiiksi idea, jolla aloittaa ratkaiseminen, eli automaatin/verkon muodostus. Valitsin algoritmit ja tietorakenteet, koska niitä tarvitaan ongelmaan ratkaisemiseen tavalla, jolla aion sen ratkaista.
### Ohjelman saama syöte ja sen käyttö 
Ohjelman on tarkoitus ottaa ensin syötteenä säännöllinen lauseke, josta muodostetaan automaatti, jos se on mahdollista. Tämän jälkeen syötteeksi voidaan antaa merkkijonoja, joista kerrotaan, kuuluvatko ne säännöllisen lausekkeen kieleen.  
Säännöllisten lausekkeiden määrittelyssä käytettävä kieli:  
A={w kuuluu {0, ..., 9, a, ..., z, A, ..., Z, (, ), [, ], -, ., *, +, |}* | w sisältää yhtä monta ( ja ) ja w sisältää yhtä monta [ ja ]} 
### Tavoite aika- ja tilavaativuudet
Tavoitteena olisi päästä automaatin muodostuksen kannalta aika- ja tilavaativuuteen O(2^m), jossa m on säännöllisen lausekkeen pituus, ja merkkijonon tarkistuksen kannalta tilavaativuuteen O(1) ja aikavaativuuteen O(n), jossa n on merkkijonon pituus, jos saan tehtyä: säännöllinenlauseke -> NFA -> DFA.  
Jos saan tehtyä vain: säännöllinen lauseke -> NFA, niin automaatin muodostuksen tavoite tila- ja aikavaativuudelle on O(m) ja merkkijonon tarkistukselle tilavaativuus on O(m) ja aikavaativuus O(nm^2).  
### Lähteet
* [https://en.wikipedia.org/wiki/Regular_expression](https://en.wikipedia.org/wiki/Regular_expression)  
* [https://en.wikipedia.org/wiki/Nondeterministic_finite_automaton](https://en.wikipedia.org/wiki/Nondeterministic_finite_automaton)  
* Introduction to the theory of computation - Michael Sipser  
* Tietorakenteet ja algoritmit - Antti Laaksonen

