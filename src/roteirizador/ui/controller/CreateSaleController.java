package roteirizador.ui.controller;
import java.util.List;

import roteirizador.dao.DaoFactory;
import roteirizador.domain.Pedido;
import roteirizador.ui.SalesOrderUI;


public class CreateSaleController implements Controller{

	private SalesOrderUI ui;
	
	public CreateSaleController(SalesOrderUI ui) {
		this.ui = ui;
	}
	
	@Override
	public void launch() {
		Pedido novoPedido = ui.show();
		adicionarPedido(novoPedido);
	}
	
	
	private void adicionarPedido(Pedido pedido) {
		DaoFactory.getOrderDAO().save(pedido);
	}
	
	
	public void ShowList(List<Pedido> list) {

	}
		
}
		



