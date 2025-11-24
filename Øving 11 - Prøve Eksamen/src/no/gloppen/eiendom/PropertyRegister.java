package no.gloppen.eiendom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Register for eiendommer. Håndterer lagring og enkle søk/analyser.
 *
 * Valg av datastruktur:
 * - ArrayList fordi antallet eiendommer ikke er kjent på forhånd (dynamisk størrelse).
 * - Rask innsetting $O(1)$ (amortisert) og naturlig iterasjonsrekkefølge egner seg for utskrift.
 * - Lineært søk og sletting $O(n)$ er tilstrekkelig for denne oppgaven og små/middels datamengder.
 * - Unikhet sikres via {@link Property#equals(Object)} som bruker (kommunenr, gnr, bnr).
 *
 * Ved behov for større skala kan man bytte til en HashMap<String, Property> med nøkkel
 * på formatet "kommunenr-gnr/bnr" for $O(1)$ oppslag og sletting.
 */
public class PropertyRegister {
    private final List<Property> properties = new ArrayList<>();

    /**
     * Legger til en eiendom hvis den ikke finnes fra før.
     * Duplikater stoppes ved at {@link Property#equals(Object)}/hashCode() definerer identitet som
     * (kommunenr, gnr, bnr).
     * @param property eiendommen
     * @return true hvis lagt til, false hvis duplikat eller null
     */
    public boolean addProperty(Property property) {
        if (property == null) return false;
        if (properties.contains(property)) return false; // uniqueness by equals()
        properties.add(property);
        return true;
    }

    /**
     * @return en umodifiserbar liste over alle eiendommer
     */
    public List<Property> getAll() {
        return Collections.unmodifiableList(properties);
    }

    /**
     * Sletter en eiendom identifisert ved kommunenr, gnr og bnr.
     * @param municipalityNumber kommunenummer
     * @param lotNumber gnr
     * @param sectionNumber bnr
     * @return true hvis en eiendom ble slettet, ellers false
     */
    public boolean deleteProperty(int municipalityNumber, int lotNumber, int sectionNumber) {
        for (int i = 0; i < properties.size(); i++) {
            Property p = properties.get(i);
            if (p.getMunicipalityNumber() == municipalityNumber &&
                p.getLotNumber() == lotNumber &&
                p.getSectionNumber() == sectionNumber) {
                properties.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Finner en eiendom basert på kommunenr, gnr og bnr.
     * @param municipalityNumber kommunenummer
     * @param lotNumber gnr
     * @param sectionNumber bnr
     * @return Optional med eiendom hvis funnet
     */
    public Optional<Property> find(int municipalityNumber, int lotNumber, int sectionNumber) {
        for (Property p : properties) {
            if (p.getMunicipalityNumber() == municipalityNumber &&
                p.getLotNumber() == lotNumber &&
                p.getSectionNumber() == sectionNumber) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    /**
     * Returnerer alle eiendommer med gitt gårdsnummer (gnr).
     * Returnerer en ny liste hver gang (kan være tom hvis ingen treff).
     * @param lotNumber gnr (>0)
     * @return liste av eiendommer med oppgitt gnr
     * @throws IllegalArgumentException hvis gnr <= 0
     */
    public List<Property> findByLotNumber(int lotNumber) {
        if (lotNumber <= 0) {
            throw new IllegalArgumentException("Gårdsnummer (gnr) må være > 0.");
        }
        List<Property> result = new ArrayList<>();
        for (Property p : properties) {
            if (p.getLotNumber() == lotNumber) {
                result.add(p);
            }
        }
        return result;
    }

    /**
     * Beregner gjennomsnittlig areal.
     * @return gjennomsnitt eller 0.0 hvis tomt
     */
    public double averageArea() {
        if (properties.isEmpty()) return 0.0;

        double sum = 0.0;
        
        for (Property p : properties) {
            sum += p.getAreaSqm();
        }

        return sum / properties.size();
    }

    /**
     * Antall registrerte eiendommer.
     */
    public int size() { return properties.size(); }
}
