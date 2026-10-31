             package fonea.ped.Offres.model;

             import java.util.Objects;

             public class Metier {

                 private String idMetier;
                 private String idSecteur;

                 public Metier(String idMetier, String idSecteur) {
                     this.idMetier = idMetier;
                     this.idSecteur = idSecteur;
                 }

                 /**
                  * Rattache le métier à un secteur.
                  */
                 public void rattacherSecteur(String idSecteur) {
                     if (idSecteur == null) {
                         throw new IllegalArgumentException("L'identifiant du secteur ne peut pas être null.");
                     }
                     this.idSecteur = idSecteur;
                 }

                 public String getIdMetier() {
                     return idMetier;
                 }

                 public String getIdSecteur() {
                     return idSecteur;
                 }

                 @Override
                 public boolean equals(Object o) {
                     if (this == o) return true;
                     if (o == null || getClass() != o.getClass()) return false;
                     Metier metier = (Metier) o;
                     return Objects.equals(idMetier, metier.idMetier);
                 }

                 @Override
                 public int hashCode() {
                     return Objects.hash(idMetier);
                 }

                 @Override
                 public String toString() {
                     return "Metier{idMetier=" + idMetier + ", idSecteur=" + idSecteur + "}";
                 }
             }