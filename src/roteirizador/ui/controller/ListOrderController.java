package roteirizador.ui.controller;

import java.util.List;

import roteirizador.dao.DaoFactory;
import roteirizador.domain.Pedido;
import roteirizador.ui.ListOrderUI;


public class ListOrderController implements Controller{
	
	private ListOrderUI ui;

	public ListOrderController(ListOrderUI ui) {
		this.ui = ui;
	}
	
	@Override
	public void launch() {
		List<Pedido> listaDePedidos = DaoFactory.getOrderDAO().getAll();
		ui.showAll(listaDePedidos);
	}
	
	

}
