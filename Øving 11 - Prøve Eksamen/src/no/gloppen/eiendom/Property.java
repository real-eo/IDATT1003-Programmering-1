package no.gloppen.eiendom;

/**
 * Representerer en eiendom i Norge.
 *
 * <p>Identifiseres unikt ved kombinasjonen kommunenummer, gnr (gårdsnummer)
 * og bnr (bruksnummer). Bruksnavn er valgfritt.</p>
 */
public class Property {
    // Immutable identitet
    private final int municipalityNumber;   // [101, 5054]
    private final String municipalityName;  // ikke-null/ikke-tom
    private final int lotNumber;            // gnr > 0
    private final int sectionNumber;        // bnr > 0

    // Mutable egenskaper
    private String name;              // kan være tom
    private double areaSqm;           // > 0
    private String ownerName;         // ikke-null/ikke-tom

    /**
     * Oppretter en ny eiendom.
     *
     * @param municipalityNumber kommunenummer (101..5054)
     * @param municipalityName   kommunenavn (ikke tom)
     * @param lotNumber          gårdsnummer/gnr (>0)
     * @param sectionNumber      bruksnummer/bnr (>0)
     * @param name               bruksnavn (kan være tomt)
     * @param areaSqm            areal i m^2 (>0)
     * @param ownerName          navn på eier (ikke tom)
     * @throws IllegalArgumentException ved ugyldige verdier
     */
    public Property(int municipalityNumber,
                    String municipalityName,
                    int lotNumber,
                    int sectionNumber,
                    String name,
                    double areaSqm,
                    String ownerName) {
        if (municipalityNumber < 101 || municipalityNumber > 5054)
            throw new IllegalArgumentException("Kommunenummer må være mellom 101 og 5054.");
        if (municipalityName == null || municipalityName.trim().isEmpty())
            throw new IllegalArgumentException("Kommunenavn kan ikke være tomt.");
        if (lotNumber <= 0)
            throw new IllegalArgumentException("Gårdsnummer (gnr) må være > 0.");
        if (sectionNumber <= 0)
            throw new IllegalArgumentException("Bruksnummer (bnr) må være > 0.");
        if (areaSqm <= 0.0)
            throw new IllegalArgumentException("Areal må være > 0.");
        if (ownerName == null || ownerName.trim().isEmpty())
            throw new IllegalArgumentException("Eiers navn kan ikke være tomt.");

        this.municipalityNumber = municipalityNumber;
        this.municipalityName = municipalityName.trim();
        this.lotNumber = lotNumber;
        this.sectionNumber = sectionNumber;
        this.name = name == null ? "" : name.trim();
        this.areaSqm = areaSqm;
        this.ownerName = ownerName.trim();
    }

    // Accessorer
    public int getMunicipalityNumber() { return municipalityNumber; }
    public String getMunicipalityName() { return municipalityName; }
    public int getLotNumber() { return lotNumber; }
    public int getSectionNumber() { return sectionNumber; }
    public String getName() { return name; }
    public double getAreaSqm() { return areaSqm; }
    public String getOwnerName() { return ownerName; }

    // Mutatorer (kun på felter som kan endres naturlig)
    /**
     * Setter/oppdaterer bruksnavn. Null behandles som tom streng.
     */
    public void setName(String name) {
        this.name = name == null ? "" : name.trim();
    }

    /**
     * Oppdaterer arealet etter ny måling.
     * @param areaSqm nytt areal (>0)
     */
    public void setAreaSqm(double areaSqm) {
        if (areaSqm <= 0.0)
            throw new IllegalArgumentException("Areal må være > 0.");
        this.areaSqm = areaSqm;
    }

    /**
     * Endrer eier ved overdragelse.
     * @param ownerName nytt eiernavn (ikke tomt)
     */
    public void setOwnerName(String ownerName) {
        if (ownerName == null || ownerName.trim().isEmpty())
            throw new IllegalArgumentException("Eiers navn kan ikke være tomt.");
        this.ownerName = ownerName.trim();
    }
    /**
     * Returnerer kort-ID på formatet kommunenr-gnr/bnr, f.eks. 1504-54/73
     */
    public String idString() {
        return municipalityNumber + "-" + lotNumber + "/" + sectionNumber;
    }

    @Override
    public String toString() {
        String navnDel = name == null || name.isEmpty() ? "(uten bruksnavn)" : name;
        return String.format("%s | %s | gnr/bnr: %d/%d | Areal: %.1f m² | Eier: %s",
                idString(), municipalityName, lotNumber, sectionNumber, areaSqm, ownerName) +
                (name.isEmpty() ? "" : String.format(" | Bruksnavn: %s", navnDel));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Property)) return false;
        Property other = (Property) o;
        return municipalityNumber == other.municipalityNumber
                && lotNumber == other.lotNumber
                && sectionNumber == other.sectionNumber;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(municipalityNumber);
        result = 31 * result + Integer.hashCode(lotNumber);
        result = 31 * result + Integer.hashCode(sectionNumber);
        return result;
    }
}
