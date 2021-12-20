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
import roteirizador.domain.Embarque;
import roteirizador.graphics.controller.showAllEmbarquesTableModel;


public class ShowAllEmbarques {
	

	private JDialog frameTable = new JDialog();
	private JTable tableEmbarque;
	private Embarque embarque;
	
	public ShowAllEmbarques() {
		
	}
	
	public Embarque searchEmbarque() {
		
		showAllEmbarquesTableModel modelAll = new showAllEmbarquesTableModel(DatabaseDaoFactory.getEmbarqueDAO().getAll());
		
		tableEmbarque = new JTable(modelAll);
		tableEmbarque.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableEmbarque.getColumnModel().getColumn(0).setPreferredWidth(80);
		tableEmbarque.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableEmbarque.getColumnModel().getColumn(2).setPreferredWidth(200);
		tableEmbarque.getColumnModel().getColumn(3).setPreferredWidth(100);
		tableEmbarque.getColumnModel().getColumn(4).setPreferredWidth(80);
		tableEmbarque.getColumnModel().getColumn(5).setPreferredWidth(100);
		tableEmbarque.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				embarque = modelAll.retornaPedido(tableEmbarque.convertRowIndexToModel(tableEmbarque.getSelectedRow()));
				
				frameTable.dispose();
			}
		});
		
		
		JScrollPane pane = new JScrollPane(tableEmbarque);
		FormLayout layout = new FormLayout("324dlu");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.border(new EmptyBorder(5, 5, 5, 5));
		
		builder.append(pane);
		
		frameTable.setTitle("Lista de Embarques");
		frameTable.setSize(700,600);
		frameTable.setLayout(new BorderLayout());
		frameTable.setLocationRelativeTo(null);
		frameTable.add(builder.getPanel(), BorderLayout.CENTER);
		frameTable.setModal(true);
		frameTable.setVisible(true);
		
		return embarque;
	}
	
	public void searchEmbarqueEdit(EditEmbarqueUI search) {
		
		showAllEmbarquesTableModel modelAll = new showAllEmbarquesTableModel(DatabaseDaoFactory.getEmbarqueDAO().getAll());
		
		tableEmbarque = new JTable(modelAll);
		tableEmbarque.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableEmbarque.getColumnModel().getColumn(0).setPreferredWidth(80);
		tableEmbarque.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableEmbarque.getColumnModel().getColumn(2).setPreferredWidth(200);
		tableEmbarque.getColumnModel().getColumn(3).setPreferredWidth(100);
		tableEmbarque.getColumnModel().getColumn(4).setPreferredWidth(80);
		tableEmbarque.getColumnModel().getColumn(5).setPreferredWidth(100);
		tableEmbarque.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				Embarque embarque = modelAll.retornaPedido(tableEmbarque.convertRowIndexToModel(tableEmbarque.getSelectedRow()));
				search.setProcuraEmbarque(embarque);
				frameTable.dispose();
			}
		});
		
		
		JScrollPane pane = new JScrollPane(tableEmbarque);
		FormLayout layout = new FormLayout("324dlu");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.border(new EmptyBorder(5, 5, 5, 5));
		
		builder.append(pane);
		
		frameTable.setTitle("Lista de Embarques");
		frameTable.setSize(700,600);
		frameTable.setLayout(new BorderLayout());
		frameTable.setLocationRelativeTo(null);
		frameTable.add(builder.getPanel(), BorderLayout.CENTER);
		frameTable.setModal(true);
		frameTable.setVisible(true);
		
	}
	
}
