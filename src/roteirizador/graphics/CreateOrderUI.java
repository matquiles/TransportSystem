package roteirizador.graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.DecimalFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import roteirizador.bindings.BindingDoubleField;
import roteirizador.bindings.BindingIntegerField;
import roteirizador.bindings.BindingTextField;
import roteirizador.dao.database.DatabaseDaoFactory;
import roteirizador.domain.Client;
import roteirizador.domain.Funcionario;
import roteirizador.domain.ItemPedido;
import roteirizador.domain.Pedido;
import roteirizador.domain.SKU;
import roteirizador.graphics.controller.ModelTable;
import roteirizador.graphics.controller.itemPedidoTableModel;
import roteirizador.validations.FormatarValores;


@SuppressWarnings("serial")
public class CreateOrderUI extends JFrame {
	
	PresentationModel<Pedido> modelPedido;
	PresentationModel<Client> modelCliente;
	PresentationModel<ItemPedido> modelItemPedido;
	PresentationModel<Funcionario> modelFuncionario;
	
	private JFrame frame = new JFrame();
	private Pedido pedido = new Pedido();
	private FormatarValores format = new FormatarValores();
	
	private double paletiPedido = 0;
	private double valPedido = 0;

	private JMenuBar menuBar;
	private JTextField codCliente;
	private JTextField codPedido;
	private JTextField nomeCliente;
	private JTable tableItensPedido;
	private JTextField valorTotal;
	private JTextField paletizacaoTotal;
	private JButton btInserirItem;
	private itemPedidoTableModel modelo;
	private JButton btSalvar;
	private itemPedidoTableModel modeloTable;
	private static int ordemItem = 1;

	public CreateOrderUI() {
		initModels();
		initComponents();
		initLayout();
	}


	private void initModels() {
		modelPedido = new PresentationModel<Pedido>();
		modelCliente = new PresentationModel<Client>();
		modelFuncionario = new PresentationModel<Funcionario>();
		modelItemPedido = new PresentationModel<ItemPedido>();
		modelPedido.setBean(pedido);
		
	}


	private void initComponents() {
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu menuArquivo = new JMenu("Arquivo");
		menuBar.add(menuArquivo);
		
		JMenuItem menuSair = new JMenuItem("Sair");
		menuSair.addActionListener(evt -> frame.dispose());
		menuArquivo.add(menuSair);
		
		codPedido = new BindingIntegerField(modelPedido.getModel("codPedido"));
		codPedido.setEditable(false);
		
		codCliente = new BindingIntegerField(modelCliente.getModel("codCliente"));
		
		LookupInstaller.install(codCliente, e-> buscaNomeCliente());
		nomeCliente = new BindingTextField(modelCliente.getModel("nomeCliente"), true);
		nomeCliente.setEditable(false);
		btInserirItem = new JButton("Inserir Item");
		
		/*
		Envia o novo pedido para a tela de adicionar item
		cria novo item pedido com o ItemPedido gerado na outra tela
		seta bean do item pedido
		adiciona o ItemPedido ao pedido e altera valores de paletização e valor total
		atualiza a tabela
		
		ItemPedido inserido no banco de dados dentro do JDialog (por causa do try catch)
		*/
		
		btInserirItem.addActionListener(evt -> {
			try {
				InsertItemPedido insertItemPedido = new InsertItemPedido();
				ItemPedido item = new ItemPedido();
				item = insertItemPedido.retornaItem(pedido);
				modelItemPedido.setBean(item);
				addItemPedido();
				btSalvar.setEnabled(true);
				
			}
			catch(NullPointerException n) {
				n.printStackTrace();
			}
			
		});
	
		valorTotal = new BindingDoubleField(modelPedido.getModel("valorTotal"), new DecimalFormat("#,###.00"));
		valorTotal.setEditable(false);
		paletizacaoTotal = new BindingDoubleField(modelPedido.getModel("paletizacaoTotal"), new DecimalFormat("#,###.00"));
		paletizacaoTotal.setEditable(false);
		
		btSalvar = new JButton("Salvar");
		btSalvar.setEnabled(false);
		btSalvar.addActionListener(evt->{
			try {
				pedido.setCliente(modelCliente.getBean());
				
				DatabaseDaoFactory.getOrderDAO().save(pedido);
				JOptionPane.showMessageDialog(this, String.format("<html>Pedido salvo com sucesso! %s</html>", modelPedido.getBean().toString()));
				this.dispose();
				
			} 
			catch(NumberFormatException n) {
				JOptionPane.showMessageDialog(null, "Informe o código do cliente");
			}
			catch(ClassCastException n) {
				JOptionPane.showMessageDialog(null, "Apenas vendedores podem inserir pedidos");
			}
			
			
		});
		
		SelectionInList<ItemPedido> selectionItemPedido = new SelectionInList(modelPedido.getModel("itensPedidos"));
		JList<ItemPedido> jList = new JList<ItemPedido>();
		Bindings.bind(jList, selectionItemPedido);
		
		tableItensPedido = new JTable();
		
		modeloTable = new itemPedidoTableModel(selectionItemPedido);
		tableItensPedido = new JTable(modeloTable);
		tableItensPedido.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableItensPedido.getColumnModel().getColumn(0).setPreferredWidth(70);
		tableItensPedido.getColumnModel().getColumn(1).setPreferredWidth(70);
		tableItensPedido.getColumnModel().getColumn(2).setPreferredWidth(230);
		tableItensPedido.getColumnModel().getColumn(3).setPreferredWidth(100);
		tableItensPedido.getColumnModel().getColumn(4).setPreferredWidth(80);
	
	}


	private void buscaNomeCliente() {
		Client client = DatabaseDaoFactory.getClienteDAO().getClient(Integer.parseInt(codCliente.getText()));
		modelCliente.setBean(client);
		nomeCliente.setText(client == null ? "" : client.getNomecliente());
	}

	private void addItemPedido() {
		pedido.setCliente(modelCliente.getBean());
		pedido.addItem(modelItemPedido.getBean());
		pedido.setValorTotal(modelItemPedido.getBean().getQtItem(), modelItemPedido.getBean().getItem().getValor());
		pedido.setPaletizacaoTotal(modelItemPedido.getBean().getQtItem(), modelItemPedido.getBean().getItem().getPaletizacao());
		
		modeloTable.fireTableDataChanged();
		
	}


	private void initLayout() {
		
		FormLayout layout = new FormLayout("pref, 10px, 20dlu,10px, 50dlu, 50dlu, 117dlu");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		JScrollPane paneTable = new JScrollPane(tableItensPedido);
		paneTable.setPreferredSize(new Dimension(0,150));
		builder.border(new EmptyBorder(10, 10, 10, 10));
		
		builder.append("Codigo Pedido", codPedido);
		builder.nextLine();
		
		builder.append("Codigo Cliente", codCliente);
		builder.append(nomeCliente, builder.getColumnCount()-4);
		builder.nextLine();
		builder.nextLine();
		builder.appendRow("10dlu");
		builder.append(btInserirItem);
		builder.nextLine();
		builder.append(paneTable, builder.getColumnCount());
		builder.nextLine();
		
		builder.append("Peletização", paletizacaoTotal);
		builder.nextLine();
		builder.append("Valor Pedido", valorTotal, builder.getColumnCount()-4);
		builder.nextLine();
		builder.append(btSalvar);
		
		frame.setTitle("Inserir Pedido");
		frame.setSize(650,420);
		frame.setLayout(getLayout());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.add(builder.getPanel(), BorderLayout.CENTER);
		pack();
		frame.setVisible(true);
		
	}
	
}
