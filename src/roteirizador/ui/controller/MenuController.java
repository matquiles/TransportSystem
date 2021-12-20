package roteirizador.ui.controller;

import roteirizador.ui.ControllerOptions;
import roteirizador.ui.MenuUI;

public class MenuController implements Controller {
	
	private MenuUI ui;

	public MenuController(MenuUI ui) {
		this.ui = ui;
	}

	@Override
	public void launch() {
		ControllerOptions escolha = ui.show();
		ControllerFactory.create(escolha).launch();
		launch();
	}

}
