import java.util.Arrays;

public class Oppgaveoversikt {
    // Objektvariabler
    private Student[] studenter;    // tabellen opprettes i konstruktøren
    private int antStud = 0;        // økes med 1 for hver ny student

    public Oppgaveoversikt(int startKapasitet) {
        if (startKapasitet <= 0) startKapasitet = 4;
        studenter = new Student[startKapasitet];
    }

    public Oppgaveoversikt() {
        this(4);
    }

    // Finn antall studenter registrert
    public int getAntStud() {
        return antStud;
    }

    // Finn antall oppgaver som en bestemt student har løst
    public int finnAntOppgaverFor(String navn) {
        int i = indeksForStudent(navn);
        if (i < 0) throw new IllegalArgumentException("Student finnes ikke: " + navn);
        return studenter[i].getAntOppg();
    }

    // Registrer en ny student
    public void registrerNyStudent(String navn) {
        if (indeksForStudent(navn) >= 0) {
            throw new IllegalArgumentException("Student finnes allerede: " + navn);
        }
        
        ensureCapacity(antStud + 1);
        studenter[antStud++] = new Student(navn);
    }

    // Øk antall oppgaver for en bestemt student
    public void økAntOppgaverFor(String navn, int okning) {
        int i = indeksForStudent(navn);
        if (i < 0) throw new IllegalArgumentException("Student finnes ikke: " + navn);
        studenter[i].økAntOppg(okning);
    }

    // ASCII alias
    public void okAntOppgaverFor(String navn, int okning) { økAntOppgaverFor(navn, okning); }

    public Student finnStudent(String navn) {
        int i = indeksForStudent(navn);
        return i >= 0 ? studenter[i] : null;
    }

    private int indeksForStudent(String navn) {
        if (navn == null) return -1;

        for (int i = 0; i < antStud; i++) {
            if (studenter[i].getNavn().equalsIgnoreCase(navn)) return i;
        }
        
        return -1;
    }

    private void ensureCapacity(int minCapacity) {
        if (studenter.length < minCapacity) {
            int nyLengde = Math.max(studenter.length * 2, minCapacity);
            studenter = Arrays.copyOf(studenter, nyLengde);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Antall studenter: ").append(antStud).append('\n');
        
        for (int i = 0; i < antStud; i++) {
            sb.append(i + 1).append(". ").append(studenter[i]).append('\n');
        }

        return sb.toString();
    }
}
