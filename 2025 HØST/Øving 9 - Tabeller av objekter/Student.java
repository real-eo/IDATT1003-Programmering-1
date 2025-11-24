public class Student {
    // Objektvariabler
    private final String navn;      // entydig
    private int antOppg;

    public Student(String navn) {
        if (navn == null || navn.isBlank()) {
            throw new IllegalArgumentException("Navn kan ikke være tomt");
        }
        this.navn = navn.trim();
        this.antOppg = 0;
    }

    // Tilgangsmetoder
    public String getNavn() {
        return navn;
    }

    // Beholder også metodenavn slik det står i oppgaven (med stor A) som alias
    public String getNAvn() {               // alias for å matche oppgavetekst
        return getNavn();
    }

    public int getAntOppg() {
        return antOppg;
    }

    // Øk antall oppgaver (med validering)
    public void økAntOppg(int okning) {
        if (okning < 0) {
            throw new IllegalArgumentException("Økning kan ikke være negativ");
        }
        antOppg += okning;
    }

    // ASCII-alias for enklere bruk på systemer uten norsk tastatur
    public void okAntOppg(int okning) {
        økAntOppg(okning);
    }

    @Override
    public String toString() {
        return navn + ": " + antOppg + " oppg. godkjent";
    }
}
