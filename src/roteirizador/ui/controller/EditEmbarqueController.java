package roteirizador.ui.controller;

import roteirizador.ui.EditEmbarqueUI;


public class EditEmbarqueController implements Controller{

	private EditEmbarqueUI ui;
	
	public EditEmbarqueController(EditEmbarqueUI ui) {
		this.ui = ui;
	}
	
	@Override
	public void launch() {
		ui.show();

	}
	
	
}


