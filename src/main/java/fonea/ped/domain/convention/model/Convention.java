package fonea.ped.domain.convention.model;

import java.time.LocalDate;
import java.util.Objects;

public class Convention {

    private String idConvention;
    private LocalDate dateEffet;
    private Double tauxFonea;
    private Double tauxEntreprise;
    private StatutConvention statutConvention;
    private TypeConvention typeConvention;
    private String idEntreprise;

    public Convention(String idConvention, LocalDate dateEffet, Double tauxFonea,Double tauxEntreprise,
                      StatutConvention statutConvention, TypeConvention typeConvention, String idEntreprise) {
        this.idConvention = idConvention;
        this.dateEffet = dateEffet;
        this.tauxFonea = tauxFonea;
        this.tauxEntreprise= tauxEntreprise;
        this.statutConvention = statutConvention != null ? statutConvention : StatutConvention.BROUILLON;
        this.typeConvention = typeConvention;
        this.idEntreprise = idEntreprise;
    }

    public void activer() {
        if (statutConvention==StatutConvention.RESILIEE) {
            throw new IllegalStateException("Impossible d'activer une convention résiliée.");
        }
        this.statutConvention = StatutConvention.ACTIVE;
    }

    public void suspendre() {
        if (statutConvention != StatutConvention.ACTIVE) {
            throw new IllegalStateException("Seule une convention active peut être suspendue.");
        }
        this.statutConvention = StatutConvention.SUSPENDUE;
    }

    public void changerStatut(StatutConvention nouveauStatut) {
        if (nouveauStatut == null ) {
            throw new IllegalArgumentException("Le nouveau statut ne peut pas être null.");
        }
        this.statutConvention = nouveauStatut;
    }

    public boolean estActive() {
        return statutConvention==StatutConvention.ACTIVE;
    }
    
    public Resiliation resilier(String idResiliation, String motif, String initiateur) {
    	// convention.resilier(idResiliation, request.getMotif(), request.getInitiateur());
        // 1. Vérifier que la convention est active
        if (statutConvention==StatutConvention.RESILIEE) {
            throw new IllegalStateException("La convention est déjà résiliée.");
        }

        // 2. Vérifier les paramètres
        if (motif == null || motif.isBlank()) {
            throw new IllegalArgumentException("Le motif de résiliation est obligatoire.");
        }
        if (initiateur == null || initiateur.isBlank()) {
            throw new IllegalArgumentException("L'initiateur est obligatoire.");
        }

        // 3. Passer la convention au statut RESILIEE
        this.statutConvention = StatutConvention.RESILIEE;

        // 4. Créer et retourner l'objet Resiliation
        return new Resiliation(
                idResiliation,
                LocalDate.now(),            // dateResiliation = aujourd'hui
                initiateur,
                this.idConvention
        );
    }

    public String getIdConvention() { return idConvention; }
    public LocalDate getDateEffet() { return dateEffet; }
    public Double getTauxFonea() { return tauxFonea; }
    public Double getTauxEntreprise() { return tauxEntreprise; }
    public StatutConvention getStatutConvention() { return statutConvention; }
    public TypeConvention getTypeConvention() { return typeConvention; }
    public String getIdEntreprise() { return idEntreprise; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Convention that = (Convention) o;
        return Objects.equals(idConvention, that.idConvention);
    }
    @Override public int hashCode() { return Objects.hash(idConvention); }
    @Override public String toString() {
        return "Convention{idConvention='" + idConvention +
                "', dateEffet=" + dateEffet + ", statutConvention='" + statutConvention + "'}";
    }
}