package roteirizador.ui.controller;


import roteirizador.ui.EditOrderUI;

public class EditOrderController implements Controller{
	
	
	private EditOrderUI ui;

	public EditOrderController(EditOrderUI ui) {
		this.ui = ui;
	}
	
	@Override
	public void launch() {
		ui.show();

	}
	
}
