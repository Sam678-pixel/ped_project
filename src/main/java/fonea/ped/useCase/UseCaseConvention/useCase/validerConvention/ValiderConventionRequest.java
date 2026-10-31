package fonea.ped.useCase.UseCaseConvention.useCase.validerConvention;

/**
 * Entrée du cas d'usage « valider (signer) une convention ».
 */
public class ValiderConventionRequest {

    private final String idConvention;

    public ValiderConventionRequest(String idConvention) {
        this.idConvention = idConvention;
    }

    public String getIdConvention() {
        return idConvention;
    }
}
