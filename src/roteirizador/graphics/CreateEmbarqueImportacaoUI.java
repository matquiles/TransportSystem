package roteirizador.graphics;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.layout.FormLayout;

import roteirizador.dao.DaoFactory;
import roteirizador.dao.database.DatabaseDaoFactory;
import roteirizador.domain.Embarque;
import roteirizador.domain.Pedido;



@SuppressWarnings("serial")
public class CreateEmbarqueImportacaoUI extends JFrame {
	
	PresentationModel<Embarque> modelEmbarque;
	PresentationModel<Pedido> modelPedido;
	
	private JFrame frame = new JFrame();
	private JTextField caminhoImportacao;
	private JButton btCriarEmbarques;
	private JButton btImportar;
	private ImageIcon pesquisa;
	private JTextArea progressoCriacao;
	
	
	public CreateEmbarqueImportacaoUI() {
		initModels();
		initComponents();
		initLayout();
	}


	private void initModels() {
		modelEmbarque = new PresentationModel<Embarque>();
		modelPedido = new PresentationModel<Pedido>();
		
	}


	private void initComponents() {
		
		caminhoImportacao = new JTextField();
		btCriarEmbarques = new JButton("OK");
		btCriarEmbarques.setEnabled(false);
		btCriarEmbarques.addActionListener(evt -> criarEmbarque());
		
		BufferedImage read;
		try {
			read = ImageIO.read(new File("pesquisa.png"));
			Image scaledInstance = read.getScaledInstance(20, 15, Image.SCALE_SMOOTH);
			pesquisa = new ImageIcon(scaledInstance);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		progressoCriacao = new JTextArea();
		progressoCriacao.setEditable(false);
		btImportar = new JButton();
		btImportar.setIcon(pesquisa);
		btImportar.addActionListener(evt -> {
			try {
				importar();
			} catch (NumberFormatException | ParseException | IOException e) {
				e.printStackTrace();
			}
			});
		
			
	}
		

	private void initLayout() {
		
		FormLayout layout = new FormLayout("pref, 5px, 200dlu, 10px, 30dlu", "pref, 12dlu, 15dlu, 120dlu");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		JScrollPane paneStatus = new JScrollPane(progressoCriacao);
		builder.border(new EmptyBorder(10, 10, 10, 10));
		
		builder.append("Arquivo", caminhoImportacao);
		builder.append(btImportar);
		builder.nextLine(2);
		builder.setRowSpan(2);
		builder.append(paneStatus, 3);
		builder.setRowSpan(1);
		builder.append(btCriarEmbarques);
		
		frame.setTitle("Criar Embarques");
		frame.setSize(600,350);
		frame.setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.add(builder.getPanel(), BorderLayout.CENTER);
		
		
		pack();
		frame.setVisible(true);
		
		
	}
	
	private void importar() throws NumberFormatException, ParseException, IOException {

		String caminho;

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
	
		chooser.setFileFilter(filter);

		int retorno = chooser.showOpenDialog(frame);

		if(retorno == JFileChooser.APPROVE_OPTION) {
			caminho = chooser.getSelectedFile().getAbsolutePath();

			caminhoImportacao.setText(caminho);
			btCriarEmbarques.setEnabled(true);

		} else {
			Object[] options = { "OK" };
			JOptionPane.showOptionDialog(null, "Selecione um arquivo .txt", "Tipo de Arquivo Inválido!",
			JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
			null, options, options[0]);

		}

	}

	private void criarEmbarque() {


		String path = caminhoImportacao.getText();


		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String line = br.readLine();
			String[] vect = line.split(",");
			Integer comparaSeNovoEmbarque = Integer.parseInt(vect[0]);
			Integer pedido = Integer.parseInt(vect[1]);
			String placa = (vect[2]);


			while (line != null) {

				boolean validar = true;
				Embarque embarque = new Embarque();
				embarque.setVeiculo(placa);

				while(validar == true ) {
					String lineAbaixo = br.readLine();
					if(lineAbaixo==null && line != null) {
						//EmbarqueDAO.getInstance().save(embarque);
						//line=null;
						//break;
					}else if (lineAbaixo==null && embarque == null) {
						line=null;
						break;

					} else if (lineAbaixo==null && embarque!=null){
						DatabaseDaoFactory.getEmbarqueDAO().save(embarque);	
						embarque.alocar();
						line=null;
						break;
					}

					progressoCriacao.append("\n**Pedido " + pedido + " inserido no embarque\n");

					System.out.println("Pedido " + pedido + " inserido no embarque\n**");
					embarque.addPedido(DatabaseDaoFactory.getOrderDAO().getPedido(pedido));

					try {
						String[] vect2 = lineAbaixo.split(",");
					}catch(NullPointerException n) {
						DatabaseDaoFactory.getEmbarqueDAO().save(embarque);
						progressoCriacao.append("\nEmbarque: " + embarque.getCodEmbarque() + 
								"\nUnidade: " + embarque.getUnidade() +
								"\nPlaca: " + embarque.getVeiculo() + "\n*********\n\n");

						embarque.alocar();
						line=null;
						break;
					}
					String[] vect2 = lineAbaixo.split(",");

					Integer comparaSeNovoEmbarqueNaLinhaAbaixo = Integer.parseInt(vect2[0]);


					if(comparaSeNovoEmbarque != comparaSeNovoEmbarqueNaLinhaAbaixo ||comparaSeNovoEmbarqueNaLinhaAbaixo == null ) {
						DatabaseDaoFactory.getEmbarqueDAO().save(embarque);
						progressoCriacao.append("\nEmbarque: " + embarque.getCodEmbarque() + 
								"\nUnidade: " + embarque.getUnidade() +
								"\nPlaca: " + embarque.getVeiculo() + "\n*********\n\n");
						embarque.alocar();
						line = lineAbaixo;
						vect = line.split(",");
						pedido = Integer.parseInt(vect[1]);
						comparaSeNovoEmbarque = Integer.parseInt(vect[0]);
						placa = (vect[2]);
						validar = false;
						break;

					} else if (comparaSeNovoEmbarque == comparaSeNovoEmbarqueNaLinhaAbaixo || comparaSeNovoEmbarque == null) {
						line = lineAbaixo;
						vect = line.split(",");
						pedido = Integer.parseInt(vect[1]);
						comparaSeNovoEmbarque = Integer.parseInt(vect[0]);
						placa = (vect[2]);
					}

				} 
			}	

		}
		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());

		}

	}


}
