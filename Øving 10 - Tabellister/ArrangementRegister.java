import java.util.*;

public class ArrangementRegister {
    private final List<Arrangement> arrangementer = new ArrayList<>();
    private final Set<Integer> nummerIndex = new HashSet<>();

    // Registrer nytt arrangement, returnerer false hvis nummer finnes fra før
    public boolean registrer(Arrangement a) {
        if (nummerIndex.contains(a.getNummer())) return false;

        arrangementer.add(a);
        nummerIndex.add(a.getNummer());
        
        return true;
    }

    // Hent alle (kopi)
    public List<Arrangement> hentAlle() {
        return new ArrayList<>(arrangementer);
    }

    public List<Arrangement> finnArrangementerPåSted(String sted) {
        List<Arrangement> res = new ArrayList<>();

        for (Arrangement a : arrangementer) {
            if (a.getSted().equalsIgnoreCase(sted)) {
                res.add(a);
            }
        }
        
        res.sort(Comparator.comparingLong(Arrangement::getTidspunkt));

        return res;
    }

    public List<Arrangement> finnArrangementerPåDato(int dato) {
        List<Arrangement> res = new ArrayList<>();

        for (Arrangement a : arrangementer) {
            if (a.getDato() == dato) {
                res.add(a);
            }
        }
        
        res.sort(Comparator.comparingLong(Arrangement::getTidspunkt));
        
        return res;
    }

    public List<Arrangement> finnArrangementerInnenfor(long fraInkl, long tilInkl) {
        List<Arrangement> res = new ArrayList<>();
        
        for (Arrangement a : arrangementer) {
            long t = a.getTidspunkt();
            if (t >= fraInkl && t <= tilInkl) {
                res.add(a);
            }
        }

        res.sort(Comparator.comparingLong(Arrangement::getTidspunkt));
        
        return res;
    }

    public List<Arrangement> listeSortertEtterSted() {
        List<Arrangement> res = hentAlle();

        res.sort(Comparator.comparing(Arrangement::getSted, String.CASE_INSENSITIVE_ORDER)
                .thenComparingLong(Arrangement::getTidspunkt));

        return res;
    }

    public List<Arrangement> listeSortertEtterType() {
        List<Arrangement> res = hentAlle();

        res.sort(Comparator.comparing(Arrangement::getType, String.CASE_INSENSITIVE_ORDER)
                .thenComparingLong(Arrangement::getTidspunkt));

        return res;
    }

    public List<Arrangement> listeSortertEtterTid() {
        List<Arrangement> res = hentAlle();

        res.sort(Comparator.comparingLong(Arrangement::getTidspunkt));
        
        return res;
    }
}
