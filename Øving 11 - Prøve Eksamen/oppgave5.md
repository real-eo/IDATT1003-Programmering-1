### Cohesion
- Entitetsklasse [`no.gloppen.eiendom.Property`](src/no/gloppen/eiendom/Property.java) har samlet alt som angår eiendommens invariants, identitet (`equals`/`hashCode`), presentasjon (`toString`) og ID-format (`idString`). Ingen UI- eller lagringslogikk blandet inn (høy kohesjon).
- Samlings-/tjenesteklassen [`no.gloppen.eiendom.PropertyRegister`](src/no/gloppen/eiendom/PropertyRegister.java) fokuserer kun på operasjoner over samlingen: lagre (`addProperty`), slette (`deleteProperty`), søk (`find`, `findByLotNumber`), aggregat (`averageArea`), og størrelse (`size`). Alle metodene opererer på samme private liste (kohesiv datasentrering).
- UI-klassen [`no.gloppen.eiendom.ConsoleMenu`](src/no/gloppen/eiendom/ConsoleMenu.java) håndterer kun interaksjon: meny (`start`, `show`), inputhjelpemetoder (`readInt`, `readPositiveDouble`, ...) og delegasjon (f.eks. `listAll`, `search`, `averageArea`). Ingen forretningsregler om identitet eller beregninger ligger her.
- Oppstartsklasse [`no.gloppen.eiendom.RealEstateApp`](src/no/gloppen/eiendom/RealEstateApp.java) begrenses til initiering og testdata (`seedTestData`), ikke input eller domeneoperasjoner.

Eksempel på kohesjon i registeret (alle operasjoner på samme liste):

```Java
// utdrag
public boolean addProperty(Property property) { /* ... */ }
public Optional<Property> find(int municipalityNumber, int lotNumber, int sectionNumber) { /* ... */ }
public double averageArea() { /* ... */ }
```



### Coupling
- UI er koblet til domenet kun via det offentlige APIet i [`no.gloppen.eiendom.PropertyRegister`](src/no/gloppen/eiendom/PropertyRegister.java): kall til `addProperty`, `getAll`, `find`, findByLotNumber, averageArea, size. Ingen direkte tilgang til interne datastrukturer (lav kobling).
- Domenemodellen [`no.gloppen.eiendom.Property`](src/no/gloppen/eiendom/Property.java) er avhengighetsfri (ingen referanser til `ConsoleMenu`, I/O eller globale singletons).
- Registeret skjuler samlingsrepresentasjonen: privat liste og umodifiserbar visning i `getAll`. Dette hindrer eksterne endringer og reduserer kobling til implementasjonsdetaljer.
- Oppstartsklassen [`no.gloppen.eiendom.RealEstateApp`](src/no/gloppen/eiendom/RealEstateApp.java) kjenner kun til én instans av registeret og injiserer den i `ConsoleMenu` (enkel konstruktøravhengighet).
- Bruk av `Optional` i `find` reduserer kobling til null-kontrakter (klientkode slipper null-sjekk-mønster).

Eksempel på lav kobling i UI (delegasjon):
```Java
// utdrag fra ConsoleMenu.listAll()
System.out.println("\nAlle eiendommer (" + register.size() + "):");
if (register.size() == 0) {
    System.out.println("(Ingen registrert)");
} else {
    for (Property p : register.getAll()) {
        System.out.println(p);
    }
}
```

### Designet i forhold til krav
- Alle påkrevde funksjoner (registrere, liste, søke, gjennomsnitt) realiseres med ett dedikert metodekall i registeret + en ren utskriftsmetode i UI (god separasjon -> lav kobling, høy kohesjon).
- ID-formatkravet løses isolert i `Property.idString` (enkelt å gjenbruke, ingen duplisering i UI).
- Validering av domeneregler (kommunenr, gnr, bnr, areal, eier) ligger kun i konstruktøren i `Property` (kohesjon, ingen spredt kobling).

#### Konkrete forbedringsmuligheter (uten å svekke nåværende lav kobling)
- Et interface (PropertyRepository) foran `PropertyRegister` for ytterligere løsning av kobling mot lagringsstrategi.
- Ekstraksjon av inputvalidering til egen klasse hvis UI vokser (styrke kohesjon i `ConsoleMenu` ytterligere).

### Oppsummert 
Klassenes ansvar er tydelig avgrenset (høy kohesjon) og kommunikasjon skjer via smale, stabile offentlige metoder (lav kobling).

