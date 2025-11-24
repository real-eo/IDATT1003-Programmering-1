import java.util.*;

public class Oppgave2 {
	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		MenyRegister register = new MenyRegister();

		boolean running = true;
		while (running) {
			printMenu();
			String choice = scanner.nextLine().trim();
			switch (choice) {
				case "1":
					leggTilRett(register);
					break;
				case "2":
					finnRett(register);
					break;
				case "3":
					finnRetterAvType(register);
					break;
				case "4":
					registrerMeny(register);
					break;
				case "5":
					finnMenyerIPrisintervall(register);
					break;
				case "6":
					listAlleMenyer(register);
					break;
				case "0":
					running = false;
					break;
				default:
					System.out.println("Ugyldig valg.");
			}
		}
		System.out.println("Avslutter.");
	}

	private static void printMenu() {
		System.out.println();
		System.out.println("Meny-register");
		System.out.println("1) Registrer ny rett");
		System.out.println("2) Finn en rett (navn)");
		System.out.println("3) Finn alle retter av en type");
		System.out.println("4) Registrer ny meny (sett av rettenavn)");
		System.out.println("5) Finn alle menyer i prisintervall");
		System.out.println("6) List alle menyer");
		System.out.println("0) Avslutt");
		System.out.print("Valg: ");
	}

	private static void leggTilRett(MenyRegister register) {
		System.out.print("Navn: ");
		String navn = scanner.nextLine().trim();
		System.out.print("Type (forrett, hovedrett, dessert, ...): ");
		String type = scanner.nextLine().trim();
		System.out.print("Pris (heltall eller desimal): ");
		double pris;
		try {
			pris = Double.parseDouble(scanner.nextLine().trim());
		} catch (NumberFormatException e) {
			System.out.println("Ugyldig pris.");
			return;
		}
		System.out.print("Oppskrift/beskrivelse: ");
		String oppskrift = scanner.nextLine().trim();

		boolean ok = register.registrerRett(new Rett(navn, type, pris, oppskrift));
		System.out.println(ok ? "Rett registrert." : "En rett med dette navnet finnes allerede.");
	}

	private static void finnRett(MenyRegister register) {
		System.out.print("Navn: ");
		String navn = scanner.nextLine().trim();
		Rett r = register.finnRett(navn);
		System.out.println(r != null ? r : "Fant ingen rett.");
	}

	private static void finnRetterAvType(MenyRegister register) {
		System.out.print("Type: ");
		String type = scanner.nextLine().trim();
		List<Rett> retter = register.finnRetterAvType(type);
		if (retter.isEmpty()) {
			System.out.println("Ingen retter av gitt type.");
		} else {
			for (Rett r : retter) System.out.println(r);
		}
	}

	private static void registrerMeny(MenyRegister register) {
		System.out.print("Navn på meny: ");
		String navn = scanner.nextLine().trim();
		System.out.println("Skriv inn rettenavn som skal inngå i menyen, separert med komma:");
		String linje = scanner.nextLine();
		String[] navnListe = Arrays.stream(linje.split(","))
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.toArray(String[]::new);

		Set<Rett> retter = new LinkedHashSet<>();
		for (String rn : navnListe) {
			Rett r = register.finnRett(rn);
			if (r != null) retter.add(r);
			else System.out.println("Advarsel: fant ikke rett '" + rn + "', hopper over.");
		}

		Meny meny = new Meny(navn, retter);
		boolean ok = register.registrerMeny(meny);
		System.out.println(ok ? "Meny registrert." : "En meny med dette navnet finnes allerede.");
	}

	private static void finnMenyerIPrisintervall(MenyRegister register) {
		try {
			System.out.print("Min pris: ");
			double min = Double.parseDouble(scanner.nextLine().trim());
			System.out.print("Maks pris: ");
			double max = Double.parseDouble(scanner.nextLine().trim());
			List<Meny> l = register.finnMenyerMedTotalprisInnenfor(min, max);
			if (l.isEmpty()) System.out.println("Ingen menyer i intervallet.");
			else l.forEach(System.out::println);
		} catch (NumberFormatException e) {
			System.out.println("Ugyldig pris.");
		}
	}

	private static void listAlleMenyer(MenyRegister register) {
		for (Meny m : register.hentAlleMenyer()) System.out.println(m);
	}
}

class Rett {
	private final String navn; // unique
	private final String type; // forrett, hovedrett, dessert, ...
	private final double pris;
	private final String oppskrift;

	public Rett(String navn, String type, double pris, String oppskrift) {
		this.navn = navn;
		this.type = type;
		this.pris = pris;
		this.oppskrift = oppskrift;
	}

	public String getNavn() { return navn; }
	public String getType() { return type; }
	public double getPris() { return pris; }
	public String getOppskrift() { return oppskrift; }

	@Override
	public String toString() {
		return String.format("Rett{%s, type=%s, pris=%.2f}", navn, type, pris);
	}
}

class Meny {
	private final String navn; 		// unique for simplicity
	private final Set<Rett> retter; // unike retter i meny

	public Meny(String navn, Collection<Rett> retter) {
		this.navn = navn;
		this.retter = new LinkedHashSet<>(retter);
	}

	public String getNavn() { return navn; }
	public Set<Rett> getRetter() { return new LinkedHashSet<>(retter); }

	public double totalPris() {
		double sum = 0.0;
		for (Rett r : retter) sum += r.getPris();
		return sum;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Meny ").append(navn).append(" (total ").append(String.format("%.2f", totalPris())).append(")\n");
		for (Rett r : retter) {
			sb.append("  - ").append(r).append("\n");
		}
		return sb.toString();
	}
}

class MenyRegister {
	private final Map<String, Rett> retter = new HashMap<>(); 		// key: navn
	private final Map<String, Meny> menyer = new LinkedHashMap<>(); // key: navn

	public boolean registrerRett(Rett r) {
		if (retter.containsKey(r.getNavn())) return false;
		
		retter.put(r.getNavn(), r);
		
		return true;
	}

	public Rett finnRett(String navn) {
		return retter.get(navn);
	}

	public List<Rett> finnRetterAvType(String type) {
		List<Rett> res = new ArrayList<>();
		
		for (Rett r : retter.values()) {
			if (r.getType().equalsIgnoreCase(type)) res.add(r);
		}
		
		res.sort(Comparator.comparing(Rett::getNavn, String.CASE_INSENSITIVE_ORDER));
		
		return res;
	}

	public boolean registrerMeny(Meny m) {
		if (menyer.containsKey(m.getNavn())) return false;
		
		menyer.put(m.getNavn(), m);
		
		return true;
	}

	public List<Meny> finnMenyerMedTotalprisInnenfor(double min, double max) {
		List<Meny> res = new ArrayList<>();

		for (Meny m : menyer.values()) {
			double sum = m.totalPris();
			if (sum >= min && sum <= max) res.add(m);
		}
		
		res.sort(Comparator.comparingDouble(Meny::totalPris));
		
		return res;
	}

	public List<Meny> hentAlleMenyer() {
		return new ArrayList<>(menyer.values());
	}
}
