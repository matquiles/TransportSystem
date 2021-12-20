package roteirizador.dao;

import java.util.List;

import roteirizador.domain.Client;

public interface ClientsDAO {

	void save(Client cliente);

	void delete(Client cliente);

	List<Client> getAll();

	Client getClient(int id);

}