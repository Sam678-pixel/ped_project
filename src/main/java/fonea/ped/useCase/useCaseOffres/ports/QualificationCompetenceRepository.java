package fonea.ped.useCase.useCaseOffres.ports;

import java.util.List;

import fonea.ped.domain.Offres.QualificationCompetence;

public interface QualificationCompetenceRepository {

    QualificationCompetence sauvegarder(QualificationCompetence liaison);

    List<QualificationCompetence> trouverParQualification(String idQualification);

    List<QualificationCompetence> trouverParCompetence(String idCompetence);

    boolean existe(String idQualification, String idCompetence);

    void supprimer(String idQualification, String idCompetence);
}
