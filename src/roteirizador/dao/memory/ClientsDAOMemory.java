package roteirizador.dao.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import roteirizador.dao.ClientsDAO;
import roteirizador.domain.Client;
import roteirizador.domain.SKU;

public class ClientsDAOMemory implements ClientsDAO {
	
	private final List<Client> client;
	
	
	public ClientsDAOMemory() {
		this.client = new ArrayList <>();
			
	}
	
	@Override
	public void save(Client cliente) {
		client.add(cliente);
	}
	
	@Override
	public void delete(Client cliente) {
		client.remove(cliente);
	
	}
	
	@Override
	public List<Client> getAll(){
		return new ArrayList<Client>(client);
	}
	
	@Override
	public Client getClient(int id) {
		for(int i = 0; i < client.size(); i++) {
			if(client.get(i).getCodcliente()==id) {
				return client.get(i);
			}
		}
		return null;
	}

}
