package roteirizador.ui.controller;

import roteirizador.dao.DaoFactory;
import roteirizador.domain.SKU;
import roteirizador.ui.CreateSkuUI;

public class CreateSkuController implements Controller {
	
	private CreateSkuUI ui;

	public CreateSkuController(CreateSkuUI ui) {
		this.ui = ui;
	}
	
	@Override
	public void launch() {
		SKU newSku = ui.show();

		adicionarSKU(newSku);
	}
	
	
	private void adicionarSKU(SKU sku) {
		DaoFactory.getSkuDAO().save(sku);
	}

}
