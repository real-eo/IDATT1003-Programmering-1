import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

/**
 * Øving 8 – Samarbeid mellom objekter
 *
 * Inneholder:
 * - Person (immutabel)
 * - ArbTaker
 * - Klientprogram (menystyrt) i main
 *
 * Merk: Identifikatorer er holdt i ASCII (f.eks. "fodselsaar") for å unngå
 * potensielle enkodingsproblemer på tvers av maskiner. Utskrifter bruker norske tegn.
 */
public class Oppgave1 {

	public static void main(String[] args) {
		// Eksempeldata
		Person p = new Person("Berit", "Johnsen", 1995);
		ArbTaker arbTaker = new ArbTaker(p, 12345, 2019, 50000, 30.0);

		// Kall alle objektmetodene en gang for å demonstrere
		demoUtskrift(arbTaker);

		// Menystyrt program
		kjorMeny(arbTaker);
	}

	private static void demoUtskrift(ArbTaker a) {
		System.out.println("\n--- Første utskrift (demo) ---");
		System.out.println(a);
		System.out.printf(Locale.US, "Skatt per måned: %.2f kr%n", a.skattPerManed());
		System.out.printf(Locale.US, "Bruttolønn per år: %.2f kr%n", a.bruttoLonnPerAar());
		System.out.printf(Locale.US, "Skattetrekk per år: %.2f kr%n", a.skattetrekkPerAar());
		System.out.println("Navn: " + a.navnEtterFornavn());
		System.out.println("Alder: " + a.alder() + " år");
		System.out.println("Antall år ansatt: " + a.antallAarAnsatt() + " år");
		System.out.println("Ansatt mer enn 5 år? " + (a.harVaertAnsattMerEnn(5) ? "Ja" : "Nei"));
	}

	private static void kjorMeny(ArbTaker a) {
		Scanner scanner = new Scanner(System.in);
		boolean ferdig = false;
		while (!ferdig) {
			System.out.println("\n======= MENY =======");
			System.out.println("1) Vis informasjon");
			System.out.println("2) Endre månedslønn");
			System.out.println("3) Endre skatteprosent");
			System.out.println("4) Avslutt");
			System.out.print("Valg: ");

			String valg = scanner.nextLine().trim();
			switch (valg) {
				case "1":
					skrivInfo(a);
					break;
				case "2":
					System.out.print("Ny månedslønn (kr): ");
					try {
						int ny = Integer.parseInt(scanner.nextLine().trim());
						a.setManedsloenn(ny);
						System.out.println("Månedslønn oppdatert.");
					} catch (NumberFormatException e) {
						System.out.println("Ugyldig tall.");
					}
					break;
				case "3":
					System.out.print("Ny skatteprosent (0-100): ");
					try {
						double ny = Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
						a.setSkatteprosent(ny);
						System.out.println("Skatteprosent oppdatert.");
					} catch (NumberFormatException e) {
						System.out.println("Ugyldig tall.");
					}
					break;
				case "4":
					ferdig = true;
					break;
				default:
					System.out.println("Ugyldig valg.");
			}
		}
		scanner.close();
	}

	private static void skrivInfo(ArbTaker a) {
		System.out.println("\n--- Informasjon ---");
		System.out.println(a);
		System.out.printf(Locale.US, "Skatt per måned: %.2f kr%n", a.skattPerManed());
		System.out.printf(Locale.US, "Bruttolønn per år: %.2f kr%n", a.bruttoLonnPerAar());
		System.out.printf(Locale.US, "Skattetrekk per år: %.2f kr%n", a.skattetrekkPerAar());
		System.out.println("Navn: " + a.navnEtterFornavn());
		System.out.println("Alder: " + a.alder() + " år");
		System.out.println("Antall år ansatt: " + a.antallAarAnsatt() + " år");
	}
}

/**
 * Immuten Person-klasse.
 */
final class Person {
	private final String fornavn;
	private final String etternavn;
	private final int fodselsaar;

	public Person(String fornavn, String etternavn, int fodselsaar) {
		if (fornavn == null || etternavn == null) {
			throw new IllegalArgumentException("Fornavn/etternavn kan ikke være null");
		}
		this.fornavn = fornavn;
		this.etternavn = etternavn;
		this.fodselsaar = fodselsaar;
	}

	public String getFornavn() { return fornavn; }
	public String getEtternavn() { return etternavn; }
	public int getFodselsaar() { return fodselsaar; }

	@Override
	public String toString() {
		return etternavn + ", " + fornavn + " (f. " + fodselsaar + ")";
	}
}

/**
 * Klasse for arbeidstaker.
 */
class ArbTaker {
	private final Person personalia;
	private final int arbtakernr;
	private final int ansettelsesaar;
	private int manedsloenn;        	// i kroner
	private double skatteprosent;    	// i prosent, f.eks. 30.0

	public ArbTaker(Person personalia, int arbtakernr, int ansettelsesaar,
					int manedsloenn, double skatteprosent) {
		if (personalia == null) throw new IllegalArgumentException("Personalia kan ikke være null");
		this.personalia = personalia;
		this.arbtakernr = arbtakernr;
		this.ansettelsesaar = ansettelsesaar;
		setManedsloenn(manedsloenn);
		setSkatteprosent(skatteprosent);
	}

	// Get-metoder
	public Person getPersonalia() { return personalia; }
	public int getArbtakernr() { return arbtakernr; }
	public int getAnsettelsesaar() { return ansettelsesaar; }
	public int getManedsloenn() { return manedsloenn; }
	public double getSkatteprosent() { return skatteprosent; }

	// Set-metoder for naturlig endringsbare attributter
	public void setManedsloenn(int manedsloenn) {
		if (manedsloenn < 0) throw new IllegalArgumentException("Månedslønn kan ikke være negativ");
		this.manedsloenn = manedsloenn;
	}

	public void setSkatteprosent(double skatteprosent) {
		if (skatteprosent < 0) skatteprosent = 0;
		if (skatteprosent > 100) skatteprosent = 100;
		this.skatteprosent = skatteprosent;
	}

	// Operasjoner
	public double skattPerManed() {
		return manedsloenn * (skatteprosent / 100.0);
	}

	public double bruttoLonnPerAar() {
		return manedsloenn * 12.0;
	}

	public double skattetrekkPerAar() {
		// Juni skattefri (0), desember halv skatt -> totalt 10.5 mnd med fullt skattetrekk
		return skattPerManed() * 10.5;
	}

	public String navnEtterFornavn() {
		// Samarbeider med Person-objektet (henter navn derfra)
		return personalia.getEtternavn() + ", " + personalia.getFornavn();
	}

	public int alder() {
		// Samarbeider med Person via fødselsår
		return innevarendeAar() - personalia.getFodselsaar();
	}

	public int antallAarAnsatt() {
		return innevarendeAar() - ansettelsesaar;
	}

	public boolean harVaertAnsattMerEnn(int aar) {
		return antallAarAnsatt() > aar;
	}

	private int innevarendeAar() {
		GregorianCalendar kalender = new GregorianCalendar();
		return kalender.get(Calendar.YEAR);
	}

	@Override
	public String toString() {
		return "Arbtaker {" +
				"personalia=" + personalia +
				", nr=" + arbtakernr +
				", ansatt siden=" + ansettelsesaar +
				", månedslønn=" + manedsloenn + " kr" +
				", skatteprosent=" + skatteprosent + "%" +
				'}';
	}
}



