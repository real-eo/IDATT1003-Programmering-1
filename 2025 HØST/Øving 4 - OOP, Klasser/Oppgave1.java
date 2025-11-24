import java.util.Locale;
import java.util.Scanner;

public class Oppgave1 {
	public static void main(String[] args) {
		// Sørg for forutsigbar punktum som desimalskilletegn
		Locale.setDefault(Locale.US);

		// Eksempelkurser: NOK per én enhet av valutaen
		Valuta dollar = new Valuta("Dollar (USD)", 10.50);
		Valuta euro = new Valuta("Euro (EUR)", 11.50);
		Valuta sek = new Valuta("Svenske kroner (SEK)", 1.00);

		Scanner scanner = new Scanner(System.in);

		boolean ferdig = false;
		while (!ferdig) {
			// Display
			System.out.println("Velg valuta:");
			System.out.println("1: dollar");
			System.out.println("2: euro");
			System.out.println("3: svenske kroner");
			System.out.println("4: avslutt");
			System.out.print("Ditt valg: ");

			String valgStr = scanner.nextLine();
			int valg;

			// Håndter ugyldig inndata
			try {
				valg = Integer.parseInt(valgStr.trim());
			} catch (NumberFormatException e) {
				System.out.println("Ugyldig valg, prøv igjen.\n");
				continue;
			}
			
			// Stop programmet
			if (valg == 4) {
				ferdig = true;
				break;
			}

			// Set valgt valuta
			Valuta valgt;
			switch (valg) {
				case 1:
					valgt = dollar;
					break;
				case 2:
					valgt = euro;
					break;
				case 3:
					valgt = sek;
					break;
				default:
					System.out.println("Ugyldig valg, prøv igjen.\n");
					continue;
			}

			// Display 
			System.out.println("\nValgt valuta: " + valgt.getNavn());
			System.out.println("1: Regn FRA " + valgt.getNavn() + " TIL norske kroner (NOK)");
			System.out.println("2: Regn FRA norske kroner (NOK) TIL " + valgt.getNavn());
			System.out.print("Velg retning (1/2): ");

			String retningStr = scanner.nextLine();
			int retning;

			// Håndter ugyldig inndata
			try {
				retning = Integer.parseInt(retningStr.trim());
			} catch (NumberFormatException e) {
				System.out.println("Ugyldig valg, går tilbake til hovedmeny.\n");
				continue;
			}

			System.out.print("Skriv inn beløp: ");
			String belopStr = scanner.nextLine();
			double belop;

			// Håndter ugyldig inndata
			try {
				belop = Double.parseDouble(belopStr.replace(',', '.').trim());
				if (belop < 0) {
					System.out.println("Beløp kan ikke være negativt.\n");
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("Ugyldig beløp.\n");
				continue;
			}

			// Beregn resultat, og display
			if (retning == 1) {
				double nok = valgt.tilNok(belop);
				System.out.printf(Locale.US, "%.2f %s = %.2f NOK\n\n", belop, valgt.getKortNavn(), nok);
			} else if (retning == 2) {
				double iValuta = valgt.fraNok(belop);
				System.out.printf(Locale.US, "%.2f NOK = %.2f %s\n\n", belop, iValuta, valgt.getKortNavn());
			} else {
				System.out.println("Ugyldig retning, prøv igjen.\n");
				// continue;
			}
		}

		System.out.println("Avslutter. På gjensyn!");
		scanner.close();
	}
}
