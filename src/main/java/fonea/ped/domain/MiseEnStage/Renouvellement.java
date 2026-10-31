package fonea.ped.domain.MiseEnStage;

import java.time.LocalDate;
import java.util.Objects;



    public class Renouvellement {

        private String idRenouvellement;
        private LocalDate dateDebut;
        private LocalDate dateFin;
        private String idMiseStage;

        public Renouvellement(String idRenouvellement, LocalDate dateDebut,
                              LocalDate dateFin, String idMiseStage) {
            this.idRenouvellement = idRenouvellement;
            this.dateDebut = dateDebut;
            this.dateFin = dateFin;
            this.idMiseStage = idMiseStage;
        }

        public void prolonger(LocalDate nouvelleDateFin) {
            if (nouvelleDateFin == null) {
                throw new IllegalArgumentException("La nouvelle date de fin ne peut pas être null.");
            }
            if (this.dateFin != null && nouvelleDateFin.isBefore(this.dateFin)) {
                throw new IllegalArgumentException(
                        "La nouvelle date doit être postérieure à la date de fin actuelle.");
            }
            this.dateFin = nouvelleDateFin;
        }

        public String getIdRenouvellement() { return idRenouvellement; }
        public LocalDate getDateDebut() { return dateDebut; }
        public LocalDate getDateFin() { return dateFin; }
        public String getIdMiseStage() { return idMiseStage; }

        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Renouvellement that = (Renouvellement) o;
            return Objects.equals(idRenouvellement, that.idRenouvellement);
        }
        @Override public int hashCode() { return Objects.hash(idRenouvellement); }
        @Override public String toString() {
            return "Renouvellement{idRenouvellement='" + idRenouvellement + "'}";
        }
    }

