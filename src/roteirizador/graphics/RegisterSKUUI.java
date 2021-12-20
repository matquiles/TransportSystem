package roteirizador.graphics;

import java.awt.BorderLayout;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import roteirizador.bindings.BindingDoubleField;
import roteirizador.bindings.BindingIntegerField;
import roteirizador.bindings.BindingTextField;
import roteirizador.dao.database.DatabaseDaoFactory;
import roteirizador.domain.SKU;


@SuppressWarnings("serial")
public class RegisterSKUUI  extends JFrame{
	
	PresentationModel<SKU> model;
	SKU sku = new SKU();
	
	private BindingIntegerField codSKU;
	private BindingTextField descricaoSKU;
	private BindingDoubleField valorSKU;
	private BindingIntegerField paletizacaoSKU;
	private JButton btCancelar;
	private JButton btSalvar;
	private JMenuBar menuBar;

	private static JFrame instance;
	static JFrame frame = new JFrame();

	public RegisterSKUUI() {
		initModels();
		initComponents();
		initLayout();
	}
	
	private void initModels() {
		model = new PresentationModel<SKU>();
		sku.setCodSKU(DatabaseDaoFactory.getSkuDAO().qtdRegistros());
		model.setBean(sku);
		
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
		
		
		codSKU = new BindingIntegerField(model.getModel("codSKU"));
		codSKU.setEditable(false);
		
		descricaoSKU = new BindingTextField(model.getModel("nomeSku"), true);
		
		valorSKU = new BindingDoubleField(model.getModel("valor"), new DecimalFormat("#,###.00"));
		
		paletizacaoSKU = new BindingIntegerField(model.getModel("paletizacao"));
		
		btCancelar = new JButton("Cancelar");
		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
		btCancelar.addActionListener(e -> frame.dispose());
		
		btSalvar = new JButton("Salvar");
		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
		btSalvar.addActionListener(e -> salvar());
		
	}
	
	public void initLayout() {
		
		FormLayout layout = new FormLayout("pref, 5px, 10dlu:grow");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.border(new EmptyBorder(5, 5, 5, 5));
		
		
		builder.append("Codigo", codSKU);
		builder.nextLine();
		
		builder.append("Descricao", descricaoSKU);
		builder.nextLine();
		
		builder.append("Valor", valorSKU);
		builder.nextLine();
		
		builder.append("Paletizacao", paletizacaoSKU);
		builder.nextLine();
		
		builder.appendRow("5px");
		builder.nextLine();
		
		builder.nextColumn();
		builder.append(btCancelar);
		builder.append(btSalvar);
		
		
		frame.setTitle("Cadastro de SKU");
		frame.setSize(230,200);
		frame.setLayout(getLayout());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.add(builder.getPanel(), BorderLayout.CENTER);
		pack();
		frame.setVisible(true);
		
	}
	

	private void salvar() {
		
		if(descricaoSKU.getText().equals("") || valorSKU.getText().equals("") || paletizacaoSKU.getText().equals("")) {
			JOptionPane.showMessageDialog(
			        null, "Verifique os valores digitados", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
	
		try {
			sku = model.getBean();
			DatabaseDaoFactory.getSkuDAO().save(sku);
			JOptionPane.showMessageDialog(this, String.format("<html>SKU registrado com Sucesso! %s</html>", model.getBean().toString()));
		}
		catch(NullPointerException n) {
			JOptionPane.showMessageDialog(null, "Não aceito valores nulos");
		}
	
	}
	
	
	public synchronized static JFrame getInstance() {
		if (instance == null) {
			instance = new RegisterSKUUI();
		}
		return instance;
	}
	

}
