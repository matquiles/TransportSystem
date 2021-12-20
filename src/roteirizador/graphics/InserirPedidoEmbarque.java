package roteirizador.graphics;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import roteirizador.bindings.BindingIntegerField;
import roteirizador.dao.database.DatabaseDaoFactory;
import roteirizador.domain.Pedido;

@SuppressWarnings("serial")
public class InserirPedidoEmbarque extends JDialog {
	
	
	PresentationModel<Pedido> modelPedido;
	
	Pedido pd = new Pedido();
	
	private JTextField codPedido;
	private JDialog frameInserirPedido;
	private JButton btBuscar;
	private JButton btAdicionar;
	
	
	
	public InserirPedidoEmbarque() {
		initModels();
		initComponents();
		initLayout();
	}
	
	private void initModels() {
		modelPedido = new PresentationModel<Pedido>();
		modelPedido.setBean(pd);
	}

	private void initComponents() {

		frameInserirPedido = new JDialog();

		codPedido = new BindingIntegerField(modelPedido.getModel("codPedido"));
		btAdicionar = new JButton("Inserir");
		btBuscar = new JButton("Buscar");

		btBuscar.addActionListener(evt -> {
			if(codPedido.getText().equals("")) {
				Pedido searchOrder = new ShowAllOrders().searchOrder();
				pd = searchOrder;
			}
		});

		btAdicionar.addActionListener(evt ->{

			try {
				pd = DatabaseDaoFactory.getOrderDAO().getPedido(Integer.parseInt(codPedido.getText()));
				
				if (pd.getAlocado() == true) {
					Object[] options = { "OK" };
					JOptionPane.showOptionDialog(null, "O Pedido já esta inserido em um embarque", "Erro ao inserir",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
							null, options, options[0]);
				} else {
					JOptionPane.showMessageDialog(null, "Pedido adicionado com sucesso!");
					frameInserirPedido.dispose();
				}
			}
			catch (NullPointerException | NumberFormatException n){
				JOptionPane.showMessageDialog(null, "Verifique os valores informados");
			}
		});

	}


	private void initLayout() {
		FormLayout layout = new FormLayout("pref, 10px, 30dlu,10px, 80dlu");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.border(new EmptyBorder(5, 5, 5, 5));

		builder.append("Pedido", codPedido);
		builder.append(btBuscar);
		builder.nextLine();

		builder.append(btAdicionar);

		frameInserirPedido.setTitle("Inserir Item");
		frameInserirPedido.setSize(400,150);
		frameInserirPedido.setLocationRelativeTo(null);
		frameInserirPedido.add(builder.getPanel(), BorderLayout.CENTER);
		frameInserirPedido.setModal(true);
		frameInserirPedido.setVisible(true);

	}
		
	public void retornoBusca(Pedido pd) {
		codPedido.setText(Integer.toString(pd.getCodPedido()));

	}

	public Pedido retornaPedido() {
		return pd;
	}
		

	


}
