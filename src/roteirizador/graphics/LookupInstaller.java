package roteirizador.graphics;

import java.awt.event.FocusAdapter;
import java.util.function.Consumer;

import javax.swing.JTextField;

public class LookupInstaller {
	
	public static void install(JTextField component, Consumer<Object> consumer) {
		component.addActionListener(evt -> consumer.accept(evt));
		component.addFocusListener(new FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent e) {
				consumer.accept(e);
			};
		});
	}

}
