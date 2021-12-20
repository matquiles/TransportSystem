package roteirizador.graphics;

import java.awt.BorderLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;

import com.jgoodies.binding.binder.BeanBinder;
import com.jgoodies.binding.binder.Binders;
import com.jgoodies.binding.binder.ValueModelBindingBuilder.Commit;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;


import roteirizador.dao.database.DatabaseDaoFactory;
import roteirizador.domain.Pedido;
import roteirizador.validations.FormatarValores;

@SuppressWarnings("serial")
public class ExportOrderUI extends JFrame {
	
	private JFrame frame = new JFrame();


	private JMenuBar menuBar;
	private JTextField dataInicial;
	private JTextField dataFinal;
	private JTextField unidade;
	private JButton btExportar;
	

	public ExportOrderUI() {
		initComponents();
		initLayout();
	}


	private void initComponents() {
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu menuArquivo = new JMenu("Arquivo");
		menuBar.add(menuArquivo);
		
		JMenuItem menuSair = new JMenuItem("Sair");
		menuSair.addActionListener(evt -> frame.dispose());
		menuArquivo.add(menuSair);
		
		dataInicial = new JTextField();
		
		dataFinal = new JTextField();
		unidade = new JTextField();
		btExportar = new JButton("Exportar");
		btExportar.addActionListener(evt -> {
			try {
				export();
			} catch (NumberFormatException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	
	}
	


	private void export() throws NumberFormatException, ParseException {
		
		String caminho;
		
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
		FileWriter arq;
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
		
		chooser.setFileFilter(filter);
	
		int retorno = chooser.showOpenDialog(frame);
		
		if(retorno == JFileChooser.APPROVE_OPTION) {
			caminho = chooser.getSelectedFile().getAbsolutePath();
			
			try {
				arq = new FileWriter(caminho);
				PrintWriter gravarArq = new PrintWriter(arq);
				List<Pedido> listaDePedidos = DatabaseDaoFactory.getOrderDAO().getRange(formato.parse(dataInicial.getText()), 
						formato.parse(dataFinal.getText()), Integer.parseInt(unidade.getText()));
				
				for (Pedido pedido : listaDePedidos) {
					gravarArq.print(pedido.getCodPedido()+",");
					gravarArq.print(pedido.getCliente().getCodcliente()+",");
					gravarArq.print(pedido.getPaletizacaoTotal()+"\n");
					
				}
				
				JOptionPane.showMessageDialog(null, "Arquivo exportado com sucesso!");
				arq.close();
								
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Arquivo não encontrado");
			}
							
		} else {
			
		}
	
	}



	private void initLayout() {
		
		FormLayout layout = new FormLayout("pref, 5px, 45dlu, 5px, 45dlu, 5px");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.border(new EmptyBorder(10, 10, 10, 10));
		 //new FormDebugPanel()
		
		builder.append("Data", dataInicial);
		
		builder.append(dataFinal);
		builder.nextLine();

		builder.append("Unidade", unidade);
		builder.nextLine();
		builder.append(btExportar);
		
		frame.setTitle("Exportar Pedidos");
		frame.setSize(320,200);
		frame.setLayout(getLayout());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.add(builder.getPanel(), BorderLayout.CENTER);
		frame.setVisible(true);
		
	}
	
	

}
