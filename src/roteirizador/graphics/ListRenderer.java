package roteirizador.graphics;

import java.awt.Component;
import java.util.function.Function;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

@SuppressWarnings("serial")
public class ListRenderer<T> extends DefaultListCellRenderer{
	
	private Function<T, Object> function;

	public ListRenderer(Function<T, Object> function) {
		this.function = function;
		
	}
	
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		if(value != null) {
			value = function.apply((T) value);
		}
		
		return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	}

}
