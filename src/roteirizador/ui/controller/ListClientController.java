package roteirizador.ui.controller;

import java.util.List;

import roteirizador.dao.DaoFactory;
import roteirizador.domain.Client;
import roteirizador.ui.ListClientsUI;


public class ListClientController implements Controller {
	
	private ListClientsUI ui;

	public ListClientController(ListClientsUI ui) {
		this.ui = ui;
	}
	
	@Override
	public void launch() {
		List<Client> listaDeClientes = DaoFactory.getClienteDAO().getAll();
		ui.show(listaDeClientes);
	}

}
