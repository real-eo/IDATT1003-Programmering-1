# Øving 10 – Tabellister

Denne mappen inneholder løsninger for Oppgave 1 (Arrangementregister) og Oppgave 2 (Menyregister), med menystyrte klienter (`Oppgave1` og `Oppgave2`).

## UML-oversikt

Oppgave 1

```
+-------------------+        +-----------------------+
|   Arrangement     |        |  ArrangementRegister  |
+-------------------+        +-----------------------+
| - nummer:int      |  1..*  | - arrangementer:List  |
| - navn:String     | <----> | - nummerIndex:Set     |
| - sted:String     |        +-----------------------+
| - arrangor:String |        | +registrer(a):boolean |
| - type:String     |        | +hentAlle():List      |
| - tidspunkt:long  |        | +finnArrangementer... |
+-------------------+        | +listeSortert...      |
| +gettere()        |        +-----------------------+
+-------------------+
```

Oppgave 2

```
+-----------+       +--------------+         +----------------+
|   Rett    | *   * |     Meny     |   1..*  |  MenyRegister  |
------------+ <---> +--------------+ ------> +----------------+
| -navn     |       | -navn        |         | -retter:Map    |
| -type     |       | -retter      |         | -menyer:Map    |
| -pris     |       +--------------+         +----------------+
| -oppskrift|       | +totalPris() |         | +registrerRett |
+-----------+       +--------------+         | +finnRett      |
                                             | +finnRetter... |
                                             | +registrerMeny |
                                             | +finnMenyer... |
                                             +----------------+
```

## Hva som er implementert

Oppgave 1
- Registrere nytt arrangement (unik `nummer`).
- Finne alle på gitt sted.
- Finne alle på gitt dato (YYYYMMDD).
- Finne alle innenfor tidsintervall (YYYYMMDDHHMM–YYYYMMDDHHMM), sortert på tid.
- Lister sortert på sted, type og tidspunkt.

Oppgave 2
- Registrere ny rett (unikt navn).
- Finne rett på navn.
- Finne alle retter av type.
- Registrere meny som sett av retter.
- Finne menyer med totalpris innenfor intervall.

Merk: Det er ikke lagt inn validering av gyldighet for tid/dato, som oppgaven tillater.

## Kjøreinstruksjoner (Windows PowerShell)

Kompiler alle Java-filene i mappen:

```powershell
javac *.java
```

Kjør Oppgave 1 (Arrangementregister):

```powershell
java Oppgave1
```

Kjør Oppgave 2 (Menyregister):

```powershell
java Oppgave2
```

## Tips
- Tidspunkt skal legges inn som tall i formatet `YYYYMMDDHHMM` (for eksempel `200210301800`).
- Dato for filtrering i Oppgave 1 legges inn som `YYYYMMDD` (for eksempel `20021030`).
- I Oppgave 2 kan du registrere en meny ved å oppgi flere rettenavn separert med komma.