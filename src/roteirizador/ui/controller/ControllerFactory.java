package roteirizador.ui.controller;

import roteirizador.ui.ControllerOptions;
import roteirizador.ui.CreateClientUI;
import roteirizador.ui.CreateEmbarqueUI;
import roteirizador.ui.CreateSkuUI;
import roteirizador.ui.EditEmbarqueUI;
import roteirizador.ui.EditOrderUI;
import roteirizador.ui.ExportOrderUI;
import roteirizador.ui.ListClientsUI;
import roteirizador.ui.ListEmbarquesUI;
import roteirizador.ui.ListOrderUI;
import roteirizador.ui.ListSkuUI;
import roteirizador.ui.SalesOrderUI;

public class ControllerFactory {
	
	public static Controller create(ControllerOptions controllerOption) {
		switch (controllerOption) {
		case CREATE_SKU: return new CreateSkuController(new CreateSkuUI());
		case LIST_SKU: return new ListSkuController(new ListSkuUI());
		case LIST_CLIENTS: return new ListClientController(new ListClientsUI());
		case CREATE_CLIENT: return new CreateClientController(new CreateClientUI());
		case CREATE_SALES_ORDER: return new CreateSaleController(new SalesOrderUI());
		case LIST_ORDER: return new ListOrderController(new ListOrderUI());
		case EDIT_ORDER: return new EditOrderController(new EditOrderUI());
		case EXPORT_ORDER: return new ExportOrderController(new ExportOrderUI());
		case CREATE_EMBARQUE: return new CreateEmbarqueController(new CreateEmbarqueUI());
		case LIST_EMBARQUE:return new ListEmbarqueController(new ListEmbarquesUI());
		case EDIT_EMBARQUE: return new EditEmbarqueController(new EditEmbarqueUI());
		
		
		}
		return null;
	}

}
