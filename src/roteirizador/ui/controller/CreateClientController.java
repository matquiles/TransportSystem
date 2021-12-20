package roteirizador.ui.controller;

import roteirizador.dao.DaoFactory;
import roteirizador.domain.Client;
import roteirizador.ui.CreateClientUI;

public class CreateClientController implements Controller {
	
	private CreateClientUI ui;

	public CreateClientController(CreateClientUI ui) {
		this.ui = ui;
	}
	
	@Override
	public void launch() {
		Client newClient = ui.show();

		adicionarCliente(newClient);
	}
	
	
	private void adicionarCliente(Client cliente) {
		DaoFactory.getClienteDAO().save(cliente);
	}

}
