public class Oppgave1 {
	// Enkel testklient for Oppgave 1
	public static void main(String[] args) {
		NyString ny = new NyString("denne setningen kan forkortes");
		System.out.println(ny.forkort()); // forventet: dskf

		NyString utenE = ny.fjernTegn('e');
		System.out.println(utenE.getTekst()); // forventet: dnn stningn kan forkorts
	}
}

// Immuten klasse som kapsler inn en String og tilbyr ekstra tjenester
class NyString {
	private final String tekst;

	public NyString(String tekst) {
		if (tekst == null) {
			throw new IllegalArgumentException("tekst kan ikke være null");
		}
		this.tekst = tekst;
	}

	public String getTekst() {
		return tekst;
	}

	// Forkorter ved å ta første tegn i hvert ord (ord splittes på mellomrom)
	public String forkort() {
		if (tekst.isBlank()) return "";

		String[] ord = tekst.trim().split("\\s+");
		
		StringBuilder sb = new StringBuilder();
		
		for (String o : ord) {
			if (!o.isEmpty()) sb.append(o.charAt(0));
		}
		
		return sb.toString();
	}

	// Returnerer en ny NyString der alle forekomster av gitt tegn er fjernet
	public NyString fjernTegn(char tegn) {
		String s = this.tekst;
		int idx = s.indexOf(tegn);

		if (idx < 0) return new NyString(s);

		StringBuilder sb = new StringBuilder();
		int start = 0;
		
		while (idx >= 0) {
			sb.append(s, start, idx);
			start = idx + 1;
			idx = s.indexOf(tegn, start);
		}
		
		sb.append(s.substring(start));
		
		return new NyString(sb.toString());
	}
}
