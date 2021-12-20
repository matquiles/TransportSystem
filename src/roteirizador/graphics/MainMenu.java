package roteirizador.graphics;


import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import roteirizador.graphics.controller.MenuBarController;
import roteirizador.CargaInicial;
import roteirizador.graphics.controller.MainMenuController;
import roteirizador.validations.Sessao;

@SuppressWarnings("serial")
public class MainMenu extends JFrame{
	
	private static JFrame instance;
	private final MainMenuController controller;
	private final MenuBarController controllerBarraMenu;
	private JButton bCadastroSKU;
	private JButton bConsultaSKU;
	private JButton bCadastroCliente;
	private JButton bConsultaCliente;
	private JButton bDigitarPedido;
	private JButton bConsultarPedido;
	private JButton bEditarPedido;
	private JButton bExportarPedido;
	private JButton bCriarEmbarque;
	private JButton bExibirEmbarque;
	private JMenuBar menuBar;
	private JTextField txUsuarioLogado;
	
	
	
	static JFrame frame = new JFrame();
	
	
	public MainMenu() {
		controller = new MainMenuController();
		controllerBarraMenu = new MenuBarController();
		
		initComponet();
		initLayout();
	}
	
	
	
	private void initComponet() {
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu menuArquivo = new JMenu("Arquivo");
		menuBar.add(menuArquivo);
		
		menuArquivo.add(new JMenuItem("Editar"));
		
		JMenuItem menuSobre = new JMenuItem("Sobre");
		menuSobre.addActionListener(evt -> MenuBarController.exibirSobre());
		menuArquivo.add(menuSobre);
		
		JMenuItem menuSair = new JMenuItem("Sair");
		menuSair.addActionListener(evt -> System.out.println("ok"));
		menuArquivo.add(menuSair);
		

		bCadastroSKU = new JButton("Cadastrar");
		bCadastroSKU.addActionListener(e -> controller.exibirCadastroSKU());
		
		bConsultaSKU = new JButton("Consultar");
		bConsultaSKU.addActionListener(e -> controller.exibirConsultaSKU());
		
		bCadastroCliente = new JButton("Cadastrar");
		bCadastroCliente.addActionListener(e -> controller.exibirCadastroCliente());
		
		
		bConsultaCliente = new JButton("Consultar");
		bConsultaCliente.addActionListener(e -> controller.exibirConsultaCliente());
	
		
		bDigitarPedido = new JButton("Digitar novo");
		bDigitarPedido.addActionListener(e -> controller.exibirDigitarPedido());	
		
		bEditarPedido = new JButton("Consultar");
		bEditarPedido.addActionListener(e -> controller.exibirConsultarPedido());
		
		bExportarPedido = new JButton("Exportar");
		bExportarPedido.addActionListener(e -> controller.exibirExportarPedido());
		
		bCriarEmbarque = new JButton("Criar Embarque");
		bCriarEmbarque.addActionListener(e -> controller.exibirCriarEmbarque());
		
		
		bExibirEmbarque = new JButton("Consultar");
		bExibirEmbarque.addActionListener(e -> controller.exibirExibirEmbarque());
	
		
		txUsuarioLogado = new JTextField("Usuário - " + Sessao.getInstance().getUsuario().getNome());
		txUsuarioLogado.setEditable(false);
		
		
	}
	public void initLayout() {
		
		
		FormLayout layout = new FormLayout("20dlu, pref, pref, 50dlu, 5px, 40dlu");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.border(new EmptyBorder(10, 10, 10, 10));
		
	
		builder.appendSeparator("SKUs");
		builder.append("");
		builder.append(bCadastroSKU);
		
		builder.nextLine();
		builder.append("");
		builder.append(bConsultaSKU);
		builder.nextLine();
		builder.appendRow("20dlu");
		builder.nextLine();
		
		builder.appendSeparator("Clientes");
		builder.append("");
		builder.append(bCadastroCliente);
		builder.nextLine();
		builder.append("");
		builder.append(bConsultaCliente);
		builder.nextLine();
		builder.appendRow("20dlu");
		builder.nextLine();
		
		builder.appendSeparator("Pedidos");
		builder.append("");
		builder.append(bDigitarPedido);
		builder.nextLine();
		builder.append("");
		builder.append(bEditarPedido);
		builder.nextLine();
		builder.append("");
		builder.append(bExportarPedido);
		builder.nextLine();
		builder.appendRow("20dlu");
		builder.nextLine();
		
		builder.appendSeparator("Embarques");
		builder.append("");
		builder.append(bCriarEmbarque);
		builder.nextLine();
		builder.append("");
		builder.append(bExibirEmbarque);
		builder.nextLine();

	

		frame.setTitle("Menu Principal");
		frame.setSize(400,720);
		frame.setLayout(getLayout());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.add(builder.getPanel(), BorderLayout.CENTER);
		frame.add(txUsuarioLogado, BorderLayout.SOUTH);
		frame.setVisible(true);

	}
	
	
	public synchronized static JFrame getInstance() {
		if (instance == null) {
			instance = new MainMenu();
		}
		return instance;
	}
	

	

}


	