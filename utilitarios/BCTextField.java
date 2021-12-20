package com.bullcontrol.client.components;

import java.awt.Graphics;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;
import javax.swing.text.Document;

import com.bullcontrol.client.components.swing.document.BCMaxLenghtSwingDocument;
import com.bullcontrol.client.components.swing.document.BCSwingDocumentStack;
import com.bullcontrol.client.components.swing.document.BCUpperCaseSwingDocument;
import com.bullcontrol.client.components.swing.listeners.EnterPorTabListener;
import com.bullcontrol.client.components.swing.listeners.EscPorShiftTabListener;
import com.bullcontrol.client.components.swing.listeners.ToolTipListener;
import com.bullcontrol.components.BullcontrolComponent;
import com.bullcontrol.components.BullcontrolComponentId;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.value.AbstractValueModel;

@SuppressWarnings("serial")
public class BCTextField extends JTextField implements BullcontrolComponent, ExplicavelComponent{

	private BullcontrolComponentId id;
	private BCSwingDocumentStack documentStack;
	private TextoExplicativoPainter textoExplicativoPainter;
	private String textoExplicativo;

	public BCTextField(BullcontrolComponentId id, AbstractValueModel model) {
		this(id, model, true, 0);
	}

	public BCTextField(BullcontrolComponentId id, AbstractValueModel model, boolean upperCase) 	{
		this(id, model, upperCase, 0);
	}

	public BCTextField(BullcontrolComponentId id, AbstractValueModel model, int maxLenght) {
		this(id, model, true, maxLenght);
	}
	
	public BCTextField(BullcontrolComponentId id, AbstractValueModel model, boolean upperCase, int maxLenght){
		this(id, model, upperCase, maxLenght, true);
	}

	public BCTextField(BullcontrolComponentId id, AbstractValueModel model, boolean upperCase, int maxLenght, boolean selectAllOnFocus){
		this(id, model, upperCase, maxLenght, selectAllOnFocus, null, false);
	}
	
	public BCTextField(BullcontrolComponentId id, AbstractValueModel model, boolean upperCase, int maxLenght, String defaultText){
		this(id, model, upperCase, maxLenght, true, defaultText, false);
	}

	public BCTextField(BullcontrolComponentId id, AbstractValueModel model, boolean upperCase, int maxLenght, boolean selectAllOnFocus, String textoExplicativo, boolean commitOnFocusLost){
		super();
		this.id = id;
		this.textoExplicativo = textoExplicativo;
		Bindings.bind(this, model, commitOnFocusLost);
		
		documentStack.addDocument(new BCMaxLenghtSwingDocument(maxLenght));
		if (upperCase){
			documentStack.addDocument(new BCUpperCaseSwingDocument());
		}

		model.addValueChangeListener(new ToolTipListener(this));
		
		addKeyListener(new EnterPorTabListener());
		addKeyListener(new EscPorShiftTabListener());
		addFocusListener(new BCComponentFocusListener());
		
		if (selectAllOnFocus){
			addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					selectAll();
				}
			});
		}
		
		
		textoExplicativoPainter = new TextoExplicativoPainter(this);

		
	}

	@Override
	protected Document createDefaultModel() {
		return this.documentStack = new BCSwingDocumentStack();
	}

	@Override
	public BullcontrolComponentId getId() {
		return id;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		try {
			super.paintComponent(g);
			if (textoExplicativoPainter != null && isEditable()){
				textoExplicativoPainter.paintTextoExplicativo(g);
			}
		} catch (Throwable e) {}
	}

	public String getTextoExplicativo() {
		return textoExplicativo;
	}

	public void setTextoExplicativo(String textoExplicativo) {
		this.textoExplicativo = textoExplicativo;
	}
	
	public void setEnabledAndFocusable(boolean enabled, boolean focusable) {
		setEnabled(enabled);
		setFocusable(focusable);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		setEditable(enabled);
	}
	
	public void setRealEnabled(boolean enabled){
		setEditable(enabled);
		setFocusable(enabled);
	}
	
	@Override
	public void encerrar() {}

}

