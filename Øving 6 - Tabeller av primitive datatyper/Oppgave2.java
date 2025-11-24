import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

// Hjelpeklasse for tekstanalyse i henhold til oppgavetekst
class Tekstanalyse {
	// Indekser: 0..28 => a..å, 29 => andre tegn
	private final int[] antallTegn = new int[30];

	// Norsk alfabet med æ, ø, å
	private static final char[] ALFABET = (
			"abcdefghijklmnopqrstuvwxyzæøå").toCharArray();

	Tekstanalyse(String tekst) {
		if (tekst == null) tekst = "";

		// Normaliser til liten skrift for å ikke skille mellom store/små
		// Locale.forLanguageTag unngår deprekerte konstruktører i nyere JDKer
		String lower = tekst.toLowerCase(Locale.forLanguageTag("no"));
		for (int i = 0; i < lower.length(); i++) {
			char ch = lower.charAt(i);
			int idx = indeksForBokstav(ch);

			if (idx >= 0) antallTegn[idx]++;
			else antallTegn[29]++; // annet tegn
		}
	}

	// Finn indeks i 0..28 for a..å, ellers -1
	private int indeksForBokstav(char ch) {
		// Rask lineær søk, gitt fast liten tabell
		for (int i = 0; i < ALFABET.length; i++) {
			if (ALFABET[i] == ch) return i;
		}
		return -1;
	}

	// Antall ulike bokstaver (med minst 1 forekomst)
	int finnAntallForskjelligeBokstaver() {
		int ulike = 0;
		for (int i = 0; i < 29; i++) if (antallTegn[i] > 0) ulike++;
		return ulike;
	}

	// Totalt antall bokstaver i teksten
	int finnTotaltAntallBokstaver() {
		int sum = 0;
		for (int i = 0; i < 29; i++) sum += antallTegn[i];
		return sum;
	}

	// Hvor stor del av teksten (i prosent) er ikke bokstaver?
	double prosentIkkeBokstaver() {
		int ikke = antallTegn[29];
		int total = finnTotaltAntallBokstaver() + ikke;
		if (total == 0) return 0.0;
		return (ikke * 100.0) / total;
	}

	// Antall forekomster av en bestemt bokstav (skiller ikke mellom store/små)
	int antallAv(char bokstav) {
		char ch = Character.toLowerCase(bokstav);
		int idx = indeksForBokstav(ch);
		if (idx < 0) return 0;
		return antallTegn[idx];
	}

	// Bokstaven(e) som forekommer oftest
	List<Character> vanligsteBokstaver() {
		int maks = 0;
		for (int i = 0; i < 29; i++) if (antallTegn[i] > maks) maks = antallTegn[i];
		List<Character> liste = new ArrayList<>();
		if (maks == 0) return liste;
		for (int i = 0; i < 29; i++) if (antallTegn[i] == maks) liste.add(ALFABET[i]);
		return liste;
	}

	// For debug/visning
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 29; i++) {
			sb.append(ALFABET[i]).append(':').append(antallTegn[i]).append(' ');
		}
		sb.append("andre:").append(antallTegn[29]);
		return sb.toString();
	}
}

public class Oppgave2 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Tekstanalyse (tom linje avslutter)\n");
		while (true) {
			System.out.print("Skriv inn en tekst: ");
			String tekst = in.nextLine();
			if (tekst == null || tekst.isEmpty()) break;

			Tekstanalyse ta = new Tekstanalyse(tekst);
			int ulike = ta.finnAntallForskjelligeBokstaver();
			int total = ta.finnTotaltAntallBokstaver();
			double prosentIkke = ta.prosentIkkeBokstaver();

			System.out.println("\nResultat:");
			System.out.println("Ulike bokstaver: " + ulike);
			System.out.println("Totalt antall bokstaver: " + total);
			System.out.printf(Locale.US, "Ikke-bokstaver: %.2f%%%n", prosentIkke);

			// Spør etter en bokstav for opptelling
			System.out.print("Oppgi en bokstav for opptelling (enter for å hoppe over): ");
			String linje = in.nextLine();
			if (!linje.isEmpty()) {
				char bokstav = linje.charAt(0);
				System.out.println("Antall '" + bokstav + "': " + ta.antallAv(bokstav));
			}

			List<Character> vanligste = ta.vanligsteBokstaver();
			if (vanligste.isEmpty()) {
				System.out.println("Ingen bokstaver funnet.");
			} else {
				System.out.print("Vanligste bokstav(er): ");
				
				for (int i = 0; i < vanligste.size(); i++) {
					if (i > 0) System.out.print(", ");
					System.out.print(vanligste.get(i));
				}

				System.out.println();
			}

			System.out.println();
		}
		
		in.close();
	}
}

