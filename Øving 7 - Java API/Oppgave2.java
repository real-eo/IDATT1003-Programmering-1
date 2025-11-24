import java.util.Locale;

public class Oppgave2 {
	// Enkel testklient for tekstbehandling
	public static void main(String[] args) {
		String t = "Dette er en test. Finnes det æ, ø og å? Ja! Og kanskje: mer.";
		Tekstbehandling tb = new Tekstbehandling(t);

		System.out.println("Antall ord: " + tb.antallOrd());
		System.out.println("Gj.snitt ordlengde: " + String.format(Locale.US, "%.2f", tb.gjennomsnittligOrdlengde()));
		System.out.println("Gj.snitt ord per periode: " + String.format(Locale.US, "%.2f", tb.gjennomsnittOrdPerPeriode()));
		System.out.println("Bytt 'Finnes'->'fins': " + tb.byttOrd("Finnes", "fins").getTekst());
		System.out.println("Uendret: " + tb.getTekst());
		System.out.println("STORE: " + tb.getTekstStore());
	}
}

// Enkel, immutabel tekstbehandler
class Tekstbehandling {
	private final String tekst;

	public Tekstbehandling(String tekst) {
		if (tekst == null) throw new IllegalArgumentException("tekst kan ikke være null");
		this.tekst = tekst;
	}

	public String getTekst() {
		return tekst;
	}

	public String getTekstStore() {
		return tekst.toUpperCase(Locale.forLanguageTag("nb-NO"));
	}

	// Teller ord; ignorerer skilletegn. Bruker regex for å erstatte skilletegn med mellomrom.
	public int antallOrd() {
		String normalisert = tekst.trim();

		if (normalisert.isEmpty()) return 0;
		
		// Behold bokstaver (inkl. æøå ÆØÅ) og tall som ordtegn; alt annet blir mellomrom
		String renset = normalisert.replaceAll("[^A-Za-zÆØÅæøå0-9]+", " ").trim();
		
		if (renset.isEmpty()) return 0;
		
		return renset.split("\\s+").length;
	}

	// Gjennomsnittlig ordlengde uten skilletegn
	public double gjennomsnittligOrdlengde() {
		String normalisert = tekst.trim();
		if (normalisert.isEmpty()) return 0.0;
		
		String[] ord = normalisert.replaceAll("[^A-Za-zÆØÅæøå0-9]+", " ").trim().split("\\s+");
		if (ord.length == 0) return 0.0;
		
		int sum = 0;
		for (String o : ord) sum += o.length();
		
		return (double) sum / ord.length;
	}

	// Perioder splittes på . ! : ? (antatt ikke dobbelte tegn)
	public double gjennomsnittOrdPerPeriode() {
		String normalisert = tekst.trim();
		if (normalisert.isEmpty()) return 0.0;
		
		String[] perioder = normalisert.split("[.!:?]");
		int antPerioder = 0;
		int ordSum = 0;
		
		for (String p : perioder) {
			String pTrim = p.trim();
			if (pTrim.isEmpty()) continue;
			antPerioder++;
			String renset = pTrim.replaceAll("[^A-Za-zÆØÅæøå0-9]+", " ").trim();
			if (!renset.isEmpty()) ordSum += renset.split("\\s+").length;
		}
		
		if (antPerioder == 0) return 0.0;
		
		return (double) ordSum / antPerioder;
	}

	// Bytter ut et ord med et annet i hele teksten; match på hele ord (case sensitive)
	public Tekstbehandling byttOrd(String gammelt, String nytt) {
		if (gammelt == null || nytt == null) return new Tekstbehandling(tekst);
		if (gammelt.isEmpty()) return new Tekstbehandling(tekst);

		// Hele ord (omgitt av start/ende eller ikke-ordtegn). Beholder nordiske bokstaver i ordklassen
		String pattern = String.format("(?<=^|[^A-Za-zÆØÅæøå0-9])%s(?=$|[^A-Za-zÆØÅæøå0-9])", java.util.regex.Pattern.quote(gammelt));
		String erstattet = tekst.replaceAll(pattern, java.util.regex.Matcher.quoteReplacement(nytt));
		
		return new Tekstbehandling(erstattet);
	}
}
