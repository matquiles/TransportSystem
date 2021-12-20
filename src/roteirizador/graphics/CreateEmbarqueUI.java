package roteirizador.graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import roteirizador.bindings.BindingIntegerField;
import roteirizador.bindings.BindingTextField;
import roteirizador.dao.database.DatabaseDaoFactory;
import roteirizador.domain.Embarque;
import roteirizador.domain.Pedido;
import roteirizador.graphics.controller.orderTableModel;
import roteirizador.validations.FormatarValores;

@SuppressWarnings("serial")
public class CreateEmbarqueUI extends JFrame {
	
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
	private JButton btCancelar;
	private JButton btSalvar;
	private orderTableModel modeloTable;
	private JButton btAddPedido;
	private JTextField qtdClientes;
	private JTextField codPedido;
	private static JFrame instance;
	
	
	
	public CreateEmbarqueUI() {
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
		
		JMenu menuImportar = new JMenu("Importação");
		menuBar.add(menuImportar);
		
		JMenuItem importar = new JMenuItem("Importar arquivo");
		importar.addActionListener(evt -> new CreateEmbarqueImportacaoUI());
		menuImportar.add(importar);
		
		codEmbarque = new BindingIntegerField(modelEmbarque.getModel("codEmbarque"));
		codEmbarque.setEditable(false);
		
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
		btAddPedido.addActionListener(evt -> {
			try {
				InserirPedidoEmbarque insertPedidoEmbarque = new InserirPedidoEmbarque();
				Pedido pd = new Pedido();
				pd = insertPedidoEmbarque.retornaPedido();
	
				modelPedido.setBean(pd);
				
				System.out.println(modelPedido.getBean().getCliente().getNomecliente());
				
				btSalvar.setEnabled(true);
				
				addPedido();
				
	
			}
			catch(NullPointerException n) {
				n.printStackTrace();
			}
		
		});
		btCancelar = new JButton("Cancelar");
		btCancelar.addActionListener(act -> frame.dispose());
		btSalvar = new JButton("Salvar");
		btSalvar.setEnabled(false);
		btSalvar.addActionListener(evt -> salvar());
		
		
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
	}


	private void initLayout() {
		FormLayout layout = new FormLayout("pref, 10px, 50dlu,10px, 50dlu,10px, 50dlu, 142dlu");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		JScrollPane paneTable = new JScrollPane(tablePedidos);
		paneTable.setPreferredSize(new Dimension(0,250));
		builder.border(new EmptyBorder(10, 10, 10, 10));
		
		builder.append("Embarque", codEmbarque);
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
		builder.append(btCancelar);
		builder.append(btSalvar);
		
		
		frame.setTitle("Criar Embarque");
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
	
		DatabaseDaoFactory.getEmbarqueDAO().save(embarque);
		embarque.alocar();
		
		
		JOptionPane.showMessageDialog(this, String.format("<html>Embarque salvo com sucesso! %s</html>", embarque.toString()));
		
	}
	
	private void addPedido() {
		embarque.addPedido(modelPedido.getBean());
		qtdClientes.setText(Integer.toString(embarque.getAll().size()));
	
		modeloTable.fireTableDataChanged();
		
	}
	
	
	public synchronized static JFrame getInstance() {
		if (instance == null) {
			instance = new CreateEmbarqueUI();
		}
		return instance;
	}
	
	
	

}
