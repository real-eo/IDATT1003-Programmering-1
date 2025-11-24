# Oppgave 1 – Begrunnelse for modellering

Klassene og samarbeid:
- [`no.gloppen.eiendom.RealEstateApp`](src/no/gloppen/eiendom/RealEstateApp.java) starter programmet, fyller testdata og delegere all brukerinteraksjon til [`no.gloppen.eiendom.ConsoleMenu`](src/no/gloppen/eiendom/ConsoleMenu.java). Den har én instans av [`no.gloppen.eiendom.PropertyRegister`](src/no/gloppen/eiendom/PropertyRegister.java) (komposisjon – lever like lenge som appen).
- [`no.gloppen.eiendom.ConsoleMenu`](src/no/gloppen/eiendom/ConsoleMenu.java) bruker registerets offentlige metoder for CRUD/søk/analyse. Ingen direkte tilgang til intern liste (lav kobling).
- [`no.gloppen.eiendom.PropertyRegister`](src/no/gloppen/eiendom/PropertyRegister.java) kapsler samlingslogikk: lagring, unikhet, søk og aggregat (gjennomsnitt). Holder en privat liste og eksponerer kun en umodifiserbar visning.
- [`no.gloppen.eiendom.Property`](src/no/gloppen/eiendom/Property.java) er ren domenemodell med validering og identitet (equals/hashCode på kommunenr, gnr, bnr). Dette muliggjør enkel duplikatsjekk via List.contains.

Valg av relasjoner i diagrammet:
- RealEstateApp *-- PropertyRegister: livssyklus-eierskap (register lages og brukes kun av appen).
- PropertyRegister *-- Property: samling (1..* mulig, komposisjon fordi eiendommer kun eksisterer meningsfullt innenfor registeret her).
- ConsoleMenu --> PropertyRegister: bruker (avhengighet) uten eierskap – kan byttes ut eller testes med mock.
- RealEstateApp --> ConsoleMenu: opprettelse og start (kontrollflyt).

Fordeler:
- Høy kohesjon: Hver klasse har ett klart ansvar (oppstart, UI, samling, entitet).
- Lav kobling: UI kjenner kun til offentlige metoder, ikke interne datastrukturer.
- Enkelt å utvide: Man kan introdusere et interface (PropertyRepository) eller alternative lagringsstrategier uten å endre ConsoleMenu.
- Testbarhet: PropertyRegister kan enhetstestes isolert; Property validering kan testes uten I/O; ConsoleMenu kan testes ved å simulere Scanner-input.

Oppsummering:
Modellen deler bekymringer mellom presentasjon, domene og datahåndtering. Dette gir lesbar, vedlikeholdbar og utvidbar kodebase som dekker nåværende behov og er klar for videre utvikling.