        package fonea.ped.Offres.model;

        import fonea.ped.Offres.model.StatutOffre;
        import java.time.LocalDate;
        import java.util.Objects;

        public class OffresStages {

            private String idOffre;
            private String intitule;
            private String description;
            private String villeStage;
            private Integer duree;
            private StatutOffre statut;
            private Integer nbrePlaces;

            private String idDomaine;
            private String idPoste;
            private String idEntreprise;

            public OffresStages(String idOffre,
                                String intitule,
                                String description,
                                String villeStage,
                                Integer duree,
                                StatutOffre statut,
                                Integer nbrePlaces,
                                String idDomaine,
                                String idPoste,
                                String idEntreprise) {
                this.idOffre = idOffre;
                this.intitule = intitule;
                this.description = description;
                this.villeStage = villeStage;
                this.duree = duree;
                this.statut = statut != null ? statut : StatutOffre.INACTIVE;
                this.nbrePlaces = nbrePlaces != null ? nbrePlaces : 0;
                this.idDomaine = idDomaine;
                this.idPoste = idPoste;
                this.idEntreprise = idEntreprise;
            }

            // ----- Méthodes métier -----

            /**
             * Publie l'offre : passe le statut à PUBLIEE et crée une Publication.
             * (La persistance de la Publication est du ressort de l'infrastructure.)
             */
            public Publication publier(String idPublication, String idAgent, LocalDate dateExpiration) {
                if (this.statut == StatutOffre.CLOTUREE) {
                    throw new IllegalStateException("Impossible de publier une offre clôturée.");
                }
                this.statut = StatutOffre.PUBLIEE;
                return new Publication(
                        idPublication,
                        LocalDate.now(),
                        dateExpiration,
                        idAgent,
                        this.idOffre
                );
            }

            /**
             * Clôture l'offre.
             */
            public void cloturer() {
                this.statut = StatutOffre.CLOTUREE;
            }

            /**
             * Change le statut de l'offre.
             */
            public void changerStatut(StatutOffre nouveauStatut) {
                if (nouveauStatut == null) {
                    throw new IllegalArgumentException("Le nouveau statut ne peut pas être null.");
                }
                this.statut = nouveauStatut;
            }

            /**
             * Indique s'il reste des places disponibles.
             */
            public boolean verifierDisponibilite() {
                return nbrePlaces != null && nbrePlaces > 0;
            }

            /**
             * Décrémente le nombre de places.
             */
            public void decrementerPlaces() {
                if (nbrePlaces == null || nbrePlaces <= 0) {
                    throw new IllegalStateException("Aucune place disponible à décrémenter.");
                }
                this.nbrePlaces--;
            }

            /**
             * Indique si l'offre est complète.
             */
            public boolean estComplete() {
                return nbrePlaces == null || nbrePlaces <= 0;
            }

            // ----- Getters -----

            public String getIdOffre() {
                return idOffre;
            }

            public String getIntitule() {
                return intitule;
            }

            public String getDescription() {
                return description;
            }

            public String getVilleStage() {
                return villeStage;
            }

            public Integer getDuree() {
                return duree;
            }

            public StatutOffre getStatut() {
                return statut;
            }

            public Integer getNbrePlaces() {
                return nbrePlaces;
            }

            public String getIdDomaine() {
                return idDomaine;
            }

            public String getIdPoste() {
                return idPoste;
            }

            public String getIdEntreprise() {
                return idEntreprise;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                OffresStages that = (OffresStages) o;
                return Objects.equals(idOffre, that.idOffre);
            }

            @Override
            public int hashCode() {
                return Objects.hash(idOffre);
            }

            @Override
            public String toString() {
                return "OffresStages{idOffre=" + idOffre +
                        ", intitule='" + intitule + '\'' +
                        ", villeStage='" + villeStage + '\'' +
                        ", statut=" + statut +
                        ", nbrePlaces=" + nbrePlaces +
                        ", idDomaine=" + idDomaine +
                        ", idPoste=" + idPoste +
                        ", idEntreprise=" + idEntreprise + "}";
            }
        }
