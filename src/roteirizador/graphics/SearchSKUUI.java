package roteirizador.graphics;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.ScrollPane;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.layout.FormLayout;

import roteirizador.bindings.BindingDoubleField;
import roteirizador.bindings.BindingIntegerField;
import roteirizador.bindings.BindingTextField;
import roteirizador.dao.database.DatabaseDaoFactory;
import roteirizador.domain.SKU;
import roteirizador.graphics.controller.ModelTable;
import roteirizador.graphics.controller.SKUTableModel;



@SuppressWarnings("serial")
public class SearchSKUUI extends JFrame {
	
	PresentationModel<SKU> model;
	
	private BindingIntegerField codSKU;
	private BindingTextField descricaoSKU;
	private BindingDoubleField valorSKU;
	private BindingIntegerField paletizacaoSKU;
	private JMenuBar menuBar;
	private JTextField consultaSKU;
	private JButton btPesquisar;
	private JButton btAnteriror;
	private JButton btProximo;
	private JTable tableSKU;
	
	private JFrame frame = new JFrame();
	private JFrame frameTable = new JFrame();

	public SearchSKUUI() {
		initComponents();
		initLayout();
	}


	private void initComponents() {
		
		model = new PresentationModel<SKU>();
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu menuArquivo = new JMenu("Arquivo");
		menuBar.add(menuArquivo);
		
		menuArquivo.add(new JMenuItem("Editar"));
		
		JMenuItem menuMostrarTodos = new JMenuItem("Exibir todos");
		menuMostrarTodos.addActionListener(evt -> showTable());
		menuArquivo.add(menuMostrarTodos);
		
		JMenuItem novoSKU = new JMenuItem("Cadastrar novo SKU");
		novoSKU.addActionListener(evt -> {
			frame.dispose();
			new RegisterSKUUI();
	
		});
		menuArquivo.add(novoSKU);
		
		
		JMenuItem menuSair = new JMenuItem("Sair");
		menuSair.addActionListener(evt -> frame.dispose());
		menuArquivo.add(menuSair);
		
		
		consultaSKU = new JTextField();
		btPesquisar = new JButton();
		btAnteriror = new JButton();
		btProximo = new JButton();
		
		codSKU = new BindingIntegerField(model.getModel("codSKU"));
		codSKU.setEditable(false);
		
		descricaoSKU = new BindingTextField(model.getModel("nomeSku"), true);
		descricaoSKU.setEditable(false);
		
		valorSKU = new BindingDoubleField(model.getModel("valor"), new DecimalFormat("#,###.00"));
		valorSKU.setEditable(false);
		
		paletizacaoSKU = new BindingIntegerField(model.getModel("paletizacao"));
		paletizacaoSKU.setEditable(false);
		
		btPesquisar = new JButton("Buscar");
		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
		btPesquisar.addActionListener(e -> search(Integer.parseInt(consultaSKU.getText())));
		
		btAnteriror = new JButton("<");
		btAnteriror.setEnabled(false);
		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
		btAnteriror.addActionListener(e -> search(Integer.parseInt(codSKU.getText())-1));
		
		btProximo = new JButton(">");
		btProximo.setEnabled(false);
		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
		btProximo.addActionListener(e -> search(Integer.parseInt(codSKU.getText())+1));
		
	}


	private void initLayout() {
		
		FormLayout layout = new FormLayout("pref, 3px, pref, 3px, pref, pref:grow");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.border(new EmptyBorder(5, 5, 5, 5));
		//new FormDebugPanel()
		
		builder.append("Codigo", consultaSKU);
		
		builder.nextColumn();
		builder.append(btPesquisar);
		builder.nextLine();
		
		builder.append("");
		builder.append(btAnteriror);
		
		builder.append(btProximo);
		
		builder.nextLine();
		builder.appendRow("10dlu");
		builder.nextLine();
		
		
		builder.appendSeparator("Sku");
		builder.nextLine();
	
		builder.append("Codigo SKU", codSKU);
		builder.nextLine();
		
		builder.append("Descricao SKU", descricaoSKU, builder.getColumnCount()-2);
		builder.nextLine();
		
		builder.append("Valor unitario", valorSKU, builder.getColumnCount()-2);
		builder.nextLine();
		
		builder.append("Paletizacao", paletizacaoSKU, builder.getColumnCount()-2);
	
		frame.setTitle("Consulta de SKU");
		frame.setSize(315,300);
		frame.setLayout(getLayout());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.add(builder.getPanel(), BorderLayout.CENTER);
		pack();
		frame.setVisible(true);
		
		
		
	}
	
	private void search(int cod) {
		
		
		try {
		SKU sku = DatabaseDaoFactory.getSkuDAO().getSKU(cod);
		model.setBean(sku);
			
		if (cod == 1) {
			btAnteriror.setEnabled(false);
			btProximo.setEnabled(true);
		} else if(cod == DatabaseDaoFactory.getSkuDAO().getAll().size()) {
			btAnteriror.setEnabled(true);
			btProximo.setEnabled(false);
		} else {
			btAnteriror.setEnabled(true);
			btProximo.setEnabled(true);
		}
		
		
		
		} catch (NumberFormatException n) {
			
			JOptionPane.showMessageDialog(null, "Codigo invalido");
		}
		
	}
	
	
	
	private void showTable() {
		

		SKUTableModel model = new SKUTableModel(DatabaseDaoFactory.getSkuDAO().getAll()); 
		
		tableSKU = new JTable(model);
		tableSKU.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableSKU.getColumnModel().getColumn(0).setPreferredWidth(70);
		tableSKU.getColumnModel().getColumn(1).setPreferredWidth(250);
		tableSKU.getColumnModel().getColumn(2).setPreferredWidth(70);
		tableSKU.getColumnModel().getColumn(3).setPreferredWidth(50);

		
		JScrollPane pane = new JScrollPane(tableSKU);
		FormLayout layout = new FormLayout("pref");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.border(new EmptyBorder(5, 5, 5, 5));
		
		
		builder.append(pane);
		
		frameTable.setTitle("Lista de SKUs");
		frameTable.setSize(475,400);
		frameTable.setLayout(getLayout());
		frameTable.setLocationRelativeTo(null);
		frameTable.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frameTable.add(builder.getPanel(), BorderLayout.CENTER);
		pack();
		frameTable.setVisible(true);
		
		
	}
	


}
