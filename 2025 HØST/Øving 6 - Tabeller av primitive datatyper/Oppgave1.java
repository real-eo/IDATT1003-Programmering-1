import java.util.Random;
import java.util.Scanner;

public class Oppgave1 {
	// Kjør simulering med gitt antall trekninger og skriv ut fordeling og stjernediagram
	private static void kjør(int antallTrekninger) {
		Random random = new Random();
		int[] antall = new int[10];

		for (int i = 0; i < antallTrekninger; i++) {
			int tall = random.nextInt(10); // 0..9
			antall[tall]++;
		}

		System.out.println("Antall trekninger: " + antallTrekninger);
		System.out.println("Tall  Antall  Stjerner (≈ % av total)");

		for (int i = 0; i < antall.length; i++) {
			int count = antall[i];
			// En stjerne ~ 1 prosentpoeng (avrundet) av total antall trekninger
			int stjerner = (int) Math.round(count * 100.0 / antallTrekninger);
			
			StringBuilder sb = new StringBuilder();
			
			for (int s = 0; s < stjerner; s++) sb.append('*');

			System.out.printf("%2d   %6d  %s%n", i, count, sb.toString());
		}

		System.out.println();
	}

	public static void main(String[] args) {
		// Kjør med 1000, 5000 og 10000 som foreslått
		kjør(1000);
		kjør(5000);
		kjør(10_000);

		// Valgfritt: la bruker prøve eget antall
		Scanner in = new Scanner(System.in);
		System.out.print("Skriv inn et valgfritt antall trekninger (eller tom linje for å avslutte): ");
		String line = in.nextLine().trim();

		if (!line.isEmpty()) {
			try {
				int n = Integer.parseInt(line);
				if (n > 0) kjør(n);
			} catch (NumberFormatException ignored) {}
		}
		
		in.close();
	}
}

