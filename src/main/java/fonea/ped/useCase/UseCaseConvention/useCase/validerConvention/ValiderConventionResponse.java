package fonea.ped.useCase.UseCaseConvention.useCase.validerConvention;

import java.time.LocalDate;

import fonea.ped.domain.convention.model.StatutConvention;

public class ValiderConventionResponse {

    private final String idConvention;
    private final StatutConvention statut;
    private final LocalDate dateEffet;

    public ValiderConventionResponse(String idConvention, StatutConvention statut, LocalDate dateEffet) {
        this.idConvention = idConvention;
        this.statut = statut;
        this.dateEffet = dateEffet;
    }

    public String getIdConvention() { return idConvention; }
    public StatutConvention getStatut() { return statut; }
    public LocalDate getDateEffet() { return dateEffet; }
}
