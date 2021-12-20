
package roteirizador.graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.Collections;

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
import roteirizador.domain.Client;
import roteirizador.domain.Embarque;
import roteirizador.domain.ItemPedido;
import roteirizador.domain.Pedido;
import roteirizador.graphics.controller.orderTableModel;
import roteirizador.validations.FormatarValores;

@SuppressWarnings("serial")
public class SearchEmbarqueUI extends JFrame {
	
	PresentationModel<Embarque> modelEmbarque;
	PresentationModel<Pedido> modelPedido;

	private JFrame frame = new JFrame();
	private Embarque embarque;
	private FormatarValores format = new FormatarValores();
	
	private JMenuBar menuBar;
	private JTextField codEmbarque;
	private JTextField placa;
	private JTable tablePedidos;
	private JTextField valorTotal;
	private JTextField paletizacaoTotal;
	private JTextField ocupacaoTotal;
	private orderTableModel modeloTable;
	private JTextField qtdClientes;
	private JButton btConsultar;
	
	
	
	public SearchEmbarqueUI() {
		initModels();
		initComponents();
		initLayout();
	}


	private void initModels() {

		modelEmbarque = new PresentationModel<Embarque>();
		modelPedido = new PresentationModel<Pedido>();
		
	}

	private void initComponents() {
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu menuArquivo = new JMenu("Arquivo");
		menuBar.add(menuArquivo);
		
		JMenuItem novo = new JMenuItem("Novo Embarque");
		novo.addActionListener(evt -> {
			new CreateEmbarqueUI();
			frame.dispose();
		});
		menuArquivo.add(novo);
		
		JMenuItem editar = new JMenuItem("Editar");
		editar.addActionListener(evt -> {
			new EditEmbarqueUI();
			frame.dispose();
		});
		menuArquivo.add(editar);
	
		JMenuItem menuSair = new JMenuItem("Sair");
		menuSair.addActionListener(evt -> frame.dispose());
		menuArquivo.add(menuSair);
		

		codEmbarque = new JTextField();
	
		
		placa = new BindingTextField(modelEmbarque.getModel("veiculo"), true);
		placa.setEditable(false);
		
		
		valorTotal = new BindingDoubleField(modelEmbarque.getModel("valorTotal"), new DecimalFormat("R$ #,###.00"));
		valorTotal.setEditable(false);
		
		paletizacaoTotal = new BindingDoubleField(modelEmbarque.getModel("paletizacaoTotal"), new DecimalFormat("#.###"));
		paletizacaoTotal.setEditable(false);
		
		ocupacaoTotal = new BindingDoubleField(modelEmbarque.getModel("ocupacao"), new DecimalFormat("% ##.##"));
		ocupacaoTotal.setEditable(false);
		
		
		qtdClientes = new JTextField();
		qtdClientes.setEditable(false);
		qtdClientes.setHorizontalAlignment(JTextField.RIGHT);
		
		
		btConsultar = new JButton("Buscar");
		btConsultar.addActionListener(act -> buscar());
		
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
		FormLayout layout = new FormLayout("pref, 10px, 50dlu,10px, 50dlu,10px, 50dlu, 161dlu");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		JScrollPane paneTable = new JScrollPane(tablePedidos);
		paneTable.setPreferredSize(new Dimension(0,250));
		builder.border(new EmptyBorder(10, 10, 10, 10));
		
		builder.append("Embarque", codEmbarque);
	
		builder.append(btConsultar);
		builder.nextLine();
		builder.append("Placa", placa);
		builder.nextLine();
		builder.appendRow("15dlu");
		builder.nextLine();
		builder.append(paneTable, builder.getColumnCount());
		builder.nextLine();
		builder.appendRow("10dlu");
		builder.nextLine();
		
		builder.append("Valor Total", valorTotal);
		builder.nextLine();
		builder.append("Paletização", paletizacaoTotal);
		builder.nextLine();
		builder.append("Ocupação", ocupacaoTotal);
		builder.nextLine();
		builder.append("Qtd Cliente", qtdClientes);


		
		frame.setTitle("Consultar Embarque");
		frame.setSize(800,600);
		frame.setLayout(getLayout());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.add(builder.getPanel(), BorderLayout.CENTER);
		pack();
		frame.setVisible(true);
		
	}
	
	private void buscar() {
		
		try {
			
			if (codEmbarque.getText().equals("")) {
				Embarque searchEmbarque = new ShowAllEmbarques().searchEmbarque();
				
				codEmbarque.setText(Integer.toString(searchEmbarque.getCodEmbarque()));
				
				
			} else {
				embarque = DatabaseDaoFactory.getEmbarqueDAO().getEmbarque(Integer.parseInt(codEmbarque.getText()));
				modelEmbarque.setBean(embarque);
				qtdClientes.setText(Integer.toString(embarque.getAll().size()));
				
				modeloTable.fireTableDataChanged();
				
			}
		}
		catch (NumberFormatException | NullPointerException n) {
			
			JOptionPane.showMessageDialog(null, "Código de cliente inválido");
		}
		
	}
	
	

	public void setEmbarque(Embarque emb) {

		codEmbarque.setText(Integer.toString(emb.getCodEmbarque()));

	}

	
}
