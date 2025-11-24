public class Valuta {
    private final String navn;
    private final double kursNokPerEnhet;                                               // hvor mange NOK for 1 enhet av denne valutaen

    public Valuta(String navn, double kursNokPerEnhet) {
        if (navn == null || navn.isBlank()) {
            throw new IllegalArgumentException("Navn kan ikke være tomt");
        }
        if (kursNokPerEnhet <= 0) {
            throw new IllegalArgumentException("Kurs må være > 0");
        }

        this.navn = navn;
        this.kursNokPerEnhet = kursNokPerEnhet;
    }

    public String getNavn() {
        return navn;
    }

    // Returner kortnavn i parentes dersom tilstede, ellers hele navnet
    public String getKortNavn() {
        int start = navn.indexOf('(');
        int end = navn.indexOf(')');

        if (start >= 0 && end > start) {
            return navn.substring(start + 1, end);
        }
        
        return navn;
    }

    // Konverter fra denne valutaen til NOK
    public double tilNok(double belop) {
        return belop * kursNokPerEnhet;
    }

    // Konverter fra NOK til denne valutaen
    public double fraNok(double belopINok) {
        return belopINok / kursNokPerEnhet;
    }
}
