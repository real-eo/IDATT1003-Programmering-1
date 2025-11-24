# Klassediagram og begrunnelse

## Klassediagram (tekstlig UML)

```
+--------------------+        uses         +-----------------------+
| RealEstateApp      |-------------------->| PropertyRegister      |
|--------------------|                     |-----------------------|
| -register: PropertyRegister              | -properties: List<Property> |
| -scanner: Scanner  |                     |-----------------------|
|--------------------|                     | +addProperty(p): boolean |
| +main(args)        |                     | +getAll(): List<Property> |
| +run()             |                     | +find(k,g,b): Optional<Property> |
| +menuAddProperty() |                     | +averageArea(): double |
| +menuListAll()     |                     | +size(): int           |
| +menuSearch()      |                     +-----------------------+
| +menuAverageArea() |
+--------------------+                               *
            |                                         |
            | seeds testdata                          | contains (1..*)
            v                                         v
+--------------------+                     +-----------------------+
| DataSeeder (foreslått)|                 | Property               |
|--------------------|                    |-----------------------|
| +seed(register)    |                    | -municipalityNumber: int (101..5054) |
+--------------------+                    | -municipalityName: String (!empty)  |
                                           | -lotNumber: int (>0)                |
                                           | -sectionNumber: int (>0)            |
                                           | -name: String (optional)            |
                                           | -areaSqm: double (>0)               |
                                           | -ownerName: String (!empty)         |
                                           |-----------------------|
                                           | +idString(): String                 |
                                           | +getXxx(): ...                      |
                                           | +equals()/hashCode() (identity)     |
                                           | +toString(): String                 |
                                           +-----------------------+

+--------------------+
| InputValidator (foreslått) |
|--------------------|
| +validateMunicipalityNumber(int) |
| +validateLot(int) |
| +validateSection(int) |
| +validateArea(double) |
| +requireNonEmpty(String, feltNavn) |
+--------------------+
```

## Roller og samarbeid

- [`no.gloppen.eiendom.RealEstateApp`](src/no/gloppen/eiendom/RealEstateApp.java) er kontroll-/presentasjonslaget (UI). Den orkestrerer meny, innlesing og kaller domenelogikk i [`no.gloppen.eiendom.PropertyRegister`](src/no/gloppen/eiendom/PropertyRegister.java).
- [`no.gloppen.eiendom.PropertyRegister`](src/no/gloppen/eiendom/PropertyRegister.java) er en samlings-/tjenesteklasse som kapsler listehåndtering, søk og enkel analyse (gjennomsnitt). Den har ansvar for lagringsintegritet (unngå duplikater) basert på [`no.gloppen.eiendom.Property`](src/no/gloppen/eiendom/Property.java) sin `equals()` identitet.
- [`no.gloppen.eiendom.Property`](src/no/gloppen/eiendom/Property.java) er domenemodellen (entitet) og håndhever invariants (validering) i konstruktør.
- Foreslåtte tillegg:
  - DataSeeder: Skiller ut seedTestData() fra app for bedre single responsibility og mulig gjenbruk i tester.
  - InputValidator: Flytter valideringslogikk fra både `Property` (som kjerne) og fra input-metoder for å unngå duplisering hvis UI endres (for eksempel bytte til GUI eller REST).

## Begrunnelse for struktur

- Separasjon av ansvar: UI (RealEstateApp) vs domenelogikk (PropertyRegister) vs entitet (Property) gir lav kobling og høy kohesjon.
- `Property` validerer seg selv for å sikre at alle instanser er gyldige; dette forenkler `PropertyRegister`.
- `PropertyRegister` holder listen privat og eksponerer kun en umodifiserbar visning (`getAll()`), som hindrer uønsket ekstern mutasjon.
- Identitet via (kommunenr, gnr, bnr) implementeres i `equals()/hashCode()` slik at `contains()` kan brukes for duplikatkontroll med enkel liste.
- Foreslåtte hjelpeklasser (DataSeeder, InputValidator) reduserer vekst i `RealEstateApp` og gjør koden lettere å teste (f.eks. unit-teste validering uavhengig av inputloop).

## Mulige utvidelser (senere)

- Persistence (PropertyRepository) for lagring til fil/DB.
- Service-lag for mer avanserte spørringer (filtrering på eier, arealintervall).
- Formatter/Printer for ulike utskriftsformater (CSV, tabell).

## Oppsummering

Diagrammet viser et enkelt tre-lags skille med mulighet for å ekstraktere generisk validering og dataseeding. Dette gir renere ansvar, testbarhet og grunnlag for videre utvidelse uten å endre kjernen.