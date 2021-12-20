package roteirizador.ui.controller;

import java.util.List;

import roteirizador.dao.DaoFactory;
import roteirizador.domain.Embarque;
import roteirizador.ui.ListEmbarquesUI;


public class ListEmbarqueController implements Controller{
	
	
	private ListEmbarquesUI ui;

	public ListEmbarqueController(ListEmbarquesUI ui) {
		this.ui = ui;
	}
	
	@Override
	public void launch() {
		List<Embarque> listaDeEmbarques = DaoFactory.getEmbarqueDAO().getAll();
		ui.showAll(listaDeEmbarques);
	}
	
	

}
