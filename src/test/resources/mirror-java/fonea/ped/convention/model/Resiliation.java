package fonea.ped.convention.model;

import java.time.LocalDate;
import java.util.Objects;

public class Resiliation {

    private String idResiliation;
    private LocalDate dateResiliation;
    private String initiateur;
    private String motif;
    private String idMotif;
    private String idConvention;

    public Resiliation(String idResiliation,
                       LocalDate dateResiliation,
                       String initiateur,
                       String motif,
                       String idMotif,
                       String idConvention) {
        this.idResiliation = idResiliation;
        this.dateResiliation = dateResiliation;
        this.initiateur = initiateur;
        this.motif = motif;
        this.idMotif = idMotif;
        this.idConvention = idConvention;
    }

    /**
     * Associe un motif de résiliation.
     */
    public void associerMotif(String idMotif) {
        if (idMotif == null) {
            throw new IllegalArgumentException("L'identifiant du motif ne peut pas être null.");
        }
        this.idMotif = idMotif;
    }

    public String getIdResiliation() {
        return idResiliation;
    }

    public LocalDate getDateResiliation() {
        return dateResiliation;
    }

    public String getInitiateur() {
        return initiateur;
    }

    public String getMotif() {
        return motif;
    }

    public String getIdMotif() {
        return idMotif;
    }

    public String getIdConvention() {
        return idConvention;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resiliation that = (Resiliation) o;
        return Objects.equals(idResiliation, that.idResiliation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idResiliation);
    }

    @Override
    public String toString() {
        return "Resiliation{idResiliation=" + idResiliation +
                ", dateResiliation=" + dateResiliation +
                ", initiateur='" + initiateur + '\'' +
                ", motif='" + motif + '\'' +
                ", idMotif=" + idMotif +
                ", idConvention=" + idConvention + "}";
    }
}
