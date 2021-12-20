package roteirizador.ui.controller;


import java.io.IOException;

import roteirizador.ui.ExportOrderUI;

public class ExportOrderController implements Controller{
	
	
	private ExportOrderUI ui;

	public ExportOrderController(ExportOrderUI ui) {
		this.ui = ui;
	}
	
	@Override
	public void launch() {
		try {
			ui.export();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
