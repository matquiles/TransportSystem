package roteirizador.graphics;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import roteirizador.dao.database.DatabaseDaoFactory;
import roteirizador.domain.Pedido;
import roteirizador.graphics.controller.showAllOrderTableModel;

public class ShowAllOrders {
	
	private JDialog frameTable = new JDialog();
	private JTable tableOrder;
	private Pedido pedido;
	
	public ShowAllOrders() {
		
	}
	
	public Pedido searchOrder() {
		
		showAllOrderTableModel modelAll = new showAllOrderTableModel(DatabaseDaoFactory.getOrderDAO().getAll()); 

		tableOrder = new JTable(modelAll);
		tableOrder.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableOrder.getColumnModel().getColumn(0).setPreferredWidth(80);
		tableOrder.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableOrder.getColumnModel().getColumn(2).setPreferredWidth(200);
		tableOrder.getColumnModel().getColumn(3).setPreferredWidth(100);
		tableOrder.getColumnModel().getColumn(4).setPreferredWidth(80);
		tableOrder.getColumnModel().getColumn(5).setPreferredWidth(100);
		tableOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				pedido = modelAll.retornaPedido(tableOrder.convertRowIndexToModel(tableOrder.getSelectedRow()));
				frameTable.dispose();
			}
		});
		
	

		JScrollPane pane = new JScrollPane(tableOrder);
		FormLayout layout = new FormLayout("400dlu");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.border(new EmptyBorder(5, 5, 5, 5));
		
		builder.append(pane);
		
		frameTable.setTitle("Lista de Pedidos");
		frameTable.setSize(1000,600);
		frameTable.setLayout(new BorderLayout());
		frameTable.setLocationRelativeTo(null);
		frameTable.add(builder.getPanel(), BorderLayout.CENTER);
		frameTable.setModal(true);
		frameTable.setVisible(true);
		
		return pedido;
	}

	
}
