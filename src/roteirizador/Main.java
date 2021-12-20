package roteirizador;


import roteirizador.dao.database.ConexaoJDBC;
import roteirizador.graphics.LoginUI;
import roteirizador.ui.MenuUI;
import roteirizador.ui.controller.MenuController;


public class Main {
	


	public static void main(String[] args) {
		
		
		LoginUI inicio = new LoginUI();
		inicio.telaLogin();
		
	
		
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                ConexaoJDBC.doCloseConnection();
            }
        });
		
	}

	
	public static void mostrarMenu() {
	
		new MenuController(new MenuUI()).launch();
	}
	

	

	

}
