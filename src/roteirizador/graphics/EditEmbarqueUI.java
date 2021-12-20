package roteirizador.graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.JButton;
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
import roteirizador.bindings.BindingTextField;
import roteirizador.dao.database.DatabaseDaoFactory;
import roteirizador.domain.Embarque;
import roteirizador.domain.Pedido;
import roteirizador.graphics.controller.ModelTable;
import roteirizador.graphics.controller.orderTableModel;
import roteirizador.validations.FormatarValores;
import roteirizador.validations.Sessao;

@SuppressWarnings("serial")
public class EditEmbarqueUI extends JFrame {
	
	PresentationModel<Embarque> modelEmbarque;
	PresentationModel<Pedido> modelPedido;
	
	private JFrame frame = new JFrame();
	private Embarque embarque = new Embarque();
	private FormatarValores format = new FormatarValores();
	
	private JMenuBar menuBar;
	private JTextField codEmbarque;
	private JTextField placa;
	private JTable tablePedidos;
	private JTextField valorTotal;
	private JTextField paletizacaoTotal;
	private JTextField ocupacaoTotal;
	private JButton btBuscar;
	private JButton btSalvar;
	private orderTableModel modeloTable;
	private JButton btAddPedido;
	private JTextField qtdClientes;
	private JTextField codPedido;
	private JButton btExcluir;
	private Boolean selecao = false;
	private Pedido pd;
	
	
	public EditEmbarqueUI() {
		initModels();
		initComponents();
		initLayout();
	}



	private void initModels() {
		modelEmbarque = new PresentationModel<Embarque>();
		modelPedido = new PresentationModel<Pedido>();
		modelEmbarque.setBean(embarque);
		
	}



	private void initComponents() {
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu menuArquivo = new JMenu("Arquivo");
		menuBar.add(menuArquivo);
	
		JMenuItem menuSair = new JMenuItem("Sair");
		menuSair.addActionListener(evt -> frame.dispose());
		menuArquivo.add(menuSair);
		
		codEmbarque = new JTextField("");
		
		placa = new BindingTextField(modelEmbarque.getModel("veiculo"), true);
		
		valorTotal = new BindingDoubleField(modelEmbarque.getModel("valorTotal"), new DecimalFormat("R$ #,###.00"));
		valorTotal.setEditable(false);
		
		paletizacaoTotal = new BindingDoubleField(modelEmbarque.getModel("paletizacaoTotal"), new DecimalFormat("#.###"));
		paletizacaoTotal.setEditable(false);
		
		ocupacaoTotal = new BindingDoubleField(modelEmbarque.getModel("ocupacao"), new DecimalFormat("% ##.##"));
		ocupacaoTotal.setEditable(false);
		
		qtdClientes = new JTextField();
		qtdClientes.setEditable(false);
		
		btAddPedido = new JButton("Add Pedido");
		btAddPedido.setEnabled(false);
		
		btAddPedido.addActionListener(evt -> {
			try {
				InserirPedidoEmbarque insertPedidoEmbarque = new InserirPedidoEmbarque();
				Pedido pd = new Pedido();
				pd = insertPedidoEmbarque.retornaPedido();
	
				modelPedido.setBean(pd);
				
				DatabaseDaoFactory.getEmbarqueDAO().savePedido(embarque, pd);
				embarque.alocar();
				
				modeloTable.fireTableDataChanged();
				btSalvar.setEnabled(true);
						
			}
			catch(NullPointerException n) {
				n.printStackTrace();
			}
		
		});
		
		
		
		btBuscar = new JButton("Buscar");
		btBuscar.addActionListener(act -> {
			try {
				buscarEmbarque();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "Embarque inválido");
				e1.printStackTrace();
			}
		});
		
		btSalvar = new JButton("Salvar");
		btSalvar.setEnabled(false);
		btSalvar.addActionListener(evt -> salvar());
		btExcluir = new JButton("Excluir");
		btExcluir.setEnabled(false);
		btExcluir.addActionListener(evt -> excluir());
		
		
		SelectionInList<Pedido> selectionItemPedido = new SelectionInList(modelEmbarque.getModel("pedidos"));
		JList<Pedido> jList = new JList<Pedido>();
		Bindings.bind(jList, selectionItemPedido);
		
		tablePedidos = new JTable();
		
		modeloTable = new orderTableModel(selectionItemPedido);
		tablePedidos = new JTable(modeloTable);
		tablePedidos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tablePedidos.getColumnModel().getColumn(0).setPreferredWidth(100);
		tablePedidos.getColumnModel().getColumn(1).setPreferredWidth(200);
		tablePedidos.getColumnModel().getColumn(2).setPreferredWidth(100);
		tablePedidos.getColumnModel().getColumn(3).setPreferredWidth(100);
		tablePedidos.getColumnModel().getColumn(4).setPreferredWidth(80);
		tablePedidos.getColumnModel().getColumn(5).setPreferredWidth(100);
		tablePedidos.getColumnModel().getColumn(6).setPreferredWidth(80);
		
		tablePedidos.addMouseListener(new MouseAdapter() {
			
		@Override
		public void mousePressed(java.awt.event.MouseEvent e) {
			pd = modeloTable.retornaPedido(tablePedidos.convertRowIndexToModel(tablePedidos.getSelectedRow()));
			selecao = true;
			
		}
	});
	}


	private void initLayout() {
		FormLayout layout = new FormLayout("pref, 10px, 50dlu,10px, 50dlu,10px, 50dlu, 142dlu");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		JScrollPane paneTable = new JScrollPane(tablePedidos);
		paneTable.setPreferredSize(new Dimension(0,250));
		builder.border(new EmptyBorder(10, 10, 10, 10));
		
		builder.append("Embarque", codEmbarque);
		builder.append(btBuscar);
		builder.nextLine();
		builder.append("Placa", placa);
		builder.nextLine();
		builder.append(btAddPedido);
		builder.nextLine();
		builder.appendRow("10dlu");
		builder.append(btAddPedido);
		builder.nextLine();
		builder.append(paneTable, builder.getColumnCount());
		builder.nextLine();
		
		builder.append("Valor Total", valorTotal);
		builder.nextLine();
		builder.append("Paletização", paletizacaoTotal);
		builder.nextLine();
		builder.append("Ocupação", ocupacaoTotal);
		builder.nextLine();
		builder.append("Qtd Cliente", qtdClientes);
		builder.nextLine();
		builder.appendRow("10dlu");
		builder.nextLine();
		builder.append(btExcluir);
		builder.append(btSalvar);
		
		
		frame.setTitle("Editar Embarque");
		frame.setSize(800,600);
		frame.setLayout(getLayout());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.add(builder.getPanel(), BorderLayout.CENTER);
		pack();
		frame.setVisible(true);
	}
	
	
	
	public void setPedido(Pedido pedido) {
		codPedido.setText(Integer.toString(pedido.getCodPedido()));
	}
	
	private void salvar() {
		
		JOptionPane.showMessageDialog(this, String.format("<html>Embarque salvo com sucesso! %s</html>", embarque.toString()));
		DatabaseDaoFactory.getEmbarqueDAO().atualizarEmbarque(modelEmbarque.getBean());
	}
	
	private void excluir() {
		
		if(selecao == false) {
			//0=yes, 1=no
			int input = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o embarque " + embarque.getCodEmbarque() + "?", 
					"Atenção!", JOptionPane.YES_NO_OPTION);

			if (input == 0) {
				embarque.desalocarTodos();
				DatabaseDaoFactory.getEmbarqueDAO().deleteEmbarque(embarque);
				JOptionPane.showMessageDialog(null, "Embarque excluído com sucesso");
				
				
			}

		} else {
			
			int input = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o pedido " 
			+ modelPedido.getBean().getCodPedido() + " do embarque "
			+ modelEmbarque.getBean().getCodEmbarque() + "?", "Atenção!", JOptionPane.YES_NO_OPTION);

			if (input == 0) {
				DatabaseDaoFactory.getEmbarqueDAO().deleteItemPedido(modelEmbarque.getBean(), modelPedido.getBean());
				JOptionPane.showMessageDialog(null, "Pedido excluído com sucesso");
				modeloTable.fireTableDataChanged();
			}
			
		}
		
	
	}
	
	private void buscarEmbarque() throws SQLException {
		
		try {

			if (codEmbarque.getText().equals("")) {
				new ShowAllEmbarques().searchEmbarqueEdit(this);
			} else {
				embarque = DatabaseDaoFactory.getEmbarqueDAO().getEmbarque(Integer.parseInt(codEmbarque.getText()));
				modelEmbarque.setBean(embarque);
				qtdClientes.setText(Integer.toString(embarque.getAll().size()));
				
				btAddPedido.setEnabled(true);
				btExcluir.setEnabled(true);
				btSalvar.setEnabled(true);
				placa.setEditable(true);
			
				modeloTable.fireTableDataChanged();
			}
		}
		catch (NumberFormatException | NullPointerException n) {
			JOptionPane.showMessageDialog(null, "Embarque inválido");
		}

	}

	public void setEmbarque(Embarque emb) {
		codEmbarque.setText(Integer.toString(emb.getCodEmbarque()));
	}
	
	
	public void setProcuraEmbarque(Embarque emb) {
		codEmbarque.setText(Integer.toString(emb.getCodEmbarque()));
	}


}
