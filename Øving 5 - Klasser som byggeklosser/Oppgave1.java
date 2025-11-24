public class Oppgave1 {
	public static void main(String[] args) {
		// Enkel testklient for Brok
		Brok a = new Brok(1, 2);   // 1/2
		Brok b = new Brok(3, 4);   // 3/4

		Brok sum = new Brok(a.getTeller(), a.getNevner());
		sum.summer(b);
		System.out.println(a + " + " + b + " = " + sum);

		Brok diff = new Brok(a.getTeller(), a.getNevner());
		diff.subtraher(b);
		System.out.println(a + " - " + b + " = " + diff);

		Brok prod = new Brok(a.getTeller(), a.getNevner());
		prod.multipliser(b);
		System.out.println(a + " * " + b + " = " + prod);

		Brok kvot = new Brok(a.getTeller(), a.getNevner());
		kvot.divider(b);
		System.out.println(a + " / " + b + " = " + kvot);

		// Kanttilfeller
		try {
			new Brok(1, 0);
		} catch (IllegalArgumentException e) {
			System.out.println("Forventet unntak (nevner=0): " + e.getMessage());
		}

		try {
			Brok x = new Brok(1, 3);
			x.divider(new Brok(0, 5));
		} catch (IllegalArgumentException e) {
			System.out.println("Forventet unntak ved deling på 0: " + e.getMessage());
		}
	}

	// Klasse for å regne med brøk (Brok = brøk)
	static class Brok {
		private int teller;
		private int nevner;

		// Konstruktør: teller og nevner (kaster unntak hvis nevner == 0)
		public Brok(int teller, int nevner) {
			if (nevner == 0) {
				throw new IllegalArgumentException("Nevner kan ikke være 0");
			}
			this.teller = teller;
			this.nevner = nevner;
			normaliser();													// ? Ekstraoppgave: forkorte og sørg for positiv nevner
		}

		// Konstruktør: kun teller (nevner settes til 1)
		public Brok(int teller) {
			this(teller, 1);
		}

		// Get-metoder
		public int getTeller() { return teller; }
		public int getNevner() { return nevner; }

		// this = this + annen
		public void summer(Brok annen) {
			this.teller = this.teller * annen.nevner + annen.teller * this.nevner;
			this.nevner = this.nevner * annen.nevner;
			normaliser();
		}

		// this = this - annen
		public void subtraher(Brok annen) {
			this.teller = this.teller * annen.nevner - annen.teller * this.nevner;
			this.nevner = this.nevner * annen.nevner;
			normaliser();
		}

		// this = this * annen
		public void multipliser(Brok annen) {
			this.teller *= annen.teller;
			this.nevner *= annen.nevner;
			if (this.nevner == 0) {
				throw new IllegalArgumentException("Nevner kan ikke bli 0");
			}
			normaliser();
		}

		// this = this / annen
		public void divider(Brok annen) {
			if (annen.teller == 0) {
				throw new IllegalArgumentException("Kan ikke dele på 0");
			}
			this.teller *= annen.nevner;
			this.nevner *= annen.teller;
			if (this.nevner == 0) {
				throw new IllegalArgumentException("Nevner kan ikke bli 0");
			}
			normaliser();
		}

		// Sørger for at nevner alltid er positiv og forkorter brøken
		private void normaliser() {
			if (nevner < 0) {												// ? flytt fortegn til teller
				teller = -teller;
				nevner = -nevner;
			}
			int g = gcd(Math.abs(teller), Math.abs(nevner));
			if (g > 1) {
				teller /= g;
				nevner /= g;
			}
		}

		private static int gcd(int a, int b) {
			if (a == 0 && b == 0) return 1; 								// ? definér gcd(0,0) som 1 for å unngå /0
			while (b != 0) {
				int t = b;
				b = a % b;
				a = t;
			}
			return a == 0 ? 1 : a;
		}

		@Override
		public String toString() {
			return (nevner == 1) ? Integer.toString(teller) : (teller + "/" + nevner);
		}
	}
}
