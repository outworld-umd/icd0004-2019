# Projekt
Rakendus, mis loeb sisse failist linnade nimed ja tagastab eraldi faili:
- hetke temperatuur
- 3 päeva ilmaennustusest leida iga päeva kohta keskmised: temp, niiskus ja õhurõhk. **NB!** jooksvat päeva ei ole vaja arvestada, ehk ennustust on vaja homse, ülehomse ja üleülehomse kohta.
- Koordinaadid kujul lat,lon, nt "59.44,24.75"
- Temperatuuri ühikuks võtke Celsius
- Kasutage nt OpenWeatherMap API-sid (vt allpool 2. etapi juures olev link)

Näide väljundfailist:
```json
{
  "weatherReportDetails": {
    "city": "Tallinn",
    "coordinates": "59.44,24.75",
    "temperatureUnit": "Celsius"
  },
  "currentWeatherReport": {
    "temperature": -5,
    "humidity": 75,
    "pressure": 985
  },
  "forecastReport": [
    {
      "date": "2019-10-01",
      "weather": {
        "temperature": -5,
        "humidity": 75,
        "pressure": 985
      }   
    },
    {
      "date": "2019-10-02",
      "weather": {
        "temperature": -5,
        "humidity": 75,
        "pressure": 985
      }   
    },
    {
      "date": "2019-10-03",
      "weather": {
        "temperature": -5,
        "humidity": 75,
        "pressure": 985
      }   
    }   
  ]   
}
```


## Progress
- [X] 1. Unittestid
- [X] 2. Programmikood
- [ ] 3. Faili lugemine/kirjutamine
- [ ] 4. Mockimine
- [ ] 5. Integratsiooni testid
- [X] 6. UI testid

## Etapid
### 1. Mõtle välja kõikvõimalikud testid, mis on vajalikud, et katta projekti nõudmised (jätke I/O testid (failist lugemine, faili kirjutamine) hetkel välja, need võtke ette 3. etapis) **(10p)**
- Hindan eeskätt testide väärtuslikkust ja testide kompositsiooni, kõik testid ei pea olema implementeeritud (võite panna @Ignore)
- Clean Code, Edge cases, C.O.R.R.E.C.T
- **NB!** Sellel etapil võib sisendit (linn) olla hardcode-itud või parameetriga edasi antav, ei pea failist lugema
- **NB!** Sellel etapil vastus ei pea salvestuma faili, piisab kui returnitakse nt objekt vajaliku infoga

Näited test case-idest:

**üldine**
- tundmatu linna puhul tagastab veateate
- tagastatakse õige linna koordinaadid
- Koordinaadid kujul lat,lon, nt "59.44,24.75"

**hetke temperatuur**
- linn on kohustuslik element päringus
- tagastatakse õige linna hetke ilm
- celsiuses küsides, tagastab celsiuses

**3-päeva ilmaennustus**
- linn on kohustuslik element päringus
- tagastatakse 3 päeva ilmaennustus
- tagastatakse mitte vanem kui 3h vana ilmaennustus

### 2. Implementeerida programmikood, et testid läheksid läbi, vajadusel kirjuta uusi teste **(10p)**
- Kasuta [OpenWeatherMap API-sid](https://openweathermap.org/api), et saada praegune temperatuur ja 3 päeva temperatuur (min/max).
- Kasuta endale meelepärane linn

### 3. Funktsionaalsuse täiendused: Lisada failist lugemise ja failisse kirjutamine **(15p)**
- Tee linn parametriseeritavaks
- Linnad (võib olla mitu) lugeda sisse failist _input.txt_ (võib olla ka JSON, Excel vms, valige ise, mis formaati kasutate)
- väljund kirjutada faili nt _{linna_nimi}.txt_ (võib olla ka JSON, Excel vms, valige ise, mis formaati kasutate)
- täienda teste + refactor

### 4. Mock testid välise API jaoks **(5p)**
- Lisa testid, mis mockivad OpenWeatherMap API sõltuvused
- Lisa testid, mis mockivad failist lugemise ja kirjutamise
- Piisab, kui on üks stub ja üks mock
- Need testid peavad töötama ka siis kui võrguühendus puudub või kui faili füüsiliselt ei ole

### 5. Kirjuta integratsiooni testid OpenWeatherMap liidestuse valideerimiseks **10p**

**Testide näiteid OpenWeatherMap-iga:**
- hetke ilma päring tagastab HTTP Status Code 200 
- vale (mitte tuvastatud) linna puhul tagastab API viga koos veateatega
- forecast-i response-is on olemas temperatuuri (või niiskuse, õhurõhu) andmed 

### 6. Kirjuta automaattestid kasutajaliidese testimiseks (UI testid) kasutades TalTechi kodulehte:**(10p)**
- Valideerige, kas Tudengi sektsioonis Õppeinfo alt räägitakse "Kvaliteetsest haridusest"
- Valideerige, et minu email töötaja otsingus on german.mumma@taltech.ee
- Valideerige, et valides üleval *Sisene* sektsioonist ÕIS, Moodle või Siseveeb suunatakse õigesse kohta (piisab URL-i kontrollist).

**Arvestage järgnevaga:**
- Kasutage Page Object Pattern
- Proovige kasutada erinevaid selectorite tüüpe (id, css, xpath)
- Piisab, kui jookseb Chrome-is

## Hindamisel jälgin, et oleks olemas järgmised punktid
- Vähemalt 3 unit testi, millest vähemalt 1 testib rakenduse poolt Exceptioni viskamist
- Vähemalt 3 integratsiooni testi (ei ole mockitud, kasutavad päris API-t).
- 2 mockitud testi, millest vähemalt 1 on stub ja 1 on mock
- Vähemalt 3 UI testi
- Kood on versioneeritud (Git) ja commitides jälgitud Git häid praktikaid
- Sõltuvusute haldamiseks on kasutatud vastavad vahendid (Maven, Gradle, NPM, NuGet vms)
- Implementeeritud mingit sorti jälgitavust, nt Logimine: request on logitud maha, response on logitud maha, vms.
- Kui test feilib, siis peaks olema selge, miks ta feilis ja kus ta feilis (sisend, väljund, tegelik vs oodatud, class, rida, screenshot, video vms)
- Projektil peab olema README, milles on välja toodud: üldine testimise strateegia ja lähenemine, kuidas rakendus käivitada (vajadusel paigaldusjuhend) ja kuidas erinevad testid käivitada ja näha tulemust
- Kood esitada Clean Code nõuetele vastavalt

## Q & A
_Siia tulevad tüüpilised küsimused ja vastused, segadused ja selgitused, kui neid tekib.._
- Failist lugemise ja faili kirjutamise kohta ei pea tegema ainult mock teste, vaid peavad olema ka testid, mis testivad integratsiooni päris komponentidega.
- Etappidel eraldi pole tähtaegu, esitate kõik korraga.
- Esitada võib ka varem, siis saan vihjeid anda, mis kohad vajaksid täiendamist.
- Rakendus võib töötada ka nt eraldi Program klassi main meetodis. St ei pea olema _fully functioning_ app. **Rõhk on ikkagi testidel ja nende abiga puhta ning modulaarse rakenduse koodi arendamisel**
- Sõltuvus konkreetsest versioonist on asi, mida tuua välja README-s. Nt "Requires Java 11 or later"
- Teeme rohkem ja läbi mõeldud committe. Vältige commite, mis "lisab kõik testid ja rakenduse ning README". Mida rohkem committe, seda lihtsam on "ajas tagasi hüpata" ja teha muudatusi, mis ei mõjutaks kogu rakendust.