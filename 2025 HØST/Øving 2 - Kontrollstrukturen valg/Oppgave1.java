import java.util.Scanner;

/**
 * Oppgave 1 - Skuddår
 *
 * Algoritme:
 * 1) Les årstall fra brukeren
 * 2) Hvis år % 4 != 0 -> ikke skuddår
 * 3) Ellers hvis år % 100 != 0 -> skuddår
 * 4) Ellers hvis år % 400 == 0 -> skuddår
 * 5) Ellers -> ikke skuddår
 *
 * Testdata (eksempler): 2000 (skuddår), 1900 (ikke), 2004 (skuddår), 2100 (ikke), 2024 (skuddår)
 */
public class Oppgave1 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Skriv inn årstall: ");
		if (!sc.hasNextInt()) {
			System.out.println("Ugyldig input. Vennligst skriv et heltall for årstall.");
			sc.close();
			return;
		}

		int aar = sc.nextInt();

		boolean skuddaar = isSkuddaar(aar);

		if (skuddaar) 	{ System.out.println(aar + " er et skuddår."); }
		else 			{ System.out.println(aar + " er ikke et skuddår."); }

		sc.close();

		// Test data
		testSkuddaar(new int[] { 2000, 1900, 2004, 2100, 2024 });
	}

	private static boolean isSkuddaar(int aar) {
		return (aar % 4 == 0) && ((aar % 100 != 0) || (aar % 400 == 0));
		
		// ? The equivalent if-statement would be:
		/*
		if (aar % 4 == 0) {
			if (aar % 100 != 0 || aar % 400 == 0) {
				return true;
			}
		}
		return false;
		*/

		// ? Expanded variant:
		/* 
		if (aar % 4 != 0) {
			return false;
		} else if (aar % 100 != 0) {
			return true;
		} else if (aar % 400 == 0) {
			return true;
		} else {
			return false;
		}
		*/
	}

	private static void testSkuddaar(int[] aar) {
		System.out.println("Tester skuddår:");
		
		for (int i = 0; i < aar.length; i++) {
			System.out.printf("%d: %s\n", aar[i], isSkuddaar(aar[i]) ? "skuddår" : "ikke skuddår");
		}
	}
}
