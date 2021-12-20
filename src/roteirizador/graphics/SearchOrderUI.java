package roteirizador.graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.ListModel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import roteirizador.bindings.BindingCheckBox;
import roteirizador.bindings.BindingDateField;
import roteirizador.bindings.BindingDoubleField;
import roteirizador.bindings.BindingIntegerField;
import roteirizador.bindings.BindingTextField;
import roteirizador.bindings.BindingTxDateField;
import roteirizador.dao.database.DatabaseDaoFactory;
import roteirizador.domain.Client;
import roteirizador.domain.Funcionario;
import roteirizador.domain.ItemPedido;
import roteirizador.domain.Pedido;
import roteirizador.domain.SKU;
import roteirizador.domain.Vendedor;
import roteirizador.graphics.controller.itemPedidoTableModel;
import roteirizador.graphics.controller.showAllOrderTableModel;
import roteirizador.validations.FormatarValores;

public class SearchOrderUI extends JFrame {
	
	PresentationModel<Pedido> modelPedido;
	PresentationModel<Client> modelCliente;
	PresentationModel<ItemPedido> modelItemPedido;
	PresentationModel<Funcionario> modelFuncionario;
	

	private JFrame frame = new JFrame();
	private JFrame frameTable = new JFrame();
	private FormatarValores format = new FormatarValores();
	private static JFrame instance;
	
	private JMenuBar menuBar;
	private JTextField buscarCodPedido;
	private BindingTextField nomeCliente;
	private JTable tableItensPedido;
	private BindingDoubleField valorTotal;
	private BindingDoubleField paletizacaoTotal;
	private itemPedidoTableModel modeloTable;
	private JButton btBuscar;
	private BindingIntegerField codCliente;
	private BindingTextField enderecoCliente;
	private BindingTextField cidadeCliente;
	private BindingTextField estadoCliente;
	private JTextField codPedido;
	private JCheckBox statusAlocado;
	private BindingTextField vendedor;
	private JTextField data;

	@SuppressWarnings("unused")
	private JTable tableOrder;
	
	
	public SearchOrderUI() {
		initComponents();
		initLayout();
	}

	private void initComponents() {
		
		
		modelPedido = new PresentationModel<Pedido>();
		modelCliente = new PresentationModel<Client>();
		modelFuncionario = new PresentationModel<Funcionario>();
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu menuArquivo = new JMenu("Arquivo");
		menuBar.add(menuArquivo);
		
		JMenuItem novo = new JMenuItem("Novo Pedido");
		novo.addActionListener(evt -> {
			new CreateOrderUI();
			frame.dispose();
		});
		menuArquivo.add(novo);
		
		JMenuItem editar = new JMenuItem("Editar");
		editar.addActionListener(evt -> {
			new EditOrderUI();
			frame.dispose();
		});
		menuArquivo.add(editar);
			
	
		JMenuItem menuSair = new JMenuItem("Sair");
		menuSair.addActionListener(evt -> frame.dispose());
		menuArquivo.add(menuSair);
		
		
		buscarCodPedido = new JTextField();
		btBuscar = new JButton("Buscar");
		btBuscar.addActionListener(act -> buscar());
		
		codCliente = new BindingIntegerField(modelCliente.getModel("codcliente"));
		codCliente.setEditable(false);
		
		nomeCliente = new BindingTextField(modelCliente.getModel("nomecliente"), true);
		nomeCliente.setEditable(false);
		
		enderecoCliente = new BindingTextField(modelCliente.getModel("endereco"), true);
		enderecoCliente.setEditable(false);
		
		cidadeCliente = new BindingTextField(modelCliente.getModel("cidade"), true);
		cidadeCliente.setEditable(false);
		
		estadoCliente = new BindingTextField(modelCliente.getModel("estado"), true);
		estadoCliente.setEditable(false);
		
		codPedido = new BindingIntegerField(modelPedido.getModel("codPedido"));
		codPedido.setEditable(false);
		
		statusAlocado = new BindingCheckBox(modelPedido.getModel("alocado"));
		statusAlocado.setEnabled(false);
		
		valorTotal = new BindingDoubleField(modelPedido.getModel("valorTotal"), new DecimalFormat("#,###.00"));
		valorTotal.setEditable(false);
		
		paletizacaoTotal = new BindingDoubleField(modelPedido.getModel("paletizacaoTotal"), new DecimalFormat("#,###.00"));
		paletizacaoTotal.setEditable(false);
		
		vendedor = new BindingTextField(modelFuncionario.getModel("nome"), true);
		vendedor.setEditable(false);
		
		data = new BindingTxDateField(modelPedido.getModel("data"));
		data.setEditable(false);
		
		SelectionInList<ItemPedido> selectionItemPedido = new SelectionInList(modelPedido.getModel("itensPedidos"));
		JList<ItemPedido> jList = new JList<ItemPedido>();
		Bindings.bind(jList, selectionItemPedido);
		
		tableItensPedido = new JTable();
		modeloTable = new itemPedidoTableModel(selectionItemPedido);
		tableItensPedido = new JTable(modeloTable);
		tableItensPedido.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableItensPedido.getColumnModel().getColumn(0).setPreferredWidth(55);
		tableItensPedido.getColumnModel().getColumn(1).setPreferredWidth(70);
		tableItensPedido.getColumnModel().getColumn(2).setPreferredWidth(200);
		tableItensPedido.getColumnModel().getColumn(3).setPreferredWidth(100);
		tableItensPedido.getColumnModel().getColumn(4).setPreferredWidth(100);
		tableItensPedido.getColumnModel().getColumn(5).setPreferredWidth(80);
		
	}
	

	private void initLayout() {
		
		FormLayout layout = new FormLayout("pref, 5px, 50dlu, 5px, 50dlu, 5px, 	50dlu, 97dlu");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		 //new FormDebugPanel()
		JScrollPane paneTable = new JScrollPane(tableItensPedido);
		paneTable.setPreferredSize(new Dimension(0,200));
		builder.border(new EmptyBorder(10, 10, 10, 10));
		
		builder.append("Cod Pedido", buscarCodPedido);
		builder.nextColumn();
		builder.nextColumn();
		builder.append(btBuscar);
		builder.nextLine();
		builder.appendSeparator("Informações Cliente");
		builder.nextLine();
		builder.appendRow("10px");
		builder.append("");
		builder.nextLine();
		
		builder.append("Cliente", codCliente);
		builder.append(nomeCliente, builder.getColumnCount()-4);
		builder.nextLine();
		builder.append("Endereço", enderecoCliente, builder.getColumnCount()-2);
		builder.nextLine();
		builder.append("");
		builder.append(cidadeCliente, builder.getColumnCount()-5);
		builder.append(estadoCliente);
		builder.nextLine();
		
		builder.appendSeparator("Informações Pedido");
		builder.nextLine();
		builder.appendRow("10px");
		builder.append("");
		builder.nextLine();
		builder.append("Cod Pedido", codPedido);
		builder.append("Alocado", statusAlocado);
		builder.nextLine();
		builder.append("Valor Total", valorTotal);
		builder.append("Paletização", paletizacaoTotal);
		builder.nextLine();
		builder.append("Vendedor", vendedor);
		builder.append("Data", data);
		builder.nextLine();
		builder.appendRow("20px");
		builder.append("");
		builder.nextLine();
		builder.append(paneTable,builder.getColumnCount());
		
		frame.setTitle("Consultar Pedido");
		frame.setSize(630,620);
		frame.setLayout(getLayout());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.add(builder.getPanel(), BorderLayout.CENTER);
		frame.setVisible(true);

		
	}
	
	public void setPedido(Pedido pd) {
		buscarCodPedido.setText(Integer.toString(pd.getCodPedido()));
	}
	
	public void buscar() {
		
		try {
			
			if (buscarCodPedido.getText().equals("")) {
				Pedido searchOrder = new ShowAllOrders().searchOrder();
				buscarCodPedido.setText(Integer.toString(searchOrder.getCodPedido()));
				
				

			} else {
				Pedido pd = DatabaseDaoFactory.getOrderDAO().getPedido(Integer.parseInt(buscarCodPedido.getText()));
				modelCliente.setBean(pd.getCliente());
				modelPedido.setBean(pd);
				modelFuncionario.setBean(pd.getVendedor());
			
				modeloTable.fireTableDataChanged();
			}
		}
		catch (NumberFormatException | NullPointerException n) {

			
		}
	
	
	
	
	}
	
	


}
