public class Oppgave1 {
	public static void main(String[] args) {
		// Testdatasett: 1, 5.5, 10, 25 tommer
		double[] tommerTest = {1, 5.5, 10, 25};
		
		for (double tommer : tommerTest) {
			double cm = tommer * 2.54;
			System.out.printf("%.2f tommer = %.2f centimeter\n", tommer, cm);
		}
	}
}
