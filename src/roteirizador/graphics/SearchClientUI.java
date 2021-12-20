package roteirizador.graphics;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import roteirizador.bindings.BindingIntegerField;
import roteirizador.bindings.BindingTextField;
import roteirizador.dao.database.DatabaseDaoFactory;
import roteirizador.domain.Client;
import roteirizador.graphics.controller.ClientTableModel;
import roteirizador.ui.CreateClientUI;


@SuppressWarnings("serial")
public class SearchClientUI extends JFrame {
	
	PresentationModel<Client> model;
	private JFrame frame = new JFrame();
	private JFrame frameTable = new JFrame();
	
	private JMenuBar menuBar;
	private JTextField codCliente;
	private JTextField nomeCliente;
	private JTextField endereco;
	private JTextField cidade;
	private JTextField estado;
	private JButton btBuscar;
	private JTextField unidade;
	private JTextField codProcurado;
	private JTable tableCliente;
	
	
	public SearchClientUI() {
		initModels();
		initComponents();
		initLayout();
	}

	private void initModels() {
		model = new PresentationModel<Client>();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initComponents() {
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu menuArquivo = new JMenu("Arquivo");
		menuBar.add(menuArquivo);

		menuArquivo.add(new JMenuItem("Editar"));
		
		JMenuItem novo = new JMenuItem("Novo");
		novo.addActionListener(evt -> {
			frame.dispose();
			new RegisterClientUI();
		});
		menuArquivo.add(novo);
		
		

		JMenuItem menuMostrarTodos = new JMenuItem("Exibir todos");
		menuMostrarTodos.addActionListener(evt -> showTable());
		menuArquivo.add(menuMostrarTodos);

		JMenuItem menuSair = new JMenuItem("Sair");
		menuSair.addActionListener(evt -> frame.dispose());
		menuArquivo.add(menuSair);


		codProcurado = new JTextField();
		
		codCliente = new BindingIntegerField(model.getModel("codcliente"));
		codCliente.setEditable(false);
		
		nomeCliente = new BindingTextField(model.getModel("nomecliente"), true);
		nomeCliente.setEditable(false);
		
		endereco = new BindingTextField(model.getModel("endereco"), true);
		endereco.setEditable(false);
		
		cidade = new BindingTextField(model.getModel("cidade"), true);
		cidade.setEditable(false);
		
		estado = new BindingTextField(model.getModel("estado"), true);
		estado.setEditable(false);	
		
		unidade = new BindingIntegerField(model.getModel("unidade"));
		unidade.setEditable(false);

		btBuscar = new JButton("Buscar");
		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
		btBuscar.addActionListener(e -> {
			if(codProcurado.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Informe um código de cliente");
			} 
			else {
				search(Integer.parseInt(codProcurado.getText()));
			}
		});

	}



	private void initLayout() {

		FormLayout layout = new FormLayout("pref, 10px, 20dlu, 10dlu, pref, pref, pref,30dlu");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.border(new EmptyBorder(5, 5, 5, 5));

		builder.append("Codigo", codProcurado);

		builder.nextColumn();
		builder.append(btBuscar);
		builder.nextLine();

		builder.nextLine();
		builder.appendRow("10dlu");

		builder.appendSeparator("Cliente");
		builder.nextLine();

		builder.append("Codigo", codCliente);
		builder.nextLine();

		builder.append("Nome", nomeCliente, builder.getColumnCount()-2);
		builder.nextLine();

		builder.append("Endereco", endereco, builder.getColumnCount()-2);
		builder.nextLine();

		builder.append("Cidade", cidade, builder.getColumnCount()-2);
		builder.nextLine();

		builder.append("Estado", estado, builder.getColumnCount()-6);
		builder.nextLine();

		builder.append("Unidade", unidade, builder.getColumnCount()-6);
		builder.nextLine();

		builder.append("");

		frame.setTitle("Consulta de Clientes");
		frame.setSize(300,320);
		frame.setLayout(getLayout());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.add(builder.getPanel(), BorderLayout.CENTER);
		pack();
		frame.setVisible(true);


	}


	private void search(int cod) {

		try {
			Client cliente = DatabaseDaoFactory.getClienteDAO().getClient(cod);
			model.setBean(cliente);
		}catch(NumberFormatException n) {
			JOptionPane.showMessageDialog(null, "Codigo invalido");
		}catch(NullPointerException n) {
			JOptionPane.showMessageDialog(null, "Codigo invalido");

		}

	}


	private void showTable() {


		ClientTableModel model = new ClientTableModel(DatabaseDaoFactory.getClienteDAO().getAll()); 
		
		tableCliente = new JTable(model);
		tableCliente.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableCliente.getColumnModel().getColumn(0).setPreferredWidth(70);
		tableCliente.getColumnModel().getColumn(1).setPreferredWidth(350);
		tableCliente.getColumnModel().getColumn(2).setPreferredWidth(250);
		tableCliente.getColumnModel().getColumn(3).setPreferredWidth(150);
		tableCliente.getColumnModel().getColumn(4).setPreferredWidth(80);
		tableCliente.getColumnModel().getColumn(5).setPreferredWidth(60);

		
		JScrollPane pane = new JScrollPane(tableCliente);
		FormLayout layout = new FormLayout("700dlu");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.border(new EmptyBorder(5, 5, 5, 5));
		
		
		builder.append(pane);
		
		frameTable.setTitle("Lista de Clientes");
		frameTable.setSize(1000,600);
		frameTable.setLayout(getLayout());
		frameTable.setLocationRelativeTo(null);
		frameTable.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frameTable.add(builder.getPanel(), BorderLayout.CENTER);
		pack();
		frameTable.setVisible(true);
		
	}

	
	

}
