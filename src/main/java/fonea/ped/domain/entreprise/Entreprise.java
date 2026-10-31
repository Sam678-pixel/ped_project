package fonea.ped.domain.entreprise;

import java.util.Objects;

public class Entreprise {

    private String idEntreprise;
    private String raisonSociale;
    private String adresse;
    private String niu;
    private String idUtilisateur;

    public Entreprise(String idEntreprise, String raisonSociale,
                      String adresse, String niu, String idUtilisateur) {
        if (raisonSociale == null || raisonSociale.isBlank()) {
            throw new IllegalArgumentException("La raison sociale est obligatoire.");
        }
        this.idEntreprise = idEntreprise;
        this.raisonSociale = raisonSociale;
        this.adresse = adresse;
        this.niu = niu;
        this.idUtilisateur = idUtilisateur;
    }

    public void mettreAJourCoordonnees(String adresse, String niu) {
        this.adresse = adresse;
        this.niu = niu;
    }

    public String getIdEntreprise() { return idEntreprise; }
    public String getRaisonSociale() { return raisonSociale; }
    public String getAdresse() { return adresse; }
    public String getNiu() { return niu; }
    public String getIdUtilisateur() { return idUtilisateur; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entreprise that = (Entreprise) o;
        return Objects.equals(idEntreprise, that.idEntreprise);
    }
    @Override public int hashCode() { return Objects.hash(idEntreprise); }
    @Override public String toString() {
        return "Entreprise{idEntreprise='" + idEntreprise +
                "', raisonSociale='" + raisonSociale +
                "', niu='" + niu + "'}";
    }
}
