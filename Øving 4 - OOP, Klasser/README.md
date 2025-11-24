# Øving 4 – OOP, Klasser

## Oppgave 1 – Valuta
- Klasse: `Valuta` med konstruktør, `tilNok()` og `fraNok()`.
- Klient: `Oppgave1` med meny for å omregne beløp.

### Klassediagram
```mermaid
classDiagram
    class Valuta {
      -navn : String
      -kursNokPerEnhet : double
      +Valuta(navn: String, kursNokPerEnhet: double)
      +getNavn() String
      +getKortNavn() String
      +tilNok(belop: double) double
      +fraNok(belopINok: double) double
    }

    class Oppgave1 {
      +main(args: String[]) void
    }

    Oppgave1 ..> Valuta : bruker
```

### Aktivitetsdiagram (klientprogrammet `Oppgave1`)
```mermaid
flowchart TD
    A[Start] --> B[Vis meny]
    B --> C{Valg}
    C -- 4 --> Z[Avslutt]
    C -- 1/2/3 --> D[Velg valutaobjekt]
    D --> E[Velg retning 1: til NOK, 2: fra NOK]
    E --> F[Les beløp]
    F --> G{Retning}
    G -- 1 --> H[beregn til NOK]
    G -- 2 --> I[beregn fra NOK]
    H --> J[Vis resultat]
    I --> J[Vis resultat]
    J --> B
    Z --> K[Slutt]
```

## Oppgave 2 – Terningspillet 100
- Klasse: `Spiller` med `sumPoeng`, `getSumPoeng()`, `kastTerningen()`, `erFerdig()`.
- Klient: `Oppgave2` simulerer runder til en spiller vinner. Enkel variant som standard; sett `eksaktHundre=true` i `Oppgave2` for raffinert regel.

### Klassediagram
```mermaid
classDiagram
    class Spiller {
      -terning : java.util.Random
      -sumPoeng : int
      +getSumPoeng() int
      +erFerdig() boolean
      +kastTerningen(eksaktHundre: boolean) int
      +trekkFra(verdi: int) void
    }

    class Oppgave2 {
      +main(args: String[]) void
    }

    Oppgave2 ..> Spiller : oppretter/bruke
```

## Kjøring
- Kjør `Oppgave1` for valutaomregning.
- Kjør `Oppgave2` for terningspillet.
