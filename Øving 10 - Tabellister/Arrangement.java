import java.util.Objects;

public class Arrangement {
    private final int nummer;
    private final String navn;
    private final String sted;
    private final String arrangor;
    private final String type;      // konsert, barneteater, foredrag, ...
    private final long tidspunkt;   // YYYYMMDDHHMM, e.g., 200210301800

    public Arrangement(int nummer, String navn, String sted, String arrangor, String type, long tidspunkt) {
        this.nummer = nummer;
        this.navn = navn;
        this.sted = sted;
        this.arrangor = arrangor;
        this.type = type;
        this.tidspunkt = tidspunkt;
    }

    public int getNummer() { return nummer; }
    public String getNavn() { return navn; }
    public String getSted() { return sted; }
    public String getArrangor() { return arrangor; }
    public String getType() { return type; }
    public long getTidspunkt() { return tidspunkt; }

    public int getDato() {
        return (int)(tidspunkt / 10000L); // YYYYMMDD
    }

    @Override
    public String toString() {
        return String.format("#%d | %s | %s | %s | %s | %s", nummer, navn, sted, arrangor, type, formatTid(tidspunkt));
    }

    private static String formatTid(long tid) {
        String s = Long.toString(tid);
        if (s.length() != 12) return s; // fallback
        
        String yyyy = s.substring(0, 4);
        String mm = s.substring(4, 6);
        String dd = s.substring(6, 8);
        String hh = s.substring(8, 10);
        String mi = s.substring(10, 12);

        return yyyy + "-" + mm + "-" + dd + " " + hh + ":" + mi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Arrangement)) return false;
        
        Arrangement that = (Arrangement) o;
        
        return nummer == that.nummer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nummer);
    }
}
