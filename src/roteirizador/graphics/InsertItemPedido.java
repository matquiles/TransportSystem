package roteirizador.graphics;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import roteirizador.bindings.BindingIntegerField;
import roteirizador.bindings.BindingTextField;
import roteirizador.dao.database.DatabaseDaoFactory;
import roteirizador.domain.ItemPedido;
import roteirizador.domain.Pedido;
import roteirizador.domain.SKU;

public class InsertItemPedido extends JDialog {
	
	PresentationModel<ItemPedido> modelItemPedido;
	PresentationModel<SKU> modelSKU;

	ItemPedido item = new ItemPedido();
	

	private JDialog frameInserirItem;
	private JComboBox<SKU> codSKU;
	private BindingIntegerField qtd;
	private JButton btAdicionar;
	private BindingTextField descricaoSku;
	private JButton btconsultar;
	
	
	public InsertItemPedido() {
		initModels();
		initComponents();
		initLayout();
	}
	

	private void initModels() {
		modelItemPedido = new PresentationModel<ItemPedido>();
		modelSKU = new PresentationModel<SKU>();
		modelItemPedido.setBean(item);
	}

	private void initComponents() {
		
		frameInserirItem = new JDialog();

		codSKU = new JComboBox<SKU>();
		codSKU.setRenderer(new ListRenderer<SKU>(SKU::getCodSKU));

		SelectionInList<SKU> selectionItemPedido = new SelectionInList<SKU>(DatabaseDaoFactory.getSkuDAO().getAll());

		ComboBoxAdapter<SKU> comboBoxAdapter = new ComboBoxAdapter<SKU>(selectionItemPedido, modelItemPedido.getModel("item"));
		
		Bindings.bind(codSKU, comboBoxAdapter);
		
		descricaoSku = new BindingTextField(modelSKU.getModel("nomeSku"), true);
		descricaoSku.setEditable(false);
		
		qtd = new BindingIntegerField(modelItemPedido.getModel("qtItem"));
		
		btconsultar = new JButton ("Info");
		btconsultar.addActionListener(evt -> {
			try {
				JOptionPane.showMessageDialog(this, String.format("<html>Informações do SKU %s</html>", modelSKU.getBean().toString()));
			}
			catch (NullPointerException n) {
				JOptionPane.showMessageDialog(this, "Selecione um SKU");
			}
		});
		
		btAdicionar = new JButton("Inserir");
		
		codSKU.addActionListener(evt ->{
			modelSKU.setBean(item.getItem());

		});
		
		btAdicionar.addActionListener(evt ->{
			try {
				
				JOptionPane.showMessageDialog(null, "Item adicionado com sucesso!");
				frameInserirItem.dispose();
				
			}
			catch (NullPointerException | NumberFormatException n){
				JOptionPane.showMessageDialog(null, "Verifique os valores informados");
			}
			
			
		});
		
	}

	private void initLayout() {
		FormLayout layout = new FormLayout("pref, 10px, 30dlu,10px, 80dlu, 5px, 40dlu");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.border(new EmptyBorder(5, 5, 5, 5));
		
		builder.append("SKU", codSKU);
		builder.append(descricaoSku);
		builder.append(btconsultar);
		builder.nextLine();
		builder.append("Qtd", qtd);
		builder.nextLine();

		builder.append(btAdicionar);
		
		frameInserirItem.setTitle("Inserir Item");
		frameInserirItem.setSize(470,150);
		frameInserirItem.setLayout(getLayout());
		frameInserirItem.setLocationRelativeTo(null);
		frameInserirItem.add(builder.getPanel(), BorderLayout.CENTER);
		frameInserirItem.setModal(true);
		frameInserirItem.setVisible(true);
		
	}
	
	public ItemPedido retornaItem(Pedido pedido) {
		
		item.setPedido(pedido);
		item.setIdItem(pedido.getAll().size()+1);
		
		return item;
	}
	
}

