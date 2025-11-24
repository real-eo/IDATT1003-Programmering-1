import java.util.*;

public class Oppgave1 {
	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		ArrangementRegister register = new ArrangementRegister();

		boolean running = true;
		while (running) {
			printMenu();
			String choice = scanner.nextLine().trim();
			switch (choice) {
				case "1":
					registrerArrangement(register);
					break;
				case "2":
					finnPåSted(register);
					break;
				case "3":
					finnPåDato(register);
					break;
				case "4":
					finnIIntervall(register);
					break;
				case "5":
					listSortert(register.listeSortertEtterSted());
					break;
				case "6":
					listSortert(register.listeSortertEtterType());
					break;
				case "7":
					listSortert(register.listeSortertEtterTid());
					break;
				case "8":
					listSortert(register.hentAlle());
					break;
				case "0":
					running = false;
					break;
				default:
					System.out.println("Ugyldig valg. Prøv igjen.");
			}
		}
		System.out.println("Avslutter.");
	}

	private static void printMenu() {
		System.out.println();
		System.out.println("Arrangement-register");
		System.out.println("1) Registrer nytt arrangement");
		System.out.println("2) Finn alle på sted");
		System.out.println("3) Finn alle på dato (YYYYMMDD)");
		System.out.println("4) Finn alle i tidsintervall (YYYYMMDDHHMM - YYYYMMDDHHMM)");
		System.out.println("5) List alle sortert etter sted");
		System.out.println("6) List alle sortert etter type");
		System.out.println("7) List alle sortert etter tidspunkt");
		System.out.println("8) List alle (usortert)");
		System.out.println("0) Avslutt");
		System.out.print("Valg: ");
	}

	private static void registrerArrangement(ArrangementRegister register) {
		try {
			System.out.print("Unikt nummer (heltall): ");
			int nr = Integer.parseInt(scanner.nextLine().trim());
			
			System.out.print("Navn: ");
			String navn = scanner.nextLine().trim();
			
			System.out.print("Sted: ");
			String sted = scanner.nextLine().trim();
			
			System.out.print("Arrangør: ");
			String arrangor = scanner.nextLine().trim();
			
			System.out.print("Type (konsert, barneteater, foredrag, ...): ");
			String type = scanner.nextLine().trim();

			System.out.print("Tidspunkt (YYYYMMDDHHMM), f.eks. 200210301800: ");
			long tid = Long.parseLong(scanner.nextLine().trim());

			Arrangement a = new Arrangement(nr, navn, sted, arrangor, type, tid);
			boolean ok = register.registrer(a);
			System.out.println(ok ? "Registrert." : "Nummeret finnes allerede.");
		} catch (NumberFormatException e) {
			System.out.println("Ugyldig tallformat. Avbrutt.");
		}
	}

	private static void finnPåSted(ArrangementRegister register) {
		System.out.print("Sted: ");
		String sted = scanner.nextLine().trim();
		listSortert(register.finnArrangementerPåSted(sted));
	}

	private static void finnPåDato(ArrangementRegister register) {
		try {
			System.out.print("Dato (YYYYMMDD): ");
			int dato = Integer.parseInt(scanner.nextLine().trim());
			
			listSortert(register.finnArrangementerPåDato(dato));
		} catch (NumberFormatException e) {
			System.out.println("Ugyldig dato.");
		}
	}

	private static void finnIIntervall(ArrangementRegister register) {
		try {
			System.out.print("Fra (YYYYMMDDHHMM): ");
			long fra = Long.parseLong(scanner.nextLine().trim());

			System.out.print("Til (YYYYMMDDHHMM): ");
			long til = Long.parseLong(scanner.nextLine().trim());
			
			listSortert(register.finnArrangementerInnenfor(fra, til));
		} catch (NumberFormatException e) {
			System.out.println("Ugyldig tidsangivelse.");
		}
	}

	private static void listSortert(List<Arrangement> liste) {
		if (liste.isEmpty()) {
			System.out.println("Ingen treff.");
			return;
		}
		for (Arrangement a : liste) {
			System.out.println(a);
		}
	}
}
