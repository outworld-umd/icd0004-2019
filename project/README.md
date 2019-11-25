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


## Progress
- [X] 1. Unittestid
- [X] 2. Programmikood
- [X] 3. Faili lugemine/kirjutamine
- [X] 4. Mockimine
- [X] 5. Integratsiooni testid
- [X] 6. UI testid

## Testimiskava
### Unit testid

### Mockimine

### Integratsiooni testid


### UI testid


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
