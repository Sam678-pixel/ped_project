package fonea.ped.domain.exceptions;

public class AucunDocumentFourniException extends DomaineException{
	public AucunDocumentFourniException(){
		super("La candidature doit contenir au moins un document.");
	}

}
