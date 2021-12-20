package roteirizador.ui;

public class MenuUI {
	
	public ControllerOptions show() {
		System.out.println("\n ---Bem vindo--- \n\nO que deseja fazer?\n");
		for (ControllerOptions option : ControllerOptions.values()) {
			System.out.println("[" + option.ordinal()+"] " + option.getLabel());
		}
		System.out.println("\n\n");

		int option = ScannerInput.getInstance().readInt();
		return ControllerOptions.values()[option];
	}

}
