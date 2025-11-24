import java.util.Scanner;

public class Oppgave1 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Multiplikasjonstabell (brukeren velger intervall). Eksempel: 13 15");

		int start;
		int slutt;

		// Enkel inndatakontroll med do-while: sørg for at start <= slutt
		do {
			System.out.print("Starttall: ");
			start = sc.nextInt(); // Forenkling: antar heltall (ingen tekst)

			System.out.print("Slutttall: ");
			slutt = sc.nextInt();

			if (start > slutt) {
				System.out.println("Start må være mindre enn eller lik slutt. Prøv igjen.\n");
			}
		} while (start > slutt);

		// Standard faktorgrense 1..10, slik som i eksempelet
		final int maksFaktor = 10;

		for (int tall = start; tall <= slutt; tall++) {
			System.out.println(tall + "-gangen:");
			for (int faktor = 1; faktor <= maksFaktor; faktor++) {
				int produkt = tall * faktor;
				System.out.println(tall + " x " + faktor + " = " + produkt);
			}
			if (tall < slutt) {
				System.out.println(); // tom linje mellom seksjoner
			}
		}

		sc.close();
	}
}
