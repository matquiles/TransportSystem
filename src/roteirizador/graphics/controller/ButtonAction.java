package roteirizador.graphics.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import roteirizador.domain.Autenticador;
import roteirizador.graphics.MainMenu;
import roteirizador.graphics.LoginUI;


public class ButtonAction implements ActionListener {
	
	private JTextField login;
	private JTextField senha;
	
	public ButtonAction(JTextField login, JTextField senha) {
		this.login = login;
		this.senha = senha;
	}
	
	public ButtonAction() {
		
	}
	

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		
		try {
			int loginValidacao = Integer.parseInt(login.getText());
			String senhaValidacao = senha.getText();
			
			Autenticador autenticarFuncionario = new Autenticador();
			
			if (autenticarFuncionario.autenticacao(loginValidacao, senhaValidacao)) {
				LoginUI.getInstance().dispose();
				MainMenu.getInstance();
				//new MenuController(new MenuUI()).launch();
				
			} else {
				JOptionPane.showMessageDialog(null, "Acesso Negado");
			}
		} catch (java.lang.NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Somente são permitidos números");
		}
		
	}
	
	
	

}
