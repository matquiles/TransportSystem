package roteirizador.graphics;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import roteirizador.bindings.BindingIntegerField;
import roteirizador.bindings.BindingTextField;
import roteirizador.dao.database.DatabaseDaoFactory;
import roteirizador.domain.Client;


@SuppressWarnings("serial")
public class RegisterClientUI extends JFrame {
	
	PresentationModel<Client> model;
	Client cliente = new Client();
	
	private JFrame frame = new JFrame();
	private JMenuBar menuBar;
	private BindingIntegerField codCliente;
	private BindingTextField nomeCliente;
	private BindingTextField endereco;
	private BindingTextField cidade;
	private JComboBox<String> cbEstado;
	private JButton btCancelar;
	private JButton btSalvar;
	private JComboBox<Integer> cbUnidade;
	
	
	public RegisterClientUI() {
		initModels();
		initComponents();
		initLayout();
	}
	
	private void initModels() {
		model = new PresentationModel<Client>();
		cliente.setCodCliente(DatabaseDaoFactory.getClienteDAO().getAll().size()+1001);
		model.setBean(cliente);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initComponents() {
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu menuArquivo = new JMenu("Arquivo");
		menuBar.add(menuArquivo);

		menuArquivo.add(new JMenuItem("Editar"));

		JMenuItem menuSair = new JMenuItem("Sair");
		menuSair.addActionListener(evt -> frame.dispose());
		menuArquivo.add(menuSair);


		codCliente = new BindingIntegerField(model.getModel("codcliente"));
		codCliente.setEditable(false);

		nomeCliente = new BindingTextField(model.getModel("nomecliente"), true);
		
		endereco = new BindingTextField(model.getModel("endereco"), true);
	
		cidade = new BindingTextField(model.getModel("cidade"), true);
	
		List<String> listEstados = new ArrayList();
		listEstados.add("RS");
		listEstados.add("SC");

		new ValueHolder(listEstados.stream().findFirst().orElse(null));
		ComboBoxAdapter comboBoxAdapter = new ComboBoxAdapter(listEstados, model.getModel("estado"));
		
		cbEstado = new JComboBox();
		Bindings.bind(cbEstado, comboBoxAdapter);
		
		
		List<Integer> listUnidades = new ArrayList();
		listUnidades.add(108);
		listUnidades.add(107);
		listUnidades.add(110);

		new ValueHolder(listUnidades.stream().findFirst().orElse(null));
		ComboBoxAdapter comboBoxAdapterUnidade = new ComboBoxAdapter(listUnidades, model.getModel("unidade"));
		
		cbUnidade = new JComboBox();
		Bindings.bind(cbUnidade, comboBoxAdapterUnidade);
		

		btCancelar = new JButton("Cancelar");
		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
		btCancelar.addActionListener(e -> frame.dispose());

		btSalvar = new JButton("Salvar");
		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
		btSalvar.addActionListener(e -> salvar());
		
	}

	
	private void initLayout() {
		
		FormLayout layout = new FormLayout("pref, 5px, 20dlu, 10dlu, pref, pref, pref");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.border(new EmptyBorder(5, 5, 5, 5));
		
		builder.append("Codigo", codCliente);
		builder.nextLine();
		
		builder.append("Nome", nomeCliente, builder.getColumnCount()-2);
		builder.nextLine();
		
		builder.append("Endereco", endereco, builder.getColumnCount()-2);
		builder.nextLine();
		
		builder.append("Cidade", cidade, builder.getColumnCount()-2);
		builder.nextLine();
		
		builder.append("Estado", cbEstado, builder.getColumnCount()-5);
		builder.nextLine();
		
		builder.append("Unidade", cbUnidade, builder.getColumnCount()-5);
		builder.nextLine();
		
		builder.append("");
		
		builder.nextColumn();
		
		builder.nextColumn();
		builder.append(btCancelar);
		builder.append(btSalvar);
		
		frame.setTitle("Cadastro de Clientes");
		frame.setSize(320,250);
		frame.setLayout(getLayout());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.add(builder.getPanel(), BorderLayout.CENTER);
		pack();
		frame.setVisible(true);
		
		
	}
	
	
	private void salvar() {
		
		try {
	
			cliente = model.getBean();
			DatabaseDaoFactory.getClienteDAO().save(cliente);
			JOptionPane.showMessageDialog(this, String.format("<html>Cliente registrado com Sucesso! %s</html>", model.getBean().toString()));
			frame.dispose();
			
		}
		catch(NullPointerException n) {
			JOptionPane.showMessageDialog(null, "Não aceito valores nulos");
		}
	
	}
	

}
