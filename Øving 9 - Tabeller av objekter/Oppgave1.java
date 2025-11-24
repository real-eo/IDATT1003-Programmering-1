public class Oppgave1 {
	public static void main(String[] args) {
		// Enkelt testprogram for Student og Oppgaveoversikt
		Oppgaveoversikt oversikt = new Oppgaveoversikt();

		System.out.println(oversikt);

		// Registrer noen studenter
		oversikt.registrerNyStudent("Ola Nordmann");
		oversikt.registrerNyStudent("Kari Nordmann");
		oversikt.registrerNyStudent("Per Hansen");

		// Øk antall oppgaver
		oversikt.økAntOppgaverFor("Ola Nordmann", 3);
		oversikt.økAntOppgaverFor("Kari Nordmann", 5);
		oversikt.økAntOppgaverFor("Per Hansen", 2);

		// Skriv ut oversikt
		System.out.println(oversikt);

		// Finn antall studenter
		System.out.println("Antall studenter registrert: " + oversikt.getAntStud());

		// Finn antall oppgaver for bestemte studenter
		System.out.println("Kari har løst " + oversikt.finnAntOppgaverFor("Kari Nordmann") + " oppgaver.");

		// Test Student.toString() direkte
		Student s = oversikt.finnStudent("Ola Nordmann");
		if (s != null) {
			System.out.println("Direkte student-objekt: " + s);
		}
	}
}
