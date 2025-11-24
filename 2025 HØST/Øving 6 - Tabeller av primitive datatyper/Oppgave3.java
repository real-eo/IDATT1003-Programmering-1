import java.util.Arrays;

// Uforanderlig (immutabel) matrise-klasse
final class Matrise {
	private final int rader;
	private final int kolonner;
	private final int[][] data; // defensiv kopi i ctor

	public Matrise(int[][] inn) {
		if (inn == null || inn.length == 0 || inn[0].length == 0)
			throw new IllegalArgumentException("Ugyldig dimensjon");

		this.rader = inn.length;
		this.kolonner = inn[0].length;
		this.data = new int[rader][kolonner];

		for (int i = 0; i < rader; i++) {
			if (inn[i] == null || inn[i].length != kolonner)
				throw new IllegalArgumentException("Ujevn radlengde");

			System.arraycopy(inn[i], 0, this.data[i], 0, kolonner);
		}
	}

	public int getRader() { return rader; }
	public int getKolonner() { return kolonner; }

	// Returnerer en dyp kopi av underliggende data for trygghet (valgfritt)
	public int[][] tilTabell() {
		int[][] kopi = new int[rader][kolonner];
		for (int i = 0; i < rader; i++) System.arraycopy(data[i], 0, kopi[i], 0, kolonner);
		return kopi;
	}

	// Addisjon: dimensjoner må være like, ellers null
	public Matrise add(Matrise annen) {
		if (annen == null || annen.rader != rader || annen.kolonner != kolonner) return null;
		
		int[][] res = new int[rader][kolonner];

		for (int i = 0; i < rader; i++) {
			for (int j = 0; j < kolonner; j++) {
				res[i][j] = data[i][j] + annen.data[i][j];
			}
		}
		
		return new Matrise(res);
	}

	// Multiplikasjon: (r x k) * (k x c) => (r x c), ellers null
	public Matrise multipliser(Matrise annen) {
		if (annen == null || this.kolonner != annen.rader) return null;

		int[][] res = new int[this.rader][annen.kolonner];
		
		for (int i = 0; i < this.rader; i++) {
			for (int j = 0; j < annen.kolonner; j++) {
				int sum = 0;
				for (int k = 0; k < this.kolonner; k++) {
					sum += this.data[i][k] * annen.data[k][j];
				}
				res[i][j] = sum;
			}
		}

		return new Matrise(res);
	}

	// Transponering: (r x k) -> (k x r)
	public Matrise transponer() {
		int[][] res = new int[kolonner][rader];

		for (int i = 0; i < rader; i++) {
			for (int j = 0; j < kolonner; j++) {
				res[j][i] = data[i][j];
			}
		}

		return new Matrise(res);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < rader; i++) {
			sb.append(Arrays.toString(data[i])).append(System.lineSeparator());
		}

		return sb.toString();
	}
}

public class Oppgave3 {
	public static void main(String[] args) {
		// Enkle tester/klient
		int[][] aData = {
				{1, 2, 3},
				{4, 5, 6}
		}; // 2x3
		int[][] bData = {
				{7, 8, 9},
				{1, 2, 3}
		}; // 2x3
		int[][] cData = {
				{1, 2},
				{3, 4},
				{5, 6}
		}; // 3x2

		Matrise A = new Matrise(aData);
		Matrise B = new Matrise(bData);
		Matrise C = new Matrise(cData);

		System.out.println("A:");
		System.out.println(A);
		System.out.println("B:");
		System.out.println(B);

		System.out.println("A + B:");
		Matrise sum = A.add(B);
		System.out.println(sum != null ? sum : "null (dimensjoner passer ikke)");

		System.out.println("A * C:");
		Matrise prod = A.multipliser(C);
		System.out.println(prod != null ? prod : "null (dimensjoner passer ikke)");

		System.out.println("transpose(A):");
		System.out.println(A.transponer());
	}
}

