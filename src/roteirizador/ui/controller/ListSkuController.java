package roteirizador.ui.controller;

import java.util.List;

import roteirizador.dao.DaoFactory;
import roteirizador.domain.SKU;
import roteirizador.ui.ListSkuUI;

public class ListSkuController implements Controller {
	
	private ListSkuUI ui;

	public ListSkuController(ListSkuUI ui) {
		this.ui = ui;
	}
	
	@Override
	public void launch() {
		List<SKU> listaDeSkus = DaoFactory.getSkuDAO().getAll();
		ui.show(listaDeSkus);
	}

}
