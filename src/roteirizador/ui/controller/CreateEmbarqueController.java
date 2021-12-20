package roteirizador.ui.controller;

import java.util.List;

import roteirizador.dao.DaoFactory;
import roteirizador.domain.Embarque;
import roteirizador.ui.CreateEmbarqueUI;


public class CreateEmbarqueController implements Controller{

private CreateEmbarqueUI ui;
	
	public CreateEmbarqueController(CreateEmbarqueUI ui) {
		this.ui = ui;
	}
	
	@Override
	public void launch() {
		Embarque novoEmbarque = ui.show();
		adicionarEmbarque(novoEmbarque);
	}
	
	
	private void adicionarEmbarque(Embarque novoEmbarque) {
		if(novoEmbarque!= null) {
			DaoFactory.getEmbarqueDAO().save(novoEmbarque);
		}
		
	}
	
	
	public void ShowList(List<Embarque> list) {
		
		
	}
	

}
