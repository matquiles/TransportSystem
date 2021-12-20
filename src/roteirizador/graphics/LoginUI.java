package roteirizador.graphics;


import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.layout.FormLayout;

import roteirizador.graphics.controller.ButtonAction;


@SuppressWarnings("serial")
public class LoginUI extends JFrame{
	
	
	public LoginUI() {
		
	}
	
	private static JFrame instance;
	static JFrame f = new JFrame();
	
	private JTextField loginDigitado;
	private JPasswordField senhaDigitada;
	private JButton button;
	private JLabel imagem;
	

	public void telaLogin() {
		initComponents();
		initLayout();
	}

	private void initComponents() {
		
		loginDigitado = new JTextField(10);
		
		
		senhaDigitada = new JPasswordField(10);
		senhaDigitada.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				 if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					ButtonAction action = new ButtonAction(loginDigitado, senhaDigitada);
					action.actionPerformed(null);
				 }
			}
		});
		
		button = new JButton("OK");
		button.setMnemonic(KeyEvent.VK_ENTER);
		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
		ButtonAction action = new ButtonAction(loginDigitado, senhaDigitada);
		button.addActionListener(action);
		
		
		try {
			BufferedImage read = ImageIO.read(new File("lua.png"));
			Image scaledInstance = read.getScaledInstance(211, 200, Image.SCALE_SMOOTH);
			Icon lua = new ImageIcon(scaledInstance);
			imagem = new JLabel(lua, SwingConstants.CENTER);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	private void initLayout() {
		FormLayout layout = new FormLayout("240px, 5px, pref,5px, 40dlu, 40dlu", "10px:grow(.5), 30px, 5px, 30px, pref, 10px:grow(.5)");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.border(new EmptyBorder(5, 5, 5, 5));
		
		builder.setRowSpan(6);
		builder.append(imagem);
		builder.nextLine();
		builder.nextColumn(2);
		builder.setRowSpan(1);

		builder.append("Matricula", loginDigitado,2);
		builder.nextLine(2);
		
		builder.nextColumn(2);
		builder.append("Senha", senhaDigitada,2);
		builder.nextLine();
		
		builder.nextColumn(5);

		builder.append(button);
		
		f.setTitle("Bem Vindo");
		f.setSize(510,300);
		f.setLayout(new BorderLayout());
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.add(builder.getPanel(), BorderLayout.CENTER);
		
		
		pack();
		f.setVisible(true);
	}

	
	public synchronized static JFrame getInstance() {
		if (instance == null) {
			instance = f;
		}
		return instance;
	}
	

}
