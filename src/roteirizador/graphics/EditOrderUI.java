package roteirizador.graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import roteirizador.bindings.BindingCheckBox;
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
import roteirizador.graphics.controller.itemPedidoTableModel;
import roteirizador.validations.FormatarValores;

@SuppressWarnings("serial")
public class EditOrderUI extends JFrame {
	
	PresentationModel<Pedido> modelPedido;
	PresentationModel<Client> modelCliente;
	PresentationModel<ItemPedido> modelItemPedido;
	PresentationModel<Funcionario> modelFuncionario;
	
	
	private JFrame frame = new JFrame();
	private Pedido pedido;
	private FormatarValores format = new FormatarValores();
	private Boolean selecao = false;
	private ItemPedido itemPedido;
	
	private JMenuBar menuBar;
	private JTextField buscarCodPedido;
	private JTextField nomeCliente;
	private JTable tableItensPedido;
	private JTextField valorTotal;
	private JTextField paletizacaoTotal;
	private itemPedidoTableModel modeloTable;
	private JButton btBuscar;
	private JTextField codCliente;
	private JTextField enderecoCliente;
	private JTextField cidadeCliente;
	private JTextField estadoCliente;
	private JTextField codPedido;
	private JCheckBox statusAlocado;
	private JTextField vendedor;
	private JTextField data;

	@SuppressWarnings("unused")
	private JTable tableOrder;
	private JButton btExcluir;
	private JButton btAdicionar;
	
	
	public EditOrderUI() {
		initModels();
		initComponents();
		initLayout();
	}

	private void initModels() {
		modelPedido = new PresentationModel<Pedido>();
		modelCliente = new PresentationModel<Client>();
		modelFuncionario = new PresentationModel<Funcionario>();
		modelItemPedido = new PresentationModel<ItemPedido>();
		
		
	}

	private void initComponents() {
		
		
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu menuArquivo = new JMenu("Arquivo");
		menuBar.add(menuArquivo);
		
		menuArquivo.add(new JMenuItem("Editar"));
		
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
		
		btExcluir= new JButton("Excluir");
		btExcluir.setEnabled(false);
		btExcluir.addActionListener(evt -> {
			if(pedido.getAlocado() == true) {
				JOptionPane.showMessageDialog(null, "Não é possível alterar um pedido alocado em embarque");
			} else {
				excluirPedido();
			}
		});
		
		btAdicionar = new JButton("Adicionar Item");
		btAdicionar.setEnabled(false);
		btAdicionar.addActionListener(evt -> {
			if(pedido.getAlocado() == true) {
				JOptionPane.showMessageDialog(null, "Não é possível alterar um pedido alocado em embarque");
			} else {
				try {
					InsertItemPedido insertItemPedido = new InsertItemPedido();
					ItemPedido item = new ItemPedido();
					item = insertItemPedido.retornaItem(pedido);
					modelItemPedido.setBean(item);
					addItemPedido();
					
					
				}
				catch(NullPointerException n) {
					n.printStackTrace();
				}
			}
		});
		
		
		SelectionInList<ItemPedido> selectionItemPedido = new SelectionInList<ItemPedido>(modelPedido.getModel("itensPedidos"));
		JList<ItemPedido> jList = new JList<ItemPedido>();
		Bindings.bind(jList, selectionItemPedido);
		
		//modelItemPedido = new PresentationModel<>(selectionItemPedido);
		
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
		
		tableItensPedido.addMouseListener(new MouseAdapter() {
		
		@Override
		public void mousePressed(java.awt.event.MouseEvent e) {
			itemPedido = modeloTable.retornaPedido(tableItensPedido.convertRowIndexToModel(tableItensPedido.getSelectedRow()));
			selecao = true;
			
		}
	});


	}
	
	private void initLayout() {
		
		FormLayout layout = new FormLayout("pref, 5px, 50dlu, 5px, 50dlu, 5px, 	50dlu, 69dlu");
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
		builder.nextLine();
		builder.append(btAdicionar);
		builder.append(btExcluir);
		
		frame.setTitle("Editar Pedido");
		frame.setSize(650,620);
		frame.setLayout(getLayout());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.add(builder.getPanel(), BorderLayout.CENTER);
		frame.setVisible(true);

		
	}
	
	
	private void buscar() {
		
		try {
			
			if (buscarCodPedido.getText().equals("")) {
				Pedido searchOrder = new ShowAllOrders().searchOrder();
				buscarCodPedido.setText(Integer.toString(searchOrder.getCodPedido()));
				
				pedido = modelPedido.getBean();
				
			} else {
				pedido = DatabaseDaoFactory.getOrderDAO().getPedido(Integer.parseInt(buscarCodPedido.getText()));
				modelCliente.setBean(pedido.getCliente());
				modelPedido.setBean(pedido);
				modelFuncionario.setBean(pedido.getVendedor());
				
				modeloTable.fireTableDataChanged();
			
				btExcluir.setEnabled(true);
				btAdicionar.setEnabled(true);
			}
		}
		catch (NumberFormatException | NullPointerException n) {
			
			JOptionPane.showMessageDialog(null, "Código de cliente inválido");
		}
	
	}
	

	private void excluirPedido() {

		if(selecao == false) {
			//0=yes, 1=no
			int input = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o pedido " + pedido.getCodPedido() + "?", 
					"Atenção!", JOptionPane.YES_NO_OPTION);

			if (input == 0) {
				DatabaseDaoFactory.getOrderDAO().deletePedido(pedido);
				JOptionPane.showMessageDialog(null, "Pedido excluído com sucesso");
				
			}

		} else {
			
			int input = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o item " 
			+ itemPedido.getIdItem() + " - " + itemPedido.getItem().getNomeSku() + " do pedido "
			+ pedido.getCodPedido() + "?", "Atenção!", JOptionPane.YES_NO_OPTION);

			if (input == 0) {
				DatabaseDaoFactory.getOrderDAO().deleteItemPedido(pedido, itemPedido);
				JOptionPane.showMessageDialog(null, "Item excluído com sucesso");
				modeloTable.fireTableDataChanged();
			}
			
		}
	}
	
	private void addItemPedido() {
		
		pedido.addItem(modelItemPedido.getBean());
		pedido.setValorTotal(modelItemPedido.getBean().getQtItem(), modelItemPedido.getBean().getItem().getValor());
		pedido.setPaletizacaoTotal(modelItemPedido.getBean().getQtItem(), modelItemPedido.getBean().getItem().getPaletizacao());
		ItemPedido retornaItem = new ItemPedido(modelItemPedido.getBean().getQtItem(), modelItemPedido.getBean().getItem());
	
		modeloTable.fireTableDataChanged();
		
		DatabaseDaoFactory.getOrderDAO().saveItemPedido(pedido, modelItemPedido.getBean());
		
		
		
	}

	
	
	
}
