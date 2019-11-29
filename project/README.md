# WeatherWise
## Kirjeldus
WeatherWise on rakendus, mis loeb sisse failist linnade nimed ja tagastab iga linna kohta eraldi ```{linna_nimi}.json``` faili, mis koosneb järgmistest andmetest:
- ```weatherReportDetails``` - ilmateate detailid;
    - ```city``` - linn, mille kohta tagastati ilmateade;
    - ```coordinates``` - linna koordinaadid;
    - ```temperatureUnit``` - temperatuuri ühik;
- ```currentWeatherReport``` - hetkeilma informatsioon;
    - ```date``` - tänane päev;
    - ```temperature``` - temperatuur hetkel;
    - ```humidity``` - õhuniiskus hetkel;
    - ```pressure``` - õhurõhk hetkel;
- ```forecastReport``` - ilmaprognoos 3 päevaks;
    - ```date``` - päev, milleks koostati prognoos;
    - ```weather``` - ilmaprognoos selleks päevaks;
        - ```temperature``` - temperatuur (päeva keskmine);
        - ```humidity``` - õhuniiskus (päeva keskmine);
        - ```pressure``` - õhurõhk (päeva keskmine).

Näide väljundfailist:
```json
{
  "weatherReportDetails": {
    "city": "Tallinn",
    "coordinates": "59.44,24.75",
    "temperatureUnit": "Celsius"
  },
  "currentWeatherReport": {
    "date": "2019-11-25",
    "temperature": -6.91,
    "humidity": 85,
    "pressure": 1024
  },
  "forecastReport": [
    {
      "date": "2019-11-26",
      "weather": {
        "temperature": -1,
        "humidity": 81,
        "pressure": 1014
      }
    },
    {
      "date": "2019-11-27",
      "weather": {
        "temperature": 1,
        "humidity": 92,
        "pressure": 1006
      }
    },
    {
      "date": "2019-11-28",
      "weather": {
        "temperature": 2,
        "humidity": 93,
        "pressure": 998
      }
    }
  ]
}
```

## Nõuded ja täpsustused
Rakendus nõuab _Java 11_ või hiljema versiooni.

Sõltuvuste haldamiseks on kasutatud _Apache Maven_ raamistikku.

Projektis on implementeeritud jälgitavust _Simple Logging Facade for Java_ kaudu. ```WeatherFile``` 
klassis on jälgitav failide lugemine/kirjutamine, ```WeatherApi``` klassis on jälgitavad API _request_-id
 ja _response_-id, ```WeatherWise``` klassis on näha ilmateate genereerimist. Loomulikult on erindid
 ja vead samuti jälgitavad.
 
## Käivitamine
Selleks, et käesolev rakendus käivitada, tuleb esialgu luua ```WeatherWise``` klassi objekt. 
```WeatherWise``` konstruktoril on kaks võimalikku parameetrit: ```WeatherApi``` ja ```WeatherFile``` klasside objektid,
kuid ```WeatherWise``` objekti saab ka ilma nende parameetrideta instantsieerida - siis luuakse uued ülalmainitud objektid.

Näide instantsieerimisest:
```java
WeatherWise weatherWise = new WeatherWise();
```

Seejärel saab välja kutsuda objekti meetod ```getWeatherReportFromFile(String path)```, millel on üks
kohustuslik parameeter - ```String path``` - faili tee, kust soovitakse linnu lugeda. Meetod ise tagastab
```List<WeatherReport>``` - järjendi, mille sees on saadud ilmateated ```WeatherReport``` objekti kujul.

Näide meetodi väljakutsumisest:
```java
weatherWise.getWeatherReportFromFile("src/path/to/file.txt");
```

Sisendfail peab kindlasti olema ```.txt``` formaadis ning sisaldama linnu, iga linn peab olema uuel real.

Näide sisendfailist:
```text
Tallinn
Riga
Vilnius
Helsinki
``` 
## Testimiskava
### Ühiktestid
Siin failis asuvad kõik põhitestid. Siin kontrollitakse, et:
- rakendus reageerib õigesti erinevate sisendite peale: õige/vale linn, suured-väiksed tähed jne.;
- andmed tagastatakse õigel kujul (nt koordinaadid);
- õigesti arvutatakse keskmine temperatuur/õhurõhk/niiskus ilmaprognoosi jaoks;
- andmed, mis peavad jääma samaks, jäävad samaks;
- kuupäevad on vastavas formaadis;
- ilmaprognoos koosneb kolmest päevast, ehk homsest, ülehomsest ning üleülehomsest;
- failide lugemine töötab õigesti - oskab failist lugeda, saab kõik linnad kätte, tühjad sõned jätab välja;
- failide kirjutamine töötab õigesti - kui sisend on korrektne, siis tagastab failid, muul juhul
    viskab vastava erindi;
- vigade puhul visatakse õige erind (I/O erindid, API erindid ja oluliste andmete puudumise erindid);

### Mockimine
Peale ```WeatherWise``` klassi, on rakenduses väga olulised klassid ```WeatherApi``` (suhtleb API-ga) ja
```WeatherFile``` (realiseerib faili lugemise/kirjutamise). Mock-testid aitavad meil veenduda, et
abiklassid töötavad korrektselt.
##### WeatherApiMock
Siin kontrollitakse:
- mitu korda pöördutakse API poole erinevatel tingimustel (õige/vale linn, mitu linna);
- kas API poole tehakse liigsed pöördumised. 
##### WeatherFileMock
Selle faili testid simuleerivad faili lugemist/kirjutamist ja kontrollivad:
- kuidas teised komponendid käsitlevad õigeid andmeid;
- mitu korda pöördutakse faili kirjutamise meetodi poole;
- kuidas muud osad reageerivad valedele andmetele.

### Integratsiooni testid
Nende testide abil kontrollitakse API korrektset tööd.

Testitakse, kas:
- API päring tagastab õige linna puhul koodi 200, vale linna puhul - 404;
- tundmatu linna puhul tagastatakse API viga koos veateatega;
- tagastatakse kõik andmed, mis on vajalikud ilmateate koostamiseks.
### UI testid
UI testid ei sõltu otseselt projektiga. Testimise all olev lehekülg: https://www.ttu.ee/.

Testid valideerivad, kas:
- Tudengi sektsioonis Õppeinfo alt räägitakse "Kvaliteetsest haridusest";
- German Mumma email töötaja otsingus on german.mumma@taltech.ee;
- Sisene sektsioonist ÕIS, Moodle või Siseveeb suunatakse õigesse kohta.

Testid kasutavad _Page Object Pattern_-i. Vajadusel on kõik POP klassid leitavad kataloogist ```src/java/page_object/```.
## Testide käivitamine
Selleks, et jooksutada testid, tuleb käivitada vastav ```.java``` fail ```test/``` kaustast.

Projekti ```test/``` kataloogis asub mitu alamkataloogi:
- ```unittest/UnitTests.java``` - üldised ühiktestid;
- ```mock/```:
    - ```WeatherApiMockTests.java``` - API mock testid; saab käivitada ka siis, kui internetiühendus puudub;
    - ```WeatherFileMockTests.java``` - I/O mock testid; saab käivitada ka siis, kui sisendfaili pole olemas;
- ```integration/IntegrationTests.java``` - integratsiooni testid;
- ```ui_tests/UITests.java``` - UI testid.

Kui on vaja käivitada:
 
- kõik __WeatherWise rakenduse testid__, kasutage käsku: ```mvn test -P WeatherWiseTests```
- __kasutajaliidese testid__, kasutage käsku: ```mvn test -P UITests```
- ainult __üks test-fail__, kasutage käsku: ```mvn -Dtest={failinimi}.java test```
