import java.util.Random;

class Spiller {
	private final Random terning = new Random();
	private int sumPoeng;
	private boolean trekkNeste; 	// brukes i eksakt-100-varianten
	private boolean sistVarTrekk; 	// for utskrift

	public int getSumPoeng() {
		return sumPoeng;
	}

	public boolean erFerdig() {
		return sumPoeng >= 100;
	}

	public boolean sistOperasjonVarTrekk() {
		return sistVarTrekk;
	}

	// Returnerer selve kastet (1..6)
	public int kastTerningen(boolean eksaktHundre) {
		int kast = terning.nextInt(6) + 1; // 1..6
		sistVarTrekk = false;

		if (!eksaktHundre) {
			if (kast == 1) {
				sumPoeng = 0;
			} else {
				sumPoeng += kast;
			}
			return kast;
		}

		// Eksakt-100-varianten med vekslende tillegg/trekk etter overskridelse
		if (kast == 1) {
			sumPoeng = 0;
			trekkNeste = false; // reset sykling
			return kast;
		}

		if (trekkNeste) {
			sumPoeng -= kast;
			if (sumPoeng < 0) sumPoeng = 0;
			sistVarTrekk = true;
			if (sumPoeng != 100) {
				// fortsett veksel: neste gang legger vi til
				trekkNeste = false;
			}
		} else {
			sumPoeng += kast;
			if (sumPoeng > 100) {
				// overskred 100 -> neste gang skal vi trekke
				trekkNeste = true;
			}
		}

		return kast;
	}
}

public class Oppgave2 {
	public static void main(String[] args) {
	boolean eksaktHundre = false; // sett true for raffinert regel

		Spiller a = new Spiller();
		Spiller b = new Spiller();

		int runde = 1;

		while (!a.erFerdig() && !b.erFerdig()) {
			System.out.println("Runde " + runde + ":");

			// Spiller A kaster
			int kastA = a.kastTerningen(eksaktHundre);
			System.out.println("  A kastet " + kastA + (eksaktHundre && a.sistOperasjonVarTrekk() ? " (trekk)" : ""));

			// Spiller B kaster
			int kastB = b.kastTerningen(eksaktHundre);
			System.out.println("  B kastet " + kastB + (eksaktHundre && b.sistOperasjonVarTrekk() ? " (trekk)" : ""));

			System.out.println("  Sum A: " + a.getSumPoeng() + ", Sum B: " + b.getSumPoeng());
			System.out.println();

			runde++;
		}

		if (a.erFerdig() && b.erFerdig()) {
			System.out.println("Uavgjort! Begge nådde 100 i samme runde.");
		} else if (a.erFerdig()) {
			System.out.println("Spiller A vant!");
		} else {
			System.out.println("Spiller B vant!");
		}
	}
}
