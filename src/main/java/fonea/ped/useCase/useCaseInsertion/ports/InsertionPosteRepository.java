package fonea.ped.useCase.useCaseInsertion.ports;

import java.util.List;

import fonea.ped.domain.insertion.Insertion;

public interface InsertionPosteRepository {
	
	public List<Insertion> trouverParPoste(String id);

}
