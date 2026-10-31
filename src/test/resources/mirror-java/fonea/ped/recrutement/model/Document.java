package fonea.ped.recrutement.model;

import java.util.Objects;

public class Document {

    private String idDocuments;
    private String cv;
    private String cni;
    private String diplome;
    private String recipisse;
    private String lettreMotivation;

    public Document(String idDocuments,
                    String cv,
                    String cni,
                    String diplome,
                    String recipisse,
                    String lettreMotivation) {
        this.idDocuments = idDocuments;
        this.cv = cv;
        this.cni = cni;
        this.diplome = diplome;
        this.recipisse = recipisse;
        this.lettreMotivation = lettreMotivation;
    }

    // ----- Méthodes métier -----

    public void ajouterCV(String cv) {
        if (cv == null || cv.isBlank()) {
            throw new IllegalArgumentException("Le CV ne peut pas être vide.");
        }
        this.cv = cv;
    }

    public void ajouterCNI(String cni) {
        if (cni == null || cni.isBlank()) {
            throw new IllegalArgumentException("La CNI ne peut pas être vide.");
        }
        this.cni = cni;
    }

    public void ajouterDiplome(String diplome) {
        if (diplome == null || diplome.isBlank()) {
            throw new IllegalArgumentException("Le diplôme ne peut pas être vide.");
        }
        this.diplome = diplome;
    }

    public void ajouterRecipisse(String recipisse) {
        this.recipisse = recipisse;
    }

    public void ajouterLettreMotivation(String lettre) {
        this.lettreMotivation = lettre;
    }

    /**
     * Vérifie si les documents obligatoires sont fournis.
     * CV, CNI et Diplôme sont considérés comme obligatoires.
     */
    public boolean estComplet() {
        return cv != null && !cv.isBlank()
                && cni != null && !cni.isBlank()
                && diplome != null && !diplome.isBlank();
    }

    // ----- Getters -----

    public String getIdDocuments() {
        return idDocuments;
    }

    public String getCv() {
        return cv;
    }

    public String getCni() {
        return cni;
    }

    public String getDiplome() {
        return diplome;
    }

    public String getRecipisse() {
        return recipisse;
    }

    public String getLettreMotivation() {
        return lettreMotivation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(idDocuments, document.idDocuments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDocuments);
    }

    @Override
    public String toString() {
        return "Document{idDocuments=" + idDocuments +
                ", cv='" + cv + '\'' +
                ", cni='" + cni + '\'' +
                ", diplome='" + diplome + '\'' +
                ", recipisse='" + recipisse + '\'' +
                ", lettreMotivation='" + lettreMotivation + '\'' + "}";
    }
}
