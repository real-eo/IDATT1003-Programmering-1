import java.util.Random;

public class Oppgave2 {
	public static void main(String[] args) {
		MinRandom mr = new MinRandom();

		// Test heltall
		int minH = -3, maxH = 7;
		boolean heltallOK = true;
		for (int i = 0; i < 1000; i++) {
			int v = mr.nesteHeltall(minH, maxH);
			if (v < minH || v >= maxH) { // høyre åpen
				heltallOK = false;
				System.out.println("Utenfor heltallsintervall: " + v);
				break;
			}
		}
		System.out.println("Heltall innenfor (" + minH + ", " + maxH + ") : " + heltallOK);

		// Test desimaltall
		double minD = 0.0, maxD = 1.0;
		boolean desimalOK = true;
		for (int i = 0; i < 1000; i++) {
			double d = mr.nesteDesimaltall(minD, maxD);
			if (!(d >= minD && d < maxD)) { // høyre åpen
				desimalOK = false;
				System.out.println("Utenfor desimalintervall: " + d);
				break;
			}
		}
		System.out.println("Desimaltall innenfor [" + minD + ", " + maxD + ") : " + desimalOK);
	}

	static class MinRandom {
		private final Random rand = new Random();

		// Returnerer et heltall i intervallet [nedre, ovre)
		public int nesteHeltall(int nedre, int ovre) {
			if (ovre <= nedre) {
				throw new IllegalArgumentException("ovre må være større enn nedre");
			}
			int bredde = ovre - nedre; // positiv
			// Random.nextInt(bound) gir [0, bound)
			return nedre + rand.nextInt(bredde);
		}

		// Returnerer et desimaltall i intervallet [nedre, ovre)
		public double nesteDesimaltall(double nedre, double ovre) {
			if (ovre <= nedre) {
				throw new IllegalArgumentException("ovre må være større enn nedre");
			}
			double u = rand.nextDouble(); // [0.0, 1.0)
			return nedre + u * (ovre - nedre);
		}
	}
}
