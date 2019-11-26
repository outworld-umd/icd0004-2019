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
### Unit testid
Projekti Unit Teste sisaldav test-fail
### Mockimine

### Integratsiooni testid


### UI testid

## Testide käivitamine
Selleks, et jooksutada testid, tuleb käivitada vastav ```.java``` fail ```test/``` kaustast.

Projekti ```test/``` kataloogis asub mitu alamkataloogi:
- ```unittest/UnitTests.java``` - üldised ühiktestid;
- ```mock/```
    - ```WeatherApiMockTests.java``` - API mock testid; saab käivitada ka siis, kui internetiühendus puudub;
    - ```WeatherFileMockTests.java``` - I/O mock testid; saab käivitada ka siis, kui sisendfaili pole olemas;
- ```integration/IntegrationTests.java``` - integratsiooni testid;
- ```ui_tests/UITests.java``` - UI testid.

## Hindamisel jälgin, et oleks olemas järgmised punktid
- Sõltuvusute haldamiseks on kasutatud vastavad vahendid (Maven, Gradle, NPM, NuGet vms)
- Implementeeritud mingit sorti jälgitavust, nt Logimine: request on logitud maha, response on logitud maha, vms.
- Projektil peab olema README, milles on välja toodud: üldine testimise strateegia ja lähenemine
