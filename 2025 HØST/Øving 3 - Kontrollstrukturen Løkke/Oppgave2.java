import java.util.Scanner;

public class Oppgave2 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Primtall-sjekk. Skriv inn heltall for å teste.");

		boolean fortsett;
		do {
			System.out.print("Tall: ");
			int n = sc.nextInt(); // Forenkling: antar gyldig heltall

			if (erPrimtall(n)) {
				System.out.println(n + " er et primtall.");
			} else {
				System.out.println(n + " er ikke et primtall.");
			}

			System.out.print("Vil du teste et nytt tall? (j/n): ");

			char svar = sc.next().trim().toLowerCase().charAt(0);
			
			fortsett = (svar == 'j');
			
			System.out.println();
		} while (fortsett);

		sc.close();
		System.out.println("Avslutter.");
	}

	// Returnerer true hvis n er et primtall, ellers false
	private static boolean erPrimtall(int n) {
		if (n <= 1) return false;       // 0, 1 og negative er ikke primtall
		if (n <= 3) return true;        // 2 og 3 er primtall
		if (n % 2 == 0) return false;   // partall > 2 er ikke primtall
		if (n % 3 == 0) return n == 3;  // multippel av 3 bortsett fra 3 selv

		// Test delere opp til og med sqrt(n) ved å hoppe 6k±1
		for (int i = 5; (long) i * i <= n; i += 6) {
			if (n % i == 0 || n % (i + 2) == 0) return false;
		}
		
		return true;
	}
}
