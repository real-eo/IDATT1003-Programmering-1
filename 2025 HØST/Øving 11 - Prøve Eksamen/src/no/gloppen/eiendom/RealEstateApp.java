package no.gloppen.eiendom;

/**
 * Tekstbasert applikasjon for Gloppen kommune sitt eiendomsregister.
 */
public class RealEstateApp {

    private final PropertyRegister register = new PropertyRegister();

    public static void main(String[] args) {
        RealEstateApp app = new RealEstateApp();
        app.seedTestData();

        // Delegerer til separat UI-klasse
        new ConsoleMenu(app.register).start();
    }

    /**
     * Legger inn testdata gitt i oppgaveteksten.
     */
    private void seedTestData() {
        register.addProperty(new Property(1445, "Gloppen", 77, 631, "", 1017.6, "Jens Olsen"));
        register.addProperty(new Property(1445, "Gloppen", 77, 131, "Syningom", 661.3, "Nicolay Madsen"));
        register.addProperty(new Property(1445, "Gloppen", 75, 19, "Fugletun", 650.6, "Evilyn Jensen"));
        register.addProperty(new Property(1445, "Gloppen", 74, 188, "", 1457.2, "Karl Ove Bråten"));
        register.addProperty(new Property(1445, "Gloppen", 69, 47, "Høiberg", 1339.4, "Elsa Indregård"));
    }
} 