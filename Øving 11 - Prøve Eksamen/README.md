# Gloppen Eiendomsregister (Konsoll-app)

En enkel Java-konsollapplikasjon for å registrere og analysere eiendommer i en kommune.

## Funksjonalitet
- Registrere/legge inn eiendom
- Skrive ut alle eiendommer som er registrert
- Søke etter eiendom basert på kommunenr, gnr og bnr (alle 3 samtidig)
- Regne ut og vise gjennomsnittsareal av alle eiendommene i registeret

Ved utskrift benyttes ID-formatet: `kommunenr-gnr/bnr` (f.eks. `1504-54/73`).

Testdata for Gloppen kommune (1445) er forhåndslastet ved oppstart.

## Kildekode
- `src/no/gloppen/eiendom/Property.java` – Domenemodell for eiendom
- `src/no/gloppen/eiendom/PropertyRegister.java` – Register med søk og analyser
- `src/no/gloppen/eiendom/RealEstateApp.java` – Menystyrt konsollapplikasjon

## Kompilering og kjøring (Windows PowerShell)

Forutsetning: Du har Java 17+ installert og `javac`/`java` på PATH.

```powershell
# Kjør fra prosjektmappen
javac -d out .\src\no\gloppen\eiendom\*.java
java -cp out no.gloppen.eiendom.RealEstateApp
```

Tips: Bruk punktum som desimalskilletegn (eksempel: `1234.5`).

## Notater om design
- Unikhet på eiendommer sikres av kombinasjonen (kommunenr, gnr, bnr)
- Inndata valideres med tydelige feilmeldinger
- Javadoc på klasser og sentrale metoder
