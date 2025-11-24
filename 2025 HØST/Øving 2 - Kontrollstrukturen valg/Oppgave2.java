import java.util.Scanner;

/**
 * Oppgave 2 - Sammenlign pris per gram
 * Merke A: 35.90 for 450 g
 * Merke B: 39.50 for 500 g
 * Programmet viser hvilket merke som er billigst per gram.
 */
public class Oppgave2 {
	public static void main(String[] args) {
		// faste priser fra oppgaven
		double prisA = 35.90;
		double gramA = 450.0;

		double prisB = 39.50;
		double gramB = 500.0;

		double prisPerGramA = prisA / gramA;
		double prisPerGramB = prisB / gramB;

		System.out.printf("Pris per gram - Merke A: %.5f kr/g\n", prisPerGramA);
		System.out.printf("Pris per gram - Merke B: %.5f kr/g\n", prisPerGramB);

		if (prisPerGramA < prisPerGramB) {
			System.out.println("Merke A er billigst.");
		} else if (prisPerGramB < prisPerGramA) {
			System.out.println("Merke B er billigst.");
		} else {
			System.out.println("Begge merkene koster det samme per gram.");
		}

		// bonus: la brukeren teste egne verdier
		Scanner sc = new Scanner(System.in);
		System.out.print("Vil du teste egne priser? (j/n): ");
		String svar = sc.nextLine().trim().toLowerCase();
		if (svar.equals("j") || svar.equals("y")) {
			try {
				System.out.print("Pris merke A (kr): ");
				double pA = Double.parseDouble(sc.nextLine().replace(',', '.'));
				System.out.print("Gram merke A: ");
				double gA = Double.parseDouble(sc.nextLine().replace(',', '.'));

				System.out.print("Pris merke B (kr): ");
				double pB = Double.parseDouble(sc.nextLine().replace(',', '.'));
				System.out.print("Gram merke B: ");
				double gB = Double.parseDouble(sc.nextLine().replace(',', '.'));

				double ppA = pA / gA;
				double ppB = pB / gB;
				System.out.printf("Merke A: %.5f kr/g, Merke B: %.5f kr/g\n", ppA, ppB);
				if (ppA < ppB) System.out.println("Merke A er billigst.");
				else if (ppB < ppA) System.out.println("Merke B er billigst.");
				else System.out.println("Begge merkene koster det samme per gram.");
			} catch (Exception ex) {
				System.out.println("Ugyldig input. Avslutter test.");
			}
		}

		sc.close();
	}
}
