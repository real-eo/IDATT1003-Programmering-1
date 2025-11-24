package no.gloppen.eiendom;

// import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;


/** // ! SE OVER !
 * Tekstbasert brukergrensesnitt for eiendomsregisteret.
 * Ansvar: all interaksjon med bruker (meny, input, utskrift).
 *
 * Kritikk av forslag i oppgavetekst:
 * - Forslaget oppretter ny Scanner i showMenu() (risiko for ressurslekkasje og blanding av innlesing).
 * - Returnere 0 ved ugyldig input gjør ekstra sjekk nødvendig; bedre å validere i løkken til gyldig valg.
 * - Oppdeling i små metoder (add/list/find/average) gir bedre kohesjon/testbarhet.
 *
 * Forbedringer her:
 * - Én Scanner-instans.
 * - Robust validering av tall (while-loop).
 * - Klare konstante menyvalg.
 */
public class ConsoleMenu {
    
    // Menykonstanter
    private static final int ADD_PROPERTY = 1;
    private static final int LIST_ALL_PROPERTIES = 2;
    private static final int FIND_PROPERTY = 3;
    private static final int CALCULATE_AVERAGE_AREA = 4;
    private static final int EXIT = 5;

    private final PropertyRegister register;
    private final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

    public ConsoleMenu(PropertyRegister register) {
        this.register = register;
    }

    /**
     * Starter hovedløkken.
     */
    public void start() {
        boolean finished = false;
        System.out.println("Velkommen til Eiendomsregisteret!\n");
        while (!finished) {
            int choice = show();

            switch (choice) {
                case ADD_PROPERTY           -> addProperty();
                case LIST_ALL_PROPERTIES    -> listAll();
                case FIND_PROPERTY          -> search();
                case CALCULATE_AVERAGE_AREA -> averageArea();
                case EXIT                   -> {
                    // System.out.println("Avslutter programmet");
                    finished = true;
                }

                default                     -> System.out.println("Ukjent valg.\n");
            }
        }
    }

    /**
     * Viser meny og returnerer gyldig menyvalg.
     */
    private int show() {
        System.out.println("----- MENY -----");
        System.out.println(ADD_PROPERTY + ". Registrer ny eiendom");
        System.out.println(LIST_ALL_PROPERTIES + ". Skriv ut alle eiendommer");
        System.out.println(FIND_PROPERTY + ". Søk etter eiendom (kommunenr, gnr, bnr)");
        System.out.println(CALCULATE_AVERAGE_AREA + ". Vis gjennomsnittsareal");
        System.out.println(EXIT + ". Avslutt");
        System.out.print("Velg (" + ADD_PROPERTY + "-" + EXIT + "): ");
        while (true) {
            String line = scanner.nextLine().trim();
            
            try {
                int val = Integer.parseInt(line);

                if (val == ADD_PROPERTY ||
                    val == LIST_ALL_PROPERTIES ||
                    val == FIND_PROPERTY ||
                    val == CALCULATE_AVERAGE_AREA ||
                    val == EXIT) {
                    return val;
                }
                System.out.print("Ugyldig menyvalg, prøv igjen: ");
                
            } catch (NumberFormatException ex) {
                System.out.print("Må være heltall, prøv igjen: ");
            }
        }
    }
    private void addProperty() {
        System.out.println("\nRegistrer ny eiendom:");
        int knr = readIntWithinRange("Kommunenummer (101-5054): ", 101, 5054);
        String kNavn = readNonEmptyString("Kommunenavn: ");
        int gnr = readPositiveInt("Gårdsnummer (gnr >0): ");
        int bnr = readPositiveInt("Bruksnummer (bnr >0): ");
        String bNavn = readOptionalString("Bruksnavn (blank hvis ingen): ");
        double areal = readPositiveDouble("Areal i m2 (>0, bruk punktum som desimal): ");
        String eier = readNonEmptyString("Navn på eier: ");

        try {
            Property p = new Property(knr, kNavn, gnr, bnr, bNavn, areal, eier);
            if (register.addProperty(p)) {
                System.out.println("Eiendom lagt til: " + p.idString());
            } else {
                System.out.println("Eiendommen finnes allerede (duplikat)." );
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Feil: " + ex.getMessage());
        }
        System.out.println();
    }

    private void listAll() {
        System.out.println("\nAlle eiendommer (" + register.size() + "):");
        if (register.size() == 0) {
            System.out.println("(Ingen registrert)");
        } else {
            for (Property p : register.getAll()) {
                System.out.println(p);
            }
        }
        System.out.println();
    }

    private void search() {
        System.out.println("\nSøk etter eiendom:");
        int knr = readInt("Kommunenummer: ");
        int gnr = readInt("Gårdsnummer (gnr): ");
        int bnr = readInt("Bruksnummer (bnr): ");
        Optional<Property> opt = register.find(knr, gnr, bnr);
        if (opt.isPresent()) {
            System.out.println("Funnet: " + opt.get());
        } else {
            System.out.println("Ingen eiendom med nøkkel " + knr + "-" + gnr + "/" + bnr);
        }
        System.out.println();
    }

    private void averageArea() {
        double avg = register.averageArea();
        if (register.size() == 0) {
            System.out.println("Ingen eiendommer, gjennomsnitt = 0.0");
        } else {
            System.out.printf(Locale.US, "Gjennomsnittlig areal: %.2f m² (%d eiendommer)%n", avg, register.size());
        }
        System.out.println();
    }

    // --- Input hjelpemetoder ---

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException ex) {
                System.out.println("Ugyldig heltall, prøv igjen.");
            }
        }
    }

    private int readIntWithinRange(String prompt, int min, int max) {
        while (true) {
            int val = readInt(prompt);
            if (val < min || val > max) {
                System.out.println("Tall må være mellom " + min + " og " + max + ".");
            } else return val;
        }
    }

    private int readPositiveInt(String prompt) {
        while (true) {
            int val = readInt(prompt);
            if (val <= 0) {
                System.out.println("Må være > 0.");
            } else return val;
        }
    }

    private double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                double d = Double.parseDouble(line);
                if (d <= 0) {
                    System.out.println("Må være > 0.");
                } else return d;
            } catch (NumberFormatException ex) {
                System.out.println("Ugyldig desimaltall, bruk punktum (f.eks. 1234.5). ");
            }
        }
    }

    private String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Kan ikke være tomt.");
        }
    }

    private String readOptionalString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
